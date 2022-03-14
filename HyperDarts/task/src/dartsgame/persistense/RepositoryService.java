package dartsgame.persistense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {
    private GameRepository gameRepository;

    @Autowired
    public RepositoryService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameEntity> getUnfinishedGames(String userName) {
        return gameRepository.findAllByPlayerOneOrPlayerTwoAndGameStatusNotOrderByIdDesc(
                userName,
                userName,
                GameStatus.USER_WINS
        );
    }

    public GameEntity create(GameEntity newGame) {
        return gameRepository.save(newGame);
    }

    public GameEntity update(GameEntity newGame) {
        return gameRepository.save(newGame);
    }

    public List<GameEntity> getAllGames() {
        return gameRepository.findAllByOrderByIdDesc();
    }

    public GameEntity getGame(long id) {
        return gameRepository.findById(id).orElse(null);
    }
}
