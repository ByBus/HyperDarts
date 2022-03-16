package dartsgame.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ThrowsDTO {
    @Pattern(regexp = "(none)|([12]:25)|([1-3]:20)|([1-3]:[1][0-9])|([1-3]:[0-9])", message = "Wrong throws!")
    private String first;
    @Pattern(regexp = "(none)|([12]:25)|([1-3]:20)|([1-3]:[1][0-9])|([1-3]:[0-9])", message = "Wrong throws!")
    private String second;
    @Pattern(regexp = "(none)|([12]:25)|([1-3]:20)|([1-3]:[1][0-9])|([1-3]:[0-9])", message = "Wrong throws!")
    private String third;
}
