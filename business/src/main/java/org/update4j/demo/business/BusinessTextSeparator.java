package org.update4j.demo.business;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BusinessTextSeparator extends BusinessFXMLView {

	@FXML
	private Label text;
	@FXML
	private HBox box;

	public BusinessTextSeparator(String text) {
		this.text.setText(text);
	}

	public BusinessTextSeparator() {
		this("");
	}

	@FXML
	private void initialize() {
		text.visibleProperty().bind(text.textProperty().isNotEmpty());
		box.spacingProperty().bind(Bindings.when(text.visibleProperty()).then(15).otherwise(0));
	}
}
