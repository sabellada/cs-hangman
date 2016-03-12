The game will be implemented in a client/server model. The client process is associated to the player (and to the interface). There are three steps in the communication between client and server:

To start a new game, the client must get the word to guess from the server.

At the end of a game (or at the beginning of the next game), the client has to send the current score to the server.

The server will multicast the score of everyone to all clients.