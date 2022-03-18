package dartsgame.buiseness;

import dartsgame.persistense.GameEntity;
import dartsgame.persistense.GameMoveEntity;
import dartsgame.persistense.GameStatus;
import javassist.NotFoundException;

public class GameRevertValidator implements Validator{
    private final GameEntity oldGame;
    private final GameMoveEntity move;
    private final GameMoveEntity lastMove;

    public GameRevertValidator(GameEntity oldGame, GameMoveEntity move, GameMoveEntity lastMove) {
        this.oldGame = oldGame;
        this.move = move;
        this.lastMove = lastMove;
    }

    @Override
    public void validOrThrow() throws NotFoundException {
        if (oldGame == null) {
            throw new NotFoundException("Game not found!");
        }
        if (move == null) {
            throw new IllegalArgumentException("Move not found!");
        }
        if (lastMove.equals(move)) {
            throw new IllegalArgumentException("There is nothing to revert!");
        }
        if (oldGame.getGameStatus() == GameStatus.USER_WINS) {
            throw new IllegalArgumentException("The game is over!");
        }
    }
}
