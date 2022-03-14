package dartsgame.persistense;

public enum GameStatus {
    CREATED,
    STARTED,
    PLAYING,
    USER_WINS;

    public String lowerCaseName() {
        return name()
                .toLowerCase()
                .replace("_", "");
    }
}
