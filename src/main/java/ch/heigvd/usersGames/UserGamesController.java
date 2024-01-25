package ch.heigvd.usersGames;

import ch.heigvd.games.Game;
import ch.heigvd.users.User;
import io.javalin.http.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserGamesController {

    private final ConcurrentHashMap<Integer, User> users;
    private final ConcurrentHashMap<Integer, Game> games;
    private final ArrayList<UserGame> usersGames;
    private final AtomicInteger gameId = new AtomicInteger();
    private final AtomicInteger userId = new AtomicInteger();

    public UserGamesController(ConcurrentHashMap<Integer, User> users,
                               ConcurrentHashMap<Integer, Game> games,
                               ArrayList<UserGame> usersGames) {
        this.users = users;
        this.games = games;
        this.usersGames = usersGames;
    }

    private User checkCookie(Context ctx) {
        String userId = ctx.cookie("user");

        if (userId == null) throw new UnauthorizedResponse();

        User user = users.get(Integer.parseInt(userId));

        if (user == null) throw new UnauthorizedResponse();

        return user;
    }

    public void addGame(Context ctx) {
        UserGame userGame = ctx.bodyValidator(UserGame.class)
                .check(obj -> obj.game.id != null, "Missing id of game")
                .check(obj -> obj.score >= 0, "Missing score")
                .check(obj -> obj.hourPlayed >= 0.0, "Missing hour played")
                .get();
        User user = checkCookie(ctx);

        for(UserGame ug : usersGames) {
            if(ug.user.id.equals(user.id) && ug.game.id.equals(userGame.game.id)) {
                ctx.status(HttpStatus.BAD_REQUEST); // 400
                throw new ConflictResponse("This user already owns this game"); // 409
            }
        }

        userGame.user = user;
        userGame.game = games.get(userGame.game.id);

        usersGames.add(userGame);

        ctx.status(HttpStatus.CREATED); // 201
        ctx.json(userGame);
    }

    public void removeGame(Context ctx) {
        UserGame userGame = ctx.bodyValidator(UserGame.class)
                .check(obj -> obj.game.id != null, "Missing id of game")
                .get();
        User user = checkCookie(ctx);

        Iterator<UserGame> iterator = usersGames.iterator();
        boolean gameFound = false;

        while(iterator.hasNext()) {
            UserGame ug = iterator.next();
            if(ug.user.id.equals(user.id) && ug.game.id.equals(userGame.game.id)) {
                iterator.remove();
                gameFound = true;
                break;
            }
        }
        if(!gameFound) throw new NotFoundResponse("This user doesn't own this game"); // 404

        ctx.status(HttpStatus.OK); // 200
    }

    public void list(Context ctx) {
        User user = checkCookie(ctx);

        ArrayList<String> gamesOfUser = new ArrayList<>();

        for (UserGame userGame : this.usersGames) {
            if (!userGame.user.id.equals(user.id)) continue;
            gamesOfUser.add("id : " + userGame.game.id + ", name : " + userGame.game.name + ", score : " +
                    userGame.score + ", hours played : " + userGame.hourPlayed);
        }

        ctx.status(HttpStatus.OK); // 200
        ctx.json(gamesOfUser);
    }
}