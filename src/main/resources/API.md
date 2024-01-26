# API Documentation

## Create an user

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"username":"testUser","email":"abc@def.com","password":"secret"}'\
      http://dai.daibrgclesa.ch/users
```

### Response in case of success (Code 201 Created):
```json
{
  "id": 1,
  "username": "testUser",
  "email": "abc@def.com",
  "password": "secret"
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
      "http://dai.daibrgclesa.ch/users?username=testUser"
```

### Response in case of success (Code 200 OK):
```json
{
  "id": 1,
  "username": "testUser",
  "email": "abc@def.com",
  "password": "secret"
}
```

### Response in case of failure (Code 404 Not Found):

```json
{
  "error": "not found"
}
```

## Connect an user

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"email":"abc@def.com","password":"secret"}'\
      http://dai.daibrgclesa.ch/login
```

### Response in case of success (Code 204 No Content):
```bash
HTTP/1.1 204 No Content
Content-Type: text/plain
Date: Thu, 25 Jan 2024 19:25:40 GMT
Expires: Thu, 01 Jan 1970 00:00:00 GMT
Set-Cookie: user=1; Path=/
```

### Response in case of failure (Code 401 Unauthorized):
```json
{
  "error": "Unauthorized"
}
```

## Create a game

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"name":"Minecraft"}'\
      http://dai.daibrgclesa.ch/games
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
  "error": "This game already exists"
}
```

## Add a game to an user

Replace ID_USER with the user's ID (Ex: 0) and ID_GAME with the game's ID

```bash
curl -i --cookie user=ID_USER -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":ID_GAME},"score":123,"hourPlayed":10}'\
      http://dai.daibrgclesa.ch/users/ID_USER/games
```

### Response in case of success (Code 201 Created):
```json
{
  "user":{
    "id":1,
    "username":"testUser",
    "email":"abc@def.com",
    "password":"secret"
  },
  "game":{
    "id":0,
    "name":"Minecraft"
  },
  "score":123,
  "hourPlayed":10.0
}
```

### Response in case of failure (Code 400 Bad Request):

```json
{
  "error": "This user already owns this game"
}
```

## Get an user's games

Replace ID_USER with the user's ID (Ex: 0)

```bash
curl -i --cookie user=ID_USER -X GET\
      -H "Content-Type: application/json"\
      http://dai.daibrgclesa.ch/users/ID_USER
```

### Response in case of success (Code 200 OK):
```json
[
   "id : 0, name : Minecraft, score : 123, hours played : 10.0"
]
```

## Delete a game from an user

Replace ID_USER with the user's ID (Ex: 0) and ID_GAME with the game's ID

```bash
curl -i --cookie user=ID_USER -X DELETE\
      -H "Content-Type: application/json"\
      -d '{"game": {"id":ID_GAME}}'\
      http://dai.daibrgclesa.ch/users/ID_USER/games
```

### Response in case of success (Code 200 OK):

```bash
HTTP/1.1 200 OK
Content-Length: 0
Content-Type: text/plain
Date: Thu, 25 Jan 2024 19:36:11 GMT
```

### Response in case of failure (Code 404 Not Found):

```json
{
  "error": "This user doesn't own this game"
}
```

## Get a game's leaderboard

Replace ID_GAME with the game's ID

```bash
curl -i --cookie user=ID_GAME -X GET\
      -H "Content-Type: application/json"\
      http://dai.daibrgclesa.ch/games/ID_GAME/leaderboard
```

### Response in case of success (Code 200 OK):
```json
[
   "1. AlanS - score : 12300",
   "2. testUser - score : 123"
]
```

### Response in case of success (Code 404 Not Found):

```json
{
  "error": "Not found"
}
```

## Delete an user
```bash
curl -i -X DELETE http://dai.daibrgclesa.ch/users/ID_USER
```

### Response in case of success (Code 200 OK):

```bash
HTTP/1.1 200 OK
Content-Length: 0
Content-Type: text/plain
Date: Thu, 25 Jan 2024 19:57:45 GMT
```

### Response in case of failure (Code 404 Not Found):

```json
{
  "error": "This user doesn't exist"
}
```

## Update an user

Replace ID_USER with the user's ID (Ex: 0)

```bash
curl -i -X PUT -H "Content-Type: application/json" -d \
'{"username":"AlanS","email":"alan@gmail.com","password":"alan"}' http://dai.daibrgclesa.ch/users/ID_USER
```

### Response in case of success (Code 200 OK):

```bash
HTTP/1.1 200 OK
Content-Length: 0
Content-Type: text/plain
Date: Thu, 25 Jan 2024 19:57:45 GMT
```

### Response in case of failure (Code 400 Bad Request):

```json
{
  "error": "Username must be at least 3 characters long"
}
```

### Response in case of failure (Code 404 Not Found):

```json
{
  "error": "User not found"
}
```

## Update a game

Replace ID_GAME with the game's ID

```bash
curl -i -X PUT -H "Content-Type: application/json" -d \
      '{"name":"minecraft"}' http://dai.daibrgclesa.ch/games/ID_GAME
