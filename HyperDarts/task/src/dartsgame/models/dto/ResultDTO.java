package dartsgame.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultDTO {
    private String result;

    public static class HasUnfinishedGames extends ResultDTO {
        public HasUnfinishedGames() {
            super("You have an unfinished game!");
        }
    }

    public static class GameNotFound extends ResultDTO {
        public GameNotFound() {
            super("Game not found!");
        }
    }

    public static class WrongRequest extends ResultDTO {
        public WrongRequest() {
            super("Wrong request!");
        }
    }
}
