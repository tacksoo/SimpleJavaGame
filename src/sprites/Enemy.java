package sprites;

import game.Game;
import game.Input;

public class Enemy extends Sprite {

	public Enemy(int x, int y) {
		super(x, y);
	}

	@Override
	public void tick(Input input) {
		setY(getY() + 1);
		if (getY() > Game.GAME_HEIGHT)
			remove();
	}

	public boolean shot(Bullet b) {
		if ((Math.abs(b.getX() - getX()) <= 50)
				&& (Math.abs(b.getY() - getY()) <= 50))
			return true;
		else
			return false;
	}
}
