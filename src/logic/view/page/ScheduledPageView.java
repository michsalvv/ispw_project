package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.LessonCard;
import logic.view.card.element.ScheduledExamCard;

public class ScheduledPageView implements Initializable {

	@FXML
	private Label labelPage;
	
	@FXML
	private VBox vboxScroll;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if (PageLoader.getPage() == Page.SCHEDULED_LESSONS) {
		
			for (int i=0; i<10; i++) {
				try {
					LessonCard lessonCard = new LessonCard("mhanz", "z5", "25:96");
					vboxScroll.getChildren().add(lessonCard);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		else {
			labelPage.setText("Exams");
			
			for (int i=0; i<10; i++) {
				try {
					ScheduledExamCard scheduledExamCard = new ScheduledExamCard("mhanzino", "z555", "37:96");
					vboxScroll.getChildren().add(scheduledExamCard);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
