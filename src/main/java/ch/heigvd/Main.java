package ch.heigvd;

import io.javalin.Javalin;

public class Main {
    public static final int PORT = 8181;

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.get("/", ctx -> ctx.result("Hello, world! TEST"));

        app.start(PORT);
    }
}