import java.util.Random;

import javalib.impworld.WorldScene;
import javalib.worldimages.FromFileImage;
import javalib.worldimages.WorldImage;

class Player {

    Cell currentCell;



    Player(IList<Cell> cells) {

        this.initCell(cells);

    }



    private void initCell(IList<Cell> cells) {

        currentCell = null;

        Random random = new Random();

        while (currentCell == null) {

            Iterator<Cell> iter = cells.getIterator();

            int count = random.nextInt(ForbiddenIslandWorld.ISLAND_SIZE

                    * ForbiddenIslandWorld.ISLAND_SIZE);

            for (int i = 0; i < count - 1; i += 1) {

                iter.next();

            }

            Cell cell = iter.next();

            //System.out.println(cell.height);

            if (cell.height > 5) {

                currentCell = cell;

            }



        }

    }



    WorldScene display(WorldScene ws) {

        WorldImage image = new FromFileImage("pilot.png");

        ws.placeImageXY(image, this.currentCell.x * ForbiddenIslandWorld.SQUARE_SIZE,

                this.currentCell.y * ForbiddenIslandWorld.SQUARE_SIZE);

        return ws;

    }



    void moveRight() {

        if (!this.currentCell.right.isFlooded) {

            this.currentCell = this.currentCell.right;

        }

    }



    void moveLeft() {

        if (!this.currentCell.left.isFlooded) {

            this.currentCell = this.currentCell.left;

        }

    }



    void moveUp() {

        if (!this.currentCell.top.isFlooded) {

            this.currentCell = this.currentCell.top;

        }

    }



    void moveDown() {

        if (!this.currentCell.bottom.isFlooded) {

            this.currentCell = this.currentCell.bottom;

        }

    }

}