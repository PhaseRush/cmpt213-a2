package ca.sfu.cmpt213.a2.model;

import ca.sfu.cmpt213.a2.model.entity.Entity;
import ca.sfu.cmpt213.a2.model.entity.Guardian;
import ca.sfu.cmpt213.a2.model.entity.Hunter;
import ca.sfu.cmpt213.a2.model.entity.Relic;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static ca.sfu.cmpt213.a2.model.Coordinate.*;
import static ca.sfu.cmpt213.a2.model.Maze.HEIGHT;
import static ca.sfu.cmpt213.a2.model.Maze.WIDTH;

public class Game {
    private static final Random rand = ThreadLocalRandom.current();
    private static final Set<Coordinate> directions = Set.of(
            at(-1, -1),     // up left
            at(0, -1),      // up
            at(1, -1),      // up right
            at(-1, 0),      // left
            at(1, 0),       // right
            at(-1, 1),      // down left
            at(0, 1),       // down
            at(1, 1)        // down right
    );

    public static final Map<Input, Coordinate> CARDINALS = Map.of(
            Input.UP, at(0, -1),            // up
            Input.LEFT, at(-1, 0),          // left
            Input.RIGHT, at(1, 0),          // right
            Input.DOWN, at(0, 1)           // down
    );
    private final Scanner sc;
    private Maze maze;
    private final Hunter hunter;
    private final List<Entity> entities;
    private final boolean[][] discovered;
    private int relicsLeft;

    public Game(Maze maze) {
        this.maze = maze;
        this.relicsLeft = 3;
        this.sc = new Scanner(System.in);


        // init discovered with walls and player pos
        this.discovered = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            this.discovered[i][0] = true;
            this.discovered[i][WIDTH - 1] = true;
        }
        for (int i = 0; i < WIDTH; i++) {
            this.discovered[0][i] = true;
            this.discovered[HEIGHT - 1][i] = true;
        }
        reveal(at(1, 1));
        hunter = new Hunter(at(1, 1));

        entities = List.of(
                hunter, // top left
                new Relic(generateRelic()), // relic
                new Guardian(at(WIDTH - 2, HEIGHT - 2)), // bottom right
                new Guardian(at(1, HEIGHT - 2)), // bottom left
                new Guardian(at(WIDTH - 2, 1))); // top right
        entities.forEach(e -> maze.getMaze()[e.y()][e.x()] = e.getTile());
    }

    private Coordinate generateRelic() {
        int relicX, relicY;
        do {
            relicX = rand.nextInt(WIDTH);
            relicY = rand.nextInt(HEIGHT);
        } while ((maze.getMaze()[relicY][relicX].equals(Tile.HUNTER) ||
                maze.getMaze()[relicY][relicX].equals(Tile.WALL)) &&
                (relicX == 1 && relicY == 1)); // do not spawn relic on hunter at start
        maze.getMaze()[relicY][relicX] = Tile.RELIC;
        return at(relicX, relicY);
    }

    private void reveal(Coordinate coord) {
        discovered[coord.y()][coord.x()] = true; // discover self
        for (Coordinate delta : directions) {
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
//                Coordinate here = Coordinate.at(j, i);
//                var special = entities.stream().filter(e -> e.getPosition().equals(here)).findAny();
//                if (special.isPresent()) {
//                    System.out.print(special.get().getSymbol() + " ");
//                } else {
//                    var curr = maze[i][j].toString();
//                    System.out.print((curr.equals(" ") ? " " : "■") + " ");
//                }
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

    public enum Input {
        REVEAL("M"),
        CHEAT("C"),
        UP("W"),
        LEFT("A"),
        DOWN("S"),
        RIGHT("D");

        final String key;
        static Input get(String value) {
            return Arrays.stream(Input.values()).filter(input -> input.key.equals(value)).findAny().orElse(UP);
        }

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

    public boolean checkCollisions() {
        // check hunters overlap any guardian
        if (entities.stream()
                .filter(e -> (e instanceof Guardian))
                .anyMatch(entity -> entity.getPosition().equals(hunter.getPosition()))) {
            return true;
        }
        if (entities.stream()
                .filter(e -> (e instanceof Relic))
                .anyMatch(entity -> entity.getPosition().equals(hunter.getPosition()))) {
            relicsLeft--;
        }
        return false;
    }

    public void play() {
        start();
        boolean gameOver = false;

//        showHidden();
        do {
            try {
                maze.showMaze(entities);
                var input = Input.get( sc.nextLine().toUpperCase());
                switch (input) {
                    case REVEAL -> maze.showMaze(entities);
                    case CHEAT -> relicsLeft = 1;
//                    case UP, LEFT, DOWN, RIGHT -> moveHunter(input);
                }
                entities.forEach(e -> e.move(maze, input));
                gameOver = checkCollisions();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid option! Try again.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid direction! Use WASD.");
            }

        } while (!gameOver);
    }
}
