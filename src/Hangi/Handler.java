package Hangi;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import Hangi.Entity.Entity;
import Hangi.build.Flag;
import Hangi.build.Tile;
import Hangi.build.Wall;
import Hangi.mob.Player;
import Hangi.mob.Teemo;


public class Handler{
	//�� ������ ������带  ����Ű�� �÷���
	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();
	
	
	/*public Handler(){
		createLevel();
	}*/
	public void render(Graphics g){
		//entity���ִ� ���� �ϳ��� en�� ����
		for(int i=0;i<entity.size();i++){
			Entity e = entity.get(i);
			e.render(g);
		}
		for(int i=0;i<tile.size();i++){
			Tile t = tile.get(i);
			t.render(g);
		}
	}
	public void tick(){

		for(int i=0;i<entity.size();i++){
			Entity e = entity.get(i);
			e.tick();
		}
		for(int i=0;i<tile.size();i++){
			Tile t = tile.get(i);

			t.tick();
		}
	}
	//�߰��ϰ� �����ϴ� ���
	public void addEntity(Entity en){
		entity.add(en);
	}
	public void removeEntity(Entity en){
		entity.remove(en);
	}
	public void addTile(Tile ti){
		tile.add(ti);
	}
	public void removeTile(Tile ti){
		tile.remove(ti);
	}
	//�ʱ��� �游���
	public void createLevel(BufferedImage level){
		int width = level.getWidth();
		int height = level.getHeight();
		
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				//>>����Ʈ �����ڸ� ����� �ȼ��� ��Ʈ�� �̵��Ͽ� rgb�� ������ ���ڷ� �����´�
				//������ rgb�� ���� ���ؼ� �ش� ���κ��� ������ �����.
				int pixel = level.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 0 && green==0 && blue ==0){
					addTile(new Wall(x*64, y*64, 64, 64, true, Id.Wall, this));
				}
				if(red == 0 && green==0 && blue ==255){
					addEntity(new Player(x*64, y*64, 64, 64, false, Id.player, this));
				}
				if(red == 255 && green==0 && blue ==0){
					addEntity(new Teemo(x*64, y*64, 64, 64, true, Id.teemo, this));
				}			
				if(red == 0 && green==255 && blue==0){
					addTile(new Flag(x*64,y*64,64,64*5,true,Id.flag,this));
				}
			}
		}
		/*for(int i= 0;i<Game.WIDTH*Game.SCALE/64+1;i++){
			addTile(new Wall(i*64,Game.HEIGHT*Game.SCALE-55,65,64,true,Id.Wall,this));
			if(i!=0 && i!=1 && i!=15 && i!=16 && i!=17)	
				addTile(new Wall(i*64,350,64,64,true,Id.Wall,this));		
		}*/
			
	}
	/*�̿ϼ�
	//������� ��� ���ֵ�� Ÿ���� �����
	public void clearLevel(){
		entity.clear();
		tile.clear();
	}*/
	
}
