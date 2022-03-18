package dartsgame.buiseness;

import dartsgame.persistense.GameEntity;
import dartsgame.persistense.GameStatus;
import org.springframework.stereotype.Component;

@Component
public class StatusFormatter {

    public String format(GameEntity game) {
        String status = game.getGameStatus().getDescription();
        if (game.getGameStatus() == GameStatus.USER_WINS) {
            status = String.format(
                    status,
                    game.getPlayerOneScores() == 0 ? game.getPlayerOne() : game.getPlayerTwo()
            );
        }
        return status;
    }
}
