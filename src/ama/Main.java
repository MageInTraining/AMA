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

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author cen62777
 */
public class Main extends Application{

    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException{
        
        //TODO: give path to csv file as parametr
        String fileName= "C:\\Users\\cen62777\\Documents\\Rizika.csv";
        
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
        
        Category c = execution;
        CSVWriter writer =
                new CSVWriter(new FileWriter("C:\\Users\\cen62777\\Documents\\log_" + c.getCategoryName()+".csv"), '\t', '\0', '\0', "\n");
        c.calculateDistribution(writer);
        writer.close();
    }
    
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
