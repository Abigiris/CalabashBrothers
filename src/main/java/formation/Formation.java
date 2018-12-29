package formation;

import java.util.ArrayList;

import creature.*;
import map.*;

public abstract class Formation {
	protected String name;
	//输入生物 队首坐标
	public abstract <T extends Creature> void form(Map map, ArrayList<T> creatures, int x, int y); //形成队形
	public abstract <T extends Creature> void loose(Map map, ArrayList<T> creatures, int x, int y);//解散队形
}
