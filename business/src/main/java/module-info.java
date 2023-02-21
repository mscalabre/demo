open module business {
    requires com.jfoenix;
    requires org.update4j;
//    requires bootstrap;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires controlsfx;
    requires jfxtras.gauge.linear;

    provides org.update4j.service.Launcher with org.update4j.demo.business.JavaFxLauncher;
}