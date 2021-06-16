package ca.sfu.cmpt213.a2.model.entity;

import ca.sfu.cmpt213.a2.model.Coordinate;

public class Hunter extends Entity {
    public Hunter(Coordinate position) {
        super(position);
    }

    @Override
    public void move(Coordinate newPos) {
        this.position = newPos;
    }
}
