package dartsgame.buiseness;

import dartsgame.models.SingleThrow;
import dartsgame.models.ThrowsDomain;
import dartsgame.models.dto.ThrowsDTO;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ThrowsMapperTest {

    @Test
    public void mapCorrect() {
        Mapper<ThrowsDTO, ThrowsDomain> mapper = new ThrowsMapper();
        ThrowsDTO throwsDTO = new ThrowsDTO("3:20", "1:11", "1:5");
        ThrowsDomain expected = new ThrowsDomain(List.of(
                new SingleThrow(3, 20),
                new SingleThrow(1, 11),
                new SingleThrow(1, 5))
        );
        ThrowsDomain actual = mapper.map(throwsDTO);
        assertEquals(expected, actual);

        throwsDTO.setFirst("1:10");
        throwsDTO.setSecond("1:9");
        throwsDTO.setThird("none");
        expected = new ThrowsDomain(List.of(
                new SingleThrow(1, 10),
                new SingleThrow(1, 9))
        );
        actual = mapper.map(throwsDTO);
        assertEquals(expected, actual);

        throwsDTO.setFirst("3:17");
        throwsDTO.setSecond("3:20");
        throwsDTO.setThird("3:10");
        expected = new ThrowsDomain(List.of(
                new SingleThrow(3, 17),
                new SingleThrow(3, 20),
                new SingleThrow(3, 10))
        );
        actual = mapper.map(throwsDTO);
        assertEquals(expected, actual);
    }

    @Test
    public void mapNotCorrect() {
        Mapper<ThrowsDTO, ThrowsDomain> mapper = new ThrowsMapper();
        ThrowsDTO throwsDTO = new ThrowsDTO("1:5", "2:10", "none");
        ThrowsDomain expected  = new ThrowsDomain(List.of(
                new SingleThrow(1, 5),
                new SingleThrow(1, 10),
                new SingleThrow(0, 10))
        );
        ThrowsDomain actual = mapper.map(throwsDTO);
        assertNotEquals(expected, actual);

        throwsDTO.setFirst("1:10");
        throwsDTO.setSecond("1:9");
        throwsDTO.setThird("none");
        expected = new ThrowsDomain(List.of());
        actual = mapper.map(throwsDTO);
        assertNotEquals(expected, actual);
    }
}