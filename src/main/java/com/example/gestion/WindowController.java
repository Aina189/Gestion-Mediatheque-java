package com.example.gestion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

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
    private TableView<MembreData> statueView;
    @FXML
    private TableColumn<MembreStatueData, String> type_mbr;

    @FXML
    private TableView<MembreData> tableView;
    @FXML
    private TableColumn<MembreData, String> ColumnStatus;
    @FXML
    private TableColumn<MembreData, String> columnMail;
    @FXML
    private TableColumn<MembreData, String> columnPrenom;
    @FXML
    private TableColumn<MembreData, Integer> columsNom;
    @FXML
    private TableColumn<MembreData, String> culumnId;
    @FXML
    private TableColumn<MembreData, String> ColumnGenre;
    @FXML
    private Button bttnList;
    @FXML
    private Button bttnAbn;

    @FXML
    private Button bttnemprunt;

    @FXML
    private Button delete_membre;

    @FXML
    private Button update_membre;
    @FXML
    private Button bttnBackToMenu;

    @FXML
    private AnchorPane window_membre;

    @FXML
    private AnchorPane window_statue_abn;



    public void switchWindow(ActionEvent event) {

        if (event.getSource() == bttnAbn) {
            window_membre.setVisible(false);
            window_statue_abn.setVisible(true);
            addAbonnementShowListData();
        } else if (event.getSource() == bttnList) {
            window_membre.setVisible(true);
            window_statue_abn.setVisible(false);
        }
    }


    private final Connection connect = DataBase.connectDB();
    private Statement statement;
    private PreparedStatement prepar;
    private ResultSet result;

    // add data from DB => addMembreListDAta
    public ObservableList<MembreData> addMembreListData() {
        ObservableList<MembreData> ListData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM membres";

        try {
            assert connect != null;
            prepar = connect.prepareStatement(sql);
            result = prepar.executeQuery();
            MembreData membreData;

            while (result.next()) {
                membreData = new MembreData(
                          result.getInt("id_membre")
                        , result.getString("nom_membre")
                        , result.getString("prenom_membre")
                        , result.getString("adress_membre")
                        , result.getInt("cin_membre")
                        , result.getString("mail_membre")
                        , result.getString("occupation_membre")
                        , result.getDate("birth_membre")
                        , result.getString("sex_membre"));
                ListData.add(membreData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ListData;
    }

    //add data in table
    private ObservableList<MembreData> addMembreList;
    public void addMembreShowListData() {
        addMembreList = addMembreListData();

        culumnId.setCellValueFactory(new PropertyValueFactory<>("id_membre"));
        columsNom.setCellValueFactory(new PropertyValueFactory<>("nom_membre"));
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom_membre"));
        columnMail.setCellValueFactory(new PropertyValueFactory<>("mail_membre"));
        ColumnStatus.setCellValueFactory(new PropertyValueFactory<>("occupation_membre"));
        ColumnGenre.setCellValueFactory(new PropertyValueFactory<>("sex_membre"));

        tableView.setItems(addMembreList);

    }

    //data abnm
    public ObservableList<MembreData> addAbnListData() {
        ObservableList<MembreData> ListData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM membres LEFT JOIN abonnement ON membres.id_membre = abonnement.id_membre UNION SELECT * FROM membres RIGHT JOIN abonnement ON membres.id_membre = abonnement.id_membre";


        try {
            assert connect != null;
            prepar = connect.prepareStatement(sql);
            result = prepar.executeQuery();
            MembreData membreData;


            while (result.next()) {
                membreData = new MembreData(
                        result.getInt("id_membre")
                        , result.getString("nom_membre")
                        , result.getString("prenom_membre")
                        , result.getString("adress_membre")
                        , result.getInt("cin_membre")
                        , result.getString("mail_membre")
                        , result.getString("occupation_membre")
                        , result.getDate("birth_membre")
                        , result.getString("sex_membre")
                        , result.getString("type_abn")
                        , result.getDate("date_debut_abn")
                        , result.getDate("date_fin_abn")
                );
                ListData.add(membreData);
                System.out.println(result.getString("nom_membre"));
                System.out.println(result.getString("type_abn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ListData;
    }

    //add data in table
    private ObservableList<MembreData> addAbnList;

    public void addAbonnementShowListData() {
        addAbnList = addAbnListData();

        id_mbr.setCellValueFactory(new PropertyValueFactory<>("id_membre"));
        nom_mbr.setCellValueFactory(new PropertyValueFactory<>("nom_membre"));
        prenom_mbr.setCellValueFactory(new PropertyValueFactory<>("prenom_membre"));
        type_mbr.setCellValueFactory(new PropertyValueFactory<>("type_abn"));
        debut_mbr.setCellValueFactory(new PropertyValueFactory<>("date_debut_abn"));
        fin_mbr.setCellValueFactory(new PropertyValueFactory<>("date_fin_abn"));

        statueView.setItems(addAbnList);

    }


    public void switchPage() throws IOException {
        bttnBackToMenu.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public int SelectedId = 0;
    //UpdateController update;
    public void selectMembre(){
        MembreData membreData = tableView.getSelectionModel().getSelectedItem();
        System.out.println(membreData.getNom_membre());
        SelectedId = membreData.getId_membre();
        System.out.println(SelectedId);

    }
    public void modifieMembre() throws IOException {
        MembreData membreData = tableView.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("update_view.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent parent = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();




    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addMembreShowListData();

    }
}
