package dartsgame.controller;

import dartsgame.buiseness.GameRevertValidator;
import dartsgame.buiseness.GameUpdaterValidator;
import dartsgame.buiseness.Mapper;
import dartsgame.models.dto.GameDTO;
import dartsgame.models.dto.GameRevertDTO;
import dartsgame.models.dto.GameUpdateDTO;
import dartsgame.models.dto.ResultDTO;
import dartsgame.persistense.GameEntity;
import dartsgame.persistense.GameMoveEntity;
import dartsgame.persistense.RepositoryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RefereeApiController {
    private final RepositoryService repository;
    private final Mapper<GameEntity, GameDTO> gameMapper;
    private final Mapper<GameMoveEntity, GameEntity> moveMapper;

    @Autowired
    public RefereeApiController(RepositoryService repository,
                                Mapper<GameEntity, GameDTO> gameMapper,
                                Mapper<GameMoveEntity, GameEntity> moveMapper) {
        this.repository = repository;
        this.gameMapper = gameMapper;
        this.moveMapper = moveMapper;
    }

    @PutMapping("/api/game/cancel")
    private ResponseEntity<?> cancelGame(@RequestBody GameUpdateDTO gameUpdate) {
        GameEntity game = repository.getGame(gameUpdate.getGameId());
        try {
            new GameUpdaterValidator(gameUpdate, game).validOrThrow();
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new ResultDTO.GameNotFound(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResultDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        game.setGameStatus(gameUpdate.getNewStatus());
        game = repository.update(game);
        return ResponseEntity.ok(gameMapper.map(game));
    }

    @PutMapping("/api/game/revert")
    private ResponseEntity<?> revertGameToMove(@RequestBody @Valid GameRevertDTO gameRevert) {
        GameEntity oldGame = repository.getGame(gameRevert.getGameId());
        GameMoveEntity move = repository.getMove(gameRevert.getGameId(), gameRevert.getMove());
        GameMoveEntity lastMove = repository.getLastGameMove(gameRevert.getGameId());
        try {
            new GameRevertValidator(oldGame, move, lastMove).validOrThrow();
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new ResultDTO.GameNotFound(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResultDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        GameEntity game = moveMapper.map(move);
        repository.update(game);
        repository.deleteMovesAfter(move);
        return ResponseEntity.ok(gameMapper.map(game));
    }
}
