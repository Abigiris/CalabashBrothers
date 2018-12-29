package formation;

import java.util.ArrayList;

import creature.*;
import map.*;

//（1,19）
public class Yoke extends Formation{
	public Yoke() {
		super.name = "衡轭阵";
	}
	public String getName() {
		return super.name;
	}
	@Override
	public <T extends Creature> void form(Map map, ArrayList<T> creatures, int x, int y) {
		for (int i = 0; i < 6; i = i+2) {
			if (map.isInMap(x+i, y)) {
				creatures.get(i).move(map.getPos()[x+i][y]);	
			}
		}
		for (int i = 1; i < 6; i = i+2) {
			if (map.isInMap(x+i, y-1)) {
				creatures.get(i).move(map.getPos()[x+i][y-1]);				
			}
		}
	}
	@Override
	public <T extends Creature> void loose(Map map, ArrayList<T> creatures, int x, int y) {
		for (int i = 0; i < 6; i = i+2) {
			if (map.isInMap(x+i, y)) {
				map.getPos()[x+i][y].removeCrearure();
			}
		}
		for (int i = 1; i < 6; i = i+2) {
			if (map.isInMap(x+i, y-1)) {
				map.getPos()[x+i][y-1].removeCrearure();			
			}
		}
	}
}

