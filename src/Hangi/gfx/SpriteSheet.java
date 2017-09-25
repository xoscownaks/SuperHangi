package Hangi.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
/******
 * ��������Ʈ�� �ڷḦ �����´�
 *****/
public class SpriteSheet {
	
	private BufferedImage sheet;
	
	public SpriteSheet(String path){
		try {
			//ImageIO�� ���� �̹����� �ε� ���⼭�� �Է¹޴� String ���� �ε�
			sheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//�̹����� �Է¹��� x, y�� ���  ũ�⸸ŭ �ڸ���.
	public BufferedImage getSprite(int x,int y){
		return sheet.getSubimage(x*32-32, y*32-32, 32, 32);
	}
}
