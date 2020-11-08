import java.awt.*;
import java.util.*;

/**
 * HeliGame class handles creating the Players and invoking update and render on
 * each player
 */
public class HeliGame implements Game {

	private int numPlayers = 0;
	private int numWinner = 1;
	private Player player[];// array for Player Objects
	private int ticks = 0;
	private int holdCounter = 0;
	private int[] scores;
	private int currScore = 0;
	private int highScore = 0;
	private int gamePause = -1;
	private int round = 1;
	private boolean gameOver;
	private GameWindow gw;// reference to the main GameWindow Object
	private KeyHandler kh;// access to the state of the keyboard
	private Font hiScoreFont = new Font("Comic Sans MS", Font.PLAIN, 32);
	private Font otherFont = new Font("Comic Sans MS", Font.PLAIN, 26);
	private Color SKYBLUE;
	private Color ACTUALORANGE;

	// YOUR CODE GOES HERE
	// declare instance variables

	/**
	 * constructor gets a reference to the main GameWindow Object as a parameter..
	 * then initializes all instance variables appropriately
	 */
	public HeliGame(GameWindow gw) {
		numPlayers = 4;

		this.gw = gw;
		this.kh = gw.getKeyHandler();

		player = new Player[numPlayers + 1];
		scores = new int[numPlayers + 1];
		// note: player[0] not used so
		// player 1 is actually player[1] .. neat, huh?

		for (int i = 1; i <= numPlayers; i++) {
			player[i] = new Player(i, this, scores[i]);
		}

		SKYBLUE = new Color(66, 158, 244);
		ACTUALORANGE = new Color(255, 136, 0);

		// YOUR CODE GOES HERE
		// initialize instance variables

	}

	/**
	 * invokes render for each player and draws all game stats like hi scores to the
	 * scoreboard
	 */
	@Override
	public void render(Graphics2D g) {
		if (gw.pWidth() == 1280)
			g.scale(0.667, 0.667);
		for (int i = 1; i <= numPlayers; i++) {
			player[i].render(g);
		}

		renderScoreBoard(g);
		// render the countDown
		// please notice the use of the ticks variable
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
		g.setColor(Color.WHITE);
		g.drawString("Round: " + round, 908, 150);
		if (ticks < 500) {
			g.drawString("" + ((598 - ticks) / 100), 200, 200);
			g.drawString("" + ((598 - ticks) / 100), 200, 740);
			g.drawString("" + ((598 - ticks) / 100), 1320, 200);
			g.drawString("" + ((598 - ticks) / 100), 1320, 740);
			// g.drawString("why mer", 500, 500);
			g.drawString("Player 1", 15, 287);
			g.drawString("Player 2", 1135, 287);
			g.drawString("Player 3", 15, 827);
			g.drawString("Player 4", 1135, 827);
			if (kh.isButtonPressed(1)) {
				g.setColor(Color.RED);
				g.drawString("Player 1", 15, 287);
			}
			if (kh.isButtonPressed(2)) {
				g.setColor(Color.GREEN);
				g.drawString("Player 2", 1135, 287);
			}
			if (kh.isButtonPressed(3)) {
				g.setColor(SKYBLUE);
				g.drawString("Player 3", 15, 827);
			}
			if (kh.isButtonPressed(4)) {
				g.setColor(ACTUALORANGE);
				g.drawString("Player 4", 1135, 827);
			}
		}
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, 1920 - 1, 1080 - 1);
		// white rectangle around your 1280x1024 screen..
		// note: dimensions will be different for 1920x1080 screen

