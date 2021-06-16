package ca.sfu.cmpt213.a2.model.entity;

import ca.sfu.cmpt213.a2.model.Coordinate;
import ca.sfu.cmpt213.a2.model.Tile;

public abstract class Entity {
    public static Tile SYMBOL;
    protected Coordinate position;

    public Entity(Coordinate position) {
        this.position = position;
    }

    public abstract void move(Coordinate newPos);

    public int x() {
        return position.x();
    }

    public int y() {
        return position.y();
    }
}