# Commandes API

## Créer un utilisateur

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"username":"testUser","email":"abc@def.com","password":"secret"}'\
      http://host.docker.internal:8181/users
```

## Récupérer un utilisateur par son nom d'utilisateur

```bash
curl -i -X GET\
      -H "Content-Type: application/json"\
      "http://host.docker.internal:8181/users?username=testUser"
```

## Se connecter avec un utilisateur

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"email":"abc@def.com","password":"secret"}'\
      http://host.docker.internal:8181/login
```
## Créer un jeu

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"name":"Minecraft"}'\
      http://host.docker.internal:8181/games
```

## Ajouter un jeu à un utilisateur

Remplacer ID_USER par l'ID de l'utilisateur (Ex: 0) et ID_GAME par l'ID du jeu

```bash
curl -i --cookie user=ID_USER -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":ID_GAME},"score":123,"hourPlayed":10}'\
      http://host.docker.internal:8181/users/ID_USER/games
```

## Récupérer la liste des jeux d'un utilisateur

Remplacer ID_USER par l'ID de l'utilisateur (Ex: 0)

```bash
curl -i --cookie user=ID_USER -X GET\
      -H "Content-Type: application/json"\
      http://host.docker.internal:8181/users/ID_USER
```

## Supprimer un jeu à un utilisateur

Remplacer ID_USER par l'ID de l'utilisateur (Ex: 0) et ID_GAME par l'ID du jeu

```bash
curl -i --cookie user=ID_USER -X DELETE\
      -H "Content-Type: application/json"\
      -d '{"game": {"id":ID_GAME}}'\
      http://host.docker.internal:8181/users/ID_USER/games
```

## Afficher le leaderboard

Remplacer ID_GAME par l'ID du jeu

```bash
curl -i --cookie user=ID_GAME -X GET\
      -H "Content-Type: application/json"\
      http://host.docker.internal:8181/games/ID_GAME/leaderboard
```

---

# Scénario d'exemple
Dans cet exemple, nous allons créer deux utilisateurs, deux jeux et ajouter des jeux à ces utilisateurs. Nous allons ensuite afficher le leaderboard d'un jeu.

## Ajout des utilisateurs

### Utilisateur 1
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"username":"sacha2bourgpalette","email":"pokemon4life@kanto.com","password":"pikachu123"}'\
      http://host.docker.internal:8181/users
```

### Utilisateur 2

```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"username":"BlockHead","email":"steve@minecraft.com","password":"H3r0br1n3"}'\
      http://host.docker.internal:8181/users
```

## Récupération d'un utilisateur

### Utilisateur 1
```bash
curl -i -X GET\
      -H "Content-Type: application/json"\
      "http://host.docker.internal:8181/users?username=sacha2bourgpalette"
```

### Résultat
```json
```

## Connexion des utilisateur
Un utilisateur doit être connecté pour pouvoir ajouter un jeu à sa liste de jeux.

### Utilisateur 1
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"email":"pokemon4life@kanto.com","password":"pikachu123"}'\
      http://host.docker.internal:8181/login
```

### Utilisateur 2
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"email":"steve@minecraft.com","password":"H3r0br1n3"}'\
      http://host.docker.internal:8181/login
```

### Résultat
```json
```

## Ajout des jeux

### Jeu 1
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"name":"Minecraft"}'\
      http://host.docker.internal:8181/games
```

### Jeu 2
```bash
curl -i -X POST\
      -H "Content-Type: application/json"\
      -d '{"name":"Pokemon"}'\
      http://host.docker.internal:8181/games
```

## Récupération des jeux

### Jeu 1
```bash
```

### Résultat
```json
```

## Ajout des jeux aux utilisateurs

### Jeu 1 à l'utilisateur 1
```bash
curl -i --cookie user=0 -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":0},"score":123,"hourPlayed":10}'\
      http://host.docker.internal:8181/users/0/games
```

### Jeu 1 à l'utilisateur 2
```bash
curl -i --cookie user=1 -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":0},"score":1000,"hourPlayed":100}'\
      http://host.docker.internal:8181/users/1/games
```

### Jeu 2 à l'utilisateur 1
```bash
curl -i --cookie user=0 -X POST\
      -H "Content-Type: application/json"\
      -d '{"game":{"id":1},"score":123,"hourPlayed":10}'\
      http://host.docker.internal:8181/users/0/games
```

## Récupération des jeux d'un utilisateur

### Utilisateur 1
```bash
curl -i --cookie user=0 -X GET\
      -H "Content-Type: application/json"\
      http://host.docker.internal:8181/users/0
```

### Résultat
```json
```

## Affichage du leaderboard

### Jeu 1
```bash
curl -i --cookie user=1 -X GET\
      -H "Content-Type: application/json"\
      http://host.docker.internal:8181/games/0/leaderboard
```

### Résultat
```json
```

## Suppression d'un jeu à un utilisateur

### Jeu 1 à l'utilisateur 1
```bash
curl -i --cookie user=0 -X DELETE\
      -H "Content-Type: application/json"\
      -d '{"game": {"id":0}}'\
      http://host.docker.internal:8181/users/0/games
```

### Résultat
```json
```

## Suppression des utilisateurs

### Utilisateur 1
```bash
```

### Résultat
```json
```