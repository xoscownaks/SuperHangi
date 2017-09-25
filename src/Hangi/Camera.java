package Hangi;

import Hangi.Entity.Entity;

public class Camera {
	public int x,y;
	
	//카메라가 집중
	//플레이어의 x,y좌표 -의 값과 전체화면 *2를 주어 중앙에있는 것처럼 시점을 준다 *2하지않으면 플레이어는 좌측위에 있음
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
