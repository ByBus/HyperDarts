package dartsgame.buiseness;

import dartsgame.persistense.GameEntity;
import dartsgame.persistense.GameMoveEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameEntityToMoveMapper implements Mapper<GameEntity, GameMoveEntity> {
    private final StatusFormatter statusFormatter;

    @Autowired
    public GameEntityToMoveMapper(StatusFormatter statusFormatter) {
        this.statusFormatter = statusFormatter;
    }

    @Override
    public GameMoveEntity map(GameEntity entity) {
        String status = statusFormatter.format(entity);
        return GameMoveEntity.builder()
                .gameId(entity.getId())
                .playerOne(entity.getPlayerOne())
                .playerTwo(entity.getPlayerTwo())
                .gameStatus(status)
                .playerOneScores(entity.getPlayerOneScores())
                .playerTwoScores(entity.getPlayerTwoScores())
                .turn(entity.getTurn())
                .build();
    }
}
