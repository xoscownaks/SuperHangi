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
	
	private Thread thread;					//쓰레드사용
	private boolean running = false;		//쓰레드의 동작유무
	private BufferedImage image;			//레벨(맵)을 만들때 사용
	
	/*private BufferedImage level1;
	private BufferedImage level2;
	private static int level = 0;
	죽으면 부활하는데 사용할 변수들 
	public static int lives = 3;
	public static int deathScreenTime = 0;*/
	//public static boolean showDeathScreen = false;
	
	public static Handler handler;			//기본적인 맵관련 객체를 핸들링
	public static SpriteSheet sheet;		//스프라이트시트관련 사용할 시트
	public static Camera cam;				//포커스가 주인공 따라다니게 하기위한 cam변수
	
	public static Sprite grass;
	public static Sprite[] player;
	public static Sprite[] teemo;
	public static Sprite[] flag;
	
	public static Sound bgm;
	public static Sound jump;
	public static Sound teemoBye;
	
	public Game(){
		Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);	//화면을 관리하기위해 사용
		setPreferredSize(size);	//크기를 지정
		setMaximumSize(size);
		setMinimumSize(size);
	}	
	//각종 객체를 호출, 기본적인 설정
	private void init(){
		handler = new Handler();
		sheet = new SpriteSheet("/spritesheet.png");
		cam = new Camera();
		addKeyListener(new KeyInput());
		
		grass = new Sprite(sheet, 1, 1);
		player = new Sprite[10];
		teemo = new Sprite[10];
		flag = new Sprite[2];
		
		//1~10개의 ㅍ플레이어 행동을 스프라이트시트에서 추출
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
	//쓰레드 동기화
	private synchronized void start(){
		if(running) return;
		running = true;
		thread = new Thread(this,"Thread");
		thread.start();	//run실행
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
		//성능측정용 부드럽게 흐르는 fps프레임을 만들기 위해 사용 Frame per Second구현
		//게임의 전체적인 속도를 제어 초당화면이 몇번 갱인되는지 조정하여 속도를 조정
		long lastTime = System.nanoTime();			//경과된 시간을 구하기위해서만 사용 나노초 단위
		long timer = System.currentTimeMillis();	//현재시간을 밀리세컨드로 표시
		double delta = 0.0;
		double ns = 1000000000.0/60.0;
		int frames = 0;								//프레임
		int ticks = 0;								//퍼세컨드
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
	//그래픽관련 렌더링 
	public void render(){
		//캔버스 또는 window가능한 클래스 ,대략 컴퓨터에 그래픽을 뿌리는 것과 화면이 그려주는 시간에 깜박이는것을 해결
		BufferStrategy bs = getBufferStrategy();
		if(bs==null){
			createBufferStrategy(3);
			return;
		}
		Graphics g =bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.translate(cam.getX(), cam.getY());	//카메라 x,y위치 이동
		handler.render(g);
		g.dispose();
		bs.show();
		
		
	}


	//업데이트 부분
	public void tick(){
		handler.tick();
		for(int i=0;i<handler.entity.size();i++){
			Entity e= handler.entity.get(i);
			if(e.getId()== Id.player){
				cam.tick(e);
			}

		}
		
		/*죽으면 다시 부활하는 코드 미완성
		 * if(showDeathScreen)
		 
			deathScreenTime++;
		if(deathScreenTime >= 180){
			showDeathScreen = false;
			deathScreenTime = 0;
			//지우고 처음부터 시작할수 있게 다시만들기
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
		frame.pack();	//프레임에 있는 컴포넌트들의 크기에 맞게 프레임의 사이즈조정
		frame.setResizable(false);	//창크기 변경 불가
		frame.setLocationRelativeTo(null);	//화면의 가운데에 띄우기
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//X누르면 닫힌다
		frame.setVisible(true); 	//보여주기
		game.start();
	}
	
	
}
