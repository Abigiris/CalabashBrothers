package formation;

import java.util.ArrayList;

import creature.*;
import map.*;

//（2,18）
public class CrescentMoon extends Formation{
	public CrescentMoon() {
		super.name = "偃月阵";
	}
	public String getName() {
		return super.name;
	}
	@Override
	public <T extends Creature> void form(Map map, ArrayList<T> creatures, int x, int y) {
		creatures.get(0).move(map.getPos()[x][y]);
		creatures.get(1).move(map.getPos()[x+1][y-1]);
		creatures.get(2).move(map.getPos()[x+1][y-2]);
		creatures.get(3).move(map.getPos()[x+2][y-2]);
		creatures.get(4).move(map.getPos()[x+2][y-3]);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				creatures.get(5+3*i+j).move(map.getPos()[x+3+i][y-4-j]);
			}
		}
		creatures.get(14).move(map.getPos()[x+6][y-3]);
		creatures.get(15).move(map.getPos()[x+6][y-2]);
		creatures.get(16).move(map.getPos()[x+7][y-2]);
		creatures.get(17).move(map.getPos()[x+7][y-1]);
		creatures.get(18).move(map.getPos()[x+8][y]);
	}
	@Override
	public <T extends Creature> void loose(Map map, ArrayList<T> creatures, int x, int y) {
		map.getPos()[x][y].removeCrearure();
		map.getPos()[x+1][y-1].removeCrearure();
		map.getPos()[x+1][y-2].removeCrearure();
		map.getPos()[x+2][y-2].removeCrearure();
		map.getPos()[x+2][y-3].removeCrearure();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				map.getPos()[x+3+i][y-4-j].removeCrearure();
			}
		}
		map.getPos()[x+6][y-3].removeCrearure();
		map.getPos()[x+6][y-2].removeCrearure();
		map.getPos()[x+7][y-2].removeCrearure();
		map.getPos()[x+7][y-1].removeCrearure();
		map.getPos()[x+8][y].removeCrearure();
	}
}

