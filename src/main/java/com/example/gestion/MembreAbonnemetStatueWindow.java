package com.example.gestion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MembreAbonnemetStatueWindow  {
    @FXML
    private TableColumn<MembreStatueData, Date> debut_mbr;
    @FXML
    private TableColumn<MembreStatueData, Date> fin_mbr;
    @FXML
    private TableColumn<MembreStatueData, Integer> id_mbr;
    @FXML
    private TableColumn<MembreStatueData, String> nom_mbr;
    @FXML
    private TableColumn<MembreStatueData, String> prenom_mbr;
    @FXML
    private TableView<MembreStatueData> statueView;
    @FXML
    private TableColumn<MembreStatueData, String> type_mbr;
    private Connection connect = DataBase.connectDB();
    private Statement statement;
    private PreparedStatement prepar;
    private ResultSet result;

    public ObservableList<MembreStatueData> addAbnListData() {
        ObservableList<MembreStatueData> ListData = FXCollections.observableArrayList();
        String sql = "SELECT membres.id_membre,membres.nom_membre, membres.prenom_membre, abonnement.type_abnmt, abonnement.date_debut_abnmt, abonnement.date_fin_abnmt " +
                "FROM membres " +
                "JOIN abonnement ON membres.id_membre = abonnement.id_membre";

        try {
            assert connect != null;
            prepar = connect.prepareStatement(sql);
            result = prepar.executeQuery();
            MembreStatueData membreData;

            while (result.next()) {
                membreData = new MembreStatueData(result.getInt("id_membre")
                        ,result.getString("nom_membre")
                        ,result.getString("prenom_membre")
                        ,result.getString("type_abnmt")
                        ,result.getDate("date_debut_abnmt")
                        ,result.getDate("date_fin_abnmt"));
                ListData.add(membreData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ListData;
    }
    //add data in table
    private ObservableList<MembreStatueData> addMembreList;

    public void addAbonnementShowListData() {
        addMembreList = addAbnListData();

        id_mbr.setCellValueFactory(new PropertyValueFactory<>("id_membre"));
        nom_mbr.setCellValueFactory(new PropertyValueFactory<>("nom_membre"));
        prenom_mbr.setCellValueFactory(new PropertyValueFactory<>("prenom_membre"));
        type_mbr.setCellValueFactory(new PropertyValueFactory<>("type_abnmt"));
        debut_mbr.setCellValueFactory(new PropertyValueFactory<>("date_debut_abnmt"));
        fin_mbr.setCellValueFactory(new PropertyValueFactory<>("date_fin_abnmt"));

        statueView.setItems(addMembreList);

    }






}
