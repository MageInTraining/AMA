/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;
import ama.containers.Category;
import ama.servitors.Sorter;
import com.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author cen62777
 */
public class Main extends Application{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, IOException{
        
        try{
//            AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("AMA.fxml"));
            AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("gui/AMA.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Advanced Measuring Aproach");
            primaryStage.show();
               //TODO: give path to csv file as parametr
            String fileName= "C:\\Users\\cen62777\\Documents\\Rizika_small.csv";

            //individual list of scenarios for each of (Erste?) groups
            Category fraud = new Category("fraud", 0.6, 1.3636);
            Category improperPractices = new Category("improperPractices", 0.16, 0.6);
            Category infrastructure = new Category("infrastructure", 0.1, 0.3756);
            Category execution = new Category("execution", 0.5, 1.9167);
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
                    new CSVWriter(new FileWriter("C:\\Users\\cen62777\\Documents\\log_" + c.getCategoryName()+".csv"), '\t', '\0', '\0', "\n");
            c.calculateDistribution(writer);
            writer.close(); 
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
}
