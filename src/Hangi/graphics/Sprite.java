package Hangi.graphics;

import java.awt.image.BufferedImage;

public class Sprite {
	
	public SpriteSheet sheet;
	public BufferedImage image;
	
	//이미지를 자른 요소를 가져와 image에 저장
	public Sprite(SpriteSheet sheet,int x,int y){
		image = sheet.getSprite(x, y);
	}
	public BufferedImage getBufferedImage(){
		return image;
	}
}
