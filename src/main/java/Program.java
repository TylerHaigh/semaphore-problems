public class Program {

    public static final int FarmersToGenerate = 1;
    public static final boolean GenerateNorthFarmers = true;
    public static final boolean GenerateSouthFarmers = true;

    public static void main(String[] args) {

        Bridge bridge = new Bridge();

        if (GenerateNorthFarmers)
            generateFarmers(Island.North, FarmersToGenerate, bridge);
        if (GenerateSouthFarmers)
            generateFarmers(Island.South, FarmersToGenerate, bridge);

    }

    private static void generateFarmers(Island island, int farmersToGenerate, Bridge bridge) {
        for(int i = 0; i < farmersToGenerate; i++) {
            Farmer f = new Farmer(i, island, bridge);
            Thread newThread = new Thread(f);
            newThread.start();
        }
    }

}
