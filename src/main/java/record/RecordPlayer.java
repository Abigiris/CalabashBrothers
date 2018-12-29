package record;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import creature.Creature;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import map.Map;
import record.Record.RecordType;

public class RecordPlayer implements Runnable {

	Map map;
	public ArrayList<Record> records;
	public ArrayList<Creature> creatures;
	
	public RecordPlayer(Map map) {
		this.map = map;
		records = new ArrayList<Record>();
		creatures = new ArrayList<Creature>();
	}
	
	public ArrayList<Record> getRecords() {
		return records;
	}
	public synchronized void addRecord(Record record) {
		records.add(record);
	}
	public synchronized void addCreature(Creature creature) {
		creatures.add(creature);
	}
	public void readRecordFile(File inFile) {
        records.clear();
        String fileName = inFile.getAbsolutePath();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(fileName);

            NodeList recordsXML = document.getElementsByTagName("record");

            System.out.printf("Total records count: %d\n", recordsXML.getLength());

            for (int i = 0; i < recordsXML.getLength(); i++) {
                Node record = recordsXML.item(i);
                String type = record.getAttributes().getNamedItem("type").getNodeValue();
                switch (Record.RecordType.valueOf(type)) {
                    case CREATE: {
                        String name = record.getAttributes().getNamedItem("creature").getNodeValue();
                        int id = Integer.parseInt(record.getAttributes().getNamedItem("id").getNodeValue());
                        int posX = Integer.parseInt(record.getAttributes().getNamedItem("posX").getNodeValue());
                        int posY = Integer.parseInt(record.getAttributes().getNamedItem("posY").getNodeValue());
                        String imagePath = record.getAttributes().getNamedItem("imagePath").getNodeValue();
                        Creature creature = new Creature(map, imagePath, id);
                        addRecord(this.newCreateRecord(id, creature, posX, posY));
                        addCreature(creature);
                        break;
                    }
                    case MOVE: {
                        int id = Integer.parseInt(record.getAttributes().getNamedItem("id").getNodeValue());
                        long time = Long.parseLong(record.getAttributes().getNamedItem("time").getNodeValue());
                        int posX = Integer.parseInt(record.getAttributes().getNamedItem("posX").getNodeValue());
                        int posY = Integer.parseInt(record.getAttributes().getNamedItem("posY").getNodeValue());
                        addRecord(this.newMoveRecord(time, id, posX, posY));
                        break;
                    }
                    case DIE: {
                        int id = Integer.parseInt(record.getAttributes().getNamedItem("id").getNodeValue());
                        long time = Long.parseLong(record.getAttributes().getNamedItem("time").getNodeValue());
                        addRecord(this.newdDieRecord(time, id));
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.printf("Load records from %s successfully\n", fileName);
	}
	
	public void writeRecordFile(File outFile) {
		Document document;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
            return;
        }

        Element root = document.createElement("records");
        document.appendChild(root);
        for (Record record : records) {
        	Element recordNode = document.createElement("record");
        	recordNode.setAttribute("type", record.type.toString());
            switch (record.type) {
            case CREATE:
            	recordNode.setAttribute("id", String.valueOf(record.id));
                recordNode.setAttribute("creature", record.creature.getName());
                recordNode.setAttribute("posX", String.valueOf(record.posX));
                recordNode.setAttribute("posY", String.valueOf(record.posY));
                recordNode.setAttribute("imagePath", record.creature.getImagePath());
                break;
            case MOVE:
            	recordNode.setAttribute("time", String.valueOf(record.time));
                recordNode.setAttribute("id", String.valueOf(record.id));
                recordNode.setAttribute("posX", String.valueOf(record.posX));
                recordNode.setAttribute("posY", String.valueOf(record.posY));
                break;
            case DIE:
                recordNode.setAttribute("time", String.valueOf(record.time));
                recordNode.setAttribute("id", String.valueOf(record.id));
                break;
            default:
                System.err.println("error type");	
            }
            root.appendChild(recordNode);
            
            TransformerFactory tf = TransformerFactory.newInstance();
            try {
                Transformer transformer = tf.newTransformer();
                DOMSource source = new DOMSource(document);
                transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                String fileName = outFile.getAbsolutePath();
                PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
                StreamResult result = new StreamResult(pw);
                transformer.transform(source, result);
                System.out.printf("Records write to %s successfully\n", fileName);
            } catch (TransformerConfigurationException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (TransformerException e) {
                System.out.println(e.getMessage());
            }
        }
	}
	
    public Record newCreateRecord(Creature creature) {
    	Record record = new Record();
    	record.type = RecordType.CREATE;
    	record.creature = creature;
    	record.id = creature.getId();
    	record.posX = creature.getPosition().getX();
    	record.posY = creature.getPosition().getY();
    	return record;
    }
    public Record newCreateRecord(int id, Creature creature, int posX, int posY) {
    	Record record = new Record();
        record.type = Record.RecordType.CREATE;
        record.id = id;
        record.creature = creature;
        record.posX = posX;
        record.posY = posY;
        record.imagePath = creature.getImagePath();
        return record;
    }
    
    public Record newMoveRecord(Creature creature, long time) {
    	Record record = new Record();
    	record.type = RecordType.MOVE;
    	record.time = time;
    	record.creature = creature;
    	record.id = creature.getId();
    	record.posX = creature.getPosition().getX();
    	record.posY = creature.getPosition().getY();
    	return record;
    }
    public Record newMoveRecord(long time, int id, int posX, int posY) {
        Record record = new Record();
        record.type = Record.RecordType.MOVE;
        record.id = id;
        record.time = time;
        record.posX = posX;
        record.posY = posY;
        return record;
    }
    
    public Record newdDieRecord(Creature creature, long time) {
    	Record record = new Record();
    	record.type = RecordType.DIE;
    	record.time = time;
    	record.creature = creature;
    	record.id = creature.getId();
    	return record;
    }
    public Record newdDieRecord(long time, int id) {
        Record record = new Record();
        record.type = Record.RecordType.DIE;
        record.id = id;
        record.time = time;
        return record;
    }

    
	public void playRecord() {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		//初始放置
		int i = 0;
		for (; i < creatures.size(); i++) {
			int posX = records.get(i).posX;
			int posY = records.get(i).posY;
			creatures.get(i).move(map.getPos()[posX][posY]);
		}
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() { 
		        map.printMap();
		    }
		});
		
		long startTime = System.currentTimeMillis();
		while (i < records.size()) {
			Record rcd = records.get(i);
			System.out.println("Record time: " + rcd.time + "  Now: " + (System.currentTimeMillis() - startTime));
			switch (rcd.type) {
			case MOVE:
				if (rcd.time < System.currentTimeMillis() - startTime) {
					//此条记录可以执行
					for (int j = 0; j < creatures.size(); j++) {
						//找到生物
						if (creatures.get(j).getId() == rcd.id) {
							creatures.get(j).move(map.getPos()[rcd.posX][rcd.posY]);
							System.out.println(map.getPos()[rcd.posX][rcd.posY].getCreature().getId());
							i++;
							System.out.println("move");
							Platform.runLater(new Runnable() {
							    @Override
							    public void run() { 
							        map.printMap();
							    }
							});
							break;
						}
					}
				} else {
		            try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case DIE:
				if (rcd.time < System.currentTimeMillis() - startTime) {
					//此条记录可以执行
					for (int j = 0; j < creatures.size(); j++) {
						//找到生物
						if ( creatures.get(j).getId() == rcd.id) {
							creatures.get(j).beKilled(map);
							i++;
							System.out.println("die");
							Platform.runLater(new Runnable() {
							    @Override
							    public void run() { 
							        map.printMap();
							    }
							});
							break;
						}
					}
				} else {
		            try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			default:
				break;
			}
		
		}

	}
	
	public synchronized void printRecord() {
		//scene.setFill(Color.WHITE);
		map.root.getChildren().clear();
		map.root.getChildren().add(map.imageView);
		for (int i = 0; i < map.getRow(); i++) {
			for (int j = 0; j < map.getCol(); j++) {
				Creature curCre = map.getPos()[i][j].getCreature();
				if (curCre != null) {
				//	curCre.printCreature();
					System.out.print('*');
					//UI
					ImageView creImageView = curCre.getImageView();
					creImageView.setX(50+j*75);
					creImageView.setY(50+i*75);
					map.root.getChildren().add(creImageView);
				} else {
					System.out.print('-');
				}
			}
			System.out.println();
		}	
		System.out.println();
	}
} 
