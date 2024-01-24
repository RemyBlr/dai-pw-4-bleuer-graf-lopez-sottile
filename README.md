# Java Web Application
This is a simple Java web application that provides user and game management functionalities, allowing users to be 
created, updated, and deleted, and the same goes for the games. Users can also add games, specifying their score and playtime. 
The application supports authentication and a leaderboard feature for a given game.

We were 4 students (Bleuer Rémy, Graf Calvin, Lopez Esteban, Sottile Alan) for this practical work. We decided to split the
work in 2 parts. The first part was to create the API based on [this practical work](https://github.com/heig-vd-dai-course/heig-vd-dai-course/tree/main/21-http-and-curl),
Calvin and Rémy worked on it. The second part was to setup the virtual machine and .... based on
[this practical work](https://github.com/heig-vd-dai-course/heig-vd-dai-course/tree/main/22-web-infrastructures)
, Alan and Esteban worked on this part.


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

2. Open your web browser and go to http://localhost:8181 to access the application.

### Running the Application with Docker
1. Build the Docker image:

``

2. Run the Docker container:

``

## Usage
Below are some sample endpoints, a complete list of this API's use cases can be found in [this API.md file](API.md):

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