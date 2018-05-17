import java.util.ArrayList;
import java.util.Random;

class RandomIsland extends ForbiddenIslandWorld {



    RandomIsland() {

        super();

        this.setBoard();

    }


    ArrayList<ArrayList<Double>> generateHeights(int peak) {

        ArrayList<ArrayList<Double>> heights = new ArrayList<ArrayList<Double>>();

        for (int x = 0; x <= ISLAND_SIZE; x ++) {

            heights.add(new ArrayList<Double>());

            for (int y = 0; y <= ISLAND_SIZE; y ++) {

                heights.get(x).add(0.0);

            }

        }

        

        heights.get(0).set(ISLAND_MAX_HEIGHT, 1.0);

        heights.get(ISLAND_SIZE).set(ISLAND_MAX_HEIGHT, 1.0);

        heights.get(ISLAND_MAX_HEIGHT).set(0, 1.0);
        
        heights.get(ISLAND_MAX_HEIGHT).set(0, 1.0);

        heights.get(ISLAND_MAX_HEIGHT).set(ISLAND_MAX_HEIGHT, (double) peak);


        for (int i = ISLAND_MAX_HEIGHT; i > 2; i = (i / 2) + 1) {

            // calculate mids along horizontal
            for (int j = i / 2; j < ISLAND_SIZE; j += (i - 1)) {
                for (int k = 0; k < ISLAND_SIZE; k += (i - 1)) {

                    heights.get(j).set(
                            k,
                            this.getHeightLine(heights.get(j - (i / 2)).get(k),
                                    heights.get(j + (i / 2)).get(k), i * i));
                }
            }

            // calculate mids along vertical
            for (int j = 0; j < ISLAND_SIZE; j += (i - 1)) {
                for (int k = i / 2; k < ISLAND_SIZE; k += (i - 1)) {
                    heights.get(j).set(
                            k,
                            this.getHeightLine(heights.get(j).get(k - (i / 2)),
                                    heights.get(j).get(k + (i / 2)), i * i));
                }
            }

            // calculate mids of mids
            for (int j = i / 2; j < ISLAND_SIZE; j += (i - 1)) {
                for (int k = i / 2; k < ISLAND_SIZE; k += (i - 1)) {
                    heights.get(j).set(k, this.getHeightRect(
                    // top
                            heights.get(j).get(k - (i / 2)),
                            // bot
                            heights.get(j).get(k + (i / 2)),
                            // left
                            heights.get(j - (i / 2)).get(k),
                            // right
                            heights.get(j + (i / 2)).get(k), i * i));
                }
            }
        }
        

//        this.heightsHelper(heights, 0, 0, ISLAND_MAX_HEIGHT);
//
//        this.heightsHelper(heights, ISLAND_MAX_HEIGHT, 0, ISLAND_MAX_HEIGHT);
//
//        this.heightsHelper(heights, 0, ISLAND_MAX_HEIGHT, ISLAND_MAX_HEIGHT);
//
//        this.heightsHelper(heights, ISLAND_MAX_HEIGHT, ISLAND_MAX_HEIGHT,
//
//        		ISLAND_MAX_HEIGHT);

        return heights;

    }
    
    double getHeightLine(double a, double b, int area) {
        return Math.min(ISLAND_MAX_HEIGHT,
                (Math.random() - OCEAN_FACTOR)
                        * NUDGE * Math.sqrt(area)
                        + ((a + b) / 2.0));
    }

    double getHeightRect(double a, double b, double c, double d, int area) {
        return Math.min(ISLAND_MAX_HEIGHT,
                (Math.random() - OCEAN_FACTOR)
                        * NUDGE * Math.sqrt(area)
                        + ((a + b + c + d) / 4.0));
    }




    void heightsHelper(ArrayList<ArrayList<Double>> heights, int x, int y,

            int size) {

        Random random = new Random();



        double tl = heights.get(x).get(y);

        double tr = heights.get(x + size).get(y);

        double bl = heights.get(x).get(y + size);

        double br = heights.get(x + size).get(y + size);



        double t = ((tl + tr) / 2)

                + ((size * random.nextDouble()) - (size / 2 + 2));

        heights.get(x + (size / 2)).set(y, t);

        double l = ((tl + bl) / 2)

                + ((size * random.nextDouble()) - (size / 2 + 2));

        heights.get(x).set(y + (size / 2), l);

        double r = ((tr + br) / 2)

                + ((size * random.nextDouble()) - (size / 2 + 2));

        heights.get(size).set(y + (size / 2), r);

        double b = ((bl + br) / 2)

                + ((size * random.nextDouble()) - (size / 2 + 2));

        heights.get(x + (size / 2)).set(size, b);



        double m = ((tl + tr + bl + br) / 4)

                + ((size * random.nextDouble()) - (size / 2 + 2));

        heights.get(x + (size / 2)).set(y + (size / 2), m);



        if (size / 2 > 1) {

            this.heightsHelper(heights, x, y, (size / 2));

            this.heightsHelper(heights, x + (size / 2), y, (size / 2));

            this.heightsHelper(heights, x, y + (size / 2), (size / 2));

            this.heightsHelper(heights, x + (size / 2), y + (size / 2),

                    (size / 2));

        }



    }

}