		if (gamePause < 0) {

		} else if (gamePause == 0) {

		} else { // gamePause > 0
			g.drawString("" + (gamePause / 100), 200, 200); // draws a 0?
			g.drawString("" + (gamePause / 100), 200, 740);
			g.drawString("" + (gamePause / 100), 1320, 200);
			g.drawString("" + (gamePause / 100), 1320, 740);
		}
		// YOUR CODE GOES HERE
		// render instance variables

	}

	/**
	 * handles all of the drawing to the scoreboard
	 * 
	 * @param g
	 */
	public void renderScoreBoard(Graphics2D g) {
		// render hiScores
		g.setColor(Color.WHITE);
		g.setFont(hiScoreFont);
		g.drawString("High Score Today", 825, 100);
		g.drawString("", 900, 200); // place score here
		for (int i = 1; i <= 4; i++) {
			currScore = scores[i];
			if (currScore > highScore) {
				highScore = currScore;
				numWinner = i;
			}
			if (gameOver) {
				if (numWinner == 1) {
					g.setColor(Color.RED);
					g.drawString("Player: " + numWinner + " Wins!", 300, 260);
					g.drawString("Player: " + numWinner + " Wins!", 1420, 260);
					g.drawString("Player: " + numWinner + " Wins!", 300, 800);
					g.drawString("Player: " + numWinner + " Wins!", 1420, 800);
				}
				if (numWinner == 2) {
					g.setColor(Color.GREEN);
					g.drawString("Player: " + numWinner + " Wins!", 300, 260);
					g.drawString("Player: " + numWinner + " Wins!", 1420, 260);
					g.drawString("Player: " + numWinner + " Wins!", 300, 800);
					g.drawString("Player: " + numWinner + " Wins!", 1420, 800);
				}
				if (numWinner == 3) {
					g.setColor(SKYBLUE);
					g.drawString("Player: " + numWinner + " Wins!", 300, 260);
					g.drawString("Player: " + numWinner + " Wins!", 1420, 260);
					g.drawString("Player: " + numWinner + " Wins!", 300, 800);
					g.drawString("Player: " + numWinner + " Wins!", 1420, 800);
				}
				if (numWinner == 4) {
					g.setColor(ACTUALORANGE);
					g.drawString("Player: " + numWinner + " Wins!", 300, 260);
					g.drawString("Player: " + numWinner + " Wins!", 1420, 260);
					g.drawString("Player: " + numWinner + " Wins!", 300, 800);
					g.drawString("Player: " + numWinner + " Wins!", 1420, 800);
				}
				g.setColor(Color.WHITE);
				g.drawString("Player 1 Hold Button to Restart", 160, 340);
				g.drawString("Player 1 Hold Button to Restart", 1280, 340);
				g.drawString("Player 1 Hold Button to Restart", 160, 880);
				g.drawString("Player 1 Hold Button to Restart", 1280, 880);
			}
		}
		g.setFont(otherFont);
		g.drawString("Made by: Brendan", 845, 1030);
		g.drawString("& Mercedes", 890, 1062);
	}

	/**
	 * invokes update() for each player and updates any game stats like hi scores
	 */
	@Override
	public void update() {
		ticks++;
		gamePause--;
		if (ticks < 500) // pause game for 5 seconds when launched
			return;

		if (gameOver) {
			if (kh.isButtonPressed(1)) {
				holdCounter++;
			} else {
				holdCounter = 0;
			}
			if (holdCounter > 200) {
				gameOver = false;
				for (int i = 1; i <= numPlayers; i++) {
					scores[i] = player[i].getScore();
					player[i] = new Player(i, this, 0);
				}
				round = 1;
				ticks = 0;
				gamePause = -1;
				holdCounter = 0;
			}

		}

		if (round > 3)
			return;
		if (gamePause < 0) {
			for (int i = 1; i <= numPlayers; i++) {
				player[i].update();
			}
			if (player[1].getDead() && player[2].getDead() && player[3].getDead() && player[4].getDead()) {
				gamePause = 598;
			}
		} else if (gamePause == 0) {
			for (int i = 1; i <= numPlayers; i++) {
				scores[i] = player[i].getScore();
				player[i] = new Player(i, this, scores[i]);
			}
			round++;
			if (round > 3) {
				gamePause++;
				gameOver = true;
			}
		} else { // gamePause > 0

		}
	}

	/**
	 * returns a reference to the main GameWindow Object
	 */
	public GameWindow getGameWindow() {
		return gw;

	}

	/**
	 * returns the number of ticks for the entire game
	 */

	public int getTicks() {
		return ticks;
	}

}// end class HeliGame