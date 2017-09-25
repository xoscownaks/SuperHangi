package Hangi.Title;

import java.awt.Graphics;
import java.awt.Rectangle;

import Hangi.Handler;
import Hangi.Id;

public abstract class Tile {
	
	public int x, y; // x, y좌표
	public int width, height;
	
	public boolean solid;
	
	public int velX,velY;
	public Id id;
	public Handler handler;
	public Tile(int x, int y,int width, int height, boolean solid, Id id,Handler handler){
		//Entity test = new Entity(100,100,50,100,true); 식으로 호출하기
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
		
	}
	public abstract void render(Graphics g);
	public abstract void tick();
	
	//죽으면 삭제
	public void die(){
		handler.removeTile(this);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Id getId(){
		return id;
	}
	public boolean isSolid() {
		return solid;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}
	public Rectangle getBounds(){
		return new Rectangle(getX(),getY(),width,height);
	}
	
}
