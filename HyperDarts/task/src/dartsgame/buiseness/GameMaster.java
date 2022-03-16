package dartsgame.buiseness;

import dartsgame.exception.ExtraPointsException;
import dartsgame.exception.NotPlayersTurnException;
import dartsgame.models.SingleThrow;
import dartsgame.models.ThrowsDomain;
import dartsgame.models.dto.ThrowsDTO;
import dartsgame.persistense.GameEntity;
import dartsgame.persistense.GameStatus;
import dartsgame.persistense.RepositoryService;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Component
public class GameMaster {
    private static int PLAYER_ONE = 1;
    private static int PLAYER_TWO = 2;

    private final RepositoryService repository;
    private final Mapper<ThrowsDTO, ThrowsDomain> throwsMapper;
    private String user = null;
    private ThrowsDomain dartThrows = null;

    @Autowired
    public GameMaster(RepositoryService repository,
                      Mapper<ThrowsDTO, ThrowsDomain> throwsMapper) {
        this.repository = repository;
        this.throwsMapper = throwsMapper;
    }

    public void setThrows(ThrowsDTO dartThrowsDTO) {
        this.dartThrows = throwsMapper.map(dartThrowsDTO);
    }

    public GameEntity updateGame() throws NotFoundException {
        GameEntity currentGame = repository.getCurrentGame(user);
        if (currentGame == null) {
            throw new NotFoundException("There are no games available!");
        }
        if (!Objects.equals(currentGame.getTurn(), user)) {
            throw new NotPlayersTurnException();
        }
        int currentPlayer = currentGame.getPlayerOne().equals(user) ? PLAYER_ONE : PLAYER_TWO;
        return updateStats(currentGame, dartThrows, currentPlayer);
    }

    private GameEntity updateStats(GameEntity game,
                                   ThrowsDomain dartThrows,
                                   int player) {
        int throwsPoints = dartThrows.getTotalPoints();

        int scores = game.getPlayerTwoScores();
        int scoreRemains = scores - throwsPoints;
        String nextPlayer = game.getPlayerOne();

        if (player == PLAYER_ONE) {
            scores = game.getPlayerOneScores();
            scoreRemains = scores - throwsPoints;
            nextPlayer = game.getPlayerTwo();
        }
        game.setGameStatus(GameStatus.PLAYING);
        boolean isVictory = checkVictory(dartThrows, scores);
        if (isVictory) {
            scoreRemains = 0;
            game.setGameStatus(GameStatus.USER_WINS);
        } else {
            game.setTurn(nextPlayer);
        }
        if (!isVictory && scoreRemains == 0) {
            scoreRemains = scores;
        }
        if (scoreRemains >= 0 && scoreRemains != 1) {
            if (player == PLAYER_ONE) {
                game.setPlayerOneScores(scoreRemains);
            } else {
                game.setPlayerTwoScores(scoreRemains);
            }
        }
        if (checkBustExtraThrows(dartThrows, scores)) {
            throw new ExtraPointsException();
        }
        return repository.update(game);
    }

    private boolean checkVictory(ThrowsDomain dartThrows, int score) {
        boolean isVictory = false;
        List<SingleThrow> points = dartThrows.getSingleTrows();
        for (SingleThrow st : points) {
            score -= st.getValue();
            if (score == 0 && st.isDouble()) { // Game must be won with throw into double sector
                isVictory = true;
                break;
            }
        }
        return isVictory;
    }

    private boolean checkBustExtraThrows(ThrowsDomain dartThrows, int score) {
        boolean isExtraThrows = false;
        List<SingleThrow> points = dartThrows.getSingleTrows();
        int lastIndex = points.size() - 1;
        for (int i = 0; i <= lastIndex; i++) {
            int p = points.get(i).getValue();
            score -= p;
            if (score <= 1 && i != lastIndex) {
                isExtraThrows = true;
            }
        }
        return isExtraThrows;
    }

}
