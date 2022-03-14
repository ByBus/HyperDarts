package dartsgame.buiseness;

import javassist.NotFoundException;

public interface Validator {
    void validOrThrow() throws NotFoundException;
}
