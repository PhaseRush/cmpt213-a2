package ca.sfu.cmpt213.a2.model.entity;

import ca.sfu.cmpt213.a2.model.Coordinate;
import ca.sfu.cmpt213.a2.model.Tile;

public class Guardian extends Entity{
    public static Tile SYMBOL = Tile.GUARDIAN;

    public Guardian(Coordinate position) {
        super(position);
    }

    @Override
    public void move(Coordinate newPos) {

    }
}
