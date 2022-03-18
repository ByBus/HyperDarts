package dartsgame.buiseness;

import dartsgame.persistense.GameEntity;
import dartsgame.persistense.GameMoveEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameMoveToEntityMapper implements Mapper<GameMoveEntity, GameEntity> {

    private final StatusRestorer statusRestorer;

    @Autowired
    public GameMoveToEntityMapper(StatusRestorer statusRestorer) {
        this.statusRestorer = statusRestorer;
    }

    @Override
    public GameEntity map(GameMoveEntity move) {
        return GameEntity.builder()
                .id(move.getGameId())
                .playerOne(move.getPlayerOne())
                .playerTwo(move.getPlayerTwo())
                .gameStatus(statusRestorer.restore(move.getGameStatus()))
                .playerOneScores(move.getPlayerOneScores())
                .playerTwoScores(move.getPlayerTwoScores())
                .turn(move.getTurn())
                .build();
    }
}
