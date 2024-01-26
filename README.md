# Java Web Application
This is a simple Java web application that provides user and game management functionalities, allowing users to be
created, updated, and deleted, and the same goes for the games. Users can also add games, specifying their score and playtime.
The application supports authentication and a leaderboard feature for a given game.

We were 4 students (Bleuer Rémy, Graf Calvin, Lopez Esteban, Sottile Alan) for this practical work. We decided to split the
work in 2 parts. The first part was to create the API based on [this practical work](https://github.com/heig-vd-dai-course/heig-vd-dai-course/tree/main/21-http-and-curl),
Calvin and Rémy worked on it. The second part was to setup the virtual machine and configure the docker created with the api to connect to the Traefik proxy so it can be accessed from the internet, based on
[this practical work](https://github.com/heig-vd-dai-course/heig-vd-dai-course/tree/main/22-web-infrastructures)
, Alan and Esteban worked on this part.

---
## Getting Started
These instructions will help you set up and run the Java web application on your local machine.

### Installation
1. Clone the repository:

```bash
    git clone https://github.com/RemyBlr/dai-pw-4-bleuer-graf-lopez-sottile.git
```

2. Navigate to the project directory:

```bash
    cd dai-pw-4-bleuer-graf-lopez-sottile
```
3. Build the project using Maven:

```
    mvn clean install
```
### Running the Application Locally
1. Run the Javalin application:
```
    java -jar target/dai-pw-4-bleuer-graf-lopez-sottile-1.0-SNAPSHOT.jar
```
2. Open your web browser and go to http://localhost:8181 to access the application.

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

### Deploying the application
As seen for the local installation, it's necessary to clone the projet to our server using the same command. Once cloned, we need to find the right file to run Traefik.
```
    cd heig-vd-course-code-examples/23-practical-work-4/traefik-secure
```
To be  able to access the application from the outside, we first needed to get a domain name. We got ours from Infomaniak and called it "daibrgclesa.ch". We then needed to change the DNS zone from the Infomaniak website so that it would be recognisable on the internet.

Here are the lines we added to be able to use the traefik and dai services:

```
dai     3600 IN A  10.190.132.62
traefik 3600 IN A  10.190.132.62
```
Now, whenever we want to access those services, we need to type "service_name.daibrgclesa.ch".

The api can be accessed because we have published the docker package and can be accessed directly from the docker-compose.yml. Now we can start the docker container.
```
    docker compose up -d
```
Now our web application can be accessed through this link [https://dai.daibrgclesa.ch](https://dai.daibrgclesa.ch).

To shut down the server, you'll need to stop the server container with
```
    docker compose down
```

----
## Docker
### Create docker image
**Warning** : This part contains names that only works for our project and must be adapted for everyone wanting to recreate it.

1. First of all we need to create the Dockerfile for our project.
2. Then build the project in the same folder as the Dockerfile
 ```
    docker build -t pw4-dockerfile-as-cg-el-rb:v1.0
```
3. Run the docker container so we create the image to upload it
```
    docker run --rm pw4-dockerfile-as-cg-el-rb:v1.0
```
### Publish the image on GitHub
1. Create a personal access token(classic) on GIT in the `Settings/Developper Settings`.
2. Export the personal access token as an environnment variable:
```
    export GITHUB_CR_PAT=<TOKEN>
```
3. Then, we need to log in to the GitHub Container Registry
```
    echo $GITHUB_CR_PAT | docker login ghcr.io -u <username> --password-stdin
```
4. With the following output
```
    Login succeeded
```
5. Then it is mandatory to tag the image for it to be possible to publish
```
    docker tag pw4-dockerfile-as-cg-el-rb:v1.0 ghcr.io/<username>/pw4-dockerfile-as-cg-el-rb:v1.0
```

6. Once that's done we can publish our image with the following command
```
    docker push ghcr.io/<username>/pw4-dockerfile-as-cg-el-rb:v1.0
```
7. We can now access our image on our github page! If we want it to pull the image without having to log to the Github Container Registry we need to change it's visibility (private by default) to public. For this porject **it's a mandatory step**
```
    https://github.com/<username>?tab=packages
```

### Pull an image from GitHub
Now to get our published image we just need to run the following command:
```
    docker pull ghcr.io/<username>/pw4-dockerfile-as-cg-el-rb:v1.0
```
---

## API usage
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