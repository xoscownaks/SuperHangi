package Hangi;

import Hangi.Entity.Entity;

public class Camera {
	public int x,y;
	
	//ī�޶� ����
	//�÷��̾��� x,y��ǥ -�� ���� ��üȭ�� *2�� �־� �߾ӿ��ִ� ��ó�� ������ �ش� *2���������� �÷��̾�� �������� ����
	public void tick(Entity player){
		setX(-player.getX()+Game.WIDTH*2);
		setY(-player.getY()+Game.HEIGHT*2);

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
	
}
