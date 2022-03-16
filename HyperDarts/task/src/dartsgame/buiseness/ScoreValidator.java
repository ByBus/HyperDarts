package dartsgame.buiseness;

import java.util.List;

public class ScoreValidator implements Validator {
    private final int score;
    private final List<Integer> correctTargetScores = List.of(101, 301, 501);

    public ScoreValidator(int score) {
        this.score = score;
    }

    @Override
    public void validOrThrow() {
        if (!correctTargetScores.contains(score)) {
            throw new IllegalArgumentException("Wrong target score!");
        }
    }
}
