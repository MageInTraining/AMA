/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.servitors;

import ama.containers.Category;
import ama.containers.Scenario;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cen62777
 */
public class Sorter {
    
    public static List<Scenario> extractScenarios(String fileName) throws FileNotFoundException{
        List<Scenario> scenarios =
                    new CsvToBeanBuilder(new FileReader(fileName))
                        .withType(Scenario.class).withSeparator(';')
                            .build().parse();
        return scenarios;
    }
    
    public static List<String> getBlacklist(){
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
        return blacklist;
    }
    
    public static void sortScenarios(
            List<String> blacklist
            , List<Scenario> scenarios
            , Category fraud
            , Category improperPractices
            , Category infrastructure
            , Category execution
            , Category notSet
            ){
        for (Scenario scenario : scenarios){
            int s = scenario.getRiskType();
            double d = scenario.getMax();
            for (String b : blacklist){
                if(scenario.getRiskardID()==b){
                    s = 0;
                }
            }
            
            switch(s) {
                case 1: case 6:
                    fraud.addToList(scenario);
                    if(fraud.getMaxRange()<d){
                        fraud.setMaxRange(d);
                    }
                    break;
                case 2: case 4:
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
                case 5: case 7:
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
    }
    
}
