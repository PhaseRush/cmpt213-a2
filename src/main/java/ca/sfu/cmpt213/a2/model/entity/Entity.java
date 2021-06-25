package ca.sfu.cmpt213.a2.model.entity;

import ca.sfu.cmpt213.a2.model.Coordinate;
import ca.sfu.cmpt213.a2.model.Game;
import ca.sfu.cmpt213.a2.model.Maze;
import ca.sfu.cmpt213.a2.model.Tile;

public abstract class Entity {
    protected Coordinate position;

    public Entity(Coordinate position) {
        this.position = position;
    }

    public abstract void move(Coordinate newPos);

    public final Coordinate getPosition() {
        return position;
    }

    public final void setPosition(Coordinate position) {
        this.position = position;
    }

    public final int x() {
        return position.x();
    }

    public final int y() {
        return position.y();
    }

    public abstract Tile getTile();

    public abstract void move(Maze maze, Game.Input input);
}