package dartsgame.persistense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RepositoryService {
    private final GameRepository gameRepository;
    private final GameMoveRepository moveRepository;

    @Autowired
    public RepositoryService(GameRepository gameRepository,
                             GameMoveRepository moveRepository) {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
    }

    public List<GameEntity> getUnfinishedGames(String userName) {
        return gameRepository.findAllByPlayerOneAndGameStatusNotOrPlayerTwoAndGameStatusNotOrderByIdDesc(
                userName,
                GameStatus.USER_WINS,
                userName,
                GameStatus.USER_WINS
        );
    }

    public GameEntity create(String authenticatedUser, int score) {
        GameEntity newGame = GameEntity.builder()
                .gameStatus(GameStatus.CREATED)
                .playerOne(authenticatedUser)
                .playerTwo("")
                .playerOneScores(score)
                .playerTwoScores(score)
                .turn(authenticatedUser)
                .build();
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

    public GameEntity getCurrentGame(String currentUser) {
        return gameRepository.findByPlayerOneAndGameStatusInOrPlayerTwoAndGameStatusIn(
                currentUser,
                List.of(GameStatus.STARTED, GameStatus.PLAYING),
                currentUser,
                List.of(GameStatus.STARTED, GameStatus.PLAYING)
        );
    }

    public GameEntity getLastGame(String currentUser) {
        return gameRepository.findFirstByPlayerOneAndGameStatusInOrPlayerTwoAndGameStatusInOrderByIdDesc(
                currentUser,
                List.of(GameStatus.USER_WINS, GameStatus.NOBODY_WINS),
                currentUser,
                List.of(GameStatus.USER_WINS, GameStatus.NOBODY_WINS)
        );
    }

    public boolean isGameExist(long id) {
        return gameRepository.existsById(id);
    }

    public List<GameMoveEntity> getGameHistory(long gameId) {
        return moveRepository.findAllByGameId(gameId);
    }

    public GameMoveEntity getLastGameMove(long gameId) {
        return moveRepository.findTopByGameIdOrderByMoveDesc(gameId);
    }

    public GameMoveEntity create(GameMoveEntity gameMove) {
        GameMoveEntity lastMove = getLastGameMove(gameMove.getGameId());
        int nextMove = lastMove == null ? 0 : lastMove.getMove() + 1;
        gameMove.setMove(nextMove);
        return moveRepository.save(gameMove);
    }

    public GameMoveEntity getMove(long gameId, int move) {
        return moveRepository.findByGameIdAndMove(gameId, move);
    }

    @Transactional
    public void deleteMovesAfter(GameMoveEntity move) {
        moveRepository.deleteAllByGameIdAndMoveGreaterThan(move.getGameId(), move.getMove());
    }
}
