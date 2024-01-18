package com.example.gestion;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private Button btnMembre;

    @FXML
    private Button btnAjout;
    @FXML
    private Button abonnement;

    @FXML
    private Label totalMembre;

    private Connection connect;
    private PreparedStatement prepar;
    private Statement statement;
    private ResultSet result;
    private void labelCount(){
        try {
            connect = DataBase.connectDB();
            statement = connect.createStatement();

            String requete = "SELECT COUNT(*) FROM membres";
            result = statement.executeQuery(requete);

            if (result.next()) {
                int longueurListe = result.getInt(1);

                // Mettez à jour le Label dans le fil d'exécution de l'interface graphique
                Platform.runLater(() -> totalMembre.setText(""+ longueurListe));
            }

            // Fermez les ressources
            result.close();
            statement.close();
            connect.close();
        }catch (Exception e){e.printStackTrace();

    }}
    public void switchPage() throws IOException {
        btnMembre.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("membre_view.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();


    }

    public void switchPageAjout() throws IOException {
        btnAjout.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ajout_view.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchPageAbonnement() throws IOException {
        abonnement.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("abonnement.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelCount();
    }
}
