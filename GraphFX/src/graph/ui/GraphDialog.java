package graph.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GraphDialog {

	public GraphDialog() {
		// TODO Auto-generated constructor stub
	}

	public static void error(String msg){
		
		if (msg==null) {
			msg = "An unknown error has occured.";
		}			
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
}
