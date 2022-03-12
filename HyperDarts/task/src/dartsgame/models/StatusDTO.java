package dartsgame.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatusDTO {
    private String status;

    public static class UnderConstruction extends StatusDTO {
        public UnderConstruction() {
            super("Under construction!");
        }
    }
}
