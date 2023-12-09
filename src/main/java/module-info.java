module com.game.ap_project {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
//    requires javafx-base;
    requires com.google.gson;

    opens com.game.ap_project to javafx.fxml, com.google.gson;
    exports com.game.ap_project;
}