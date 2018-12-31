package application;
	
import java.io.File;

import map.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class Main extends Application {
	
	@Override
	public void start(final Stage primaryStage) {	
		try {
			Group root = new Group();
			Scene scene = new Scene(root,1000,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("ImageView");
			primaryStage.setWidth(1000);
			primaryStage.setHeight(700);
			primaryStage.setScene(scene); 
			primaryStage.sizeToScene(); 
			primaryStage.show(); 
			
			final Map map = new Map(8, 12, scene, root);
			map.initMap();
			map.printMap();
			
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent event) {
					switch (event.getCode()) {
		            case SPACE:  
		            	map.battle(); 
		            	break;
		            case R:  
		            	map.clearMap();
		            	map.initMap(); 
		            	map.printMap(); 
		            	break;
		            case C:
		            	map.clearMap();
		            	break;
		            case S:
		            	FileChooser fileChooser1 = new FileChooser();
		            	fileChooser1.setTitle("Save File");
		            	File outFile = fileChooser1.showSaveDialog(primaryStage);
		            	map.getRecordPlayer().writeRecordFile(outFile);
		            	break;
		            case L:  
		            	FileChooser fileChooser = new FileChooser();
		                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
		                fileChooser.getExtensionFilters().add(extFilter);
		                File inFile = fileChooser.showOpenDialog(primaryStage);
		                map.getRecordPlayer().readRecordFile(inFile);
		                map.replay();
		                break;
					default:
						break;
		                }
		            }
		        });
			
            
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
}

