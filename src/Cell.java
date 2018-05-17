import java.awt.Color;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

//Represents a single square of the game area

class Cell {

    // represents absolute height of this cell, in feet

    double height;

    // In logical coordinates, with the origin at the top-left corner of the

    // screen

    int x;

    int y;

    // the four adjacent cells to this one

    Cell left;

    Cell top;

    Cell right;

    Cell bottom;

    // reports whether this cell is flooded or not

    boolean isFlooded;



    Cell(int x, int y, double height) {

        this.x = x;

        this.y = y;

        this.height = height;

        this.isFlooded = false;

        this.left = null;

        this.top = null;

        this.right = null;

        this.bottom = null;

    }



    WorldImage displayCell(int waterHeight) {

        Color color = null;

        double heightDiff = waterHeight - this.height;

        if (this.isFlooded) {

            color = new Color(
                    0f, 0f,
                    Math.min(1f, Math.max(0f, 0.5f - 
                            (float)(heightDiff / 
                                    ForbiddenIslandWorld.ISLAND_SIZE))));

        } else if (this.height <= waterHeight) {

            color = new Color(
                    Math.min(0.5f + (float)
                            (heightDiff / 
                                    ( ForbiddenIslandWorld.ISLAND_MAX_HEIGHT )),
                            1f),
                    Math.max(0.5f - (float) 
                            (heightDiff / 
                                    ( ForbiddenIslandWorld.ISLAND_MAX_HEIGHT )), 
                            0), 0f); 

        } else {

            double heightPercent = -(heightDiff)

                    / ForbiddenIslandWorld.ISLAND_MAX_HEIGHT;

            color = new Color(0, 128, 0, 255 - (int) (255 * heightPercent));

        }

        return new RectangleImage(ForbiddenIslandWorld.SQUARE_SIZE, ForbiddenIslandWorld.SQUARE_SIZE, OutlineMode.SOLID, color);

    }
    


    boolean cellEquals(Cell that) {

        return this.x == that.x && this.y == that.y;

    }

}



class OceanCell extends Cell {



    OceanCell(int x, int y, double height) {

        super(x, y, height);

        this.isFlooded = true;

    }

}