package creature;

import java.util.ArrayList;
import java.util.Random;

import map.Map;

public class CalabashBrother {
	private ArrayList<CalabashBody> order;
	private final int CB_NUM = 7;
	public CalabashBrother(Map map, int id) {
		order = new ArrayList<CalabashBody>();
		for (int i = 0; i < CB_NUM; i++) {
			order.add( new CalabashBody(map, i, id + i));
		}
		randomOrder();
	}
	//for Test
	public CalabashBrother(boolean forTest) {
		order = new ArrayList<CalabashBody>();
		for (int i = 0; i < CB_NUM; i++) {
			order.add( new CalabashBody(i, forTest));
		}
		randomOrder();
	}
	public ArrayList<CalabashBody> getOrder() {
		return order;
	}
	
	public void swap(int p, int q) {
		CalabashBody pe = order.get(p);
		CalabashBody qe = order.get(q);
		order.set(p, qe);
		order.set(q, pe);
	}
	
	public  void randomOrder() {
		Random count = new Random();
		for (int i = 0; i < count.nextInt(10)+3; i++) {
			Random p = new Random();
			Random q = new Random();
			swap(p.nextInt(CB_NUM), q.nextInt(CB_NUM));
		}
	}
	
	public void bubbleSort() {
		for (int i = 0; i < CB_NUM; i++) {
			for (int j = 1; j < CB_NUM-i; j++) {
				//if (order[j-1].getNo() > order[j].getNo()) 
				if (order.get(j-1).getNo() > order.get(j).getNo()) 
					swap(j-1, j);
			}
		}
	}
	public void printInfo(boolean isColor) {
		for (int i = 0; i < CB_NUM; i++) {
			if (isColor) {
				//System.out.printf(order[i].getColor() + " ");
				System.out.printf(order.get(i).getColor() + " ");
			} else {
				//System.out.printf(order[i].getName() + " ");			
				System.out.printf(order.get(i).getName() + " ");	
			}
		}			
		System.out.println();
	}
}