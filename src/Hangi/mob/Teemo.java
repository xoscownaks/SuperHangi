package Hangi.mob;

import java.awt.Graphics;
import java.util.Random;

import Hangi.Game;
import Hangi.Handler;
import Hangi.Id;
import Hangi.Entity.Entity;
import Hangi.build.Tile;

public class Teemo extends Entity{
	private int frame = 0;
	private int frameDelay = 0;
	private Random random = new Random();
	
	public Teemo(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		// TODO Auto-generated constructor stub
		//������ �߰��� Teemo ��ü�� �������� �¿�� �����̰� ���� �κ�
		int dir = random.nextInt(2);
		switch(dir){
		case 0:
			setVelX(-1);
			facing =0;
			break;
		case 1:
			setVelX(1);
			facing = 1;
			break;
		}
	}
	//��������Ʈ��Ʈ���� ������ 
	public void render(Graphics g){
		if(facing == 0){
			g.drawImage(Game.teemo[5+frame].getBufferedImage(), x, y, width, height,null);
		}
		//��������Ʈ��Ʈ ������ 5��
		else if(facing == 1){
			g.drawImage(Game.teemo[frame].getBufferedImage(), x, y, width, height,null);
		}
	}
	public void tick(){
		x+=velX;
		y+=velY;
		for(int i=0;i<handler.tile.size();i++){
			Tile t = handler.tile.get(i);
			if(t.isSolid()){
				if(getBoundsBottom().intersects(t.getBounds())){
					setVelY(0);
					//y = t.getY()-t.height;
					if(falling)
						falling = false;
				}
				else if(!falling && !jumping){
					//���� ������ �Ѿ�� �׳� �������� �ʱ⶧���� 
					//falling �� true�� �ٲپ� ���������Ѵ�
					gravity = 0.8;
					falling = true;
					
				}
				if(getBoundsLeft().intersects(t.getBounds())){
					setVelX(5);
					facing = 1;
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(-5);
					facing=0;
				}
			}
			
		}
		if(falling){
			gravity += 0.1;
			setVelY((int)gravity);
					
		}
		if(velX!=0){
			frameDelay++;
			if(frameDelay >=10){
				frame++;
				if(frame > 3){
					frame = 0;
				}
				frameDelay = 0;
			}
		}
	}
}

