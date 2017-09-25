package Hangi.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
/******
 * 스프라이트의 자료를 가져온다
 *****/
public class SpriteSheet {
	
	private BufferedImage sheet;
	
	public SpriteSheet(String path){
		try {
			//ImageIO를 통해 이미지를 로딩 여기서는 입력받는 String 형을 로딩
			sheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//이미지를 입력받은 x, y의 비례  크기만큼 자른다.
	public BufferedImage getSprite(int x,int y){
		return sheet.getSubimage(x*32-32, y*32-32, 32, 32);
	}
}
