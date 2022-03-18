package dartsgame.persistense;

public enum GameStatus {
    CREATED("created"),
    STARTED("started"),
    PLAYING("playing"),
    USER_WINS("%s wins!"),
    NOBODY_WINS("Nobody wins!");

    private final String description;

    GameStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
