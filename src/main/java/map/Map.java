package map;

import java.net.URL;
import java.util.ArrayList;

import creature.*;
import formation.SingleLine;
import formation.Yoke;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import record.RecordPlayer;

@SuppressWarnings("unused")
public class Map {
	public ImageView imageView;
	private int row, col;               //大小
	@SuppressWarnings("rawtypes")
	private Position[][] pos;           //位置
	private CalabashBrother brothers;   //葫芦兄弟
	private GrandFather grandpa;        //爷爷
	private ArrayList<Minion> minions;           //若干小喽啰
	private SnakeMonster snake;         //蛇精
	private ScorpionMonster scorpion;   //蝎子精
	
	ArrayList<Thread> threads = new ArrayList<Thread>();
	
	public Scene scene;
	public Group root;
	
	RecordPlayer recordPlayer;
	Thread replayThread = new Thread();
	
	long startTime;
	
	//初始化地图   建造整个地图的位置信息以及必须有的生物
	public Map(int row, int col, Scene scene, Group root) {
		this.scene = scene;
		this.root = root;
		//String imagePath = "application/icons/garden.jpg";
		String imagePath = "icons/garden.jpg";
		URL loc = this.getClass().getClassLoader().getResource(imagePath);
    	Image image = new Image(loc.toString());
	//	Image image = new Image(imagePath);
		imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitHeight(700);
		imageView.setFitWidth(1000);
		
		this.row = row;
		this.col = col;
	}

	//获取信息
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	@SuppressWarnings("rawtypes")
	public synchronized Position[][] getPos() {
		return pos;
	}
	public CalabashBrother getBrother() {
		return brothers;
	}
	public GrandFather getGrandpa() {
		return grandpa;
	}
	public ArrayList<Minion> getMinions() {
		return minions;
	}
	public SnakeMonster getSnake() {
		return snake;
	}
	public ScorpionMonster getScorpion() {
		return scorpion;
	}
	public RecordPlayer getRecordPlayer() {
		return recordPlayer;
	}
	
	public boolean isInMap(int x, int y) {
		//只检查数组越界，位置是否被占用依靠异常处理（position.java）
		return (x >= 0 && x < row && y >= 0 && y < col);
	}
	public long getRunningTime() {
	    return System.currentTimeMillis() - this.startTime;
	}
	@SuppressWarnings("rawtypes")
	public synchronized void clearMap() {
		root.getChildren().clear();
		threads.clear();
	//	recordPlayer = null;
		//位置
		pos = new Position[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				pos[i][j] = new Position(i, j);
			}
		}
		grandpa = null;
		brothers = null;
		minions = null;
		snake = null;
		scorpion = null;

		printMap();
	}
	//输出位置上的生物
	public synchronized void printMap() {
		//scene.setFill(Color.WHITE);
		root.getChildren().clear();
		root.getChildren().add(imageView);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Creature curCre = pos[i][j].getCreature();
				if (curCre != null) {
				//	curCre.printCreature();
				//	System.out.print('*');
					//UI
					ImageView creImageView = curCre.getImageView();
					creImageView.setX(50+j*75);
					creImageView.setY(50+i*75);
					root.getChildren().add(creImageView);
					Rectangle bkHp = new Rectangle();
					bkHp.setWidth(50);
					bkHp.setHeight(5);
					bkHp.setX(62.5+j*75);
					bkHp.setY(51+i*75);
					bkHp.setFill(Color.GRAY);
			        root.getChildren().add(bkHp);
			        Rectangle curHp = new Rectangle();
			        curHp.setWidth(50*curCre.getHpRate());
			        curHp.setHeight(5);
			        curHp.setX(62.5+j*75);
					curHp.setY(51+i*75);
					curHp.setFill(Color.RED);
					root.getChildren().add(curHp);
				} else {
				//	System.out.print('-');
				}
			}
		//	System.out.println();
		}	
	//	System.out.println();
	}
	
	public void battle() {
	//	recordPlayer = new RecordPlayer(this);
		this.threads.clear();
		this.threads.add(new Thread(grandpa));
        for (CalabashBody huluwa : brothers.getOrder())
            this.threads.add(new Thread(huluwa));
        for (Minion minion : minions)
            this.threads.add(new Thread(minion));
        this.threads.add(new Thread(scorpion));
        this.threads.add(new Thread(snake));
        
        this.startTime = System.currentTimeMillis();
        for (Thread thread : this.threads)
            thread.start();
	}
	public boolean battleIsFinished() {
		boolean goodAlive = false;
		for (CalabashBody cb : brothers.getOrder()) {
			if (cb.isAlive()) {
				goodAlive = true;
				break;
			}
		}
		if (grandpa.isAlive()) {
			goodAlive = true;
		}
		
		boolean badAlive = false;
		for (Minion m : minions) {
			if (m.isAlive()) {
				badAlive = true;
			}
		}
		if (snake.isAlive()) {
			badAlive = true;
		}
		if (scorpion.isAlive()) {
			badAlive = true;
		}
		
		if (!goodAlive || !badAlive)
		{
			  for (Thread thread : threads) {
				  thread.interrupt();
	          }
		}
		
		return (!goodAlive || !badAlive);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void initMap() {
		clearMap();
		recordPlayer = new RecordPlayer(this);
		
		//位置
		pos = new Position[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				pos[i][j] = new Position(i, j);
			}
		}
		
		//生物
		brothers = new CalabashBrother(this, 0);
		
		grandpa = new GrandFather(this, 7);
		
		snake = new SnakeMonster(this, 8);
		
		scorpion = new ScorpionMonster(this, 9);
		
		minions = new ArrayList<Minion>();
		for (int i = 0; i < 6; i++) {
			minions.add(new Minion(this, i+1, 10+i));
		}
		

		
		
	/*	for (Thread thread : threads) {
             thread.stop();
        }*/
		this.threads.clear();
		this.recordPlayer.getRecords().clear();
		
		//葫芦娃排成长蛇阵
		SingleLine changSheZhen = new SingleLine();
		getBrother().bubbleSort();  //乱序葫芦娃排序
		changSheZhen.form(this, getBrother().getOrder(), 0, 0);
		
		//放置爷爷
		//getGrandpa().cheer(this, 4, 1);
		getGrandpa().move(pos[4][1]);
		
		//放置蝎子精
		//getScorpion().lead(this, 0, 10);
		getScorpion().move(pos[0][10]);
		
		//小喽啰排成衡轭阵
		Yoke hengEZhen = new Yoke();
		hengEZhen.form(this, getMinions(), 1, 11);
		
		//放置蛇精
		//getSnake().cheer(this, 4, 8);
		getSnake().move(pos[4][8]);

		printMap();
		
		//记录
		for (CalabashBody cb : brothers.getOrder()) {
			recordPlayer.addRecord(recordPlayer.newCreateRecord(cb));
		}
		recordPlayer.addRecord(recordPlayer.newCreateRecord(grandpa));
		recordPlayer.addRecord(recordPlayer.newCreateRecord(scorpion));
		recordPlayer.addRecord(recordPlayer.newCreateRecord(snake));
		for (Minion minion : minions) {
			recordPlayer.addRecord(recordPlayer.newCreateRecord(minion));
		}
	}

	public void replay() {
		clearMap();
		printMap();
		replayThread = (new Thread(recordPlayer));
		replayThread.start();
		replayThread.interrupt();
	}
}
