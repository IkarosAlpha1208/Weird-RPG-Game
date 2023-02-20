import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Game_Start extends JPanel implements KeyListener, Runnable {

	// game loop detection
	public static boolean play = true;

	// player related variables
	public static int playerFrameCount = 0;
	public static int pcSpriteSelector = 2;
	public static int pcDirection = 0; // 0 is left, 1 is right
	public static boolean[] isMoving = { false, false, false, false };
	public static BufferedImage[][] pcSprite = new BufferedImage[2][3]; // 0 = walk left and idle, 1 = walk right and
																		// idle

	public static int pcXCord = 582; // pc x dimensions = 54
	public static int pcYCord = 700; // pc y dimensions = 75
	public static boolean moveTurn = true; // use for determining who is going in combat 0 is player 1 is enemy

	// Enemy
	public static int boss = 5; // how much boss is left
	public static boolean bossF = false; // check if is in boss fight
	public static int[] enemy = new int[5];
	public static int enemyType = 0;

	// Game States
	// GS0 -> Title Screen
	// GS1 -> map 1
	// GS2 -> map 2
	// GS3 -> map 3
	// GS4 -> map
	// GS5 ->
	// GS6 -> town
	// GS7 -> map 1 boss
	// GS8 -> map 2 boss
	// GS9 -> map 3 boss
	// GS10 -> map 4 boss
	// GS11 -> map 5 boss
	public static int gameState = 0;

	// screens(title, map, menu)
	public static BufferedImage title;
	public static BufferedImage title2;
	public static BufferedImage settings;
	public static BufferedImage credits;
	public static BufferedImage loading;
	public static BufferedImage inGameMenu;
	public static BufferedImage inGameMenuMarker;
	public static BufferedImage shop;
	public static BufferedImage hotel;
	public static BufferedImage smith;
	public static BufferedImage shopMarker1;
	public static BufferedImage shopMarker2;
	public static BufferedImage hotelMarker;
	public static BufferedImage smithMarker;
	public static BufferedImage inventoryScreen;
	public static BufferedImage inventoryMarker;
	public static BufferedImage inventoryMarker2;
	public static BufferedImage equipmentScreen;
	public static BufferedImage equipmentScreenMarker;
	public static BufferedImage equipmentScreenMarker2;
	public static BufferedImage[] weapon = new BufferedImage[6];
	public static BufferedImage[] helm = new BufferedImage[6];
	public static BufferedImage[] chest = new BufferedImage[6];
	public static BufferedImage[] legs = new BufferedImage[6];
	public static BufferedImage[] boots = new BufferedImage[6];
	public static BufferedImage[] ability = new BufferedImage[6];
	public static BufferedImage combatScreen;
	public static BufferedImage combatMarker;
	public static BufferedImage titleMarker;
	public static BufferedImage titleMarker2;
	public static BufferedImage townMap;
	public static BufferedImage[] monsters = new BufferedImage[3]; // 0 is cat, 1 is chiken, 2 is turtle
	public static BufferedImage map1;
	public static BufferedImage map2;
	public static BufferedImage map3;
	public static BufferedImage map4;
	public static BufferedImage map5;
	public static BufferedImage map1Boss;
	public static BufferedImage map2Boss;
	public static BufferedImage map3Boss;
	public static BufferedImage map4Boss;
	public static BufferedImage bo1ss;
	public static BufferedImage bo2ss;
	public static BufferedImage bo3ss;
	public static BufferedImage bo4ss;
	public static BufferedImage bo5ss;

	// Game Stats
	public static int buttonNum = 0; // Will determine which button will be pressed and highlighted by marker
	public static int buttonX = 25; // Determines X location of button marker
	public static int buttonY = 25; // Determines Y location of button marker
	public static int selectEquip = 1; // Determines which item to equip
	public static boolean combating = false;
	public static int[] item = new int[8];
	public static int[] weaponOwned = new int[6];
	public static int[] helmOwned = new int[6];
	public static int[] chestOwned = new int[6];
	public static int[] legsOwned = new int[6];
	public static int[] bootsOwned = new int[6];
	public static int[] abilityOwned = new int[6];
	public static int[] equipped = new int[6]; // 0 is weapon, 1 - 4 is helm, chest, leg, boot, 5 is ability
	public static int[] equipStats = new int[5];
	public static int continueGame = 0; // 0 is not continue, 1 is yes continue
	public static int menu = 0; // 1 is in game menu, 2 is combat menu, 3 inevtory, 4 is equipment, 5 is
								// shop, 6 is controls, 7 is loading, 8 is black smith, 9 hotel, 10 credits, 11
								// is level up
	public static int marker = 1;
	public static int[] shopArmor = new int[6];

	// Misc stuff
	public static Clip music1;
	public static Clip map2Music;
	public static Clip boss1Music;
	public static Clip boss2Music;
	public static Clip boss3Music;
	public static Clip boss4Music;
	public static Random rando = new Random();
	public static int load = 0;
	public static boolean loadScreen = false;

	public static void PlayMusic() {
		try {
			if (gameState == 2) {
				map2Music.start();
				map2Music.loop(Clip.LOOP_CONTINUOUSLY);
			}
			if (gameState == 6) {
				music1.start();
				music1.loop(Clip.LOOP_CONTINUOUSLY);
			}
			if (gameState == 7) {
				boss1Music.start();
				boss1Music.loop(Clip.LOOP_CONTINUOUSLY);
			}
			if (gameState == 8) {
				boss2Music.start();
				boss2Music.loop(Clip.LOOP_CONTINUOUSLY);
			}
			if (gameState == 9) {
				boss3Music.start();
				boss3Music.loop(Clip.LOOP_CONTINUOUSLY);
			}
			if (gameState == 10) {
				boss4Music.start();
				boss4Music.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} catch (Exception e) {
		}
	}

	public static void StopMusic() {
		try {
			map2Music.stop();
			music1.stop();
			boss1Music.stop();
			boss2Music.stop();
			boss3Music.stop();
			boss4Music.stop();
		} catch (Exception e) {
		}
	}

	public static void Title_Screen(char key) {

		if (key == 's' && buttonNum < 4) {
			buttonNum++;
		}
		if (key == 'w' && buttonNum > 0) {
			buttonNum--;
		}
		if (buttonNum == 0 && key == 'j') {
			PC.newPC();
			pcXCord = 582;
			pcYCord = 700;
			try {
				PrintWriter outputFile = new PrintWriter(new File("SaveData.txt"));
				outputFile.close();
			} catch (Exception e) {
			}
			gameState = 6;
			PlayMusic();
		}
		if (buttonNum == 1 && key == 'j' && continueGame == 1) {
			LoadSave();
			PlayMusic();
		} else if (buttonNum == 2 && key == 'j') {
			System.exit(0);
		} else if (buttonNum == 3 && key == 'j') {
			menu = 6;
			buttonNum = 0;
		} else if (buttonNum == 4 && key == 'j') {
			menu = 10;
			buttonNum = 0;
		}
	}

	public static void Settings_Screen(char key) {
		if (key == 's' && buttonNum < 1) {
			buttonNum++;
		}
		if (buttonNum == 1 && key == 'j' || key == 'k') {
			gameState = 0;
			buttonNum = 0;
			menu = 0;
		}
	}

	public static void Credit_Screen(char key) {
		if (key == 's' && buttonNum < 1) {
			buttonNum++;
		}
		if (buttonNum == 1 && key == 'j' || key == 'k') {
			gameState = 0;
			buttonNum = 0;
			menu = 0;
		}
	}

	public static void combatDetection() {
		int fight = rando.nextInt(4);
		enemyType = rando.nextInt(3);
		if (gameState == 1) {
			if (pcXCord > 270 && pcXCord < 926 && pcYCord > 170 && pcYCord < 554 && fight == 3) {
				loadScreen = true;
				combating = true;
				menu = 2;
				enemy = Enemy.resetEnemyStats(gameState, enemyType, false);
				moveTurn = PC.dex >= enemy[4];
			}
		}
		if (gameState == 2) {
			if (fight == 3) {
				loadScreen = true;
				combating = true;
				menu = 2;
				enemy = Enemy.resetEnemyStats(gameState, enemyType, false);
				moveTurn = PC.dex >= enemy[4];
			}
		}
		if (gameState == 3) {
			if (pcXCord > 338 && pcXCord < 842 && pcYCord > 168 && pcYCord < 584 && fight == 3) {
				loadScreen = true;
				combating = true;
				menu = 2;
				enemy = Enemy.resetEnemyStats(gameState, enemyType, false);
				moveTurn = PC.dex >= enemy[4];
			}
		}
		if (gameState == 4) {
			if (fight == 3) {
				loadScreen = true;
				combating = true;
				menu = 2;
				enemy = Enemy.resetEnemyStats(gameState, enemyType, false);
				moveTurn = PC.dex >= enemy[4];
			}
		}
	}

	public static void inCombat(char key) {
		if (moveTurn) {
			if (key == 's' && buttonNum != 3) {
				buttonNum++;
			}
			if (key == 'w' && buttonNum != 0) {
				buttonNum--;
			}
			if (key == 'j') {
				if (buttonNum == 0) {
					enemy[0] -= PC.atk - enemy[2];
				} else if (buttonNum == 1 && PC.mp >= 8) {
					PC.ability();
					enemy[0] -= PC.atk + 10 * PC.wis - enemy[2];
				} else if (buttonNum == 2) {
					loadScreen = true;
					menu = 3;
					buttonNum = 0;
				} else if (buttonNum == 3) {
					menu = 0;
					buttonNum = 0;
					combating = false;
					bossF = false;
					loadScreen = true;
				}
				moveTurn = false;
			}
		} else {
			if (enemy[1] > PC.def) {
				PC.damage(enemy[1] - PC.def);
				;
			}
			moveTurn = true;
		}
		if (PC.hp < 1) {
			loadScreen = true;
			menu = 0;
			gameState = 0;
			buttonNum = 1;
			combating = false;
		} else if (enemy[0] < 1) {
			PC.loot(enemy[3]);
			if (bossF) {
				bossF = false;
				boss--;
			}
			if (PC.exp >= PC.requireExp) {
				menu = 0;
				PC.levelUp();
				buttonNum = 0;
			} else {
				menu = 0;
			}
			combating = false;
		}
	}

	public static void openMenu(char e) {
		if (e == 'k') {
			buttonNum = 0;
			menu = 1;
		}
	}

	public static void In_Game_Menu(char key) {
		if (key == 's' && buttonNum < 4) {
			buttonNum++;
		}
		if (key == 'w' && buttonNum > 0) {
			buttonNum--;
		}
		if (buttonNum == 0 && key == 'j') {
			menu = 3; // inventory screen
			buttonX = 45;
			buttonY = 45;
		}
		if (buttonNum == 1 && key == 'j') {
			loadScreen = true;
			menu = 4; // equipment screen
			buttonX = 25;
			buttonY = 25;
		}
		if (buttonNum == 2 && key == 'j') {
			Save();
			gameState = 0;
			menu = 0;
			buttonNum = -1;
		}
		if (buttonNum == 3 && key == 'j') {
			gameState = 0;
			buttonNum = 0;
			menu = 0;
		}
		if (buttonNum == 4 && key == 'j') {
			menu = 0;
		}
	}

	public static void Inventory(char key) {
		if (key == 'w' && buttonY > 50) {
			buttonY -= 110;
			buttonNum -= 10;
		}
		if (key == 's' && buttonY <= 490) {
			buttonY += 110;
			buttonNum += 10;
		}
		if (key == 'a' && buttonX > 50) {
			buttonX -= 110;
			buttonNum--;
		}
		if (key == 'd' && buttonX <= 1140) {
			buttonX += 110;
			buttonNum++;
		}
		if (key == 's' && buttonY > 490) {
			marker = 0;
		}
		if (key == 'w' && marker == 0) {
			marker = 1;
		}
		if ((key == 'j' && marker == 0) || key == 'k' && combating == false) {
			buttonY = 25;
			buttonX = 25;
			marker = 1;
			menu = 1;
			buttonNum = 0;
		}
		if (key == 'j' && buttonNum < 8 && loadScreen == false) {
			if (item[buttonNum] > 0) {
				item[buttonNum]--;
				PC.potion(buttonNum);
			}

			if (combating) {
				menu = 2;
				buttonNum = 0;
			}
		}
	}

	public static void Shop(char key) {
		int[] cost = { 100, 200, 300, 400, 100, 200, 300, 400, 10, 100, 100, 100, 100, 100 };

		if (key == 's' && buttonNum < 13) {
			buttonNum++;
		}
		if (key == 'w' && buttonNum > 0) {
			buttonNum--;
		}
		if (key == 'k') {
			menu = 0;
			buttonNum = 0;
		}
		if (key == 'j' && PC.money >= cost[buttonNum]) {
			if (buttonNum < 8) {
				item[buttonNum]++;
				PC.loseMoney(cost[buttonNum]);
				;
			} else if (shopArmor[buttonNum - 8] == 0) {
				PC.loseMoney(cost[buttonNum]);
				;
				shopArmor[buttonNum - 8] = 1;
				if (buttonNum == 8) {
					weaponOwned[0] = 1;
				}
				if (buttonNum == 9) {
					helmOwned[0] = 1;
				}
				if (buttonNum == 10) {
					chestOwned[0] = 1;
				}
				if (buttonNum == 11) {
					legsOwned[0] = 1;
				}
				if (buttonNum == 12) {
					bootsOwned[0] = 1;
				}
				if (buttonNum == 13) {
					abilityOwned[0] = 1;
				}
			}
		}
	}

	public static void Bsmith(char key) {
		int[] cost = { 100, 100, 150, 125, 50 };

		if (key == 's' && buttonNum < 4) {
			buttonNum++;
		}
		if (key == 'w' && buttonNum > 0) {
			buttonNum--;
		}
		if (key == 'k') {
			menu = 0;
			buttonNum = 0;
		}
		if (key == 'j' && PC.money >= cost[buttonNum] && equipped[buttonNum] != 0) {
			equipStats[buttonNum]++;
			PC.loseMoney(cost[buttonNum]);
		}
	}

	public static void Hotel(char key) {
		if (key == 'd' && buttonNum < 1) {
			buttonNum++;
		}
		if (key == 'a' && buttonNum > 0) {
			buttonNum--;
		}
		if (key == 'k' || key == 'j' && buttonNum == 1) {
			menu = 0;
			buttonNum = 0;
		}
		if (key == 'j' && buttonNum == 0) {
			PC.hotelReset();
			menu = 0;
			buttonNum = 0;
		}
	}

	public static void Equipment(char key) {
		if (loadScreen == false) {
			if (key == 'w' && buttonY > 25) {
				buttonY -= 135;
			}
			if (key == 's' && buttonY < 700) {
				buttonY += 135;
			}
			if (key == 'a' && buttonX > 25) {
				buttonX -= 135;
				selectEquip -= 1;
			}
			if (key == 'd' && buttonX <= 565) {
				buttonX += 135;
				selectEquip += 1;
			}
			if (key == 'd' && buttonX > 565) {
				marker = 0;
			}
			if (key == 'a' && marker == 0) {
				marker = 1;
			}
			if ((key == 'j' && marker == 0) || key == 'k') {
				buttonY = 25;
				buttonX = 25;
				selectEquip = 1;
				marker = 1;
				menu = 1;
			}
			if (key == 'j' && marker == 1 && buttonY == 25 && weaponOwned[selectEquip - 1] == 1) {
				if (equipped[0] == selectEquip) {
					equipped[0] = 0;
					equipStats[0]--;
				} else {
					equipped[0] = selectEquip;
					equipStats[0]++;
				}
			}
			if (key == 'j' && marker == 1 && buttonY == 160 && helmOwned[selectEquip - 1] == 1) {
				if (equipped[1] == selectEquip) {
					equipped[1] = 0;
					equipStats[1]--;
				} else {
					equipped[1] = selectEquip;
					equipStats[1]++;
				}
			}
			if (key == 'j' && marker == 1 && buttonY == 295 && chestOwned[selectEquip - 1] == 1) {
				if (equipped[2] == selectEquip) {
					equipped[2] = 0;
					equipStats[2]--;
				} else {
					equipped[2] = selectEquip;
					equipStats[2]++;
					equipStats[2]--;
				}
			}
			if (key == 'j' && marker == 1 && buttonY == 430 && legsOwned[selectEquip - 1] == 1) {
				if (equipped[3] == selectEquip) {
					equipped[3] = 0;
					equipStats[3]--;
				} else {
					equipped[3] = selectEquip;
					equipStats[3]++;
				}
			}
			if (key == 'j' && marker == 1 && buttonY == 565 && bootsOwned[selectEquip - 1] == 1) {
				if (equipped[4] == selectEquip) {
					equipped[4] = 0;
					equipStats[4]--;
				} else {
					equipped[4] = selectEquip;
					equipStats[4]++;
				}
			}
			if (key == 'j' && marker == 1 && buttonY == 700 && abilityOwned[selectEquip - 1] == 1) {
				if (equipped[5] == selectEquip) {
					equipped[5] = 0;
					equipStats[5]--;
				} else {
					equipped[5] = selectEquip;
				}
			}
		}
	}

	public static void Save() {
		try {
			int[] playerStats = PC.savePC();
			PrintWriter outputFile = new PrintWriter(new FileWriter("SaveData.txt"));
			outputFile.println(gameState);
			outputFile.println(pcXCord);
			outputFile.println(pcYCord);
			for (int i = 0; i < 14; i++) {
				outputFile.println(playerStats[i]);
			}
			outputFile.println(boss);
			for (int i = 0; i < 5; i++) {
				outputFile.println(equipStats[i]);
			}
			for (int i = 0; i < 6; i++) {
				outputFile.println(equipped[i]);
			}
			for (int i = 0; i < 8; i++) {
				outputFile.println(item[i]);
			}
			outputFile.println(weaponOwned[0]);
			outputFile.println(helmOwned[0]);
			outputFile.println(chestOwned[0]);
			outputFile.println(legsOwned[0]);
			outputFile.println(bootsOwned[0]);
			outputFile.println(abilityOwned[0]);
			outputFile.println(weaponOwned[0]);
			outputFile.println(weaponOwned[0]);
			for (int i = 0; i < 6; i++) {
				outputFile.println(equipped[i]);
			}
			for (int i = 0; i < 5; i++) {
				outputFile.println(equipStats[i]);
			}
			for (int i = 0; i < 6; i++) {
				outputFile.println(shopArmor[i]);
			}
			outputFile.close();
		} catch (Exception e) {
		}
	}

	public static void LoadSave() {
		try {
			int[] playerStats = new int[14];
			Scanner inputFile = new Scanner(new File("SaveData.txt"));
			gameState = inputFile.nextInt();
			pcXCord = inputFile.nextInt();
			pcYCord = inputFile.nextInt();
			for (int i = 0; i < 14; i++) {
				playerStats[i] = inputFile.nextInt();
			}
			PC.continuePC(playerStats);
			boss = inputFile.nextInt();
			for (int i = 0; i < 5; i++) {
				equipStats[i] = inputFile.nextInt();
			}
			for (int i = 0; i < 6; i++) {
				equipped[i] = inputFile.nextInt();
			}
			for (int i = 0; i < 8; i++) {
				item[i] = inputFile.nextInt();
			}
			weaponOwned[0] = inputFile.nextInt();
			helmOwned[0] = inputFile.nextInt();
			chestOwned[0] = inputFile.nextInt();
			legsOwned[0] = inputFile.nextInt();
			bootsOwned[0] = inputFile.nextInt();
		} catch (Exception e) {
		}
	}

	public static void playerAnimation() {
		playerFrameCount += 1;
		if (playerFrameCount == 10) {
			if (isMoving[2] || isMoving[0]) {
				pcDirection = 0;
				pcSpriteSelector = (pcSpriteSelector + 1) % 2;
				combatDetection();
			} else if (isMoving[3] || isMoving[1]) {
				pcDirection = 1;
				pcSpriteSelector = (pcSpriteSelector + 1) % 2;
				combatDetection();
			}
			playerFrameCount = 0;
		}

	}

	public static void playerMove() {
		playerAnimation();
		if (isMoving[0]) {
			pcYCord -= 5;
		} else if (isMoving[1]) {
			pcYCord += 5;
		} else if (isMoving[2]) {
			pcXCord -= 5;
		} else if (isMoving[3]) {
			pcXCord += 5;
		}
	}

	public static void collision() {
		if (gameState == 1) {
			if (pcYCord == 170 && pcXCord >= 475 && pcXCord <= 730 && isMoving[0]) {
				StopMusic();
				loadScreen = true;
				gameState = 7;
				pcXCord = 570;
				pcYCord = 700;
				PlayMusic();
			}
			if (pcYCord == 725 && pcXCord >= 475 && pcXCord <= 730 && isMoving[1]) {
				StopMusic();
				loadScreen = true;
				gameState = 6;
				pcXCord = 230;
				pcYCord = 460;
				PlayMusic();
			}
			if (pcYCord <= 170 && isMoving[0]) {
				isMoving[0] = false;
			}
			if (pcYCord >= 725 && isMoving[1]) {
				isMoving[1] = false;
			}
			if (pcXCord <= 0 && isMoving[2]) {
				isMoving[2] = false;
			}
			if (pcXCord >= 1146 && isMoving[3]) {
				isMoving[3] = false;
			}
		}
		if (gameState == 2) {
			if (pcYCord == 80 && pcXCord >= 505 && pcXCord <= 675 && isMoving[0]) {
				StopMusic();
				loadScreen = true;
				gameState = 8;
				pcXCord = 575;
				pcYCord = 600;
				PlayMusic();
			}
			if (pcYCord == 725 && pcXCord >= 475 && pcXCord <= 730 && isMoving[1]) {
				StopMusic();
				loadScreen = true;
				gameState = 6;
				pcXCord = 335;
				pcYCord = 460;
				PlayMusic();
			}
			if (pcYCord <= 80 && isMoving[0]) {
				isMoving[0] = false;
			}
			if (pcYCord >= 725 && isMoving[1]) {
				isMoving[1] = false;
			}
			if (pcXCord <= 205 && isMoving[2]) {
				isMoving[2] = false;
			}
			if (pcXCord >= 905 && isMoving[3]) {
				isMoving[3] = false;
			}
		}
		if (gameState == 3) {
			if (pcXCord == 910 && pcYCord >= 245 && pcYCord <= 485 && isMoving[3]) {
				StopMusic();
				loadScreen = true;
				gameState = 9;
				pcXCord = 220;
				pcYCord = 360;
				PlayMusic();
			}
			if (pcXCord == 0 && pcYCord >= 265 && pcYCord <= 525 && isMoving[2]) {
				StopMusic();
				loadScreen = true;
				gameState = 6;
				pcXCord = 800;
				pcYCord = 460;
				PlayMusic();
			}
			if (pcYCord <= 0 && isMoving[0]) {
				isMoving[0] = false;
			}
			if (pcYCord >= 725 && isMoving[1]) {
				isMoving[1] = false;
			}
			if (pcXCord <= 0 && isMoving[2]) {
				isMoving[2] = false;
			}
			if (pcXCord >= 910 && isMoving[3]) {
				isMoving[3] = false;
			}
		}
		if (gameState == 4) {
			if (pcXCord == 880 && pcYCord >= 295 && pcYCord <= 355 && isMoving[3]) {
				StopMusic();
				loadScreen = true;
				gameState = 10;
				pcXCord = 570;
				pcYCord = 690;
				PlayMusic();
			}
			if (pcXCord == 200 && pcYCord >= 310 && pcYCord <= 440 && isMoving[2]) {
				loadScreen = true;
				gameState = 6;
				pcXCord = 890;
				pcYCord = 460;
			}
			if (pcYCord <= 130 && isMoving[0]) {
				isMoving[0] = false;
			}
			if (pcYCord >= 535 && isMoving[1]) {
				isMoving[1] = false;
			}
			if (pcXCord <= 200 && isMoving[2]) {
				isMoving[2] = false;
			}
			if (pcXCord >= 880 && isMoving[3]) {
				isMoving[3] = false;
			}
		}
		if (gameState == 5) {
			if (pcXCord == 880 && pcYCord >= 295 && pcYCord <= 355 && isMoving[3]) {
				loadScreen = true;
				gameState = 10;
				pcXCord = 570;
				pcYCord = 690;
			}
			if (pcXCord == 200 && pcYCord >= 310 && pcYCord <= 440 && isMoving[2]) {
				loadScreen = true;
				gameState = 6;
				pcXCord = 890;
				pcYCord = 460;
			}
			if (pcYCord <= 300 && isMoving[0]) {
				bossF = true;
				loadScreen = true;
				combating = true;
				menu = 2;
				enemy = Enemy.resetEnemyStats(gameState, 0, bossF);
				moveTurn = PC.dex >= enemy[4];
			}
			if (pcYCord >= 725 && isMoving[1]) {
				isMoving[1] = false;
			}
			if (pcXCord <= 372 && isMoving[2]) {
				isMoving[2] = false;
			}
			if (pcXCord >= 700 && isMoving[3]) {
				isMoving[3] = false;
			}
		}
		if (gameState == 6) {
			if (pcYCord == 400 && pcXCord >= 552 && pcXCord <= 572 && isMoving[0]) {
				menu = 5;
				loadScreen = true;
			}
			if (pcXCord <= 177 && pcYCord >= 650 && pcYCord <= 660 && isMoving[2]) {
				menu = 9;
				loadScreen = true;
			}
			if (pcXCord >= 892 && pcYCord >= 575 && pcYCord <= 635 && isMoving[3]) {
				menu = 8;
				loadScreen = true;
			}
			if (pcYCord == 400 && pcXCord >= 210 && pcXCord <= 310 && isMoving[0]) {
				StopMusic();
				loadScreen = true;
				gameState = 1;
				pcXCord = 580;
				pcYCord = 700;
				PlayMusic();
			}
			if (pcYCord == 400 && pcXCord >= 310 && pcXCord <= 410 && isMoving[0]) {
				StopMusic();
				gameState = 2;
				pcXCord = 565;
				pcYCord = 700;
				loadScreen = true;
				PlayMusic();
			}
			if (pcYCord == 400 && pcXCord >= 758 && pcXCord <= 830 && isMoving[0]) {
				StopMusic();
				gameState = 3;
				pcXCord = 25;
				pcYCord = 350;
				loadScreen = true;
				PlayMusic();
			}
			if (pcYCord == 400 && pcXCord >= 856 && pcXCord <= 939 && isMoving[0]) {
				StopMusic();
				gameState = 4;
				pcXCord = 220;
				pcYCord = 330;
				loadScreen = true;
				PlayMusic();
			}
			if (pcYCord <= 400 && isMoving[0]) {
				isMoving[0] = false;
			}
			if (pcYCord >= 700 && isMoving[1]) {
				isMoving[1] = false;
			}
			if (pcXCord <= 177 && isMoving[2]) {
				isMoving[2] = false;
			}
			if (pcXCord >= 892 && isMoving[3]) {
				isMoving[3] = false;
			}
		}
		if (gameState == 7) {
			if (pcYCord == 725 && pcXCord >= 335 && pcXCord <= 798 && isMoving[1]) {
				StopMusic();
				gameState = 1;
				pcXCord = 575;
				pcYCord = 190;
				loadScreen = true;
				PlayMusic();
			}
			if (pcYCord <= 420 && isMoving[0] && boss == 5) {
				bossF = true;
				loadScreen = true;
				combating = true;
				menu = 2;
				enemy = Enemy.resetEnemyStats(gameState, 0, bossF);
				moveTurn = PC.dex >= enemy[4];

			}
			if (pcYCord <= 420 && isMoving[0]) {
				isMoving[0] = false;
			}
			if (pcYCord >= 725 && isMoving[1]) {
				isMoving[1] = false;
			}
			if (pcXCord <= 355 && isMoving[2]) {
				isMoving[2] = false;
			}
			if (pcXCord >= 795 && isMoving[3]) {
				isMoving[3] = false;
			}
		}
		if (gameState == 8) {
			if (pcYCord == 625 && pcXCord >= 550 && pcXCord <= 650 && isMoving[1]) {
				StopMusic();
				gameState = 2;
				pcXCord = 565;
				pcYCord = 100;
				loadScreen = true;
			}
			if (pcYCord <= 475 && isMoving[0] && boss == 4) {
				bossF = true;
				loadScreen = true;
				combating = true;
				menu = 2;
				enemy = Enemy.resetEnemyStats(gameState, 0, bossF);
				moveTurn = PC.dex >= enemy[4];

			}
			if (pcYCord <= 475 && isMoving[0]) {
				isMoving[0] = false;
			}
			if (pcYCord >= 625 && isMoving[1]) {
				isMoving[1] = false;
			}
			if (pcXCord <= 200 && isMoving[2]) {
				isMoving[2] = false;
			}
			if (pcXCord >= 945 && isMoving[3]) {
				isMoving[3] = false;
			}
		}
		if (gameState == 9) {
			if (pcXCord == 200 && pcYCord >= 300 && pcYCord <= 495 && isMoving[2]) {
				StopMusic();
				gameState = 3;
				pcXCord = 900;
				pcYCord = 320;
				loadScreen = true;
			}
			if (pcXCord >= 340 && isMoving[3] && boss == 3) {
				bossF = true;
				loadScreen = true;
				combating = true;
				menu = 2;
				enemy = Enemy.resetEnemyStats(gameState, 0, bossF);
				moveTurn = PC.dex >= enemy[4];

			}
			if (pcYCord <= 200 && isMoving[0]) {
				isMoving[0] = false;
			}
			if (pcYCord >= 525 && isMoving[1]) {
				isMoving[1] = false;
			}
			if (pcXCord <= 200 && isMoving[2]) {
				isMoving[2] = false;
			}
			if (pcXCord >= 340 && isMoving[3]) {
				isMoving[3] = false;
			}
		}
		if (gameState == 10) {
			if (pcYCord == 715 && pcXCord >= 520 && pcXCord <= 670 && isMoving[1]) {
				StopMusic();
				gameState = 4;
				pcXCord = 860;
				pcYCord = 325;
				loadScreen = true;
			}
			if (pcYCord <= 590 && isMoving[0] && boss == 2) {
				bossF = true;
				loadScreen = true;
				combating = true;
				menu = 2;
				enemy = Enemy.resetEnemyStats(gameState, 0, bossF);
				moveTurn = PC.dex >= enemy[4];

			}
			if (pcYCord <= 590 && isMoving[0]) {
				isMoving[0] = false;
			}
			if (pcYCord >= 715 && isMoving[1]) {
				isMoving[1] = false;
			}
			if (pcXCord <= 325 && isMoving[2]) {
				isMoving[2] = false;
			}
			if (pcXCord >= 810 && isMoving[3]) {
				isMoving[3] = false;
			}
		}
	}

	public Game_Start() {
		setPreferredSize(new Dimension(1200, 800));
		addKeyListener(this);
		this.setFocusable(true);
		Thread thread = new Thread(this);
		thread.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		PC.updatePC(equipStats[0], equipStats[1] + equipStats[2] + equipStats[3] + equipStats[4]);
		if (loadScreen == true) {
			g.drawImage(loading, 0, 0, null);
			load++;
			if (load == 25) {
				loadScreen = false;
				load = 0;
				buttonNum = 0;
			}
		} else if (menu == 1) {
			g.drawImage(inGameMenu, 0, 0, null);
			if (buttonNum == 0) {
				g.drawImage(inGameMenuMarker, 55, 160, null);
			}
			if (buttonNum == 1) {
				g.drawImage(inGameMenuMarker, 55, 230, null);
			}
			if (buttonNum == 2) {
				g.drawImage(inGameMenuMarker, 55, 300, null);
			}
			if (buttonNum == 3) {
				g.drawImage(inGameMenuMarker, 55, 370, null);
			}
			if (buttonNum == 4) {
				g.drawImage(inGameMenuMarker, 55, 440, null);
			}
		} else if (menu == 2) {
			g.drawImage(combatScreen, 0, 0, null);
			g.setFont(new Font("Arial", Font.PLAIN, 16));
			g.drawImage(pcSprite[1][2], 235, 172, null);
			if (bossF) {
				if (boss == 5) {
					g.drawImage(bo1ss, 760, 115, null);
				}
				if (boss == 4) {
					g.drawImage(bo2ss, 760, 115, null);
				}
				if (boss == 3) {
					g.drawImage(bo3ss, 760, 115, null);
				}
				if (boss == 2) {
					g.drawImage(bo4ss, 760, 115, null);
				}
				if (boss == 1) {
					g.drawImage(bo5ss, 760, 115, null);
				}
			} else {
				g.drawImage(monsters[enemyType], 810, 180, null);
			}
			g.drawString("HP:" + PC.hp + "/" + PC.maxHp, 200, 345);
			g.drawString("HP:" + enemy[0], 800, 345);
			if (buttonNum == 0) {
				g.drawImage(combatMarker, 600, 499, null);
			}
			if (buttonNum == 1) {
				g.drawImage(combatMarker, 600, 648, null);
			}
			if (buttonNum == 2) {
				g.drawImage(combatMarker, 900, 499, null);
			}
			if (buttonNum == 3) {
				g.drawImage(combatMarker, 900, 648, null);
			}
		} else if (menu == 3) {
			g.drawImage(inventoryScreen, 0, 0, null);
			g.setFont(new Font("Arial", Font.PLAIN, 16));
			for (int i = 0; i < 8; i++) {
				g.drawString("x" + item[i], 70 + 110 * i, 140);
			}
			if (marker == 1) {
				g.drawImage(inventoryMarker, buttonX, buttonY, null);
			}
			if (marker == 0) {
				g.drawImage(inventoryMarker2, 395, 645, null);
			}
		} else if (menu == 4) {
			g.drawImage(equipmentScreen, 0, 0, null);
			g.setFont(new Font("Arial", Font.PLAIN, 16));
			g.drawString("Level:" + PC.level, 730, 140);
			g.drawString("Hp:" + PC.hp + "/" + PC.maxHp, 1025, 80);
			g.drawString("Mp:" + PC.mp + "/" + PC.maxMp, 1025, 100);
			g.drawString("Atk:" + PC.atk, 730, 630);
			g.drawString("Def:" + PC.def, 1060, 630);
			if (marker == 1) {
				g.drawImage(equipmentScreenMarker, buttonX, buttonY, null);
			}
			if (marker == 0) {
				g.drawImage(equipmentScreenMarker2, 695, 670, null);
			}
			if (weaponOwned[0] == 1) {
				g.drawImage(weapon[0], 35, 35, null);
			}
			if (helmOwned[0] == 1) {
				g.drawImage(helm[0], 35, 170, null);
			}
			if (chestOwned[0] == 1) {
				g.drawImage(chest[0], 35, 305, null);
			}
			if (legsOwned[0] == 1) {
				g.drawImage(legs[0], 35, 440, null);
			}
			if (bootsOwned[0] == 1) {
				g.drawImage(boots[0], 35, 575, null);
			}
			if (abilityOwned[0] == 1) {
				g.drawImage(ability[0], 35, 710, null);
			}

			if (equipped[0] != 0) {
				g.drawImage(weapon[equipped[0] - 1], 745, 210, null);
			}
			if (equipped[1] != 0) {
				g.drawImage(helm[equipped[1] - 1], 880, 75, null);
			}
			if (equipped[2] != 0) {
				g.drawImage(chest[equipped[2] - 1], 880, 210, null);
			}
			if (equipped[3] != 0) {
				g.drawImage(legs[equipped[3] - 1], 880, 345, null);
			}
			if (equipped[4] != 0) {
				g.drawImage(boots[equipped[4] - 1], 880, 480, null);
			}
			if (equipped[5] != 0) {
				g.drawImage(ability[equipped[5] - 1], 1015, 210, null);
			}
		} else if (menu == 5) {
			g.drawImage(shop, 100, 100, null);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("$" + PC.money, 10, 20);
			if (buttonNum < 8) {
				g.drawImage(shopMarker1, 548, 100 + buttonNum * 75, null);
			}
			if (buttonNum > 7) {
				g.drawImage(shopMarker2, 823, 100 + (buttonNum - 8) * 99, null);
			}
		} else if (menu == 6) {
			g.drawImage(settings, 0, 0, null);

			if (buttonNum == 1) {
				g.drawImage(titleMarker, 340, 620, null);
			}
		} else if (menu == 8) {
			g.drawImage(smith, 0, 0, null);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("$" + PC.money, 10, 20);
			g.drawImage(smithMarker, 600, 0 + 160 * buttonNum, null);
		} else if (menu == 9) {
			g.drawImage(hotel, 0, 0, null);
			g.drawImage(hotelMarker, 0 + 600*buttonNum, 600, null);
		} else if (menu == 10) {
			g.drawImage(credits, 0, 0, null);

			if (buttonNum == 1) {
				g.drawImage(titleMarker, 340, 620, null);
			}
		}

		else {
			if (gameState == 0) {
				if (continueGame == 0) {
					g.drawImage(title, 0, 0, null);
				}
				if (continueGame == 1) {
					g.drawImage(title2, 0, 0, null);
				}
				if (buttonNum == 0) {
					g.drawImage(titleMarker, 340, 280, null);
				}
				if (buttonNum == 1) {
					g.drawImage(titleMarker, 340, 410, null);
				}
				if (buttonNum == 2) {
					g.drawImage(titleMarker, 340, 540, null);
				}
				if (buttonNum == 3) {
					g.drawImage(titleMarker2, 340, 665, null);
				}
				if (buttonNum == 4) {
					g.drawImage(titleMarker2, 737, 665, null);
				}
			}
			if (gameState == 1) {
				g.drawImage(map1, 0, 0, null);
				g.drawImage(pcSprite[pcDirection][pcSpriteSelector], pcXCord, pcYCord, null);
				collision();
				playerMove();
			}
			if (gameState == 2) {
				g.drawImage(map2, 0, 0, null);
				g.drawImage(pcSprite[pcDirection][pcSpriteSelector], pcXCord, pcYCord, null);
				collision();
				playerMove();
			}
			if (gameState == 3) {
				g.drawImage(map3, 0, 0, null);
				g.drawImage(pcSprite[pcDirection][pcSpriteSelector], pcXCord, pcYCord, null);
				collision();
				playerMove();
			}
			if (gameState == 4) {
				g.drawImage(map4, 0, 0, null);
				g.drawImage(pcSprite[pcDirection][pcSpriteSelector], pcXCord, pcYCord, null);
				collision();
				playerMove();
			}
			if (gameState == 5) {
				g.drawImage(map5, 0, 0, null);
				g.drawImage(pcSprite[pcDirection][pcSpriteSelector], pcXCord, pcYCord, null);
				collision();
				playerMove();
			}
			if (gameState == 6) {
				g.drawImage(townMap, 0, 0, null);
				g.drawImage(pcSprite[pcDirection][pcSpriteSelector], pcXCord, pcYCord, null);
				collision();
				playerMove();
			}
			if (gameState == 7) {
				g.drawImage(map1Boss, 0, 0, null);
				g.drawImage(pcSprite[pcDirection][pcSpriteSelector], pcXCord, pcYCord, null);
				collision();
				playerMove();
			}
			if (gameState == 8) {
				g.drawImage(map2Boss, 0, 0, null);
				g.drawImage(pcSprite[pcDirection][pcSpriteSelector], pcXCord, pcYCord, null);
				collision();
				playerMove();
			}
			if (gameState == 9) {
				g.drawImage(map3Boss, 0, 0, null);
				g.drawImage(pcSprite[pcDirection][pcSpriteSelector], pcXCord, pcYCord, null);
				collision();
				playerMove();
			}
			if (gameState == 10) {
				g.drawImage(map4Boss, 0, 0, null);
				g.drawImage(pcSprite[pcDirection][pcSpriteSelector], pcXCord, pcYCord, null);
				collision();
				playerMove();
			}
		}
	}

	public static void main(String[] args) {

		// Image Import
		try {
			title = ImageIO.read(new File("TitleScreenTemp.png"));
			title2 = ImageIO.read(new File("TitleScreenTempContinue.png"));
			settings = ImageIO.read(new File("SettingScreen.png"));
			credits = ImageIO.read(new File("CreditScreen.png"));
			loading = ImageIO.read(new File("Loading.png"));
			inGameMenu = ImageIO.read(new File("InGameMenu.png"));
			inGameMenuMarker = ImageIO.read(new File("InGameMenuMarker.png"));
			shop = ImageIO.read(new File("Shop.png"));
			hotel = ImageIO.read(new File("hotel Menu.png"));
			smith = ImageIO.read(new File("Blacksmith.png"));
			shopMarker1 = ImageIO.read(new File("shop_marker_1.png"));
			shopMarker2 = ImageIO.read(new File("shop_marker_2.png"));
			hotelMarker = ImageIO.read(new File("hotel_marker.png"));
			smithMarker = ImageIO.read(new File("smith_marker.png"));
			inventoryScreen = ImageIO.read(new File("Inventory.png"));
			inventoryMarker = ImageIO.read(new File("InventoryMarker.png"));
			inventoryMarker2 = ImageIO.read(new File("InventoryMarker2.png"));
			equipmentScreen = ImageIO.read(new File("EquipmentScreen.png"));
			equipmentScreenMarker = ImageIO.read(new File("EquipmentMarker.png"));
			equipmentScreenMarker2 = ImageIO.read(new File("EquipmentMarker2.png"));
			combatScreen = ImageIO.read(new File("Combat.png"));
			combatMarker = ImageIO.read(new File("Combat Marker.png"));
			titleMarker = ImageIO.read(new File("TitleScreenButtonMarker.png"));
			titleMarker2 = ImageIO.read(new File("TitleScreenButtonMarker2.png"));
			pcSprite[0][0] = ImageIO.read(new File("Walk_Down1.png"));
			pcSprite[0][1] = ImageIO.read(new File("Walk_Down2.png"));
			pcSprite[0][2] = ImageIO.read(new File("Idle_Down.png"));
			pcSprite[1][0] = ImageIO.read(new File("Walk_Right1.png"));
			pcSprite[1][1] = ImageIO.read(new File("Walk_Right2.png"));
			pcSprite[1][2] = ImageIO.read(new File("Idle_Down2.png"));
			map1 = ImageIO.read(new File("Map1.png"));
			map2 = ImageIO.read(new File("Map2.png"));
			map3 = ImageIO.read(new File("map3.png"));
			map4 = ImageIO.read(new File("Map4.png"));
			map5 = ImageIO.read(new File("Map5.png"));
			map1Boss = ImageIO.read(new File("Map1Boss.png"));
			map2Boss = ImageIO.read(new File("Map2Boss.png"));
			map3Boss = ImageIO.read(new File("Map3Boss.png"));
			map4Boss = ImageIO.read(new File("Map4Boss.png"));
			bo1ss = ImageIO.read(new File("boss1.png"));
			bo2ss = ImageIO.read(new File("boss2.png"));
			bo3ss = ImageIO.read(new File("boss3.png"));
			bo4ss = ImageIO.read(new File("boss4.png"));
			bo5ss = ImageIO.read(new File("boss5.png"));
			townMap = ImageIO.read(new File("map6.png"));
			monsters[0] = ImageIO.read(new File("cat.png"));
			monsters[1] = ImageIO.read(new File("chicken.png"));
			monsters[2] = ImageIO.read(new File("turtle.png"));

			weapon[0] = ImageIO.read(new File("Weapon1.png"));
			helm[0] = ImageIO.read(new File("Helm1.png"));
			chest[0] = ImageIO.read(new File("Chest1.png"));
			legs[0] = ImageIO.read(new File("Legs1.png"));
			boots[0] = ImageIO.read(new File("Boots1.png"));
			ability[0] = ImageIO.read(new File("ult.png"));

			music1 = AudioSystem.getClip();
			music1.open(AudioSystem.getAudioInputStream(new File("map6Music.wav")));
			boss1Music = AudioSystem.getClip();
			boss1Music.open(AudioSystem.getAudioInputStream(new File("Boss1.wav")));
			boss2Music = AudioSystem.getClip();
			boss2Music.open(AudioSystem.getAudioInputStream(new File("Boss2.wav")));
			boss3Music = AudioSystem.getClip();
			boss3Music.open(AudioSystem.getAudioInputStream(new File("Boss3.wav")));
			boss4Music = AudioSystem.getClip();
			boss4Music.open(AudioSystem.getAudioInputStream(new File("Boss4.wav")));

			map2Music = AudioSystem.getClip();
			map2Music.open(AudioSystem.getAudioInputStream(new File("map2Music.wav")));

		} catch (Exception e) {
		}

		JFrame frame = new JFrame();
		Game_Start panel = new Game_Start();
		frame.add(panel);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(false);

		try {
			Scanner inputFile = new Scanner(new File("SaveData.txt"));
			if (inputFile.hasNextInt()) {
				continueGame = 1;
				System.out.print(continueGame);
			}
			inputFile.close();
		} catch (Exception e) {
		}

	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(20);
				repaint();
			} catch (Exception e) {
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (gameState == 0) // Title Screen
		{
			Title_Screen(e.getKeyChar());
		}
		if (gameState != 0 && menu == 0) // Town Screen
		{
			openMenu(e.getKeyChar());
			if (e.getKeyChar() == 'w') {
				isMoving[0] = true;
			} else if (e.getKeyChar() == 's') {
				isMoving[1] = true;
			} else if (e.getKeyChar() == 'a') {
				isMoving[2] = true;
			} else if (e.getKeyChar() == 'd') {
				isMoving[3] = true;
			}
		}
		if (menu == 1) {
			In_Game_Menu(e.getKeyChar());
		}
		if (menu == 2) {
			inCombat(e.getKeyChar());
		}
		if (menu == 3) {
			Inventory(e.getKeyChar());
		}
		if (menu == 4) {
			Equipment(e.getKeyChar());
		}
		if (menu == 5) {
			Shop(e.getKeyChar());
		}
		if (menu == 6) {
			Settings_Screen(e.getKeyChar());
		}
		if (menu == 8) {
			Bsmith(e.getKeyChar());
		}
		if (menu == 9) {
			Hotel(e.getKeyChar());
		}
		if (menu == 10) {
			Credit_Screen(e.getKeyChar());
		}
	}

	public void keyReleased(KeyEvent e) {
		if (gameState != 0) {
			if (e.getKeyChar() == 'w') {
				isMoving[0] = false;
			}
			if (e.getKeyChar() == 's') {
				isMoving[1] = false;
			}
			if (e.getKeyChar() == 'a') {
				isMoving[2] = false;
			}
			if (e.getKeyChar() == 'd') {
				isMoving[3] = false;
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

}
