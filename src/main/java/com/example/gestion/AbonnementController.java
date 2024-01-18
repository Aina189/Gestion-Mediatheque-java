package com.example.gestion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AbonnementController implements Initializable {

    @FXML
    private Button backBttn;

    @FXML
    private DatePicker date_update_result;

    @FXML
    private TableColumn<MembreData, Integer> id_result;

    @FXML
    private TableColumn<MembreData, String> nom_result;

    @FXML
    private TableColumn<MembreData, String> prenom_result;
    @FXML
    private ChoiceBox<?> search_type;
    @FXML
    private Button save_result;

    @FXML
    private Button search_button;

    @FXML
    private TextField search_field;

    @FXML
    private TableView<MembreData> table_result;

    private static Connection connect = DataBase.connectDB();
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void switchPage() throws IOException {
        backBttn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void searchMembers() {
        DataBase.connectDB();
        String keyword = search_field.getText();
        List<MembreData> resultList = MembreDAO.searchMembers(keyword);
        populateTable(resultList);
    }

    private void populateTable(List<MembreData> members) {
        id_result.setCellValueFactory(new PropertyValueFactory<>("id_membre"));
        nom_result.setCellValueFactory(new PropertyValueFactory<>("nom_membre"));
        prenom_result.setCellValueFactory(new PropertyValueFactory<>("prenom_membre"));

        table_result.getItems().setAll(members);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeAbn();
    }

    public static class MembreDAO {
        public static List<MembreData> searchMembers(String keyword) {
            List<MembreData> resultList = new ArrayList<>();
            String sql = "SELECT * FROM membres WHERE nom_membre LIKE ? OR prenom_membre LIKE ? OR Id_membre LIKE ?";

            try (Connection connection = DataBase.connectDB();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, "%" + keyword + "%");
                preparedStatement.setString(2, "%" + keyword + "%");
                preparedStatement.setString(3, "%" + keyword + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Construct a MembreData object from the query results
                        MembreData member = new MembreData(
                                resultSet.getInt("id_membre"),
                                resultSet.getString("nom_membre"),
                                resultSet.getString("prenom_membre"),
                                resultSet.getString("adress_membre"),
                                resultSet.getInt("cin_membre"),
                                resultSet.getString("mail_membre"),
                                resultSet.getString("occupation_membre"),
                                resultSet.getDate("birth_membre"),
                                resultSet.getString("sex_membre")
                        );
                        resultList.add(member);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return resultList;
        }
    }
    private final String[] typeAbnListe = {"Individuel","Groupe"};
    public void typeAbn()
    {
        List<String> oList = new ArrayList<>();
        for(String data : typeAbnListe)
        {
            oList.add(data);
        }
        ObservableList ListData = FXCollections.observableArrayList(oList);
        search_type.setItems(ListData);
    }

    public void effectuerAbn(int idMembre, Date dateFin, String typeAbn) {
        // Vérifiez d'abord si la ligne existe déjà
        String selectQuery = "SELECT * FROM abonnement WHERE Id_membre = ?";
        String updateQuery = "UPDATE abonnement SET date_debut_abn = ?, date_fin_abn = ?, type_abn = ? WHERE Id_membre = ?";
        String insertQuery = "INSERT INTO abonnement (Id_membre, date_debut_abn, date_fin_abn, type_abn) VALUES (?, ?, ?, ?)";

        try (Connection connection = DataBase.connectDB();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            // Obtenez la date actuelle
            Date dateDebut = Date.valueOf(LocalDate.now());

            // Paramètre pour la requête SELECT
            selectStatement.setInt(1, idMembre);

            // Exécutez la requête SELECT
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // La ligne existe déjà, effectuez une mise à jour
                updateStatement.setDate(1, dateDebut);
                updateStatement.setDate(2, dateFin);
                updateStatement.setString(3, typeAbn);
                updateStatement.setInt(4, idMembre);

                updateStatement.executeUpdate();
            } else {
                // La ligne n'existe pas, effectuez une insertion
                insertStatement.setInt(1, idMembre);
                insertStatement.setDate(2, dateDebut);
                insertStatement.setDate(3, dateFin);
                insertStatement.setString(4, typeAbn);

                insertStatement.executeUpdate();
            }

            // Affichez une boîte de dialogue de confirmation
            String message = "Opération réussie!";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(message);
            alert.showAndWait();

        } catch (SQLException e) {
            // En cas d'erreur, affichez un message d'erreur
            String errorMessage = "Une erreur s'est produite : " + e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    public void save(){
        MembreData membreData = table_result.getSelectionModel().getSelectedItem();
        effectuerAbn(membreData.getId_membre(),Date.valueOf(date_update_result.getValue()),(String) search_type.getSelectionModel().getSelectedItem());

    }

}
