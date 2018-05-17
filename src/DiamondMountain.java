import java.util.ArrayList;

class DiamondMountain extends ForbiddenIslandWorld {


    DiamondMountain() {

        super();

        this.setBoard();



    }



    ArrayList<ArrayList<Double>> generateHeights(int peak) {
    	

        ArrayList<ArrayList<Double>> heights = new ArrayList<ArrayList<Double>>();

        for (int x = 0; x <= ISLAND_SIZE; x++) {

            heights.add(new ArrayList<Double>());

            for (int y = 0; y <= ISLAND_SIZE; y++ ) {

                double manDist = (Math.abs(x - ISLAND_MAX_HEIGHT)

                        + Math.abs(y - ISLAND_MAX_HEIGHT));

                double height = peak - manDist;

                heights.get(x).add(height);

            }

        }

        return heights;

    }
    
   

}