package dartsgame.persistense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "game_move")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameMoveEntity {
    @Id
    @GeneratedValue
    private long id;

    private long gameId;
    private int move = 0;
    private String playerOne;
    private String playerTwo;
    private String gameStatus;
    private int playerOneScores;
    private int playerTwoScores;
    private String turn;
}
