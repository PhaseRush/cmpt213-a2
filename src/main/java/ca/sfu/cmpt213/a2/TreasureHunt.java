package ca.sfu.cmpt213.a2;

import ca.sfu.cmpt213.a2.model.Maze;
import ca.sfu.cmpt213.a2.textui.UI;

public class TreasureHunt {
    public static void main(String[] args) {
        UI ui = new UI(new Maze());
        ui.showMaze();
        System.out.println("place");
    }
}
