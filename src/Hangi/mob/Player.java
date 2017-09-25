package Hangi.mob;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Hangi.Game;
import Hangi.Handler;
import Hangi.Id;
import Hangi.Entity.Entity;
import Hangi.build.Tile;

public class Player extends Entity{
	
	//��������Ʈ��Ʈ�� ���ϴ� �����ӹ����� �����ö� ���
	private int frame = 0;
	private int frameDelay = 0;
	private boolean animate = false;
	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) {
		//��������Ʈ��Ʈ ���� 5��
		if(facing == 0){
			g.drawImage(Game.player[frame+5].getBufferedImage(), x, y, width, height,null);
		}
		//��������Ʈ��Ʈ ������ 5��
		else if(facing == 1){
			g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height,null);
		}
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		//ĵ���� ������ ����� �ʰ� �����
		//if(x<=0) 
		//	x=0;
		//if(y<=0)
		//	y=0;
		//if(x+width >=1090)
		//	x = 1090 - width;
		//if(y+height >=771)
		//	y = 771-height;
		
		//if(velX!=0)
		//	animate = true;
		//else 
		//	animate = false;
		//��ü�� ũ�⸦ �����ͼ� �΋Hġ�� ���̻� �������� ���ϰ� �Ѵ�.
		for(int i=0;i<handler.tile.size();i++){
			Tile t = handler.tile.get(i);

			if(t.isSolid()){
				if(getBoundsTop().intersects(t.getBounds())){
					setVelY(0);
					if(jumping){
						jumping = false;
						gravity = 0.8;
						falling = true;
					}
				}
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
					setVelX(0);
					x = t.getX()+t.width;
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(0);
					x = t.getX()-t.width;
				}
				//��߰� �΋Hġ�� ��
				if(getBounds().intersects(t.getBounds())){
					if(t.getId()==Id.flag){
						System.exit(0);

					}
				}
			}
			
		}
		for(int i=0;i<handler.entity.size();i++){
			Entity e= handler.entity.get(i);
			if(e.getId()==Id.teemo){
				if(getBoundsBottom().intersects(e.getBoundsTop())){
					/*int tpX = getX();
					int tpY = getY();
					setX(tpX-width);
					setY(tpY-height);*/
					Game.teemoBye.play();
					e.die();				
	
				}
				else if(getBounds().intersects(e.getBounds())){
					die();
					
				}
			}
		}

		//���߿� �ö󰡴� �ӵ��� �������¼ӵ�
		//������ -> ��Ʈ�� 
		if(jumping){
			gravity-=0.2;
			setVelY((int)-gravity);
			//���߿� �߸� ������ �ٲ۴�
			if(gravity <= 0.0){
				jumping = false;
				falling = true;
			}
			
		}
		if(falling){
			gravity+=0.1;
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
