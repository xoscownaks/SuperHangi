package Hangi.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Hangi.Game;
import Hangi.Id;
import Hangi.Entity.Entity;

public class KeyInput implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {	
		
	}
	//Ű���� Ű�� ������
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i=0;i<Game.handler.entity.size();i++){
			//���� Ű�� ���� ���̽��� ����
			//�÷��̾ x,y������ �����δ�
			Entity en = Game.handler.entity.get(i);
			if(en.getId()==Id.player){
				switch(key){
				case KeyEvent.VK_UP:
					if(!en.jumping){
						en.jumping = true;
						//�ö󰡴� �ӿ� ���� ���߿� ��� �ִ� ����
						en.gravity = 9.7;
						System.out.print("up");
						Game.jump.play();			
					}
					break;
				case KeyEvent.VK_LEFT:
					en.setVelX(-5);
					en.facing = 0;
					break;
				case KeyEvent.VK_RIGHT:
					en.setVelX(5);
					en.facing = 1;
					break;
				}
			}			
		}
	}
	//Ű���带 ������
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i=0;i<Game.handler.entity.size();i++){
			Entity en = Game.handler.entity.get(i);
			//���� Ű�� ���� ���̽��� ����
			//�÷��̾ x,y������ �����δ�
			if(en.getId()==Id.player){
				switch(key){
				case KeyEvent.VK_UP:
					en.setVelY(0);
					break;
				case KeyEvent.VK_DOWN:
					en.setVelY(0);
					break;
				case KeyEvent.VK_LEFT:
					en.setVelX(0);
					break;
				case KeyEvent.VK_RIGHT:
					en.setVelX(0);
					break;
				}
			}
			
		}
	}
	
}

