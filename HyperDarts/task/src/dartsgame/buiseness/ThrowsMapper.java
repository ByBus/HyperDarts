package dartsgame.buiseness;

import dartsgame.models.SingleThrow;
import dartsgame.models.ThrowsDomain;
import dartsgame.models.dto.ThrowsDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ThrowsMapper implements Mapper<ThrowsDTO, ThrowsDomain> {
    @Override
    public ThrowsDomain map(ThrowsDTO entity) {
        return new ThrowsDomain(preparePoints(entity.getFirst(), entity.getSecond(), entity.getThird()));
    }

    private List<SingleThrow> preparePoints(String... dartThrows) {
        return Arrays.stream(dartThrows)
                .filter(t -> !"none".equals(t))
                .map(points -> Arrays.stream(points.split(":"))
                        .mapToInt(Integer::valueOf)
                        .toArray())
                .map(a -> new SingleThrow(a[0], a[1]))
                .collect(Collectors.toList());
    }
}
