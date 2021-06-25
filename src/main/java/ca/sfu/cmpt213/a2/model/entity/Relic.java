package ca.sfu.cmpt213.a2.model.entity;

import ca.sfu.cmpt213.a2.model.Coordinate;
import ca.sfu.cmpt213.a2.model.Game;
import ca.sfu.cmpt213.a2.model.Maze;
import ca.sfu.cmpt213.a2.model.Tile;

public class Relic extends Entity {

    public Relic(Coordinate position) {
        super(position);
    }

    @Override
    public void move(Coordinate newPos) {
        throw new UnsupportedOperationException("Relic cannot move.");
    }

    @Override
    public Tile getTile() {
        return Tile.RELIC;
    }


    @Override
    public void move(Maze maze, Game.Input input) {
        // do nothing
    }

}
