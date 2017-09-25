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
	
	//스프라이트시트의 원하는 프렘임범위를 가져올때 사용
	private int frame = 0;
	private int frameDelay = 0;
	private boolean animate = false;
	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) {
		//스프라이트시트 왼쪽 5개
		if(facing == 0){
			g.drawImage(Game.player[frame+5].getBufferedImage(), x, y, width, height,null);
		}
		//스프라이트시트 오른쪽 5개
		else if(facing == 1){
			g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height,null);
		}
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		//캔버스 범위를 벗어나지 않게 만들기
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
		//객체의 크기를 가져와서 부딫치면 더이상 지나가지 못하게 한다.
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
					//위의 구조로 넘어가면 그냥 내려오지 않기때문에 
					//falling 을 true로 바꾸어 떨어지게한다
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
				//깃발과 부딫치면 끝
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

		//공중에 올라가는 속도아 내려오는속도
		//더블형 -> 인트형 
		if(jumping){
			gravity-=0.2;
			setVelY((int)-gravity);
			//공중에 뜨면 판정을 바꾼다
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
