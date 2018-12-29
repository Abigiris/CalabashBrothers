package formation;

import java.util.ArrayList;

import creature.*;
import map.*;

//（0,15）
public class FishScale extends Formation{
	public FishScale() {
		super.name = "鱼鳞阵";
	}
	public String getName() {
		return super.name;
	}
	@Override
	public <T extends Creature> void form(Map map, ArrayList<T> creatures, int x, int y) {
		creatures.get(0).move(map.getPos()[x][y]);
		creatures.get(1).move(map.getPos()[x+1][y+1]);
		creatures.get(2).move(map.getPos()[x+2][y-2]);
		creatures.get(3).move(map.getPos()[x+2][y]);
		creatures.get(4).move(map.getPos()[x+2][y+2]);
		creatures.get(5).move(map.getPos()[x+3][y-3]);
		creatures.get(6).move(map.getPos()[x+3][y-1]);
		creatures.get(7).move(map.getPos()[x+3][y+1]);
		creatures.get(8).move(map.getPos()[x+3][y+3]);
		creatures.get(9).move(map.getPos()[x+4][y]);
	}
	@Override
	public <T extends Creature> void loose(Map map, ArrayList<T> creatures, int x, int y) {
		map.getPos()[x][y].removeCrearure();
		map.getPos()[x+1][y+1].removeCrearure();
		map.getPos()[x+2][y-2].removeCrearure();
		map.getPos()[x+2][y].removeCrearure();
		map.getPos()[x+2][y+2].removeCrearure();
		map.getPos()[x+3][y-3].removeCrearure();
		map.getPos()[x+3][y-1].removeCrearure();
		map.getPos()[x+3][y+1].removeCrearure();
		map.getPos()[x+3][y+3].removeCrearure();
		map.getPos()[x+4][y].removeCrearure();
	}
}

//
//...*
//....*
//.*.*.*
//*.*.*.*
//...*
