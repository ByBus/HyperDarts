package dartsgame.buiseness;

import dartsgame.persistense.GameEntity;
import dartsgame.persistense.GameStatus;
import dartsgame.persistense.RepositoryService;
import javassist.NotFoundException;

import java.util.List;

public class GameValidator implements Validator {
    private final RepositoryService repository;
    private final String user;
    private final GameEntity game;

    public GameValidator(String user,
                         GameEntity game,
                         RepositoryService repository) {
        this.user = user;
        this.game = game;
        this.repository = repository;
    }

    @Override
    public void validOrThrow() throws NotFoundException {
        if (game == null) {
            throw new NotFoundException("Game not found!");
        }
        if (game.getPlayerOne().equals(user)) {
            throw new IllegalArgumentException("You can't play alone!");
        }
        if (game.getGameStatus() != GameStatus.CREATED) {
            throw new IllegalArgumentException("You can't join the game!");
        }
        List<GameEntity> unfinishedGames = repository.getUnfinishedGames(user);
        if (!unfinishedGames.isEmpty()) {
            throw new IllegalArgumentException("You have an unfinished game!");
        }
    }
}
