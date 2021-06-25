package ca.sfu.cmpt213.a2.model.entity;

import ca.sfu.cmpt213.a2.model.Coordinate;
import ca.sfu.cmpt213.a2.model.Game;
import ca.sfu.cmpt213.a2.model.Maze;
import ca.sfu.cmpt213.a2.model.Tile;

import java.util.Scanner;

import static ca.sfu.cmpt213.a2.model.Coordinate.at;
import static ca.sfu.cmpt213.a2.model.Game.CARDINALS;

public class Hunter extends Entity {
    private static final Scanner SC = new Scanner(System.in);

    public Hunter(Coordinate position) {
        super(position);
    }

    public void move(Maze maze, Game.Input input) {
        var desired = CARDINALS.get(input);
        maze.getMaze()[y() + desired.y()][x() + desired.x()] = Tile.HUNTER;
        maze.getMaze()[y()][x()] = Tile.SPACE;
        move(at(x() + desired.x(), y() + desired.y()));
    }

    @Override
    public void move(Coordinate newPos) {
        this.position = newPos;
    }

    @Override
    public Tile getTile() {
        return Tile.HUNTER;
    }
}
