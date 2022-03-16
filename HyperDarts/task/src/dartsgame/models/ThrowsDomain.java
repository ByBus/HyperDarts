package dartsgame.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ThrowsDomain {
    private List<SingleThrow> singleTrows = new ArrayList<>();

    public ThrowsDomain(List<SingleThrow> singleThrows) {
        this.singleTrows.addAll(singleThrows);
    }

    public int getTotalPoints() {
        return singleTrows.stream()
                .mapToInt(SingleThrow::getValue)
                .sum();
    }
}
