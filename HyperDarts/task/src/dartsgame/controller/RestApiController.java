package dartsgame.controller;

import dartsgame.buiseness.GameMaster;
import dartsgame.buiseness.GameValidator;
import dartsgame.buiseness.Mapper;
import dartsgame.buiseness.ScoreValidator;
import dartsgame.models.dto.GameDTO;
import dartsgame.models.dto.ScoreDTO;
import dartsgame.models.dto.ResultDTO;
import dartsgame.models.dto.ThrowsDTO;
import dartsgame.persistense.GameEntity;
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
    private final GameMaster gameMaster;

    @Autowired
    public RestApiController(RepositoryService repository,
                             Mapper<GameEntity, GameDTO> gameMapper,
                             GameMaster gameMaster) {
        this.repository = repository;
        this.gameMapper = gameMapper;
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
        GameEntity newGame = GameEntity.builder()
                .gameStatus(GameStatus.CREATED)
                .playerOne(authenticatedUser)
                .playerTwo("")
                .playerOneScores(score.getTargetScore())
                .playerTwoScores(score.getTargetScore())
                .turn(authenticatedUser)
                .build();
        newGame = repository.create(newGame);
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
        return new ResponseEntity<>(gamesDTOs, HttpStatus.OK);
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
        return new ResponseEntity<>(gameMapper.map(game), HttpStatus.OK);
    }

    @GetMapping("/api/game/status")
    private ResponseEntity<?> getGameStatus(Authentication authentication) {
        List<GameEntity> games = repository.getUnfinishedGames(authentication.getName());
        GameEntity lastGame = repository.getLastGame(authentication.getName());
        if (games.isEmpty() && lastGame == null) {
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }
        lastGame = games.isEmpty() ? lastGame : games.get(0);
        return new ResponseEntity<>(gameMapper.map(lastGame), HttpStatus.OK);
    }

    @PostMapping("/api/game/throws")
    private ResponseEntity<?> setThrows(Authentication authentication,
                                @RequestBody @Valid ThrowsDTO dartThrows) {
        String currentUser = authentication.getName();
        gameMaster.setUser(currentUser);
        try {
            gameMaster.setThrows(dartThrows);
            GameEntity game = gameMaster.updateGame();
            return new ResponseEntity<>(gameMapper.map(game), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new ResultDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ResultDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
