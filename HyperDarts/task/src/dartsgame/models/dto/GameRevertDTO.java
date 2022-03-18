package dartsgame.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class GameRevertDTO {
    @NotNull(message = "No id")
    @Min(value = 0, message = "Wrong id!")
    private long gameId;
    @NotNull(message = "No move!")
    @Min(value = 0, message = "Wrong move!")
    private int move;
}
