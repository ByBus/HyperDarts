package dartsgame.buiseness;

import dartsgame.models.dto.GameDTO;
import dartsgame.persistense.GameEntity;
import dartsgame.persistense.GameStatus;
import org.springframework.stereotype.Component;

@Component
public class GameMapper implements Mapper<GameEntity, GameDTO> {

    @Override
    public GameDTO map(GameEntity entity) {
        String status = entity.getGameStatus().lowerCaseName();
        if (entity.getGameStatus() == GameStatus.USER_WINS) {
            String winner = entity.getPlayerOneScores() < entity.getPlayerTwoScores() ?
                    entity.getPlayerOne() : entity.getPlayerTwo();
            status = winner + " wins!";
        }
        return new GameDTO(entity.getId(),
                entity.getPlayerOne(),
                entity.getPlayerTwo(),
                status,
                entity.getPlayerOneScores(),
                entity.getPlayerTwoScores(),
                entity.getTurn());
    }
}
