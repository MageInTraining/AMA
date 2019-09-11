/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import static logic.Constants.NUMBER_OF_YEARS;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author cen62777
 */
public class AMA extends Application{

    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException{
        
        //TODO: give path to csv file as parametr
        String file_name= "C:\\Users\\cen62777\\Documents\\Rizika.csv";
        //creates list of scenarios, provided in csv file
        //TODO: give blacklist as separate csv
        List<String> blacklist  = new ArrayList();
            blacklist.add("KR29");
            blacklist.add("KR35");
            blacklist.add("KR32");
            blacklist.add("KR34");
            blacklist.add("KR35");
            blacklist.add("KR36");
            blacklist.add("KR37");
            blacklist.add("KR38");
            blacklist.add("KR40");
            blacklist.add("KR41");
            blacklist.add("KR42");
            blacklist.add("KR57");
            blacklist.add("KR69");
            blacklist.add("KR78");
            blacklist.add("KR81");
            blacklist.add("KR99");
        
        List<Scenario> scenarios =
                    new CsvToBeanBuilder(new FileReader(file_name))
                        .withType(Scenario.class).withSeparator(';')
                            .build().parse();
        
        //individual list of scenarios for each of (Erste?) groups
        Category fraud = new Category("fraud", 0.6, 1.3636);
        Category improperPractices= new Category("improperPractices", 0.16, 0.6);
        Category infrastructure = new Category("infrastructure", 0.1, 0.3756);
        Category execution = new Category("execution", 0.5, 1.9167);
        Category notSet = new Category();
        
        for (Scenario scenario : scenarios){
            int s = scenario.getRiskType();
            double d = scenario.getMax();
            for (String b : blacklist){
                if(scenario.getRiskardID()==b){
                    s = 0;
                }
            }
//            //Zahors bed
//            if(scenario.getProbability()>1.0){
//                scenario.setProbability(1.0);
//            }
            switch(s) {
                case 1:
                    fraud.addToList(scenario);
                    if(fraud.getMaxRange()<d){
                        fraud.setMaxRange(d);
                    }
                    break;
                case 2:
                    improperPractices.addToList(scenario);
                    if(improperPractices.getMaxRange()<d){
                        improperPractices.setMaxRange(d);
                    }
                    break;
                case 3:
                    if(d>=execution.getThreshold()){
                       execution.addToList(scenario);
                       if(execution.getMaxRange()<d){
                           execution.setMaxRange(d);
                       }                       
                    }
                    break;
                case 4:
                    improperPractices.addToList(scenario);
                    if(improperPractices.getMaxRange()<d){
                        improperPractices.setMaxRange(d);
                    }
                    break;
                case 5:
                    infrastructure.addToList(scenario);
                    if(infrastructure.getMaxRange()<d){
                        infrastructure.setMaxRange(d);
                    }
                    break;
                case 6:
                    fraud.addToList(scenario);
                    if(fraud.getMaxRange()<d){
                        fraud.setMaxRange(d);
                    }
                    break;
                case 7:
                    infrastructure.addToList(scenario);
                    if(infrastructure.getMaxRange()<d){
                        infrastructure.setMaxRange(d);
                    }
                    break;
                case 0:
                    notSet.addToList(scenario);
                    if(notSet.getMaxRange()<d){
                        notSet.setMaxRange(d);
                    }
                    break;
            }
        }
        scenarios.clear();
        Category c = infrastructure;
        CSVWriter writer =
                new CSVWriter(new FileWriter("C:\\Users\\cen62777\\Documents\\log_" + c.getCategoryName()+".csv"), '\t', '\0', '\0', "\n");
        c.calculateDistribution(writer);
        writer.close();
//        for(int i = 0; i < 5; i++){
//            System.out.println(" Bucket " + (i+1) +" : " + (NUMBER_OF_YEARS/(c.getBucket(i))));
//        }
//        GraphCreator gc = new GraphCreator();
//        stage.setScene(gc.seScene(c));
//        stage.show();
    }
    
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
