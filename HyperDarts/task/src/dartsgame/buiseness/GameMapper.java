package dartsgame.buiseness;

import dartsgame.models.dto.GameDTO;
import dartsgame.persistense.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameMapper implements Mapper<GameEntity, GameDTO> {
    private final StatusFormatter statusFormatter;

    @Autowired
    public GameMapper(StatusFormatter statusFormatter) {
        this.statusFormatter = statusFormatter;
    }

    @Override
    public GameDTO map(GameEntity entity) {
        String status = statusFormatter.format(entity);
        return new GameDTO(entity.getId(),
                entity.getPlayerOne(),
                entity.getPlayerTwo(),
                status,
                entity.getPlayerOneScores(),
                entity.getPlayerTwoScores(),
                entity.getTurn());
    }
}
