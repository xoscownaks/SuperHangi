package Hangi;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

import Hangi.Entity.Entity;
import Hangi.Input.KeyInput;
import Hangi.graphics.Sprite;
import Hangi.graphics.SpriteSheet;
import javazoom.jl.player.*;

public class Game extends Canvas implements Runnable{
	public static final int WIDTH = 320;
	public static final int HEIGHT = 180;
	public static final int SCALE = 4;	
	public static final String TITLE="SuperHangi";
	
	private Thread thread;					//��������
	private boolean running = false;		//�������� ��������
	private BufferedImage image;			//����(��)�� ���鶧 ���
	
	/*private BufferedImage level1;
	private BufferedImage level2;
	private static int level = 0;
	������ ��Ȱ�ϴµ� ����� ������ 
	public static int lives = 3;
	public static int deathScreenTime = 0;*/
	//public static boolean showDeathScreen = false;
	
	public static Handler handler;			//�⺻���� �ʰ��� ��ü�� �ڵ鸵
	public static SpriteSheet sheet;		//��������Ʈ��Ʈ���� ����� ��Ʈ
	public static Camera cam;				//��Ŀ���� ���ΰ� ����ٴϰ� �ϱ����� cam����
	
	public static Sprite grass;
	public static Sprite[] player;
	public static Sprite[] teemo;
	public static Sprite[] flag;
	
	public static Sound bgm;
	public static Sound jump;
	public static Sound teemoBye;
	
	public Game(){
		Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);	//ȭ���� �����ϱ����� ���
		setPreferredSize(size);	//ũ�⸦ ����
		setMaximumSize(size);
		setMinimumSize(size);
	}	
	//���� ��ü�� ȣ��, �⺻���� ����
	private void init(){
		handler = new Handler();
		sheet = new SpriteSheet("/spritesheet.png");
		cam = new Camera();
		addKeyListener(new KeyInput());
		
		grass = new Sprite(sheet, 1, 1);
		player = new Sprite[10];
		teemo = new Sprite[10];
		flag = new Sprite[2];
		
		//1~10���� ���÷��̾� �ൿ�� ��������Ʈ��Ʈ���� ����
		for(int i =0;i<player.length;i++){
			player[i] = new Sprite(sheet,i+1,16);
		}
		for(int i =0;i<teemo.length;i++){
			teemo[i] = new Sprite(sheet,i+1,15);
		}
		for(int i =0;i<flag.length;i++){
			flag[i] = new Sprite(sheet,i+2,1);
		}
		try {
			image = ImageIO.read(getClass().getResource("/level.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.createLevel(image);
		bgm = new Sound("/thema.wav");
		jump = new Sound("/jump.wav");
		teemoBye = new Sound("/TeemoDeath.wav");	
		bgm.play();

	}
	//������ ����ȭ
	private synchronized void start(){
		if(running) return;
		running = true;
		thread = new Thread(this,"Thread");
		thread.start();	//run����
	}
	private synchronized void stop(){
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run(){
		init();
		requestFocus();
		//���������� �ε巴�� �帣�� fps�������� ����� ���� ��� Frame per Second����
		//������ ��ü���� �ӵ��� ���� �ʴ�ȭ���� ��� ���εǴ��� �����Ͽ� �ӵ��� ����
		long lastTime = System.nanoTime();			//����� �ð��� ���ϱ����ؼ��� ��� ������ ����
		long timer = System.currentTimeMillis();	//����ð��� �и�������� ǥ��
		double delta = 0.0;
		double ns = 1000000000.0/60.0;
		int frames = 0;								//������
		int ticks = 0;								//�ۼ�����
		while(running){
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime=now;
			while(delta>=1){
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis()-timer>1000){
				timer += 1000;
				//System.out.println(ticks+"frame per second "+frames+"Update per second");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}
	//�׷��Ȱ��� ������ 
	public void render(){
		//ĵ���� �Ǵ� window������ Ŭ���� ,�뷫 ��ǻ�Ϳ� �׷����� �Ѹ��� �Ͱ� ȭ���� �׷��ִ� �ð��� �����̴°��� �ذ�
		BufferStrategy bs = getBufferStrategy();
		if(bs==null){
			createBufferStrategy(3);
			return;
		}
		Graphics g =bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.translate(cam.getX(), cam.getY());	//ī�޶� x,y��ġ �̵�
		handler.render(g);
		g.dispose();
		bs.show();
		
		
	}


	//������Ʈ �κ�
	public void tick(){
		handler.tick();
		for(int i=0;i<handler.entity.size();i++){
			Entity e= handler.entity.get(i);
			if(e.getId()== Id.player){
				cam.tick(e);
			}

		}
		
		/*������ �ٽ� ��Ȱ�ϴ� �ڵ� �̿ϼ�
		 * if(showDeathScreen)
		 
			deathScreenTime++;
		if(deathScreenTime >= 180){
			showDeathScreen = false;
			deathScreenTime = 0;
			//����� ó������ �����Ҽ� �ְ� �ٽø����
			handler.clearLevel();
			handler.createLevel(image);
		}*/
	}
	public int getFrameWidth(){
		return WIDTH*SCALE;
	}
	public int getFrameHeight(){
		return HEIGHT*SCALE;
	}
	

	public static void main(String[] args){
		Game game = new Game();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.pack();	//�����ӿ� �ִ� ������Ʈ���� ũ�⿡ �°� �������� ����������
		frame.setResizable(false);	//âũ�� ���� �Ұ�
		frame.setLocationRelativeTo(null);	//ȭ���� ����� ����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//X������ ������
		frame.setVisible(true); 	//�����ֱ�
		game.start();
	}
	
	
}
