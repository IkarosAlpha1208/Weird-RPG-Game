public class Enemy {
    public static String bossA = "Gob Lin Ds";
    public static int[] boss1 = {20, 10, 5, 5, 3}; // 0 is hp, 1 is atk, 2 is def, 3 level(exp you gain), 4 is speed
    public static String bossB = "Loaded Toad";
    public static int[] boss2 = {50, 20, 10, 10, 5};
    public static String bossC = "OrnnStar";
    public static int[] boss3 = {100, 30, 15, 15, 7};
    public static String bossD = "Je Sus";
    public static int[] boss4 = {200, 50, 50, 20, 9};
    public static String bossE = "Choseph Chostar";
    public static int[] boss5 = {999, 99, 99, 100, 20};
    public static String[] mobs = {"chicken", "cat", "turtle"};
    public static int[] cat = {5, 2, 0, 1, 1};
    public static int[] chicken = {10, 3, 1, 2, 2};
    public static int[] turtle = {20, 1, 3, 3, 0};

    public static int[] resetEnemyStats(int gameState, int rando, boolean boss)
    {
        int[] tempEnemyStats = new int[5];
        if(gameState == 7 && boss)
        {
            tempEnemyStats = boss1;
        }
        else if(gameState == 8 && boss)
        {
            tempEnemyStats = boss2;
        }
        else if(gameState == 9 && boss)
        {
            tempEnemyStats = boss3;
        }
        else if(gameState == 10 && boss)
        {
            tempEnemyStats = boss4;
        }
        else if(gameState == 5 && boss)
        {
            tempEnemyStats = boss5;
        }
        else if(boss == false)
        {
            if(rando == 0)
            {
                tempEnemyStats[3] = cat[3] + gameState*5;
                tempEnemyStats[0] = cat[0] + 2*(tempEnemyStats[3]-1);
                tempEnemyStats[1] = cat[1] + 2*(tempEnemyStats[3]-1);
                tempEnemyStats[2] = cat[2];
                tempEnemyStats[4] = cat[4];
            }
            if(rando == 1)
            {
                tempEnemyStats[3] = chicken[3] + gameState*5;
                tempEnemyStats[0] = chicken[0] + 4*(tempEnemyStats[3]-2);
                tempEnemyStats[1] = chicken[1] + 3*(tempEnemyStats[3]-2);
                tempEnemyStats[2] = chicken[2] + 1*(tempEnemyStats[3]-2);
                tempEnemyStats[4] = chicken[4];
            }
            if(rando == 2)
            {
                tempEnemyStats[3] = turtle[3] + gameState*5;
                tempEnemyStats[0] = turtle[0] + 10*(tempEnemyStats[3]-3);
                tempEnemyStats[1] = turtle[1] + 2*(tempEnemyStats[3]-3);
                tempEnemyStats[2] = turtle[2] + 2*(tempEnemyStats[3]-3);
                tempEnemyStats[4] = turtle[4];
            }
        }
        return tempEnemyStats;
    }
}
