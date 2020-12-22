package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewQuestionView implements Initializable{

    @FXML
    private TextArea textQuestion;

    @FXML
    private TextField textSubject;

    @FXML
    private Button btnSubmit;
    
    @FXML
    private ComboBox<String> courseComboBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		courseComboBox.getItems().setAll("ISPW", "CE", "MP");
	}

}