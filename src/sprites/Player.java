package sprites;

import game.Input;

public class Player extends Sprite {

	public Player(int x, int y) {
		super(x, y);
	}

	@Override
	public void tick(Input input) {
		if (input.buttons[Input.LEFT])
			setX(getX() - 1);
		if (input.buttons[Input.RIGHT])
			setX(getX() + 1);
	}
}
