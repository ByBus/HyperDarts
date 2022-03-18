package dartsgame.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GameMoveDTO {
    private long gameId;
    private int move;
    private String playerOne;
    private String playerTwo;
    private String gameStatus;
    private int playerOneScores;
    private int playerTwoScores;
    private String turn;
}
