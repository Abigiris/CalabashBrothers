package record;

import creature.Creature;

public class Record {
    public enum RecordType { CREATE, MOVE, DIE };
    public RecordType type;
    public long time;
    public int id;
    public Creature creature;
    public int posX;
    public int posY;
    public String imagePath;
    public int maxHp;
    public int hp;
/*    
*/
}
