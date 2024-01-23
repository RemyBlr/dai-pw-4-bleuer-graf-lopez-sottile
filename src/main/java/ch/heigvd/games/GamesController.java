package ch.heigvd.games;

import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GamesController {

    private final ConcurrentHashMap<Integer, Game> games;
    private final AtomicInteger gameId = new AtomicInteger();

    public GamesController(ConcurrentHashMap<Integer, Game> games) {
        this.games = games;
    }

    public void create(Context ctx) {
        Game newGame = ctx.bodyValidator(Game.class)
                .check(obj -> obj.name != null, "Missing name")
                .get();

        for (Game game : games.values()) {
            if (game.name.equals(newGame.name)) {
                ctx.status(HttpStatus.BAD_REQUEST); // 400
                throw new ConflictResponse("This game already exists");
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
        Integer id = ctx.pathParamAsClass("id", Integer.class)
                .check(gameId -> games.get(gameId) != null, "Game not found")
                .getOrThrow(message -> new NotFoundResponse());

        Game updateGame = ctx.bodyValidator(Game.class)
                .check(obj -> obj.name != null, "Missing name")
                .get();

        // Manque erreur 400 et 404

        Game game = games.get(id);

        game.name = updateGame.name;

        games.put(id, game);

        ctx.status(HttpStatus.OK); // 200
        ctx.json(game);
    }

    public void delete(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class)
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

        ctx.json(getGames);
    }
}