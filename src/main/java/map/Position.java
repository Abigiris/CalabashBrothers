package map;

import creature.*;

public class Position <T extends Creature> {

	private int x, y;
	private T creature;
	
	public Position(int x, int y) {
		this.x = x;
		this.y =y;
		this.creature = null; 
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public synchronized Creature getCreature() {
		return this.creature;
	}
	public synchronized boolean getIsSet() {
		return (this.creature != null);
	}
	
	public synchronized void set(T creature) throws OccupyingException {
		if (this.creature != null) {
			throw new OccupyingException();
		}
		this.creature = creature;
	}
	public synchronized void setCreature(T creature) {
		try {
			set(creature);
		} catch (OccupyingException e) {
			System.out.println("位置(" + x + ',' + y + ")已放置生物, <" + creature.getName() + ">无法移动到该位置！");
		}
	}
	public synchronized void removeCrearure() {
		this.creature = null; 
	}
}

class OccupyingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OccupyingException() {
		
	}
}
