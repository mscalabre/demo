package org.update4j.demo.business;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BusinessFXMLView extends StackPane {


    protected BusinessFXMLView() {
        this((Runnable)null);
    }

    protected BusinessFXMLView(Runnable preLoad) {
        String name = getClass().getSimpleName();
        String hyphenated = name.replaceAll("(.)(\\p{Upper})", "$1-$2").toLowerCase();

        URL fxml = getClass().getResource(name + ".fxml");
        if (fxml == null) {
            fxml = getClass().getResource(hyphenated + ".fxml");
        }

        if (fxml == null) {
            throw new IllegalStateException(name + ".fxml missing.");
        }

        FXMLLoader loader = new FXMLLoader(fxml);

        // TODO: check if root is present with string reading
        loader.setRoot(this);

        if (getClass().getResource(name + ".properties") != null) {
            ResourceBundle resources = ResourceBundle.getBundle(name);
            loader.setResources(resources);
        }

        // TODO: check if controller is present with string reading
        loader.setController(this);

        URL style = getClass().getResource(name + ".css");
        if (style == null) {
            style = getClass().getResource(hyphenated + ".css");
        }

        if (style != null) {
            getStylesheets().add(style.toExternalForm());
        }

        getStyleClass().add(hyphenated);

        if (preLoad != null) {
            preLoad.run();
        }

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
