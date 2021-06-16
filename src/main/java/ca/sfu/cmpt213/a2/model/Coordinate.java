package ca.sfu.cmpt213.a2.model;

public record Coordinate(int x, int y) {
    public static Coordinate at(int x, int y) {
        return new Coordinate(x, y);
    }
}
