/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//AAAAAAAAAAAAAAAAAAAAAAAAAAA
package ama;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 *
 * @author cen62777
 */
public class AMA extends Application{

    @Override
    public void start(Stage stage) throws FileNotFoundException{
        
        //TODO: give path to csv file as parametr
        String file_name= "C:\\Users\\cen62777\\Documents\\Rizika.csv";
        PercentileSeeker pSeeker = new PercentileSeeker();   
        //creates list of scenarios, provided in csv file
        List<Scenario> scenarios =
                    new CsvToBeanBuilder(new FileReader(file_name))
                        .withType(Scenario.class).withSeparator('\t')
                            .build().parse();
        //individual list of scenarios for each of (Erste?) groups
        Category fraud = new Category();
        Category improperPractices= new Category();
        Category infrastructure = new Category();
        Category execution = new Category();
        Category notSet = new Category();
        
        for (Scenario scenario : scenarios){
            int s = scenario.getRiskType();
            switch(s) {
                case 1:
                    fraud.addToList(scenario);
                    break;
                case 2:
                    improperPractices.addToList(scenario);
                    break;
                case 3:
                    execution.addToList(scenario);
                    break;
                case 4:;
                    improperPractices.addToList(scenario);
                    break;
                case 5:
                    infrastructure.addToList(scenario);
                    break;
                case 6:
                    fraud.addToList(scenario);
                    break;
                case 7:
                    infrastructure.addToList(scenario);
                    break;
                case 0:
                    notSet.addToList(scenario);
                    break;
            }
        }
        scenarios.clear();
        Category c = execution;
        c.calculateDistribution(pSeeker);
        GraphCreator gc = new GraphCreator();
        stage.setScene(gc.seScene(c));
        stage.show();
    }
    
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
