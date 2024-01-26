package ch.heigvd.games;

import ch.heigvd.usersGames.UserGame;
import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GamesController {

    private final ConcurrentHashMap<Integer, Game> games;
    private final AtomicInteger gameId = new AtomicInteger();
    private final ArrayList<UserGame> usersGames;

    public GamesController(ConcurrentHashMap<Integer, Game> games, ArrayList<UserGame> usersGames) {
        this.games = games;
        this.usersGames = usersGames;
    }

    public void create(Context ctx) {
        Game newGame = ctx.bodyValidator(Game.class)
                .check(obj -> obj.name != null, "Missing name")
                .get();

        for (Game game : games.values()) {
            if (game.name.equals(newGame.name)) {
                ctx.status(HttpStatus.BAD_REQUEST); // 400
                throw new ConflictResponse("This game already exists"); // 409
            }
        }

        Game game = new Game();

        game.id = gameId.getAndIncrement();
        game.name = newGame.name;

        games.put(game.id, game);

        ctx.status(HttpStatus.CREATED); // 201
        ctx.json(game);
    }

    public void update(Context ctx) {
        Integer id = ctx.pathParamAsClass("gameId", Integer.class)
                .check(gameId -> games.get(gameId) != null, "Game not found")
                .getOrThrow(message -> new NotFoundResponse()); // 404

        Game updateGame = ctx.bodyValidator(Game.class)
                .check(obj -> obj.name != null, "Missing name")
                .get();

        Game game = games.get(id);

        game.name = updateGame.name;

        games.put(id, game);

        ctx.status(HttpStatus.OK); // 200
        ctx.json(game);
    }

    public void delete(Context ctx) {
        Integer id = ctx.pathParamAsClass("gameId", Integer.class)
                .check(gameId -> games.get(gameId) != null, "Game not found")
                .getOrThrow(message -> new NotFoundResponse());

        games.remove(id);

        // manque erreur 404

        ctx.status(HttpStatus.OK); // 200
    }

    public void list(Context ctx) {
        String name = ctx.queryParam("name");
        ArrayList<Game> getGames = new ArrayList<>();

        for (Game game : this.games.values()) {
            if (name != null && !game.name.equals(name)) {
                continue;
            }

            getGames.add(game);
        }

        ctx.status(HttpStatus.OK); // 200
        ctx.json(getGames);
    }

    public void leaderboard(Context ctx) {
        Integer id = ctx.pathParamAsClass("gameId", Integer.class)
                .check(gameId -> games.get(gameId) != null, "Game not found")
                .getOrThrow(message -> new NotFoundResponse()); // 404

        ArrayList<UserGame> sortedUsersGames = new ArrayList<>(usersGames);
        sortedUsersGames.removeIf(userGame -> !Objects.equals(userGame.game.id, id));
        sortedUsersGames.sort(Comparator.comparingInt(UserGame::getScore).reversed());

        ArrayList<String> leaderboard = new ArrayList<>();
        int ranking = 0;
        for (UserGame userGame : sortedUsersGames) {
            leaderboard.add(++ranking + ". " + userGame.user.username + " - score : " + userGame.score);
        }


        ctx.status(HttpStatus.OK); // 200
        ctx.json(leaderboard);
    }
}