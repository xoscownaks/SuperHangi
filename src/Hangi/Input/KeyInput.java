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
	//키보드 키를 누르면
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i=0;i<Game.handler.entity.size();i++){
			//받은 키에 따라 케이스문 실행
			//플레이어가 x,y축으로 움직인다
			Entity en = Game.handler.entity.get(i);
			if(en.getId()==Id.player){
				switch(key){
				case KeyEvent.VK_UP:
					if(!en.jumping){
						en.jumping = true;
						//올라가는 속에 비래한 공중에 뜰수 있는 높이
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
	//키보드를 놓으면
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i=0;i<Game.handler.entity.size();i++){
			Entity en = Game.handler.entity.get(i);
			//받은 키에 따라 케이스문 실행
			//플레이어가 x,y축으로 움직인다
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

