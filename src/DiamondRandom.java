import java.util.ArrayList;
import java.util.Random;

class DiamondRandom extends ForbiddenIslandWorld {



    DiamondRandom() {

        super();

        this.setBoard();

    }



    ArrayList<ArrayList<Double>> generateHeights(int peak) {

        ArrayList<ArrayList<Double>> heights = new ArrayList<ArrayList<Double>>();

        Random random = new Random();

        for (int x = 0; x <= ISLAND_SIZE; x += 1) {

            heights.add(new ArrayList<Double>());

            for (int y = 0; y <= ISLAND_SIZE; y += 1) {

                double manDist = (Math.abs(x - (ISLAND_SIZE / 2))

                        + Math.abs(y - (ISLAND_SIZE / 2)));

                double height = peak - manDist;

                if (height > 0) {

                    height = random.nextDouble() * 32;

                    heights.get(x).add(height);

                } else {

                    heights.get(x).add(height);

                }

            }

        }

        return heights;

    }

}


