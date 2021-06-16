package ca.sfu.cmpt213.a2.textui;

import ca.sfu.cmpt213.a2.model.Maze;

import java.util.Arrays;

public class UI {
    private Maze maze;

    public UI(Maze maze) {
        this.maze = maze;
    }

    public void showMaze() {
        for (int i = 0; i < Maze.HEIGHT; i++) {
//            System.out.println(Arrays.toString(maze.getMaze()[i]));
            for (int j = 0; j < Maze.WIDTH; j++) {
                System.out.print(maze.getMaze()[i][j] + " ");
            }
            System.out.println();
        }
    }

}
