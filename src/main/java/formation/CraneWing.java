package formation;

import java.util.ArrayList;

import creature.*;
import map.*;

//（1,10）
public class CraneWing extends Formation{
	public CraneWing() {
		super.name = "鹤翼阵";
	}
	public String getName() {
		return super.name;
	}
	@Override
	public <T extends Creature> void form(Map map, ArrayList<T> creatures, int x, int y) {
		for (int i = 0; i < 4; i++) {
			if (map.isInMap(x+i, y+i)) {
				creatures.get(i).move(map.getPos()[x+i][y+i]);
			}
		}
		for (int i = 0; i < 3; i++) {
			if (map.isInMap(x+2-i, y+4+i)) {
				creatures.get(i+4).move(map.getPos()[x+2-i][y+4+i]);
			}
		}
	}
	@Override
	public <T extends Creature> void loose(Map map, ArrayList<T> creatures, int x, int y) {
		for (int i = 0; i < 4; i++) {
			if (map.isInMap(x+i, y+i)) {
				map.getPos()[x+i][y+i].removeCrearure();
			}
		}
		for (int i = 0; i < 3; i++) {
			if (map.isInMap(x+2-i, y+4+i)) {
				map.getPos()[x+2-i][y+4+i].removeCrearure();
			}
		}
	}
}

