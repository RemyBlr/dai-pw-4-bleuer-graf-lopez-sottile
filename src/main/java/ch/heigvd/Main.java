package ch.heigvd;

import ch.heigvd.auth.AuthController;
import ch.heigvd.users.User;
import ch.heigvd.users.UsersController;
import io.javalin.Javalin;

import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final int PORT = 8181;

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        // This will serve as our database
        ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

        // Controllers
        AuthController authController = new AuthController(users);
        UsersController usersController = new UsersController(users);

        // Auth routes
        app.post("/login", authController::login);
        app.post("/logout", authController::logout);
        app.get("/profile", authController::profile);

        // Users routes
        app.post("/users", usersController::create);            // Create
        app.get("/users", usersController::list);               // Read
        app.put("/users/{userId}", usersController::update);    // Update
        app.delete("/users/{userId}", usersController::delete); // Delete

        app.start(PORT);
    }
}