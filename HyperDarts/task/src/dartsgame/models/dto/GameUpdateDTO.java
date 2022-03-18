package dartsgame.models.dto;

import dartsgame.persistense.GameStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@NoArgsConstructor
@Data
public class GameUpdateDTO {
    @NotNull(message = "No id")
    private int gameId;
    @NotNull(message = "Wrong status!")
    @Pattern(regexp = "\\w+ wins!", message = "Wrong status!")
    private String status;

    public GameStatus getNewStatus() {
        return Objects.equals(status, "Nobody wins!") ? GameStatus.NOBODY_WINS : GameStatus.USER_WINS;
    }
}
