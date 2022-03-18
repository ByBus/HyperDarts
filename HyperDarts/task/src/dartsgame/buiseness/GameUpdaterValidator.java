package dartsgame.buiseness;

import dartsgame.models.dto.GameUpdateDTO;
import dartsgame.persistense.GameEntity;
import dartsgame.persistense.GameStatus;
import javassist.NotFoundException;

import java.util.Objects;

public class GameUpdaterValidator implements Validator{
    private final GameUpdateDTO updater;
    private final GameEntity game;

    public GameUpdaterValidator(GameUpdateDTO updater,
                                GameEntity game) {
        this.updater = updater;
        this.game = game;
    }

    @Override
    public void validOrThrow() throws NotFoundException {
        if (game == null) {
           throw new NotFoundException("");
        }
        String newWinner = updater.getStatus().replaceAll(" .*", "");
        if (!newWinner.equals("Nobody")) {
            if (!Objects.equals(game.getPlayerOne(), newWinner) && !Objects.equals(game.getPlayerTwo(), newWinner)) {
                throw new IllegalArgumentException("Wrong status!");
            }
        }
        if (game.getGameStatus() == GameStatus.USER_WINS) {
            throw new IllegalArgumentException("The game is already over!");
        }
    }
}
