package creature;

import java.net.URL;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import map.Map;
import map.Position;

public class Minion extends Creature{
	public Minion(Map map, int no, int id) {
		super.id = id;
		super.map = map;
		super.name = "小喽啰" + no;
    	super.good = false;
    	super.alive = true;
    	//super.imagePath = "application/icons/_小喽啰.jpeg";
    	super.imagePath = "icons/_小喽啰.jpeg";
    	URL loc = this.getClass().getClassLoader().getResource(imagePath);
    	Image image = new Image(loc.toString());
    	//Image image = new Image(imagePath);
		super.imageView = new ImageView();
		super.imageView.setImage(image);
		super.imageView.setFitHeight(75);
		super.imageView.setFitWidth(75);
	}
	public String getName() {
		return this.name;
	}
    @Override
    public void printCreature() {
		System.out.print('*');
	}
}
