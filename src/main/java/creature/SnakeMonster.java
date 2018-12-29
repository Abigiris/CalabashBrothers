package creature;

import java.net.URL;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import map.Map;
import map.Position;

public class SnakeMonster extends Creature implements Cheer{
	public SnakeMonster(Map map, int id) {
		super.id = id;
		super.map = map;
		super.name = "蛇精";
    	super.good = false;
    	super.alive = true;
    	//super.imagePath = "application/icons/_蛇精.jpeg";
    	super.imagePath = "icons/_蛇精.jpeg";
    	URL loc = this.getClass().getClassLoader().getResource(imagePath);
    	Image image = new Image(loc.toString());
    	//Image image = new Image(imagePath);
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
		System.out.print('@');
	}
}
