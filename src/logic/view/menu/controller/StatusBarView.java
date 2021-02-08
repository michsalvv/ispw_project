package logic.view.menu.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import logic.bean.CourseBean;
import logic.bean.UserBean;
import logic.controller.AcceptRequestController;
import logic.controller.LoginController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AppProperties;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;
import logic.view.page.QuestionPageView;
import logic.view.page.RequestPageView;

public class StatusBarView implements Initializable {
	
	@FXML
	private Button btnNotifications;
	
	@FXML
	private Button btnLogout;
	
	@FXML
	private Label labelName;
	
	@FXML
	private Rectangle rectAvatar;
	
	int reqCount;
	private Stage dialogStage;
	private Button btnRequest, btnCancel;
	private Label labelNotification;
	private EventHandler<ActionEvent> addGotoRequestevent, cancRequestEvent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelName.setText(UserBean.getInstance().getName());
		
		String img = "/res/png/avatar/status/" + AppProperties.getInstance().getProperty("avatar") + ".png";
		setAvatar(img);
		
		reqCount=0;
		setNotificationStatus();
	}
	
	@FXML
	public void logout(ActionEvent event) throws IOException {
		System.out.println("Logout");
		
		LoginController loginController = new LoginController();
		loginController.logout();
		PageLoader.getInstance().buildPage(Page.LOGIN);
	}
	
	
	// --------------------------------------------------------
	@FXML
	public void notification(ActionEvent event) throws IOException {
		System.out.println("Notifications");
		if(UserBean.getInstance().getRole().equals(Role.PROFESSOR)) {
			setupRequestDialog();
		}
	}
	
	private void setupRequestDialog() throws IOException {
		dialogStage = new Stage();
		
		URL url = new File("src/res/fxml/dialog/NotificationDialog.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(RequestPageView.class.getResource("/res/style/dialog/NotificationDialog.css").toExternalForm());
		scene.setFill(Color.TRANSPARENT);
		
		dialogStage.setScene(scene);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.initStyle(StageStyle.TRANSPARENT);
		dialogStage.setResizable(false);
		dialogStage.setTitle("App - Request notifications");
		
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
		
		PageLoader.getStage().getScene().getRoot().setEffect(adj);
		dialogStage.show();
		animation(dialogStage);
		
		btnRequest = (Button) scene.lookup("#btnRequest");
		btnCancel = (Button) scene.lookup("#btnCancel");
		labelNotification = (Label) scene.lookup("#labelNotification");
		
		setupEvent();
		btnRequest.setOnAction(addGotoRequestevent);
		btnCancel.setOnAction(cancRequestEvent);
		
		if(reqCount > 0) {
			labelNotification.setText("You have " + reqCount + " requests.");
		} else {
			labelNotification.setText("You don't have requests.");
		}
			

	}
	
	private void animation(Stage stage) {
		double yIni = -stage.getHeight();
		double yEnd = stage.getY();
		
		DoubleProperty yProperty = new SimpleDoubleProperty(yIni);
		yProperty.addListener((ob,n,n1)->stage.setY(n1.doubleValue()));
		
		Timeline timeIn = new Timeline();
		timeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(yProperty, yEnd, Interpolator.EASE_BOTH)));
		timeIn.play();
	}
	
	private void setupEvent() {
		this.addGotoRequestevent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				dialogStage.close();
				try {
					PageLoader.getInstance().buildPage(Page.REQUEST);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		
		this.cancRequestEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				dialogStage.close();
				PageLoader.getStage().getScene().getRoot().setEffect(null);	//TODO
			}
		};
	}
	
	private void setNotificationStatus() {
		if(UserBean.getInstance().getRole() == Role.PROFESSOR) {
			AcceptRequestController acceptRequestController = new AcceptRequestController();
			try {
				reqCount = acceptRequestController.getRequests(UserBean.getInstance()).size();
				System.out.println("REQUEST COUNT " +  reqCount);
				btnNotifications.setStyle("-icon-paint: #FF00FF; -fx-text-fill: #FF00FF");
				btnNotifications.setText(String.valueOf(reqCount));
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RecordNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("NOREQ");
				btnNotifications.setStyle("-icon-paint: black; -fx-text-fill: black");
				btnNotifications.setText("");
			}
		} else {
			btnNotifications.setVisible(false);
		}
	}
	
	// ---------------------------------------------------------
	
	@FXML
	public void profile(ActionEvent event) throws IOException {
		System.out.println("Profile");
		PageLoader.getInstance().buildPage(Page.PROFILE);
	}
	
	private void setAvatar(String res) {
		ImagePattern pattern = new ImagePattern(new Image(res, 200, 200, false, false));
		rectAvatar.setFill(pattern);
	}
}
