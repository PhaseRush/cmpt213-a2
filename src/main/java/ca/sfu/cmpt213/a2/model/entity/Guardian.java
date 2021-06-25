package ca.sfu.cmpt213.a2.model.entity;

import ca.sfu.cmpt213.a2.model.Coordinate;
import ca.sfu.cmpt213.a2.model.Game;
import ca.sfu.cmpt213.a2.model.Maze;
import ca.sfu.cmpt213.a2.model.Tile;

public class Guardian extends Entity {

    public Guardian(Coordinate position) {
        super(position);
    }

    @Override
    public void move(Coordinate newPos) {
        this.position = newPos;
    }

    @Override
    public Tile getTile() {
        return Tile.GUARDIAN;
    }

    @Override
    public void move(Maze maze, Game.Input input) {
        move(maze.randomNeighbour(position, Tile.SPACE));
    }
}
