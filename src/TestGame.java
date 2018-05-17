import tester.*;


class TestGame {

    void testRandomIsland(Tester t) {
        RandomIsland island = new RandomIsland();  
        island.bigBang(ForbiddenIslandWorld.ISLAND_SIZE * ForbiddenIslandWorld.SQUARE_SIZE,
                ForbiddenIslandWorld.ISLAND_SIZE * ForbiddenIslandWorld.SQUARE_SIZE, .2);
    }
    
//    void testDiamondIsland(Tester t) {
//        DiamondMountain island = new DiamondMountain();  
//        island.bigBang(ForbiddenIslandWorld.ISLAND_SIZE * 10,
//                ForbiddenIslandWorld.ISLAND_SIZE * 10, .7);
//    }
//    
//    void testDiamondRandomIsland(Tester t) {
//        DiamondRandom island = new DiamondRandom();  
//        island.bigBang(ForbiddenIslandWorld.ISLAND_SIZE * 10,
//                ForbiddenIslandWorld.ISLAND_SIZE * 10, .7);
//    }
    
    boolean testmakeCell(Tester t) {
        ForbiddenIslandWorld world = new ForbiddenIslandWorld();
        return t.checkExpect(world.makeCell(50, 50, 20),
                new Cell(50, 50, 20))
                && t.checkExpect(world.makeCell(50, 50, -20),
                        new OceanCell(50, 50, -20));
    }
    
    
}