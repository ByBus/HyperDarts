package dartsgame.buiseness;

import dartsgame.persistense.GameStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StatusRestorer {
    public GameStatus restore(String statusDescription) {
        return Arrays.stream(GameStatus.values())
                .filter(value -> value.getDescription().equals(statusDescription))
                .findFirst()
                .orElse(GameStatus.USER_WINS);
    }
}
