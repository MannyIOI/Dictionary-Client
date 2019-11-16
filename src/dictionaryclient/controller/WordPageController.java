/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import network.Client;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class WordPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String currentWord;
    
    @FXML
    private ListView<String> word_list;
    
    @FXML
    private TextField search_bar;
    
    @FXML
    private TextArea definition;
    
    Client client;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       new GetList().start();
//        items.addAll(new ArrayList<String>());
        
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
    public void onItemSelected(MouseEvent ev){
        System.out.println("Item selected");
        new GetDefinition(word_list.getSelectionModel().getSelectedItem()).start();
    }
    
    @FXML
    public void onTestClicked(KeyEvent ev) throws IOException{
          if(ev.getCharacter().hashCode() == 13){
              // WHEN ENTER CLICKED
              new GetDefinition(search_bar.getText()).start();
          }
    }
    
    class GetList extends Thread{

        
        @Override
        synchronized public void run() {
            try{
                Client client = Client.getInstance();
                String query = "{'query': 'getAllWords'}";
                System.out.println("Sending request");
                while(client.dis == null){
                    Thread.sleep(100);
                }
                String res = client.sendRequest(query);
                
                JSONObject obj = new JSONObject(res);
                JSONArray wordListJson = obj.getJSONArray("res");
                
                ArrayList<String> wordList = new ArrayList<>();
                for(int i = 0;i < wordListJson.length();i++){
//                    wordList.add(wordListJson.getJSONObject(i).getString("word"));
//                    System.out.println(wordListJson.getString(i));
                    JSONObject wordObj = new JSONObject(wordListJson.getString(i));
                    wordList.add(wordObj.getString("word"));
                }
                
                ObservableList<String> items = FXCollections.observableArrayList(wordList);
                word_list.setItems(items);
            }
            catch(IOException ex){
                System.out.println("Sorry there was some error in GET LIST function while sending request");
            }
            catch(InterruptedException ex){
                System.out.println("Thread exception in GET LIST function while sending request");
            }
            catch(JSONException ex){
                System.out.println("There was an error in the response GET LIST");
            }
        }
        
    }
    
    class GetDefinition extends Thread{
        String word;
        public GetDefinition(String word){
            this.word = word;
        }

        @Override
        public void run() {
            try{
            Client client = Client.getInstance();
                String query = "{'query': 'getDefinition', 'word': '"+this.word+"'}";
                System.out.println("Sending request");
                while(client.dis == null){
                    Thread.sleep(100);
                }
                String res = client.sendRequest(query);
                JSONObject obj = new JSONObject(res);
                System.out.println(obj);
                if("INEXISTENT_WORD_ERROR".equals(obj.getString("res"))){
                    definition.setText("Sorry Could not find a definition for that word");
                }
                else{
                    JSONArray defList = new JSONObject(obj.getString("res")).getJSONArray("definition");
                    String displayString = "Defintion\n\n\t";
                    for(int i = 0;i < defList.length();i++){
                        int j = i + 1;
                        displayString += j+".\t"+defList.getString(i)+"\n\n\t";
                    }
                    definition.setText(displayString);
                }
            }
            catch(IOException | JSONException | InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }
    
}
