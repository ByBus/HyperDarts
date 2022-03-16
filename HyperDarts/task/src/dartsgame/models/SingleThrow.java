package dartsgame.models;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class SingleThrow {
    private int multiplier;
    private int sector;

    public boolean isDouble() {
        return multiplier == 2;
    }

    public int getValue() {
        return multiplier * sector;
    }
}
