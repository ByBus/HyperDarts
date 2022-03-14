package dartsgame.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameDTO {
    private long gameId;
    private String playerOne;
    private String playerTwo;
    private String gameStatus;
    private int playerOneScores;
    private int playerTwoScores;
    private String turn;
}
