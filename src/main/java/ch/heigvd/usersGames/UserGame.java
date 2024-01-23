package ch.heigvd.usersGames;

import ch.heigvd.users.User;
import ch.heigvd.games.Game;

public class UserGame {

    public User user;

    public Game game;

    public int score;

    public double hourPlayed;

    public UserGame() {
        // Empty constructor for serialisation/deserialization
    }

    public int getScore() {
        return score;
    }
}
