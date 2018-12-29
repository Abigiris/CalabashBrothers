package formation;

import java.util.ArrayList;

import creature.*;
import map.*;

public class SingleLine extends Formation{
	public SingleLine() {
		super.name = "长蛇阵";
	}
	public String getName() {
		return super.name;
	}
	@Override
	public <T extends Creature> void form(Map map, ArrayList<T> creatures, int x, int y) {
		for (int i = 0; i < creatures.size(); i++) {
			if (map.isInMap(x+i, y))
				creatures.get(i).move(map.getPos()[x+i][y]);
		}
	}
	@Override
	public <T extends Creature> void loose(Map map, ArrayList<T> creatures, int x, int y) {
		for (int i = 0; i < creatures.size(); i++) {
			if (map.isInMap(x+i, y))
				map.getPos()[x+i][y].removeCrearure();
		}
	}
}

