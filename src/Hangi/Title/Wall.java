package Hangi.Title;

import java.awt.Color;
import java.awt.Graphics;

import Hangi.Game;
import Hangi.Handler;
import Hangi.Id;

public class Wall extends Tile{

	public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.grass.getBufferedImage(), x, y, width, height,null);

	}

	@Override
	public void tick() {
		
	}

}
