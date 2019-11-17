/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.Client;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class AddWordController implements Initializable {

    @FXML
    public TextField wordTextField;
    
    @FXML
    public TextArea definitionTextArea;
    
    @FXML
    public ProgressIndicator progress;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        progress.setVisible(false);
    }    
    
    
    @FXML
    public void onAddWordClicked(ActionEvent event) throws IOException{
        
        String[] defList = definitionTextArea.getText().split("#");
        new AddWord(wordTextField.getText(), definitionTextArea.getText().split("#")).start();
        
    }
    
    
    @FXML
    public void onBackClicked(ActionEvent event) throws IOException{
        Parent addWordParent = FXMLLoader.load(getClass().getResource("WordPage.fxml"));
        Scene addWordScene = new Scene(addWordParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(addWordScene);
        window.show();
    }
    
    class AddWord extends Thread{
        String word;
        String[] defList;
        public AddWord(String word, String[] defList){
            this.word = word;
            this.defList = defList;
        }
        
        @Override
        public void run() {
            try {
                progress.setVisible(true);
                progress.setProgress(0.1);
                String query = "{'query':'addWord', 'newWord':'"+this.word+"'}";
                JSONObject queryObj = new JSONObject(query);
                queryObj.put("def", defList);
                progress.setProgress(0.3);
                Client client = Client.getInstance();
                progress.setProgress(0.4);
                String res = client.sendRequest(queryObj.toString());
                progress.setProgress(1);
                
                
//                progre
//                System.out.println(res);
//                
//                
            } catch (IOException ex) {
                
            } catch (JSONException ex){
                
            }
        }
    }
    
}
