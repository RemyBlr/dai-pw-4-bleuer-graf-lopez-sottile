# Ajouter un utilisateur

`curl -i -X POST -H "Content-Type: application/json" -d '{"username":"testUser","email":"abc@def.com","password":"secret"}' http://host.docker.internal:8181/users`

# Se connecter avec un utilisateur

`curl -i -X POST -H "Content-Type: application/json" -d '{"email":"abc@def.com","password":"secret"}' http://host.docker.internal:8181/login`