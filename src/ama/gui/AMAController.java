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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * FXML Controller class
 *
 * @author cen62777
 */
public class AMAController implements Initializable {
    
    private ObservableList<String> categoryNamesList = FXCollections
            .observableArrayList(
            "internalFraud"
            , "employmentPractices"
            , "execution"
            , "clientPractices"
            , "businessDisruption"
            , "externalFraud"
            , "damageToAssest");
//    private String fileName;

    @FXML //  fx:id="outputTextArea"
    private TextArea outputTextArea;
    
    @FXML // fx:id="btnCalculate"
    private Button btnCalculate;
    
    @FXML //fx:id="categoryChoiceField"
    private ChoiceBox categoryChoiceField;
    
    @FXML //fx:id="chosenFileTextField"
    private TextField chosenFileTextField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryChoiceField.setItems(categoryNamesList);
        categoryChoiceField.setValue("internalFraud");
    }
    
    @FXML
    void singleFileChooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
        File f = fc.showOpenDialog(null);
        if (f !=null){
            chosenFileTextField.setText(f.getAbsolutePath());
        }
    }
    
    @FXML
    private void handleBtnGCalculateAction(ActionEvent event) throws IOException{
//        String fileName= "C:\\Users\\cen62777\\Documents\\Scenarios_EMUS.csv";
        String fileName= chosenFileTextField.getText();
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
        
        HashMap<String, Category> CategoryMap = new HashMap();
            CategoryMap.put(internalFraud .getCategoryName()
                    , internalFraud );
            CategoryMap.put(employmentPractices.getCategoryName()
                    , employmentPractices);
            CategoryMap.put(execution.getCategoryName()
                    , execution);
            CategoryMap.put(clientPractices.getCategoryName()
                    , clientPractices);
            CategoryMap.put(businessDisruption.getCategoryName()
                    , businessDisruption);
            CategoryMap.put(externalFraud.getCategoryName()
                    , externalFraud);
            CategoryMap.put(damageToAssest.getCategoryName()
                    , damageToAssest);
            
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

        Category c = CategoryMap.get(categoryChoiceField.getValue());
        CSVWriter writer =
                new CSVWriter(new FileWriter("C:\\Users\\cen62777\\Documents\\"
                        + "log_"+ c.getCategoryName()+".csv"), '\t', '\0', '\0'
                        , "\n");
        Distributor.calculateDistribution(writer, c, outputText);
        writer.close();
        outputTextArea.setText("Basel II category: " + c.getCategoryName() + "\n");
        for(String s:outputText){
            outputTextArea.setText(outputTextArea.getText() + s + "\n");
        }
    }
}
