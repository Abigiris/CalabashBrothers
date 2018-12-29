package formation;

import java.util.ArrayList;

import creature.*;
import map.*;

//（2,16）
public class SquareCircle extends Formation{
	public SquareCircle() {
		super.name = "方圆阵";
	}
	public String getName() {
		return super.name;
	}
	@Override
	public <T extends Creature> void form(Map map, ArrayList<T> creatures, int x, int y) {
		creatures.get(0).move(map.getPos()[x][y]);
		creatures.get(0).move(map.getPos()[x+1][y+1]);
		creatures.get(0).move(map.getPos()[x+2][y+2]);
		creatures.get(0).move(map.getPos()[x+3][y+1]);
		creatures.get(0).move(map.getPos()[x+4][y]);
		creatures.get(0).move(map.getPos()[x+3][y-1]);
		creatures.get(0).move(map.getPos()[x+2][y-2]);
		creatures.get(0).move(map.getPos()[x+1][y-1]);
	}
	@Override
	public <T extends Creature> void loose(Map map, ArrayList<T> creatures, int x, int y) {
		map.getPos()[x][y].removeCrearure();
		map.getPos()[x+1][y+1].removeCrearure();
		map.getPos()[x+2][y+2].removeCrearure();
		map.getPos()[x+3][y+1].removeCrearure();
		map.getPos()[x+4][y].removeCrearure();
		map.getPos()[x+3][y-1].removeCrearure();
		map.getPos()[x+2][y-2].removeCrearure();
		map.getPos()[x+1][y-1].removeCrearure();
	}
}

