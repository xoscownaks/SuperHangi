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
	//각 노드들이 다음노드를  가리키는 컬렉션
	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();
	
	
	/*public Handler(){
		createLevel();
	}*/
	public void render(Graphics g){
		//entity에있는 값을 하나씩 en에 대입
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
	//추가하고 제거하는 기능
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
	//맵구조 길만들기
	public void createLevel(BufferedImage level){
		int width = level.getWidth();
		int height = level.getHeight();
		
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				//>>쉬프트 연산자를 사용해 픽섹의 비트를 이동하여 rgb의 색상을 숫자로 가져온다
				//가져온 rgb의 색상에 비교해서 해당 색부부을 벽으로 만든다.
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
	/*미완성
	//죽을경우 모든 유닛들과 타일을 지운다
	public void clearLevel(){
		entity.clear();
		tile.clear();
	}*/
	
}
