/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.servitors;

import ama.containers.BlacklistItem;
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
                        .withType(Scenario.class).withSeparator('\t')
                            .build().parse();
        return scenarios;
    }
    
    public static List<BlacklistItem> getBlacklist(){
        List<BlacklistItem> blacklist  = new ArrayList();
            blacklist.add(new BlacklistItem("KR29"));
            blacklist.add(new BlacklistItem("KR35"));
            blacklist.add(new BlacklistItem("KR32"));
            blacklist.add(new BlacklistItem("KR34"));
            blacklist.add(new BlacklistItem("KR35"));
            blacklist.add(new BlacklistItem("KR36"));
            blacklist.add(new BlacklistItem("KR37"));
            blacklist.add(new BlacklistItem("KR38"));
            blacklist.add(new BlacklistItem("KR40"));
            blacklist.add(new BlacklistItem("KR41"));
            blacklist.add(new BlacklistItem("KR42"));
            blacklist.add(new BlacklistItem("KR57"));
            blacklist.add(new BlacklistItem("KR69"));
            blacklist.add(new BlacklistItem("KR78"));
            blacklist.add(new BlacklistItem("KR81"));
            blacklist.add(new BlacklistItem("KR99"));
        return blacklist;
    }
    
    public static void sortScenarios(
            List<BlacklistItem> blacklist
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
            for (BlacklistItem b : blacklist){
                if(scenario.getRiskardID().equals(b.getRiskCardID()) ||
                        scenario.getScenarioNumber()==b.getScenarioNumber()){
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
