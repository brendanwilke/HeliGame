import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class Player {
	private Rectangle viewport;
	private int playerNum;
	private HeliGame hg;
	private KeyHandler kh;
	private int ticks = 0;
	// ~~~ additional code ~~~
	private Rectangle2D.Double head;
	private ArrayList<Rectangle2D.Double> tail;
	private ArrayList<Rectangle2D.Double> caveTop, caveBottom;
	private ArrayList<Rectangle2D.Double> obstacles;
	private boolean isDead = false;
	private double scrollSpeed = 1.5;
	private int tailLength = 100;
	private double velocity = 0.0;
	private int score = 0;
	private int fontSize = 35;
	private final double GRAVITY = 0.08;
	private final double THRUST = 0.05;
	private final double MAXVELOCITY = 2.0;
	private final double MINVELOCITY = -2.5;
	private Random rand = new Random(29);
	private Color SKYBLUE;
	private Color ACTUALORANGE;

	public Player() {

	}

	public Player(int playerNum, HeliGame hg, int initScore) {
		SKYBLUE = new Color(66, 158, 244);
		ACTUALORANGE = new Color (255, 136, 0);
		this.playerNum = playerNum;
		this.hg = hg;
		kh = hg.getGameWindow().getKeyHandler();

		// viewport 874x540 .. golden ratio .. only leaves 172 px for scoreboard
		// viewport 800x540 .. ratio 2 to 3 .. leaves 320px for scoreboard

		// four-player view on the flat screen tv 1920x1080
		// +------------+------------+------+
		// | viewport 1 | viewport 2 | scor |
		// +------------+------------| e_bo |
		// | viewport 3 | viewport 4 | ard! |
		// +------------+------------+------+

		if (playerNum == 1) {
			viewport = new Rectangle(0, 0, 800, 540);
		}
		if (playerNum == 2) {
			viewport = new Rectangle(1120, 0, 800, 540);
		}
		if (playerNum == 3) {
			viewport = new Rectangle(0, 540, 800, 540);
		}
		if (playerNum == 4) {
			viewport = new Rectangle(1120, 540, 800, 540);
		}
		head = new Rectangle2D.Double(266 + viewport.x, 270 + viewport.y, 20, 20);
		tail = new ArrayList<Rectangle2D.Double>();
		scrollSpeed = 1.5;
		tailLength = 100;
		caveTop = new ArrayList<Rectangle2D.Double>();
		caveBottom = new ArrayList<Rectangle2D.Double>();
		obstacles = new ArrayList<Rectangle2D.Double>();
		score = initScore;

		for (int i = 0; i < 41; i++) {
			caveTop.add(new Rectangle2D.Double(i * 20 + viewport.x, 0 + viewport.y, 20, 60));
			caveBottom.add(new Rectangle2D.Double(i * 20 + viewport.x, 480 + viewport.y, 20, 60));
			obstacles.add(null);
		}
		int j = 5;
		int dir = 1;
		for (int i = 41; i < 5000; i++) {
			if (j > 100 || j < 5)
				dir = -dir;
			if (dir > 0) {
				caveTop.add(new Rectangle2D.Double(i * 20 + viewport.x, 0 + viewport.y, 20, 60 + j));
				caveBottom.add(new Rectangle2D.Double(i * 20 + viewport.x, 480 - j + viewport.y, 20, 60 + j));
				j += dir * 5;
			} else if (dir < 0) {
				caveTop.add(new Rectangle2D.Double(i * 20 + viewport.x, 0 + viewport.y, 20, 60 + j));
				caveBottom.add(new Rectangle2D.Double(i * 20 + viewport.x, 480 - j + viewport.y, 20, 60 + j));
				j += dir * 5;
			}
			double t = caveTop.get(i).height;
			double b = viewport.height - caveBottom.get(i).height - 60;
			double pos = rand.nextDouble() * (b - t) + t;
			if (i % 15 == 0)
				obstacles.add(new Rectangle2D.Double(i * 20 + viewport.x, pos + viewport.y, 20, 100));
			else
				obstacles.add(null);
		}
	}

	public void update() {
		head.y -= velocity;
		if (playerNum == 1) {
			if (kh.isButtonPressed(1) && velocity <= MAXVELOCITY) { // player 1 uses Z key
				velocity += THRUST;
			} else if (!kh.isButtonPressed(1) && velocity >= MINVELOCITY) {
				velocity -= GRAVITY;
			}
		} else if (playerNum == 2) {
			if (kh.isButtonPressed(2) && velocity <= MAXVELOCITY) { // player 2 uses F key
				velocity += THRUST;
			} else if (!kh.isButtonPressed(2) && velocity >= MINVELOCITY) {
				velocity -= GRAVITY;
			}
		} else if (playerNum == 3) {
			if (kh.isButtonPressed(3) && velocity <= MAXVELOCITY) { // player 3 uses U key
				velocity += THRUST;
			} else if (!kh.isButtonPressed(3) && velocity >= MINVELOCITY) {
				velocity -= GRAVITY;
			}
		} else if (playerNum == 4) {
			if (kh.isButtonPressed(4) && velocity <= MAXVELOCITY) { // player 4 uses P key
				velocity += THRUST;
			} else if (!kh.isButtonPressed(4) && velocity >= MINVELOCITY) {
				velocity -= GRAVITY;
			}
		}

		tail.add(new Rectangle2D.Double(head.x, head.y, head.width, head.height));
		if (tail.size() > tailLength)
			tail.remove(0);
		for (Rectangle2D.Double link : tail) {
			link.x -= scrollSpeed;
		}
		for (Rectangle2D.Double link : caveTop) {
			link.x -= scrollSpeed;
		}
		for (Rectangle2D.Double link : caveBottom) {
			link.x -= scrollSpeed;
		}
		for (Rectangle2D.Double link : obstacles) {
			if (link != null) {
				link.x -= scrollSpeed;
			}
		}
		if (caveBottom.get(0).x < viewport.x - 20) {
			caveTop.remove(0);
			caveBottom.remove(0);
			obstacles.remove(0);
		}
		for (int i = 0; i < 20; i++) {
			if (head.intersects(caveTop.get(i)) || head.intersects(caveBottom.get(i)))
				isDead = true;
		}
		for (int i = 0; i < 20; i++) {
			if (obstacles.get(i) != null && head.intersects(obstacles.get(i)))
				isDead = true;
		}
		if (isDead) {
			scrollSpeed = 0;
			velocity -= 0.5;
		}

		if (!isDead)
			score++;

		// YOUR CODE GOES HERE
		// update instance variables

	}

	public void render(Graphics2D g) {

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.BLACK);
		g.fill(viewport);
		g.setColor(Color.WHITE);
		int realScore = score / 10;
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
		String score = "Player " + playerNum + " Score: " + realScore;
		String viewportScore = "" + realScore;
		if (playerNum == 1) {
			g.drawString(score, 848, 400);	
		}
		if (playerNum == 2) {
			g.drawString(score, 848, 500);
		}
		if (playerNum == 3) {
			g.drawString(score, 848, 600);
		}
		if (playerNum == 4) {
			g.drawString(score, 848, 700);
		}

		double whiteVal = 255.0;
		for (int i = tail.size() - 1; i >= 0; i--) {
			g.setColor(new Color((int) whiteVal, (int) whiteVal, (int) whiteVal));
			g.fill(tail.get(i));
			whiteVal -= 255.0 / tailLength;
		}
		g.setColor(Color.WHITE);
		g.fill(head);
		if (playerNum == 1) {
			g.setColor(Color.RED);
		} else if (playerNum == 2) {
			g.setColor(Color.GREEN);
		} else if (playerNum == 3) {
			g.setColor(SKYBLUE);
		} else {
			g.setColor(ACTUALORANGE);
		}

		for (int i = 0; i < 41; i++) {
			g.fill(caveTop.get(i));
			g.fill(caveBottom.get(i));
			if (obstacles.get(i) != null) {
				g.fill(obstacles.get(i));
			} else {
				// else do nothing
			}
		}
		g.setColor(Color.BLACK);
		// YOUR CODE GOES HERE
		// render instance variables and other game graphics

		// draw 4 px border to frame the viewport
		g.setColor(Color.BLACK);
		for (int k = 0; k < 4; k++) {
			g.drawRect(viewport.x + k, viewport.y + k, viewport.width - 2 * k, viewport.height - 2 * k);
		}
		g.setColor(Color.BLACK);
		g.fill(new Rectangle2D.Double(800.0, 0.0, 20.0, 1080.0));
		g.fill(new Rectangle2D.Double(1100, 0, 20, 1080));
		g.setColor(Color.BLACK);
		if (playerNum == 1) {
			g.drawString(viewportScore, 17, 42);	
		}
		if (playerNum == 2) {
			g.drawString(viewportScore, 1137, 42);
		}
		if (playerNum == 3) {
			g.drawString(viewportScore, 17, 582);
		}
		if (playerNum == 4) {
			g.drawString(viewportScore, 1137, 582);
		}
		g.setColor(Color.WHITE);
		if (playerNum == 1) {
			g.drawString(viewportScore, 15, 40);	
		}
		if (playerNum == 2) {
			g.drawString(viewportScore, 1135, 40);
		}
		if (playerNum == 3) {
			g.drawString(viewportScore, 15, 580);
		}
		if (playerNum == 4) {
			g.drawString(viewportScore, 1135, 580);
		}
	}

	public boolean getDead() {
		return isDead;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public Rectangle getViewport() {
		return viewport;
	}
	
	public int getScore() {
		return score;
	}
	

	// YOUR CODE GOES HERE
	// define methods

}// end class Player