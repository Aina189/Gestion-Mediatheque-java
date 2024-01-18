package com.example.gestion;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoadingController {

    @FXML
    private ProgressBar barProgression;
    @FXML
    private Label labelId;


    @FXML
    public void initialize() {
        Task<Void> task = createLoadingTask();
        // Binding sur l'avancement
        barProgression.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(e -> {
            openMainScene();
        });
        // Lancement de la tâche dans un nouveau thread
        new Thread(task).start();
    }

    private Task<Void> createLoadingTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Avancement total
                List<Runnable> tasks = new ArrayList<>();
                DataBase dataBase= new DataBase();
                tasks.add(() -> {
                    dataBase.connectDBTest();
                    System.out.println("Connexion établie");
                    Platform.runLater(() -> labelId.setText("Connection..."));
                    try {
                        Thread.sleep(5000);
                    Platform.runLater(()-> labelId.setText("...run..."));
                    } catch (InterruptedException e) {
                        System.out.println("Error");
                        throw new RuntimeException(e);

                    }
                });

                tasks.add(() -> {
                    Platform.runLater(()-> labelId.setText("Loading..."));
                    if(dataBase.isMediathequeDatabaseExists()){
                        System.out.println("DataBase existe");
                        Platform.runLater(()-> labelId.setText("Database found..."));
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        System.out.println("Base de donnee non trouver...");
                        Platform.runLater(()-> labelId.setText("Database not found..."));
                        System.out.println("Creation d'une nouvelle base de donnee...");
                        Platform.runLater(()-> labelId.setText("creat a new database..."));
                        Platform.runLater(()-> labelId.setText("Creating..."));
                        try {
                            dataBase.creatDataBase();
                            Platform.runLater(()-> labelId.setText("Creating tables..."));
                            dataBase.createTable();
                            Platform.runLater(()-> labelId.setText("Succesfullll..."));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                });
                // Exécuter les tâches
                executeTasks(tasks);

                return null;
            }

            private void executeTasks(List<Runnable> tasks) {
                int totalTasks = tasks.size();
                int completedTasks = 0;

                for (Runnable task : tasks) {
                    task.run();
                    completedTasks++;
                    updateProgress(completedTasks, totalTasks);
                }
            }
        };
    }

    private void openMainScene() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
