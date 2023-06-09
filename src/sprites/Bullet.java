package sprites;

import game.Input;

public class Bullet extends Sprite {

	public int acceleration;

	public Bullet(int x, int y, int acceleration) {
		super(x, y);
		this.acceleration = acceleration;
	}

	public void tick(Input input) {
		if (getY() >= 0) {
			setY(getY() - acceleration);
		} else
			remove();
	}
}
