package dartsgame.exception;

public class NotPlayersTurnException extends RuntimeException{
    public NotPlayersTurnException() {
        super("Wrong turn!");
    }
}
