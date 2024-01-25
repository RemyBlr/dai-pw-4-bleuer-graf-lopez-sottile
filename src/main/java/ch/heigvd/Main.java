package ch.heigvd;

import ch.heigvd.auth.AuthController;
import ch.heigvd.users.*;
import ch.heigvd.games.*;
import ch.heigvd.usersGames.UserGame;
import ch.heigvd.usersGames.UserGamesController;
import io.javalin.Javalin;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        // This will serve as our database
        ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, Game> games = new ConcurrentHashMap<>();
        ArrayList<UserGame> usersGames = new ArrayList<>();

        // Controllers
        AuthController authController = new AuthController(users);
        UsersController usersController = new UsersController(users);
        GamesController gamesController = new GamesController(games,usersGames);
        UserGamesController userGamesController = new UserGamesController(users, games, usersGames);

        // Auth routes
        app.post("/login", authController::login);
        app.post("/logout", authController::logout);
        app.get("/profile", authController::profile);

        // Users routes
        app.post("/users", usersController::create);                // Create
        app.get("/users", usersController::list);                   // Read
        app.put("/users/{userId}", usersController::update);        // Update
        app.delete("/users/{userId}", usersController::delete);     // Delete

        app.post("/games", gamesController::create); // Create
        app.get("/games", gamesController::list);    // Read
        app.put("/games/{gameId}", gamesController::update);    // Update
        app.delete("/games/{gameId}", gamesController::delete); // Delete
        app.get("/games/{gameId}/leaderboard", gamesController::leaderboard); // Leaderboard

        // Routes that link user and
        app.post("/users/{userId}/games", userGamesController::addGame); // Add a game
        app.get("/users/{userId}", userGamesController::list); // List
        app.delete("/users/{userId}/games", userGamesController::removeGame); // Remove a game

        app.get("/", ctx -> {
            Path path = Paths.get("API.md");
            if (Files.exists(path)) {
                byte[] contentBytes = Files.readAllBytes(path);
                String content = new String(contentBytes, StandardCharsets.UTF_8);

                // Remove backticks around code blocks and language identifier
                content = content.replaceAll("```[A-Za-z]+", "");
                content = content.replaceAll("```+", "");

                ctx.result(content).contentType("text/markdown; charset=UTF-8");
            } else {
                ctx.result("API documentation not found").status(404);
            }
        });

        app.start(PORT);
    }
}