package ca.sfu.cmpt213.a2.model;

public enum Tile {
    WALL("#"),
    HUNTER("@"),
    GUARDIAN("!"),
    RELIC("^"),
    SPACE(" "),
    UNEXPLORED(".");

    protected String symbol;

    Tile(String symbol) {
        this.symbol = symbol;
    }

    public boolean alwaysShow() {
        return this.equals(HUNTER) ||
                this.equals(GUARDIAN) ||
                this.equals(RELIC);
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
