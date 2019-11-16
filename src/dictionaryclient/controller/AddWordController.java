/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class AddWordController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    public void onAddWordClicked(ActionEvent event) throws IOException{
        Parent addWordParent = FXMLLoader.load(getClass().getResource("AddWord.fxml"));
        Scene addWordScene = new Scene(addWordParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(addWordScene);
        window.show();
    }
    
    
    @FXML
    public void onBackClicked(ActionEvent event) throws IOException{
        Parent addWordParent = FXMLLoader.load(getClass().getResource("WordPage.fxml"));
        Scene addWordScene = new Scene(addWordParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(addWordScene);
        window.show();
    }
    
}
