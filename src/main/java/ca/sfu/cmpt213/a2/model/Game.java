package ca.sfu.cmpt213.a2.model;

import ca.sfu.cmpt213.a2.model.entity.Guardian;
import ca.sfu.cmpt213.a2.model.entity.Hunter;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static ca.sfu.cmpt213.a2.model.Coordinate.*;
import static ca.sfu.cmpt213.a2.model.Maze.HEIGHT;
import static ca.sfu.cmpt213.a2.model.Maze.WIDTH;

public class Game {
    private final Scanner sc;
    private Maze maze;
    private final Hunter hunter;
    private final List<Guardian> guardians;
    private final boolean[][] discovered;

    private final Map<Input, Coordinate> directions = Map.of(
            Input.SENTINEL1, at(-1, -1),    // up left
            Input.UP, at(0, -1),            // up
            Input.SENTINEL2, at(1, -1),     // up right
            Input.LEFT, at(-1, 0),          // left
            Input.RIGHT, at(1, 0),          // right
            Input.CHEAT, at(-1, 1),         // down left
            Input.DOWN, at(0, 1),           // down
            Input.REVEAL, at(1, 1)          // down, right
    );

    public Game(Maze maze) {
        this.maze = maze;
        this.sc = new Scanner(System.in);


        // init discovered with walls and player pos
        this.discovered = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            this.discovered[i][0] = true;
            this.discovered[i][WIDTH - 1] = true;
        }
        for (int i = 0; i < WIDTH; i++) {
            this.discovered[0][i] = true;
            this.discovered[HEIGHT - 1][0] = true;
        }
        reveal(at(1, 1));
        hunter = new Hunter(at(1, 1));

        guardians = List.of(
                new Guardian(at(WIDTH - 2, HEIGHT - 2)), // bottom right
                new Guardian(at(1, HEIGHT - 2)), // bottom left
                new Guardian(at(WIDTH - 2, 1))); // top right
    }

    private void reveal(Coordinate coord) {
        discovered[coord.y()][coord.x()] = true; // discover self
        for (Coordinate delta : directions.values()) {
            try {
                discovered[coord.y() + delta.y()][coord.x() + delta.x()] = true;
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }

    private void showHidden() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Tile tile = maze.getMaze()[i][j];
                if (tile.alwaysShow() || discovered[i][j]) {
                    sb.append(tile);
                } else {
                    sb.append(Tile.UNEXPLORED);
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    private enum Input {
        REVEAL("M"),
        CHEAT("C"),
        UP("W"),
        LEFT("A"),
        DOWN("S"),
        RIGHT("D"),
        SENTINEL1(null),
        SENTINEL2(null);

        final String key;

        Input(String d) {
            this.key = d;
        }
    }

    public void start() {
        System.out.println("""
                DIRECTIONS:
                Collect 3 relics!
                LEGEND:
                    #: Wall
                    @: You (the treasure hunter)
                    !: Guardian
                    ^: Relic
                    .: Unexplored space
                MOVES:
                Use W (up), A (left), S (down) and D (right) to move.
                (You must press enter after each move).""");
    }

    private void moveHunter(Input direction) throws ArrayIndexOutOfBoundsException {
        var desired = directions.get(direction);
        maze.getMaze()[hunter.y() + desired.y()][hunter.x() + desired.x()] = Tile.HUNTER;
        hunter.move(at(hunter.x() + desired.x(), hunter.y() + desired.y()));
    }

    public void play() {
        start();
        while (sc.hasNext()) {
            try {
                var input = Input.valueOf(sc.nextLine());
                switch (input) {
                    case REVEAL -> maze.showMaze();
                    case CHEAT -> System.out.println("todo cheat");
                    case UP, LEFT, DOWN, RIGHT -> moveHunter(input);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid option! Try again.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid direction! Use WASD.");
                continue;
            }
            // move guardians
//            guardians.forEach(guardian -> guardian.move());
        }


    }
}
