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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    
    //number of years, for which we run simulation
    //15_10_2018 values for 5% tolerance:
    //internalFraud:        10M
    //employmentPractices:  100M
    //execution:            1M
    //clientPractices:      1M
    //businessDisruption:   10M
    //externalFraud:        10M
    //damageToAssets:       100M
    public static int numberOfYears;

    @FXML //  fx:id="outputTextArea"
    private TextArea outputTextArea;
    @FXML // fx:id="btnCalculate"
    private Button btnCalculate;
    @FXML //fx:id="categoryChoiceField"
    private ChoiceBox categoryChoiceField;
    @FXML //fx:id="chosenScenarioTextField"
    private TextField chosenScenarioTextField;
    @FXML //fx:id="chosenEMUSTextField"
    private TextField chosenEMUSTextField;
    @FXML //fx:id="chosenBlacklistTextField"
    private TextField chosenBlacklistTextField;
    @FXML //fx:id="numberOfYearsTextField"
    private TextField numberOfYearsTextField;
    @FXML //fx:id="checkBoxLogSimulation"
    private CheckBox checkBoxLogSimulation; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Implicit scenario csv path, for demonstration purpouses only
        chosenScenarioTextField
                .setText("C:\\Users\\cen62777\\Documents\\Scenarios_EMUS.csv");
        categoryChoiceField.setItems(categoryNamesList);
        categoryChoiceField.setValue("internalFraud");
        numberOfYears = 1000;
        numberOfYearsTextField.setText("1000");
        numberOfYearsTextField.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends String> observable
                            , String oldValue, 
                        String newValue) {
                        if (!newValue.matches("\\d*")) {
                            numberOfYearsTextField
                                    .setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    }
                });
    }
    
    @FXML
    void handleBtnScenarioFileChooser(ActionEvent event) {
            chosenScenarioTextField.setText(chooseCSVFile());
    }
    @FXML
    void handleBtnEMUSFileChooser(ActionEvent event) {
            chosenEMUSTextField.setText(chooseCSVFile());
    }
    @FXML
    void handleBtnBlacklistFileChooser(ActionEvent event) {
            chosenBlacklistTextField.setText(chooseCSVFile());
    }
    
    private String chooseCSVFile(){
        String r = null;
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
        File f = fc.showOpenDialog(null);
        if (f !=null){
            r = f.getAbsolutePath();
        }
        return r;
    }
    
    @FXML
    private void handleBtnGCalculateAction(ActionEvent event) throws IOException{
        String fileName = chosenScenarioTextField.getText();
        numberOfYears = Integer.valueOf(numberOfYearsTextField.getText());
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
        CSVWriter writer = null;
        //TODO> make this more elegant
        if(checkBoxLogSimulation.isSelected()){
                writer = 
                    new CSVWriter(new FileWriter(
                            "C:\\Users\\cen62777\\Documents\\" + "log_"
                            + c.getCategoryName()+".csv"), '\t', '\0', '\0'
                            , "\n");
        }
        Distributor.calculateDistribution(writer, c, outputText
                , checkBoxLogSimulation.isSelected());
        if(checkBoxLogSimulation.isSelected()){
            writer.close();
        }
        outputTextArea.setText("Basel II category: " + c.getCategoryName() + "\n");
        for(String s:outputText){
            outputTextArea.setText(outputTextArea.getText() + s + "\n");
        }
    }
}
