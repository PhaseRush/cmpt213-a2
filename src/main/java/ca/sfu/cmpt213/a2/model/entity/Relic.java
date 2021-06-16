package ca.sfu.cmpt213.a2.model.entity;

import ca.sfu.cmpt213.a2.model.Coordinate;

public class Relic extends Entity {

    public Relic(Coordinate position) {
        super(position);
    }

    @Override
    public void move(Coordinate newPos) {
        throw new UnsupportedOperationException("Relic cannot move.");
    }
}
