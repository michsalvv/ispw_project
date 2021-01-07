package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.bean.ExamBean;
import logic.view.card.controller.ScheduledExamCardView;

public class ScheduledExamCard extends AnchorPane {
	
	private ScheduledExamCardView scheduledExamCardView = new ScheduledExamCardView();
	
	public ScheduledExamCard(ExamBean exam) throws IOException {
		URL url = new File("src/res/fxml/card/ScheduledExamCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(scheduledExamCardView);
		this.getChildren().add(loader.load());

		scheduledExamCardView.setCard(exam);
	}
}