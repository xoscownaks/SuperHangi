package Hangi.Entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import Hangi.Game;
import Hangi.Handler;
import Hangi.Id;
/**********************
 * �������  ��ü���� ���̽��� �� �κ�
 *  
 */
public abstract class Entity {
	
	public int x, y; // x, y��ǥ
	public int width, height;	//����, ���α��� 
	
	public int facing = 0;	//0�̸� ���� 1�̸� ������
	public boolean solid;
	//������ ��������
	public boolean jumping = false;
	public boolean falling = true;
	
	//
	public double gravity = 0.0;
	
	public int velX,velY;	//x,y�� �����̴� ����
	public Id id;
	
	public Handler handler;
	public Entity(int x, int y,int width, int height, boolean solid,Id id,Handler handler){
		//Entity test = new Entity(100,100,50,100,true,Id enum�� ��ϵȾ��̵�,); ������ ȣ���ϱ�
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
	//������ �ش� ��ü�� �����Ѵ�.
	public void die(){
		handler.removeEntity(this);
		/*
		Game.lives--;
		�̿ϼ�*/
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
	//����� ���簢���� ������ ����� Rectangle ũ�⸦ ������ �浹�� �����Ҷ� ���
	//������ �̹����� ũ�⸦ �����Ͽ� ����� ������ ��
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
