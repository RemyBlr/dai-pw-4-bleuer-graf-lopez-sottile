# API Documentation

## Create an user

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"username":"testUser","email":"abc@def.com","password":"secret"}'\
      http://host.docker.internal:8181/users
```

### Response in case of success (Code 201 Created):
```json
{
  "id": 1,
  "username": "testUser",
  "email": "abc@def.com"
}
```

### Response in case of failure (Code 400 Bad Request):

```json
{
  "error": "Email already exists"
}
```

## Get an user by username

```bash
curl -i -X GET\
      -H "Content-Type: application/json"\
      "http://host.docker.internal:8181/users?username=testUser"
```

### Response in case of success (Code 200 OK):
```json
{
  "id": 1,
  "username": "testUser",
  "email": "abc@def.com"
}
```

### Response in case of failure (Code 404 Not Found):

```json
{
  "error": "User not found"
}
```

## Connect an user

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"email":"abc@def.com","password":"secret"}'\
      http://host.docker.internal:8181/login
```

### Response in case of success (Code 200 OK):
```json
```

### Response in case of failure (Code 401 Unauthorized):
```json
```

## Create a game

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"name":"Minecraft"}'\
      http://host.docker.internal:8181/games
```

### Response in case of success (Code 201 Created):
```json
{
  "id": 1,
  "name": "Minecraft"
}
```

### Response in case of failure (Code 400 Bad Request):

```json
{
  "error": "Game already exists"
}
```

## Add a game to an user

Replace ID_USER with the user's ID (Ex: 0) and ID_GAME with the game's ID

```bash
curl -i --cookie user=ID_USER -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":ID_GAME},"score":123,"hourPlayed":10}'\
      http://host.docker.internal:8181/users/ID_USER/games
```

### Response in case of success (Code 201 Created):
```json
{
  "id": 1,
  "game": {
    "id": 1,
    "name": "Minecraft"
  },
  "score": 123,
  "hourPlayed": 10
}
```

### Response in case of failure (Code 400 Bad Request):

```json
{
  "error": "Game already exists"
}
```

## Get an user's games

Replace ID_USER with the user's ID (Ex: 0)

```bash
curl -i --cookie user=ID_USER -X GET\
      -H "Content-Type: application/json"\
      http://host.docker.internal:8181/users/ID_USER
```

### Response in case of success (Code 200 OK):
```json
```

### Response in case of failure (Code 404 Not Found):

```json
```

## Delete a game from an user

Replace ID_USER with the user's ID (Ex: 0) and ID_GAME with the game's ID

```bash
curl -i --cookie user=ID_USER -X DELETE\
      -H "Content-Type: application/json"\
      -d '{"game": {"id":ID_GAME}}'\
      http://host.docker.internal:8181/users/ID_USER/games
```

### Response in case of success (Code 200 OK):
```json
```

### Response in case of failure (Code 404 Not Found):

```json
```

## Get a game's leaderboard

Replace ID_GAME with the game's ID

```bash
curl -i --cookie user=ID_GAME -X GET\
      -H "Content-Type: application/json"\
      http://host.docker.internal:8181/games/ID_GAME/leaderboard
```

### Response in case of success (Code 200 OK):
```json
```

### Response in case of success (Code 404 Not Found):

```json
```

---

# Example scenario

In this example, we will create two users, two games and add games to these users. We will then display the leaderboard of a game.

## Add users

### User 1
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"username":"sacha2bourgpalette","email":"pokemon4life@kanto.com","password":"pikachu123"}'\
      http://host.docker.internal:8181/users
```

### User 2

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"username":"BlockHead","email":"steve@minecraft.com","password":"H3r0br1n3"}'\
      http://host.docker.internal:8181/users
```

## Get users

### User 1
```bash
curl -i -X GET\
      -H "Content-Type: application/json"\
      "http://host.docker.internal:8181/users?username=sacha2bourgpalette"
```

### Result
```json
```

## Connect users

An user must be connected to add a game to his list of games.

### User 1
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"email":"pokemon4life@kanto.com","password":"pikachu123"}'\
      http://host.docker.internal:8181/login
```

### User 2
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"email":"steve@minecraft.com","password":"H3r0br1n3"}'\
      http://host.docker.internal:8181/login
```

### Result
```json
```

## Add games

### Game 1
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"name":"Minecraft"}'\
      http://host.docker.internal:8181/games
```

### Game 2
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"name":"Pokemon"}'\
      http://host.docker.internal:8181/games
```

## Get games

### Game 1
```bash
```

### Result
```json
```

## Add games to users

### Game 1 to user 1
```bash
curl -i --cookie user=0 -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":0},"score":123,"hourPlayed":10}'\
      http://host.docker.internal:8181/users/0/games
```

### Game 1 to user 2
```bash
curl -i --cookie user=1 -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":0},"score":1000,"hourPlayed":100}'\
      http://host.docker.internal:8181/users/1/games
```

### Game 2 to user 1
```bash
curl -i --cookie user=0 -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":1},"score":123,"hourPlayed":10}'\
      http://host.docker.internal:8181/users/0/games
```

## Get an user's games

### User 1
```bash
curl -i --cookie user=0 -X GET\
      -H "Content-Type: application/json"\
      http://host.docker.internal:8181/users/0
```

### Result
```json
```

## Display a game's leaderboard

### Game 1
```bash
curl -i --cookie user=1 -X GET\
      -H "Content-Type: application/json"\
      http://host.docker.internal:8181/games/0/leaderboard
```

### Result
```json
```

## Delete a game from an user

### Game 1 from user 1
```bash
curl -i --cookie user=0 -X DELETE\
      -H "Content-Type: application/json"\
      -d '{"game": {"id":0}}'\
      http://host.docker.internal:8181/users/0/games
```

### Result
```json
```

## Delete an user

### User 1
```bash
```

### Result
```json
```