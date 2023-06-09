package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sprites.Bullet;
import sprites.Enemy;
import sprites.Player;

/*
 * This is the game screen that gets tacked on the JFrame.
 * Notice that this gets drawn many times per second.
 * 
 */
public class Game extends Canvas implements Runnable, KeyListener {

	private Player p;
	private List<Bullet> bullets = new ArrayList<Bullet>();
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private Input input = new Input();
	private final int REGENERATION = 1500;
	private int time = 0;
	private Random r = new Random();

	public static final int GAME_WIDTH = 500;
	public static final int GAME_HEIGHT = 500;

	public Game() {
		p = new Player(250, 400); // create a player which is simply a box;
		addKeyListener(this);
	}

	// generate one enemy in one interval
	private void generateEnemy() {
		if (time++ > REGENERATION) {
			time = 0;
			enemies.add(new Enemy(r.nextInt(450), 0));
		}
	}

	private void processBullets(Graphics g) {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet a = bullets.get(i);
			if (!a.isRemoved()) {
				a.tick(input);
				g.drawLine(a.getX(), a.getY(), a.getX(), a.getY() - 5);
			} else {
				bullets.remove(a);
			}
		}
	}

	private void processPlayer(Graphics g) {
		p.tick(input);
		if (input.buttons[Input.SPACE] && !input.oldButtons[Input.SPACE]) {
			bullets.add(new Bullet(p.getX() + 25, p.getY(), 1));
			input.buttons[Input.SPACE] = false;
		}
		g.drawRect(p.getX(), p.getY(), 50, 50);
	}

	private void processEnemies(Graphics g) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (!e.isRemoved()) {
				e.tick(input);
				g.fillRect(e.getX(), e.getY(), 50, 50);
			}

			for (int j = 0; j < bullets.size(); j++) {
				if (e.shot(bullets.get(j))) {
					enemies.remove(e);
				}
			}
		}
	}

	// the main game loop
	private void render() {

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.RED);

		generateEnemy();
		processPlayer(g);
		processBullets(g);
		processEnemies(g);

		g.drawString("Sample Game", 400, 50);
		g.dispose();
		bs.show();

		// game is too fast without this delay
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			render();
		}
	}

	public void start() {
		new Thread(this).start();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		input.set(arg0.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		input.set(e.getKeyCode(), true);
	}

}
