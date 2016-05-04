import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static HashMap<String, User> users = new HashMap<>();
    static Connection conn;

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS games (id IDENTITY, game_name VARCHAR, game_genre VARCHAR, " +
                "game_platform VARCHAR, game_year INT )");

        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "login.html");
                    } else {
                        ArrayList<Game> games = selectGames();
                        return new ModelAndView(games, "home.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    User user = users.get(name);
                    if (user == null) {
                        users.put(name, new User(name));
                    }

                    Session session = request.session();
                    session.attribute("userName", name);

                    response.redirect("/");

                    return "";
                })
        );
        Spark.post(
                "/create-game",
                ((request, response) -> {

                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    if (user == null) {
                        throw new Exception("User is not logged in");
                    }
                    String gameName = request.queryParams("gameName");
                    String gameGenre = request.queryParams("gameGenre");
                    String gamePlatform = request.queryParams("gamePlatform");
                    Integer gameYear = Integer.parseInt(request.queryParams("gameYear"));

                    Main.insertGame(Main.conn, gameName, gameGenre, gamePlatform, gameYear);

                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/delete-game",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    if (user == null) {
                        throw new Exception("User is not logged in");
                    }
                    Integer gameId = Integer.parseInt(request.queryParams("gameId")) -1;
                    deleteGame(conn, gameId);
                    response.redirect("/");
                    return "";
                })
        );
    }

        public static void insertGame(Connection conn, String gameName, String gameGenre, String gamePlatform, Integer gameYear)throws SQLException{
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO games VALUES (null, ?, ?, ?, ?)");
            stmt.setString(1, gameName);
            stmt.setString(2, gameGenre);
            stmt.setString(3, gamePlatform);
            stmt.setInt(4, gameYear);
            stmt.execute();
    }

    public static void deleteGame(Connection conn, Integer id)throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM games WHERE id = ?");
        stmt.setInt(1, id);
    }

    public static ArrayList<Game> selectGames() throws SQLException{
        ArrayList<Game> games = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM games");
        ResultSet results = stmt.executeQuery();
        while (results.next()){
            String gameName = results.getString("game_name");
            String gameGenre = results.getString("game_genre");
            String gamePlatform = results.getString("game_platform");
            Integer gameYear = results.getInt("game_year");
            games.add(new Game(gameName, gameGenre, gamePlatform, gameYear));
        }
        return games;
    }
}
