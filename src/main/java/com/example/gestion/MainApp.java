package com.example.gestion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
    private String page = "installSetup.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent loadingRoot = loader.load();
            Scene loadingScene = new Scene(loadingRoot);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setScene(loadingScene);
            primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
