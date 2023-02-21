package org.update4j.demo.business;

import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.ToggleSwitch;
//import org.update4j.demo.bootstrap.FXMLView;
//import org.update4j.demo.bootstrap.JavaFxDelegate;
//import org.update4j.demo.bootstrap.TextSeparator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;

public class LibraryView extends BusinessFXMLView {

	@FXML
	private ImageView image;

	@FXML
	private GridPane jfoenixContainer;

	@FXML
	private GridPane controlsfxContainer;

	@FXML
	private GridPane jfxtrasContainer;

	@FXML
	private ToggleSwitch toggleSwitch;

	@FXML
	private BreadCrumbBar<String> breadCrumb;

	@FXML
	private Slider slider;
	
	@FXML
	private JFXSpinner jfxspinner;

	@FXML
	private SimpleMetroArcGauge gauge;

	public LibraryView() {
		super();
		getStylesheets().add(
						JFXButton.class.getResource("/com/jfoenix/assets/css/jfoenix-design.css").toExternalForm());

//		image.setImage(JavaFxDelegate.inverted);

		BusinessTextSeparator jfoenixSeparator = new BusinessTextSeparator("JFoeniX");
		jfoenixContainer.add(jfoenixSeparator, 0, 0, GridPane.REMAINING, 1);

		BusinessTextSeparator controlsfxSeparator = new BusinessTextSeparator("ControlsFX");
		controlsfxContainer.add(controlsfxSeparator, 0, 0, GridPane.REMAINING, 1);

		BusinessTextSeparator jfxtrasSeparator = new BusinessTextSeparator("JFXtras");
		jfxtrasContainer.add(jfxtrasSeparator, 0, 0, GridPane.REMAINING, 1);

		TreeItem<String> crumb1 = new TreeItem<>("Item 1");
		TreeItem<String> crumb2 = new TreeItem<>("Item 2");
		TreeItem<String> crumb3 = new TreeItem<>("Item 3");

		crumb1.getChildren().add(crumb2);
		crumb2.getChildren().add(crumb3);
		breadCrumb.setSelectedCrumb(crumb3);

		toggleSwitch.selectedProperty().addListener((obs, ov, nv) -> {
			breadCrumb.setSelectedCrumb(crumb3);
		});

		jfxspinner.progressProperty().bind(slider.valueProperty().divide(100));
		gauge.valueProperty().bind(slider.valueProperty());
	}

	@FXML
	private void showDialog(ActionEvent evt) {
		JavaFxLauncher.showDialog("Message", "This is a JFoeniX dialog.", ButtonType.OK);
	}

}
