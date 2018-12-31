package creature;

import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import map.Map;

public class SnakeMonster extends Creature implements Cheer{
	private long lastHealTime;
	public SnakeMonster(Map map, int id) {
		super.id = id;
		super.map = map;
		super.name = "蛇精";
    	super.good = false;
    	super.alive = true;
    	super.speed = 800;
    	super.maxHp = 800;
    	super.hp = maxHp;
    	super.damage = 100;
    	//super.imagePath = "application/icons/_蛇精.jpeg";
    	super.imagePath = "icons/_蛇精.jpeg";
    	URL loc = this.getClass().getClassLoader().getResource(imagePath);
    	Image image = new Image(loc.toString());
    	//Image image = new Image(imagePath);
		super.imageView = new ImageView();
		super.imageView.setImage(image);
		super.imageView.setFitHeight(75);
		super.imageView.setFitWidth(75);
		lastHealTime = 0;
	}
	public String getName() {
		return super.name;
	}
	public void cheer() {
		if (super.getHpRate() > 0.25) {
			System.out.println("<" + name + ">进行治疗");
			hp -= maxHp*0.1;
			map.getScorpion().beHealed((int)(maxHp*0.1));
			for (Minion m: map.getMinions()) {
				m.beHealed((int)(maxHp*0.1));
			}
			lastHealTime = map.getRunningTime();
		}
	}
    @Override
    public void printCreature() {
		System.out.print('@');
	}
    @Override
	public void run() {
		while (!Thread.interrupted()) {
	        if (!isAlive() || map.battleIsFinished()) {
	        	return;
	        }
            autoMove();
            if (map.getRunningTime() > 10000 && map.getRunningTime() - lastHealTime > 3000) {
            	cheer();
            }
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
