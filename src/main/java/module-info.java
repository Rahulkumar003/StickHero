module com.game.stichhero {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.media;
    requires junit;
    requires org.testng;

    opens com.game to javafx.fxml;
    exports com.game;
    exports com.game.features;
    opens com.game.features to javafx.fxml;
    exports com.game.Components;
    opens com.game.Components to javafx.fxml;
}