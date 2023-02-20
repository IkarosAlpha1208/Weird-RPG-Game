
public class PC {
    // stats
    public static String name;
    public static int maxHp;
    public static int maxMp;
    public static int exp;
    public static int requireExp;
    public static int level;
    // attributes
    public static int statsPoint;
    public static int str;
    public static int dex;
    public static int con;
    public static int wis;
    public static int cha;
    // dynamics stats
    public static int hp;
    public static int mp;
    public static int atk;
    public static int def;
    public static int money;

    public static void getName(String word) {
        name = word;
    }

    public static void newPC() {
        str = 1;
        dex = 1;
        con = 1;
        wis = 1;
        cha = 1;
        maxHp = 10;
        hp = maxHp;
        maxMp = 10;
        mp = maxMp;
        exp = 0;
        requireExp = 10;
        level = 1;
        money = 10000;
        atk = 1;
        def = 0;
    }

    public static void continuePC(int[] stats) {
        str = stats[0];
        dex = stats[1];
        con = stats[2];
        wis = stats[3];
        maxHp = stats[4];
        hp = stats[5];
        maxMp = stats[6];
        mp = stats[7];
        exp = stats[8];
        requireExp = stats[9];
        level = stats[10];
        money = stats[11];
        atk = stats[12];
        def = stats[13];
    }

    public static void damage(int num)
    {
        hp -= num;
    }

    public static void ability()
    {
        if(mp >= 8)
        {
            mp -= 8;
        }
    }

    public static void loot(int num)
    {
        exp += num;
        money += 2*num;
    }

    public static void levelUp()
    {
        level++;
        exp -= requireExp;
        requireExp += 10;
        con++;
        statsPoint = 1;
    }

    public static void loseMoney(int num)
    {
        money -= num;
    }

    public static void potion(int buttonNum)
    {
        int[] itemEff = {5,20,50,100,5,20,50,100};
        if(buttonNum < 4)
        {
            hp += itemEff[buttonNum];
        }
        else{
            mp += itemEff[buttonNum];
        }
        if(hp > maxHp)
        {
            hp = maxHp;
        }
        if(mp > maxMp)
        {
            mp = maxMp;
        }
    }

    public static void addStat(int num)
    {
        if(num == 0)
        {
            str++;
        }
        if(num == 1)
        {
            dex++;
        }
        if(num == 2)
        {
            con++;
        }
        if(num == 3)
        {
            wis++;
        }
        statsPoint = 0;
    }

    public static void hotelReset()
    {
        hp = maxHp;
        mp = maxMp;
    }

    public static void updatePC(int weapon, int armors) {
        maxHp = 10 * con;
        maxMp = 5 + 5 * wis;
        atk = str*2 + weapon;
        def = armors + con/2;
    }

    public static int[] savePC() {
        int[] pcStats = { str, dex, con, wis, maxHp, hp, maxMp, mp, exp, requireExp, level, money, atk, def };
        return pcStats;
    }
}
