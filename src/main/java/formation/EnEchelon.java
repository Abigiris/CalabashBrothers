package formation;

import java.util.ArrayList;

import creature.*;
import map.*;

//（1,17）
public class EnEchelon extends Formation{
	public EnEchelon() {
		super.name = "雁行阵";
	}
	public String getName() {
		return super.name;
	}
	@Override
	public <T extends Creature> void form(Map map, ArrayList<T> creatures, int x, int y) {
		for (int i = 0; i < 5; i++) {	
			if (map.isInMap(x+i, y-i)) {
				creatures.get(i).move(map.getPos()[x+i][y-i]);				
			}
		}
	}
	@Override
	public <T extends Creature> void loose(Map map, ArrayList<T> creatures, int x, int y) {
		for (int i = 0; i < 5; i++) {
			if (map.isInMap(x+i, y-i)) {
				map.getPos()[x+i][y-i].removeCrearure();	
			}
		}
	}
}

