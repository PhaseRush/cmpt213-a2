package ca.sfu.cmpt213.a2.textui;

import ca.sfu.cmpt213.a2.model.Game;
import ca.sfu.cmpt213.a2.model.Maze;

public class UI {
    private Game game;

    public UI() {
        this.game = new Game(new Maze());
    }

    public void start() {
        this.game.play();
    }

}