```

### Response in case of success (Code 200 OK):
    
```bash
HTTP/1.1 200 OK
Content-Length: 0
Content-Type: text/plain
Date: Fri, 26 Jan 2024 09:18:34 GMT
```

### Response in case of failure (Code 404 Not Found):
    
```json
{
"error": "Game not found"
}
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
      http://dai.daibrgclesa.ch/users
```

### User 2

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"username":"BlockHead","email":"steve@minecraft.com","password":"H3r0br1n3"}'\
      http://dai.daibrgclesa.ch/users
```

## Get users

### User 1
```bash
curl -i -X GET\
      -H "Content-Type: application/json"\
      "http://dai.daibrgclesa.ch/users?username=sacha2bourgpalette"
```

### Result
```json
[
   {
      "id":2,
      "username":"sacha2bourgpalette",
      "email":"pokemon4life@kanto.com",
      "password":"pikachu123"
   }
]
```

## Connect users

An user must be connected to add a game to his list of games.

### User 1
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"email":"pokemon4life@kanto.com","password":"pikachu123"}'\
      http://dai.daibrgclesa.ch/login
```

### User 2
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"email":"steve@minecraft.com","password":"H3r0br1n3"}'\
      http://dai.daibrgclesa.ch/login
```

### Result
```bash
HTTP/1.1 204 No Content
Content-Type: text/plain
Date: Thu, 25 Jan 2024 19:42:22 GMT
Expires: Thu, 01 Jan 1970 00:00:00 GMT
Set-Cookie: user=3; Path=/
```

## Add games

### Game 1
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"name":"Minecraft"}'\
      http://dai.daibrgclesa.ch/games
```

### Game 2
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"name":"Pokemon"}'\
      http://dai.daibrgclesa.ch/games
```

## Add games to users

### Game 1 to user 1
```bash
curl -i --cookie user=0 -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":0},"score":123,"hourPlayed":10}'\
      http://dai.daibrgclesa.ch/users/0/games
```

### Game 1 to user 2
```bash
curl -i --cookie user=1 -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":0},"score":1000,"hourPlayed":100}'\
      http://dai.daibrgclesa.ch/users/1/games
```

### Game 2 to user 1
```bash
curl -i --cookie user=0 -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":1},"score":123,"hourPlayed":10}'\
      http://dai.daibrgclesa.ch/users/0/games
```

## Get an user's games

### User 1
```bash
curl -i --cookie user=0 -X GET\
      -H "Content-Type: application/json"\
      http://dai.daibrgclesa.ch/users/0
```

### Result
```json
[
   "id : 0, name : Minecraft, score : 123, hours played : 10.0",
   "id : 1, name : Pokemon, score : 123, hours played : 10.0"
]
```

## Display a game's leaderboard

### Game 1
```bash
curl -i --cookie user=2 -X GET\
      -H "Content-Type: application/json"\
      http://dai.daibrgclesa.ch/games/0/leaderboard
```

### Result
```json
[
   "1. BlockHead - score : 1000",
   "2. sacha2bourgpalette - score : 123"
]
```

## Delete a game from an user

### Game 1 from user 1
```bash
curl -i --cookie user=0 -X DELETE\
      -H "Content-Type: application/json"\
      -d '{"game": {"id":0}}'\
      http://dai.daibrgclesa.ch/users/0/games
```

### Result
```bash
HTTP/1.1 200 OK
Content-Length: 0
Content-Type: text/plain
Date: Thu, 25 Jan 2024 19:48:03 GMT
```

## Delete an user

### User 1
```bash
curl -i -X DELETE http://dai.daibrgclesa.ch/users/0
```

### Result
```bash
HTTP/1.1 200 OK
Content-Length: 0
Content-Type: text/plain
Date: Thu, 25 Jan 2024 19:58:32 GMT
```