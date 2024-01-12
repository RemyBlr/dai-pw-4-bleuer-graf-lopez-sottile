# Créer un utilisateur

`curl -i -X POST -H "Content-Type: application/json" -d '{"username":"testUser","email":"abc@def.com","password":"secret"}' http://host.docker.internal:8181/users`

# Récupérer un utilisateur par son nom d'utilisateur

`curl -i -X GET -H "Content-Type: application/json" "http://host.docker.internal:8181/users?username=testUser"`

# Se connecter avec un utilisateur

`curl -i -X POST -H "Content-Type: application/json" -d '{"email":"abc@def.com","password":"secret"}' http://host.docker.internal:8181/login`

# Créer un jeu

`curl -i -X POST -H "Content-Type: application/json" -d '{"name":"Minecraft"}' http://host.docker.internal:8181/games`

# Ajouter un jeu à un utilisateur

Remplacer ID_USER par l'ID de l'utilisateur (Ex: 0) et ID_GAME par l'ID du jeu

`curl -i --cookie user=ID_USER -X POST -H "Content-Type: application/json" -d '{"game":
{"id":ID_GAME}}' http://host.docker.internal:8181/users/ID_USER/games`

# Récupérer la liste des jeux d'un utilisateur

Remplacer ID_USER par l'ID de l'utilisateur (Ex: 0)

`curl -i --cookie user=ID_USER -X GET -H "Content-Type: application/json" http://host.docker.internal:8181/users/ID_USER`

# Supprimer un jeu à un utilisateur

Remplacer ID_USER par l'ID de l'utilisateur (Ex: 0) et ID_GAME par l'ID du jeu

`curl -i --cookie user=ID_USER -X DELETE -H "Content-Type: application/json" -d '{"game": {"id":ID_GAME}}' http://host.docker.internal:8181/users/ID_USER/games`
