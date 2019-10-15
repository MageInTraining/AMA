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
        String fileName= "C:\\Users\\cen62777\\Documents\\Rizika_new.csv";
        List<String> outputText = new ArrayList();

        //individual list of scenarios for each of (Erste?) groups
        //events per year calculated as follows:
        //  = number_of_events/(year of last events - year_of firs_events)
        Category fraud = new Category("fraud", 0.6, 1.0714);
        Category improperPractices = new Category("improperPractices", 0.16, 0.4286);
        Category infrastructure = new Category("infrastructure", 0.1, 0.2143);
        Category execution = new Category("execution", 0.5, 1.6429);
        Category notSet = new Category();

        Sorter.sortScenarios(Sorter.getBlacklist()
                , Sorter.extractScenarios(fileName)
                , fraud
                , improperPractices
                , infrastructure
                , execution
                , notSet);

        Category c = infrastructure;
        CSVWriter writer =
                new CSVWriter(new FileWriter("C:\\Users\\cen62777\\Documents\\log_"
                        + c.getCategoryName()+".csv"), '\t', '\0', '\0', "\n");
        Distributor.calculateDistribution(writer, c, outputText);
        writer.close();
        output.setText("Vienna category: " + c.getCategoryName() + "\n");
        for(String s:outputText){
            output.setText(output.getText() + s + "\n");
        }
    }
}
