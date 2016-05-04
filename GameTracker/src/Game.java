/**
 * Created by Brittany on 4/19/16.
 */
public class Game {
    Integer id;
    String game;
    String genre;
    String platform;
    Integer releaseYear;

    public Game(String game, String genre, String platform, Integer releaseYear) {
        this.game = game;
        this.genre = genre;
        this.platform = platform;
        this.releaseYear = releaseYear;
    }
}
