package dartsgame.persistense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "game")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameEntity {
    @Id
    @GeneratedValue
    private long id;

    private String playerOne;
    private String playerTwo;
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;
    private int playerOneScores;
    private int playerTwoScores;
    private String turn;
}
