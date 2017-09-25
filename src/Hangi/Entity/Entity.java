package Hangi.Entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import Hangi.Game;
import Hangi.Handler;
import Hangi.Id;
/**********************
 * 만들어질  객체들의 베이스가 될 부분
 *  
 */
public abstract class Entity {
	
	public int x, y; // x, y좌표
	public int width, height;	//가로, 세로길이 
	
	public int facing = 0;	//0이면 왼쪽 1이면 오른쪽
	public boolean solid;
	//점프와 떨어지기
	public boolean jumping = false;
	public boolean falling = true;
	
	//
	public double gravity = 0.0;
	
	public int velX,velY;	//x,y로 움직이는 간격
	public Id id;
	
	public Handler handler;
	public Entity(int x, int y,int width, int height, boolean solid,Id id,Handler handler){
		//Entity test = new Entity(100,100,50,100,true,Id enum에 등록된아이디,); 식으로 호출하기
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
	//죽으면 해당 객체를 제거한다.
	public void die(){
		handler.removeEntity(this);
		/*
		Game.lives--;
		미완성*/
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
	//배경의 직사각형의 공간을 만드는 Rectangle 크기를 가져와 충돌을 설정할때 사용
	//범위는 이미지의 크기를 생각하여 감잡아 조정한 값
	public Rectangle getBounds(){
		return new Rectangle(getX(),getY(),width,height);
	}
	public Rectangle getBoundsTop(){
		return new Rectangle(getX()+10,getY(),width-20,5);
	}
	public Rectangle getBoundsBottom(){
		return new Rectangle(getX()+10,getY()+height-5,width-20,5);

	}
	public Rectangle getBoundsLeft(){
		return new Rectangle(getX(),getY()+10,5,height-20);

	}
	public Rectangle getBoundsRight(){
		return new Rectangle(getX()+width-5,getY()+10,5,height-20);

	}
}
