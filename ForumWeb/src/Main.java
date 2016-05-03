import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


//"jdbc:h2:mem:test"


public class Main {
    static HashMap<String, User> users = new HashMap<>();
    static ArrayList<Message> messages = new ArrayList<>();

    public static void main(String[] args) {
        Spark.init();
        addTestUsers();
        addTestMessages();

        Spark.get(
                "/",
                ((request, response) -> {


                    String replyId = request.queryParams("replyId");
                    Integer replyIdNum = -1;
                    if (replyId != null){
                        replyIdNum = Integer.valueOf(replyId);
                    }

                    ArrayList<Message> threads = new ArrayList<>();
                    for (Message message : messages) {
                        if (message.replyId == replyIdNum) {
                            threads.add(message);
                        }
                    }
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    HashMap m = new HashMap<>();
                    m.put("messages", threads);
                    m.put("userName", userName);
                    m.put("replyID", replyIdNum);
                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/login",
                ((request, response) -> {
                    String loginName = request.queryParams("loginName");
                    if (loginName == null){
                        throw new Exception("No username entered");
                    }

                    if (! users.containsKey(loginName)) {
                        users.put("loginName", new User(loginName));
                    }

                    Session session = request.session();
                    session.attribute("userName", loginName);

                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/create-message",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    if (userName == null){
                        throw new Exception("Not logged in");
                    }
                    String message = request.queryParams("messageText");
                    String replyId = request.queryParams("replyId");
                    if (message == null){
                        throw new Exception("query params?");
                    }
                    Integer replyIdNum = Integer.valueOf(replyId);
                    Message m = new Message(messages.size(), replyIdNum, userName, message);
                    messages.add(m);


                    response.redirect(request.headers("Referer"));
                    return "";
                })
        );
        Spark.post(
                "/logout",
                ((request, response) -> {
                    request.session().invalidate();
                    response.redirect("/");
                    return "";
                })
        );
    }

    static void addTestMessages(){
        messages.add(new Message(0, -1, "Alice", "Hello world"));
        messages.add(new Message(1, -1, "Bob", "This is another thread"));
        messages.add(new Message(2, 0, "Charlie", "You guys suck"));
        messages.add(new Message(3, 2, "Alice", "No you suck"));
    }

    static void addTestUsers(){
        users.put("Alice", new User ("Alice"));
        users.put("Bob", new User ("Bob"));
        users.put("Charlies", new User ("Charlie"));
    }
}
