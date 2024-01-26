package ch.heigvd.users;

import io.javalin.http.*;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UsersController {

    private final ConcurrentHashMap<Integer, User> users;
    private final AtomicInteger userId = new AtomicInteger();

    public UsersController(ConcurrentHashMap<Integer, User> users) {
        this.users = users;
    }

    public void create(Context ctx) {
        User newUser = ctx.bodyValidator(User.class)
                .check(obj -> obj.username != null, "Missing username")
                .check(obj -> obj.email != null, "Missing email")
                .check(obj -> obj.password != null, "Missing password")
                .get();

        for (User user : users.values()) {
            if (user.email.equals(newUser.email)) {
                ctx.status(HttpStatus.BAD_REQUEST); // 400
                throw new ConflictResponse("Email already exists"); // 409
            }
        }

        User user = new User();

        user.id = userId.getAndIncrement();
        user.username = newUser.username;
        user.email = newUser.email;
        user.password = newUser.password;

        users.put(user.id, user);

        ctx.status(HttpStatus.CREATED); // 201
        ctx.json(user);
    }

    public void update(Context ctx) {
        Integer id = ctx.pathParamAsClass("userId", Integer.class)
                .check(userId -> users.get(userId) != null, "User not found")
                .getOrThrow(message -> new NotFoundResponse()); // 404

        User updateUser = ctx.bodyValidator(User.class)
                .check(obj -> obj.username != null, "Missing username")
                .check(obj -> obj.email != null, "Missing email")
                .check(obj -> obj.password != null, "Missing password")
                .get();

        if (updateUser.username.length() < 3) {
            ctx.status(HttpStatus.BAD_REQUEST); // 400
            throw new BadRequestResponse("Username must be at least 3 characters long");
        }

            User user = users.get(id);

        user.username = updateUser.username;
        user.email = updateUser.email;
        user.password = updateUser.password;

        users.put(id, user);

        ctx.status(HttpStatus.OK); // 200
        ctx.json(user);
    }

    public void delete(Context ctx) {
        Integer id = ctx.pathParamAsClass("userId", Integer.class)
                .check(userId -> users.get(userId) != null, "User not found")
                .getOrThrow(message -> new NotFoundResponse()); // 404

        users.remove(id);

        ctx.status(HttpStatus.OK); // 200
    }

    public void list(Context ctx) {
        String username = ctx.queryParam("username");
        ArrayList<User> getUsers = new ArrayList<>();

        for (User user : this.users.values()) {
            if (username != null && !user.username.equals(username)) {
                continue;
            }

            getUsers.add(user);
        }

        ctx.status(HttpStatus.OK); // 200
        ctx.json(getUsers);
    }
}