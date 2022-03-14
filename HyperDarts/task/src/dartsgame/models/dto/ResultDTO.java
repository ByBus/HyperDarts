package dartsgame.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultDTO {
    private String result;

    public static class UnderConstruction extends ResultDTO {
        public UnderConstruction() {
            super("Under construction!");
        }
    }

    public static class HasUnfinishedGames extends ResultDTO {
        public HasUnfinishedGames() {
            super("You have an unfinished game!");
        }
    }
}
