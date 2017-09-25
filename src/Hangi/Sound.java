package Hangi;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Sound{
	/*Player clip;
//	boolean SoundLoop;
	public Sound(String file){
		try {
			FileInputStream fis = new FileInputStream(file);
			clip = new Player(fis);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void play(){
		try {
			clip.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	Clip clip;
	Sound(String file){
		try{
			//������ audio������ �����´�
			//��������������� �����´�
			//mp3�������� ���ڵ�
			//���� ��ġ�� ���� �� ������Ʈ�� �� �Ҽ� ����.
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(file));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 
					16, baseFormat.getChannels(), baseFormat.getChannels()*2,baseFormat.getSampleRate(),false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat,ais);
		
			clip = AudioSystem.getClip();
			clip.open(dais);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void play(){
		if(clip == null)
			return;
		stop();
		//Ŭ���ȿ� �ִ� ���带 �ٽ� �ǰ���
		clip.setFramePosition(0);
		clip.start();
	}
	public void close(){
			stop();
			clip.close();
	}
	public void stop(){
		if(clip.isRunning())
			clip.stop();
	}
	/*AudioInputStream ais;
	boolean SoundLoop;
	/*Sound(String file,boolean Loop){
		try{
			//������ audio������ �����´�
			//��������������� �����´�
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			clip = AudioSystem.getClip();
			SoundLoop = Loop;
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	public void play(){
		try {
			clip.start();
			if(SoundLoop)
				clip.loop(-1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	*/

	
}
