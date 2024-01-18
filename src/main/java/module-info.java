module com.example.gestion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    // add icon pack modules



    opens com.example.gestion to javafx.fxml;
    exports com.example.gestion;
}