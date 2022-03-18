package dartsgame.controller;

import dartsgame.buiseness.GameMaster;
import dartsgame.buiseness.GameValidator;
import dartsgame.buiseness.Mapper;
import dartsgame.buiseness.ScoreValidator;
import dartsgame.models.dto.*;
import dartsgame.persistense.GameEntity;
import dartsgame.persistense.GameMoveEntity;
import dartsgame.persistense.GameStatus;
import dartsgame.persistense.RepositoryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestApiController {
    private final RepositoryService repository;
    private final Mapper<GameEntity, GameDTO> gameMapper;
    private final Mapper<GameEntity, GameMoveEntity> gameToMoveEntityMapper;
    private final GameMaster gameMaster;
    private final Mapper<GameMoveEntity, GameMoveDTO> gameMoveMapper;

    @Autowired
    public RestApiController(RepositoryService repository,
                             Mapper<GameEntity, GameDTO> gameMapper,
                             Mapper<GameMoveEntity, GameMoveDTO> gameMoveMapper,
                             Mapper<GameEntity, GameMoveEntity> gameToMoveEntityMapper,
                             GameMaster gameMaster) {
        this.repository = repository;
        this.gameMapper = gameMapper;
        this.gameMoveMapper = gameMoveMapper;
        this.gameToMoveEntityMapper = gameToMoveEntityMapper;
        this.gameMaster = gameMaster;
    }

    @PostMapping("/api/game/create")
    private ResponseEntity<?> createGame(Authentication authentication,
                                         @RequestBody ScoreDTO score) {
        String authenticatedUser = authentication.getName();
        try {
            new ScoreValidator(score.getTargetScore()).validOrThrow();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResultDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        List<GameEntity> unfinishedGames = repository.getUnfinishedGames(authenticatedUser);
        if (!unfinishedGames.isEmpty()) {
            return new ResponseEntity<>(new ResultDTO.HasUnfinishedGames(), HttpStatus.BAD_REQUEST);
        }
        GameEntity newGame = repository.create(authenticatedUser, score.getTargetScore());
        return ResponseEntity.ok(gameMapper.map(newGame));
    }

    @GetMapping("/api/game/list")
    private ResponseEntity<?> getGamesList(Authentication authentication) {
        List<GameEntity> games = repository.getAllGames();
        if (games.isEmpty()) {
            return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
        }
        List<GameDTO> gamesDTOs = games.stream()
                .map(gameMapper::map)
                .collect(Collectors.toList());
        return ResponseEntity.ok(gamesDTOs);
    }

    @GetMapping("/api/game/join/{id}")
    private ResponseEntity<?> joinGame(Authentication authentication,
                                       @PathVariable long id) {
        String user = authentication.getName();
        GameEntity game = repository.getGame(id);
        try {
            new GameValidator(user, game, repository).validOrThrow();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResultDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new ResultDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
        game.setGameStatus(GameStatus.STARTED);
        game.setPlayerTwo(user);
        repository.update(game);
        repository.create(gameToMoveEntityMapper.map(game));
        return ResponseEntity.ok(gameMapper.map(game));
    }

    @GetMapping("/api/game/status")
    private ResponseEntity<?> getGameStatus(Authentication authentication) {
        List<GameEntity> games = repository.getUnfinishedGames(authentication.getName());
        GameEntity lastGame = repository.getLastGame(authentication.getName());
        if (games.isEmpty() && lastGame == null) {
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }
        lastGame = games.isEmpty() ? lastGame : games.get(0);
        return ResponseEntity.ok(gameMapper.map(lastGame));
    }

    @PostMapping("/api/game/throws")
    private ResponseEntity<?> setThrows(Authentication authentication,
                                        @RequestBody @Valid ThrowsDTO dartThrows) {
        String currentUser = authentication.getName();
        gameMaster.setUser(currentUser);
        try {
            gameMaster.setThrows(dartThrows);
            GameEntity game = gameMaster.updateGame();
            repository.create(gameToMoveEntityMapper.map(game));
            return new ResponseEntity<>(gameMapper.map(game), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new ResultDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResultDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("api/history/{gameId}")
    private ResponseEntity<?> getGameHistory(@PathVariable long gameId) {
        if (gameId < 0) {
            return new ResponseEntity<>(new ResultDTO.WrongRequest(), HttpStatus.BAD_REQUEST);
        }
        List<GameMoveEntity> history = repository.getGameHistory(gameId);
        if (history.isEmpty()) {
            return new ResponseEntity<>(new ResultDTO.GameNotFound(), HttpStatus.NOT_FOUND);
        }
        List<GameMoveDTO> historyDTO = history.stream()
                .map(gameMoveMapper::map)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historyDTO);
    }
}
