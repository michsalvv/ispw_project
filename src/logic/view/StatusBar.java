package logic.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class StatusBar extends HBox {
	private static StatusBar instance = null;
	
	private StatusBar() {}
	
	public static StatusBar getInstance() throws IOException {
		if (instance == null) {
			instance = new StatusBar();
			loadView();
		}
		return instance;
	}
	
	private static void loadView() throws IOException {
		URL url = new File("src/res/fxml/StatusBar.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(new StatusBarView());
		instance.getChildren().add(loader.load());
	}
}
