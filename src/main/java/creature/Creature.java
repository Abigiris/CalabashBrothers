package creature;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import map.*;
import record.Record;

public class Creature implements Runnable {
	Map map;
	protected int id;
	protected String name;
	protected boolean good;
	protected boolean alive;
	protected int speed;
	protected int maxHp;
	protected int hp;
	protected int damage;
	protected String imagePath;
	protected ImageView imageView;
	@SuppressWarnings("rawtypes")
	protected Position position;
	public Creature() {}
	public Creature(Map map, String imagePath, int maxHp, int hp, int id) {
		this.map = map;
		this.imagePath = imagePath;
		Image image = new Image(imagePath);
		this.imageView = new ImageView();
		this.imageView.setImage(image);
		this.imageView.setFitHeight(75);
		this.imageView.setFitWidth(75);
		this.id = id;
		this.alive = true;
		this.maxHp = maxHp;
		this.hp = hp;
	}
	//生物走到指定位置
	@SuppressWarnings(value={"unchecked"})
	public void move(@SuppressWarnings("rawtypes") Position pos) {
		if (position != null) {
			position.removeCrearure();
		}
		pos.setCreature(this);
		position = pos;
	}
	//自动移动并战斗
	@SuppressWarnings("rawtypes")
	public void autoMove() {
		if (map.battleIsFinished()) { 
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return; 
		}
		
		if (good) {
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
			if (lr > map.getCol()*5/12) {	
				//已经到达右方敌方阵营 可以回退
				Creature lCre = map.getPos()[ud][lr-1].getCreature();
				if (lCre == null) {
					emptyPos.add(map.getPos()[ud][lr-1]);
				} else if (lCre.isGood() != good) {
					enemyPos.add(map.getPos()[ud][lr-1]);
				}
			}
			if (lr < map.getCol()*7/12) {
				Creature rCre = map.getPos()[ud][lr+1].getCreature();
				if (rCre == null) {
					emptyPos.add(map.getPos()[ud][lr+1]);
				} else if (rCre.isGood() != good) {
					enemyPos.add(map.getPos()[ud][lr+1]);
				}
			}
			
			if (enemyPos.size() > 0) {
				int posIndex = (int)(Math.random()*(enemyPos.size()+0));
				if (posIndex >= enemyPos.size()) { posIndex -= enemyPos.size();}
				Position battlePos =  enemyPos.get(posIndex);
		/*		//概率阵亡
				if ((int)(Math.random()*2) == 0) {
					kill(map, battlePos.getCreature());
				} else {
					beKilled(map);
				}
				*/
				//攻击
				attackCre(battlePos.getCreature());
			} else if (emptyPos.size() > 0) {
				int posIndex = (int)(Math.random()*(emptyPos.size()+1));
				if (posIndex >= emptyPos.size()) { return;}
				Position movePos =  emptyPos.get(posIndex);
				move(movePos);
				//记录
				Record moveRecord = map.getRecordPlayer().newMoveRecord(this, map.getRunningTime());
				map.getRecordPlayer().addRecord(moveRecord);
			}
			
			
			
		} else {
			
			ArrayList<Position> optPos = new ArrayList<Position>();
			int ud = position.getX();
			int lr = position.getY();
			//上下方向
			if (ud > 0) {
				//其上方不越界
				Creature uCre = map.getPos()[ud-1][lr].getCreature();
				if (uCre == null) {
					//空地或者敌方 可以选择该方向
					optPos.add(map.getPos()[ud-1][lr]);
				}
			}
			if (ud < map.getRow()-1) {
				Creature dCre = map.getPos()[ud+1][lr].getCreature();
				if (dCre == null) {
					optPos.add(map.getPos()[ud+1][lr]);
				}
			}
			if (lr > map.getCol()*5/12) {
				//未达到左侧敌方深处 可以继续向左
				Creature lCre = map.getPos()[ud][lr-1].getCreature();
				if (lCre == null) {
					optPos.add(map.getPos()[ud][lr-1]);
				}
			}
			if (lr < map.getCol()*7/12) {
				//已达到敌方阵营 可以回退
				Creature rCre = map.getPos()[ud][lr+1].getCreature();
				if (rCre == null) {
					optPos.add(map.getPos()[ud][lr+1]);
				}
			}
			
			if (optPos.size() == 0) { return;}
			
			int posIndex = (int)(Math.random()*(optPos.size()+1));
			if (posIndex >= optPos.size()) { return;}
			Position movePos = optPos.get(posIndex);
			if (movePos.getCreature() == null) {
				move(movePos);
				//记录
				Record moveRecord = map.getRecordPlayer().newMoveRecord(this, map.getRunningTime());
				map.getRecordPlayer().addRecord(moveRecord);
			} 

		}
		Platform.runLater(new Runnable() {
		    public void run() { 
		        map.printMap();
		    }
		});
		
	}
	public void printCreature() {
		
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public int getHp() {
		return hp;
	}
	public String getName() {
		return name;
	}
	public boolean isGood() {
		return good;
	}
	public boolean isAlive() {
		return alive;
	}
	public ImageView getImageView() {
		return imageView;
	}
	@SuppressWarnings("rawtypes")
	public Position getPosition() {
		return position;
	}
	public int getId() {
		return id;
	}
	public double getHpRate() {
		return hp/ (double) maxHp;
	}
	public int getDamage() {
		return damage;
	}
	public String getImagePath() {
		return  imagePath;
	}
	public void attackCre(Creature cre) {
		hurt(map, cre);	
		if (cre.isAlive() == true) {
			beHurt(cre.getDamage());
		}
	}
	public void beHurt(int d) {
		this.hp -= d;
		if (hp <= 0) {
			this.beKilled(map);
		}
	}
	public void hurt(Map map, Creature cre) {
		cre.beHurt(damage);
	}
	public void beKilled(Map map) {
		System.out.println("<" + name + ">阵亡！" );
		this.map = map;
		alive = false;
		map.getPos()[position.getX()][position.getY()].removeCrearure();
		Record dieRecord = map.getRecordPlayer().newdDieRecord(this, map.getRunningTime());
		map.getRecordPlayer().addRecord(dieRecord);
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void kill(Map map, Creature cre) {
		cre.beKilled(map);
	}
	public void beHealed(int h) {
		if (alive) {
			hp += h;
			if (hp > maxHp) {
				hp = maxHp;
			}
		}
	}
	public void run() {
		while (!Thread.interrupted()) {
	        if (!isAlive() || map.battleIsFinished()) {
	        	return;
	        }
            autoMove();
            try {
				Thread.sleep(this.speed + 200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			//	System.out.println(name);
			//	e.printStackTrace();
			}
		}

	}

}
