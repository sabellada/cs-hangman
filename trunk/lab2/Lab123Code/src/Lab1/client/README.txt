The client code has two classes :
    - gui.java which extends the gameBoard (the interface)
    - game.java which should has the main method and displays the interface.

game.java should be the class that handles all the communications to/from
the server, leaving gui.java as the entity which interacts with the interface.

All implementations related to contact the server must be in game.java class.