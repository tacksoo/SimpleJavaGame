package game;

import java.awt.event.KeyEvent;

public class Input {

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int SPACE = 2;
	public boolean[] buttons = new boolean[3];
	public boolean[] oldButtons = new boolean[3];

	public void set(int key, boolean down) {
		int button = -1;
		if (key == KeyEvent.VK_LEFT)
			button = 0;
		if (key == KeyEvent.VK_RIGHT)
			button = 1;
		if (key == KeyEvent.VK_SPACE)
			button = 2;

		if (button >= 0)
			buttons[button] = down;
	}

	public void tick() {
		for (int i = 0; i < buttons.length; i++) {
			oldButtons[i] = buttons[i];
		}
	}
}
