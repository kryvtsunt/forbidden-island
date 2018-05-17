import java.awt.Color;
import java.util.Random;

import javalib.impworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.FromFileImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.WorldImage;

class Target {

    Cell locCell;



    Target(IList<Cell> cells) {

        this.initCell(cells);

    }



    private void initCell(IList<Cell> cells) {

        locCell = null;

        Random random = new Random();

        while (locCell == null) {

            Iterator<Cell> iter = cells.getIterator();

            int count = random.nextInt(ForbiddenIslandWorld.ISLAND_SIZE

                    * ForbiddenIslandWorld.ISLAND_SIZE);

            for (int i = 0; i < count - 1; i += 1) {

                iter.next();

            }

            Cell cell = iter.next();

            if (cell.height > 10) {

                locCell = cell;

            }



        }

    }



    WorldScene display(WorldScene ws) {

        
        WorldImage image2 = new CircleImage(5, OutlineMode.SOLID, Color.RED);
        WorldImage image = new CircleImage(7, OutlineMode.SOLID, Color.ORANGE);

        ws.placeImageXY(image, this.locCell.x * ForbiddenIslandWorld.SQUARE_SIZE, this.locCell.y * ForbiddenIslandWorld.SQUARE_SIZE);
        ws.placeImageXY(image2, this.locCell.x * ForbiddenIslandWorld.SQUARE_SIZE, this.locCell.y * ForbiddenIslandWorld.SQUARE_SIZE);

        return ws;

    }



}



class HelicopterTarget extends Target {

    HelicopterTarget(IList<Cell> cells) {

        super(cells);

        this.initCell(cells);

    }



    private void initCell(IList<Cell> cells) {

        Iterator<Cell> iter = cells.getIterator();

        int count = (ForbiddenIslandWorld.ISLAND_SIZE

                * ForbiddenIslandWorld.ISLAND_SIZE) / 2;

        for (int i = 0; i < count - 1; i += 1) {

            iter.next();

        }

        locCell = iter.next();

    }



    WorldScene display(WorldScene ws) {

        WorldImage image = new FromFileImage("helicopter.png");

        ws.placeImageXY(image, (this.locCell.x-1) * ForbiddenIslandWorld.SQUARE_SIZE, this.locCell.y * ForbiddenIslandWorld.SQUARE_SIZE);

        return ws;

    }

}
