package com.example.gestion;

import javafx.fxml.FXML;

import javafx.scene.control.*;


public class UpdateController  {
    @FXML
    private Button Btn_update_Cancel;

    @FXML
    private TextField adress_update_field;

    @FXML
    private TextField cin_update_field;

    @FXML
    private TextField lastName_update_field;

    @FXML
    private TextField mail_update_field;

    public void setName_update_field(TextField name_update_field) {
        this.name_update_field = name_update_field;
    }

    @FXML
    private TextField name_update_field;

    @FXML
    private ChoiceBox<String> occupation_update_field;

    @FXML
    private Button save_update_bttn;


    public void setData(){
        name_update_field.setText("gkg");
        this.lastName_update_field.setText("lastName");
        this.cin_update_field.setText(String.valueOf(12));
        this.adress_update_field.setText("adress");
        this.mail_update_field.setText("mail");
        this.occupation_update_field.setValue("occupation");

    }

}
