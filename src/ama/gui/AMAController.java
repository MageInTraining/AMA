/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.gui;

import ama.beans.Category;
import ama.servitors.Distributor;
import ama.servitors.Sorter;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author cen62777
 */
public class AMAController implements Initializable {

    @FXML //  fx:id="output"
    private TextArea output;
    
    @FXML // fx:id="bntGO"
    private Button btnGo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        output.setText("Hello World!\n");
    }
    
    @FXML
    private void handleBtnGoAction(ActionEvent event) throws IOException{
        String fileName= "C:\\Users\\cen62777\\Documents\\Scenarios_EMUS.csv";
        List<String> outputText = new ArrayList();

        //individual list of scenarios for each of (Erste?) groups
        //events per year calculated as follows:
        //  = number_of_events/(year of last events - year_of firs_events)
        Category internalFraud
                            = new Category("internalFraud"      , 0.6,  1.0643);
        Category employmentPractices
                            = new Category("employmentPractices", 0.16, 0);
        Category execution          
                            = new Category("execution"          , 0.5,  1.2002);
        Category clientPractices    
                            = new Category("clientPractices"    , 0.16, 1.0231);
        Category businessDisruption
                            = new Category("businessDisruption" , 0.1,  0.029);
        Category externalFraud 
                            = new Category("externalFraud"      , 0.6,  2.4388);
        Category damageToAssest
                            = new Category("damageToAssest"     , 0.1,  0.6955);
        Category notSet
                            = new Category();

        Sorter.sortScenarios(Sorter.getBlacklist()
                , Sorter.extractScenarios(fileName)
                , internalFraud 
                , employmentPractices
                , execution
                , clientPractices
                , businessDisruption
                , externalFraud
                , damageToAssest
                , notSet);

        Category c = damageToAssest;
        CSVWriter writer =
                new CSVWriter(new FileWriter("C:\\Users\\cen62777\\Documents\\"
                        + "log_"+ c.getCategoryName()+".csv"), '\t', '\0', '\0'
                        , "\n");
        Distributor.calculateDistribution(writer, c, outputText);
        writer.close();
        output.setText("Basel II category: " + c.getCategoryName() + "\n");
        for(String s:outputText){
            output.setText(output.getText() + s + "\n");
        }
    }
}
