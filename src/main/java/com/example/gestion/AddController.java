package com.example.gestion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    @FXML
    private Button BtnCancel;

    @FXML
    private TextField adress_field;

    @FXML
    private DatePicker birth_field;

    @FXML
    private TextField cin_field;

    @FXML
    private TextField lastName_field;

    @FXML
    private TextField mail_field;

    @FXML
    private TextField name_field;

    @FXML
    private ChoiceBox<?> occupation_field;

    @FXML
    private ChoiceBox<?> sex_field;

    private Connection connect;
    private PreparedStatement prepar;
    private Statement statement;
    private ResultSet result;
    public void addNewMembre(){
        String addMbr = "INSERT INTO membres ( nom_membre, prenom_membre,sex_membre,birth_membre, adress_membre,occupation_membre,  cin_membre, mail_membre)"+"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        connect = DataBase.connectDB();

        try {
            if (name_field.getText().isEmpty()
                    ||lastName_field.getText().isEmpty()
                    ||cin_field.getText().isEmpty()
                    ||adress_field.getText().isEmpty()
                    ||occupation_field.getSelectionModel().getSelectedItem()==null
                    ||mail_field.getText().isEmpty()
                    ||sex_field.getSelectionModel().getSelectedItem()==null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("fill all blanks field");
                alert.showAndWait();
            }
            else
            {
                prepar = connect.prepareStatement(addMbr);
                prepar.setString(1,name_field.getText());
                prepar.setString(2,lastName_field.getText());
                prepar.setString(3,(String)sex_field.getSelectionModel().getSelectedItem());
                prepar.setDate(4, Date.valueOf(birth_field.getValue()));
                prepar.setString(5,adress_field.getText());
                prepar.setString(6,(String)occupation_field.getSelectionModel().getSelectedItem());
                prepar.setString(7,cin_field.getText());
                prepar.setString(8,mail_field.getText());


                prepar.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Succes");
                alert.showAndWait();

            }

        }catch (Exception e){e.printStackTrace();}
    }

    private final String[] occupationList = {"Etudiant(e)","Salarier"};
    private final String[] sexList = {"Masculin","Feminin"};
    public void mbrOccupationList()
    {
        List<String> oList = new ArrayList<>();
        for(String data : occupationList)
        {
            oList.add(data);
        }
        ObservableList ListData = FXCollections.observableArrayList(oList);
        occupation_field.setItems(ListData);
    }
    public void mbrSexList()
    {
        List<String> oList = new ArrayList<>();
        for(String data : sexList)
        {
            oList.add(data);
        }
        ObservableList ListData = FXCollections.observableArrayList(oList);
        sex_field.setItems(ListData);
    }

    public void cancel() throws IOException {
        BtnCancel.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mbrOccupationList();
        mbrSexList();
    }
}
