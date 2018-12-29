package creature;

import java.net.URL;
import java.util.ArrayList;

import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import map.Map;
import map.Position;

public class GrandFather extends Creature implements Cheer{
	public GrandFather(Map map, int id) {
		super.id = id;
		super.map = map;
		super.name = "爷爷";
    	super.good = true;
    	super.alive = true;
    	//super.imagePath = "application/icons/_爷爷.jpeg";
    	super.imagePath = "icons/_爷爷.jpeg";
    	URL loc = this.getClass().getClassLoader().getResource(imagePath);
    	Image image = new Image(loc.toString());
	//	Image image = new Image(imagePath);
		super.imageView = new ImageView();
		super.imageView.setImage(image);
		super.imageView.setFitHeight(75);
		super.imageView.setFitWidth(75);
	}
	public String getName() {
		return super.name;
	}
	@Override
	public void cheer(Map map, int x, int y) {
		move(map.getPos()[x][y]);
	}
    @Override
    public void printCreature() {
		System.out.print('#');
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
