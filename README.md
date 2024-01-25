# Java Web Application
This is a simple Java web application that provides user and game management functionalities, allowing users to be 
created, updated, and deleted, and the same goes for the games. Users can also add games, specifying their score and playtime. 
The application supports authentication and a leaderboard feature for a given game.

We were 4 students (Bleuer Rémy, Graf Calvin, Lopez Esteban, Sottile Alan) for this practical work. We decided to split the
work in 2 parts. The first part was to create the API based on [this practical work](https://github.com/heig-vd-dai-course/heig-vd-dai-course/tree/main/21-http-and-curl),
Calvin and Rémy worked on it. The second part was to setup the virtual machine and configure the docker created with the api to connect to the Traefik proxy so it can be accessed from the internet, based on
[this practical work](https://github.com/heig-vd-dai-course/heig-vd-dai-course/tree/main/22-web-infrastructures)
, Alan, Esteban and Calvin worked on this part.
And lastly, Esteban and Rémy worked on the documentation.

---
## Getting Started
These instructions will help you set up and run the Java web application on your local machine.

### Installation
1. Clone the repository:

`git clone git@github.com:RemyBlr/dai-pw-4-bleuer-graf-lopez-sottile.git`

2. Navigate to the project directory:

`cd dai-pw-4-bleuer-graf-lopez-sottile`

3. Build the project using Maven:

`mvn clean install`

### Running the Application Locally
1. Run the Javalin application:

`java -jar target/dai-pw-4-bleuer-graf-lopez-sottile-1.0-SNAPSHOT.jar`

2. Open your web browser and go to http://localhost:8080 to access the application.

---
## Running the Application outside the local network
The application can be used outside a local network, a server deployment will be required for it to be possible. The server's ports 80 (http) and 443 (https) need to be open so the http and https works. 

Once the server has setup with the needed configuration, you need to clone the repository and run the application with our docker image like following: 


### Connection to the virtual machine
Supposing we have a virtual machine, it's necessary to connect to it using ssh to have a secure acces: 

```
    ssh heiguser@10.190.132.62
```

Then we will be asked to add the password. However this can be modified by creating an ssh key and adding it to our virtual machine so we won't be asked to write the password every time we want to connect to the machine.

### SSH key 
First of all it's necessary to create a SSH key that will be linked to our server. This can be done by following the ["Generating a new SSH key"](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent#generating-a-new-ssh-key) steps. 

Once we have the ssh key created, we run the following command so we link it to the server:
```
    ssh-copy-id heiguser@10.190.132.62
```

(Note that the username `heiguser` and ip `10.190.132.62` are just examples for our project and wont work for your project and need to be modified).

### 


1. Build the Docker image:

``

2. Run the Docker container:

``

## Usage
Below are some sample endpoints, a complete list of this API's use cases can be found in this [ API.md](src/main/resources/API.md) file:

### Users:

- POST /users: Create a new user.
- GET /users: Retrieve a list of users.
- PUT /users/{userId}: Update a user.
- DELETE /users/{userId}: Delete a user.

### Games:

- POST /games: Create a new game.
- GET /games: Retrieve a list of games.
- PUT /games/{gameId}: Update a game.
- DELETE /games/{gameId}: Delete a game.
- GET /games/{gameId}/leaderboard: Retrieve the leaderboard for a specific game.

## Response Codes
This API returns the following response codes:

- 200: Success.
- 201: Created.
- 400: Bad request.
- 409: Conflict.
