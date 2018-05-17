
import java.awt.Color;
import java.util.ArrayList;

import javalib.impworld.*;
import javalib.worldimages.*;




class ForbiddenIslandWorld extends World {

    // size of an island

    static final int ISLAND_SIZE = 64;
    
    // pixel size of a single square
    
    static final int SQUARE_SIZE = 15;

    static final int ISLAND_MAX_HEIGHT = ISLAND_SIZE / 2;
    
    // nudge factor
    static final double NUDGE = .8;

    // ocean factor
    static final double OCEAN_FACTOR = .5;

    // All the cells of the game, including the ocean

    IList<Cell> board = new Empty<Cell>();

    // the current height of the ocean

    int waterHeight;

    int ticks;

    Player player;

    IList<Target> targets;

    Target helicopter;

    // 0 : game is running

    // -1 : player lost

    // 1 : player won

    int end;



    ForbiddenIslandWorld() {

        super();

        this.waterHeight = 0;

        this.ticks = 0;

        this.end = 0;

    }



    Cell makeCell(int x, int y, double height) {

        if (height > this.waterHeight) {

            return new Cell(x, y, height);

        } else {

            return new OceanCell(x, y, height);

        }

    }



    void initUnits() {

        this.player = new Player(this.board);

        this.targets = new Cons<Target>(new Target(this.board),

                new Empty<Target>());

        this.targets = new Cons<Target>(new Target(this.board), this.targets);

        this.targets = new Cons<Target>(new Target(this.board), this.targets);

        this.targets = new Cons<Target>(new Target(this.board), this.targets);

        this.helicopter = new HelicopterTarget(this.board);

    }



    WorldScene displaysTargets(WorldScene ws) {

        Iterator<Target> iter = this.targets.getIterator();

        while (iter.hasNext()) {

            iter.next().display(ws);

        }

        return ws;



    }



    public WorldScene makeScene() {

        WorldScene ws = new WorldScene(ISLAND_SIZE * SQUARE_SIZE, ISLAND_SIZE * SQUARE_SIZE);

        Iterator<Cell> cellIter = this.board.getIterator();

        while (cellIter.hasNext()) {

            Cell cell = cellIter.next();

            ws.placeImageXY(cell.displayCell(this.waterHeight), cell.x * SQUARE_SIZE,

                    cell.y * SQUARE_SIZE);
        }

            this.helicopter.display(ws);

            this.displaysTargets(ws);

            this.player.display(ws);

        return ws;

    }



    void setBoard() {

        ArrayList<ArrayList<Cell>> cells = this.generateCells();

        for (int x = 0; x <= ISLAND_SIZE; x ++) {

            for (int y = 0; y <= ISLAND_SIZE; y ++) {

                this.board = new Cons<Cell>(cells.get(x).get(y), this.board);

            }

        }

        this.initUnits();

    }



    ArrayList<ArrayList<Cell>> generateCells() {

        ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();

        ArrayList<ArrayList<Double>> heights = this

                .generateHeights(ISLAND_MAX_HEIGHT);

        for (int x = 0; x <= ISLAND_SIZE; x ++) {

            cells.add(new ArrayList<Cell>());

            for (int y = 0; y <= ISLAND_SIZE; y ++) {

                double height = heights.get(x).get(y);

                cells.get(x).add(this.makeCell(x, y, height));

            }

        }

        this.setAdjacent(cells);

        return cells;

    }



    void setAdjacent(ArrayList<ArrayList<Cell>> cells) {

        for (int x = 0; x <= ISLAND_SIZE; x ++) {

            for (int y = 0; y <= ISLAND_SIZE; y ++) {

                if (x > 0) {

                    cells.get(x).get(y).left = cells.get(x - 1).get(y);

                } else {

                    cells.get(x).get(y).left = cells.get(x).get(y);

                }

                if (x < ISLAND_SIZE) {

                    cells.get(x).get(y).right = cells.get(x + 1).get(y);

                } else {

                    cells.get(x).get(y).right = cells.get(x).get(y);

                }

                if (y > 0) {

                    cells.get(x).get(y).top = cells.get(x).get(y - 1);

                } else {

                    cells.get(x).get(y).top = cells.get(x).get(y);

                }

                if (y < ISLAND_SIZE) {

                    cells.get(x).get(y).bottom = cells.get(x).get(y + 1);

                } else {

                    cells.get(x).get(y).bottom = cells.get(x).get(y);

                }

            }

        }

    }



