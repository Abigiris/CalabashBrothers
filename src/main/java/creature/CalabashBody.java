package creature;

import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import map.Map;

public class CalabashBody extends Creature {
	public enum COLOR {RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE}
	public String[] allName = {"老大", "老二", "老三", "老四", "老五", "老六", "老七"};
	//赤赤黄绿青蓝紫
	public String[] allPrintColor = {"\033[31;4m" ,"\033[31;4m","\033[33;4m","\033[32;4m","\033[36;4m","\033[34;4m","\033[35;4m"};
	private int no;
    private COLOR color;
    private String printColor;
    
    public CalabashBody(Map map, int no, int id) {
    	super.id = id;
    	super.map = map;
    	this.no = no;
    	super.name = allName[no];
    	super.good = true;
    	super.alive = true;
    	super.speed = 500;
    	super.maxHp = 600;
    	super.hp = maxHp;
    	super.damage = 200;
    	this.color = COLOR.values()[no];
    	this.printColor = allPrintColor[no];
    	
		//super.imagePath = "application/icons/_" + super.name + ".jpeg";
    	super.imagePath = "icons/_" + super.name + ".jpeg";
    	URL loc = this.getClass().getClassLoader().getResource(imagePath);
    	Image image = new Image(loc.toString());
    	//Image image = new Image(imagePath);
		super.imageView = new ImageView();
		super.imageView.setImage(image);
		super.imageView.setFitHeight(75);
		super.imageView.setFitWidth(75);

    }

    //for test, without image
    public CalabashBody(int no, boolean forTest) {
    	this.no = no;
    	super.name = allName[no];
    	super.good = true;
    	super.alive = true;
    	this.color = COLOR.values()[no];
    	this.printColor = allPrintColor[no];
		super.imageView = null;
    }
    public int getNo() {
    	return no;
    }
    public String getName() {
    	return super.name;
    }
    public COLOR getColor() {
    	return color;
    }
    @Override
    public void printCreature() {
		System.out.print(printColor + '&' + "\033[0m");
	}
    /*
    @Override
	public void autoMove(Map map) {
		if (map.battleIsFinished()) { return; }
		
		ArrayList<Position> enemyPos = new ArrayList<Position>();
		ArrayList<Position> emptyPos = new ArrayList<Position>();
		int ud = position.getX();
		int lr = position.getY();
		//上下方向
		if (ud > 0) {
			//其上方不越界
			Creature uCre = map.getPos()[ud-1][lr].getCreature();
			if (uCre == null ) {
				//空地
				emptyPos.add(map.getPos()[ud-1][lr]);
			} else if (uCre.isGood() != good) {
				enemyPos.add(map.getPos()[ud-1][lr]);
			}
		}
		if (ud < map.getRow()-1) {
			Creature dCre = map.getPos()[ud+1][lr].getCreature();
			if (dCre == null) {
				emptyPos.add(map.getPos()[ud+1][lr]);
			} else if (dCre.isGood() != good) {
				enemyPos.add(map.getPos()[ud+1][lr]);
			}
		}
		if (lr >= map.getCol()/2) {
			//已经到达右方敌方阵营 可以回退
			Creature lCre = map.getPos()[ud][lr-1].getCreature();
			if (lCre == null) {
				emptyPos.add(map.getPos()[ud][lr-1]);
			} else if (lCre.isGood() != good) {
				enemyPos.add(map.getPos()[ud][lr-1]);
			}
		}
		if (lr < map.getCol()*3/4) {
			Creature rCre = map.getPos()[ud][lr+1].getCreature();
			if (rCre == null) {
				emptyPos.add(map.getPos()[ud][lr+1]);
			} else if (rCre.isGood() != good) {
				enemyPos.add(map.getPos()[ud][lr+1]);
			}
		}
		
		if (enemyPos.size() > 0) {
			Position battlePos =  enemyPos.get((int)(Math.random()*enemyPos.size()));
			if ((int)(Math.random()*2) == 0) {
				kill(map, battlePos.getCreature());
			} else {
				beKilled(map);
			}
		} else if (emptyPos.size() > 0) {
			Position movePos =  emptyPos.get((int)(Math.random()*emptyPos.size()));
			move(movePos);
		}
		
					
	}*/
}