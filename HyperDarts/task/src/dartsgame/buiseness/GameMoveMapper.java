package dartsgame.buiseness;

import dartsgame.models.dto.GameMoveDTO;
import dartsgame.persistense.GameMoveEntity;
import org.springframework.stereotype.Component;

@Component
public class GameMoveMapper implements Mapper<GameMoveEntity, GameMoveDTO> {
    @Override
    public GameMoveDTO map(GameMoveEntity entity) {
        return new GameMoveDTO(
                entity.getGameId(),
                entity.getMove(),
                entity.getPlayerOne(),
                entity.getPlayerTwo(),
                entity.getGameStatus(),
                entity.getPlayerOneScores(),
                entity.getPlayerTwoScores(),
                entity.getTurn()
        );
    }
}