    ArrayList<ArrayList<Double>> generateHeights(int peak) {

        ArrayList<ArrayList<Double>> heights = new ArrayList<ArrayList<Double>>();

        for (int x = 0; x <= ISLAND_SIZE; x ++) {

            heights.add(new ArrayList<Double>());

            for (int y = 0; y <= ISLAND_SIZE; y ++) {

                heights.get(x).add((double) peak);

            }

        }

        return heights;

    }



    @Override

    public void onTick() {

        if (this.ticks % SQUARE_SIZE == 0 && this.ticks != 0) {

            this.waterHeight ++;
            
            this.flood(this.board.getIterator());

            this.end = this.isUnitFlooded();

        }

        this.ticks ++;

        this.targets = this.collectTargets();

        this.flyAway();

    }



    void flood(Iterator<Cell> cellIter) {

        IList<Cell> cells = new Empty<Cell>();

        while (cellIter.hasNext()) {

            Cell cell = cellIter.next();

            if (!cell.isFlooded && cell.height <= this.waterHeight) {

                if (cell.bottom.isFlooded || cell.top.isFlooded

                        || cell.left.isFlooded || cell.right.isFlooded) {

                    cell.isFlooded = true;

                }

            }

        }

    }



    int isUnitFlooded() {



        if (this.player.currentCell.isFlooded) {

            return -1;

        }

        if (this.helicopter.locCell.isFlooded) {

            return -1;

        }

        Iterator<Target> iter = this.targets.getIterator();

        while (iter.hasNext()) {

            if (iter.next().locCell.isFlooded) {

                return -1;

            }

        }



        return 0;

    }



    IList<Target> collectTargets() {

        Iterator<Target> iter = this.targets.getIterator();

        IList<Target> result = new Empty<Target>();

        while (iter.hasNext()) {

            Target target = iter.next();

            if (!target.locCell.cellEquals(this.player.currentCell)) {

                result = new Cons<Target>(target, result);

            }

        }

        return result;

    }



    void flyAway() {

        if (this.player.currentCell.cellEquals(this.helicopter.locCell)

                && !this.targets.isCons()) {

            this.end = 1;

        }

    }



    public void onKeyEvent(String ke) {

        if (ke.equals("m")) {

        	//this.stopWorld();
        	
            DiamondMountain world = new DiamondMountain();

            world.bigBang(ISLAND_SIZE * SQUARE_SIZE, ISLAND_SIZE * SQUARE_SIZE, 0.3);

        }

        if (ke.equals("r")) {

        	//this.stopWorld();

            DiamondRandom world = new DiamondRandom();

            world.bigBang(ISLAND_SIZE * SQUARE_SIZE, ISLAND_SIZE * SQUARE_SIZE, 0.3);

        }

        if (ke.equals("t")) {

            //this.stopWorld();

            RandomIsland world = new RandomIsland();

            world.bigBang(ISLAND_SIZE * SQUARE_SIZE, ISLAND_SIZE * SQUARE_SIZE, 0.3);

        }

        if (ke.equals("left")) {

            this.player.moveLeft();

        }

        if (ke.equals("right")) {

            this.player.moveRight();

        }

        if (ke.equals("up")) {

            this.player.moveUp();

        }

        if (ke.equals("down")) {

            this.player.moveDown();

        }



    }



    public WorldEnd worldEnds() {

        if (this.end == 1) {

            WorldImage endGameText = new TextImage("You WON!", ISLAND_SIZE * 2,

                    Color.GREEN);

            WorldScene ws = new WorldScene(ISLAND_SIZE * SQUARE_SIZE, ISLAND_SIZE * SQUARE_SIZE);

            ws.placeImageXY(endGameText, ISLAND_SIZE * (SQUARE_SIZE/2), ISLAND_SIZE * (SQUARE_SIZE/2));

            return new WorldEnd(true, ws);

        } else if (this.end == -1) {

            WorldImage endGameText = new TextImage("You LOST!", ISLAND_SIZE * 2,

                    Color.RED);

            WorldScene ws = new WorldScene(ISLAND_SIZE * SQUARE_SIZE, ISLAND_SIZE * SQUARE_SIZE);

            ws.placeImageXY(endGameText, ISLAND_SIZE * (SQUARE_SIZE/2), ISLAND_SIZE * (SQUARE_SIZE/2));

            return new WorldEnd(true, ws);

        } else {

            return new WorldEnd(false, makeScene());

        }

    }

}


















