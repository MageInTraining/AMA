/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.servitors;

import ama.beans.BlacklistItem;
import ama.beans.Category;
import ama.beans.EmusHistory;
import ama.beans.Scenario;
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
    
    public static List<Scenario> extractScenarios(String fileName)
            throws FileNotFoundException{
        List<Scenario> scenarios =
                    new CsvToBeanBuilder(new FileReader(fileName))
                        .withType(Scenario.class).withSeparator('\t')
                            .build().parse();
        return scenarios;
    }
    
    public static List<BlacklistItem> extractBlacklist(String fileName)
            throws FileNotFoundException{
        List<BlacklistItem> blacklist =
                    new CsvToBeanBuilder(new FileReader(fileName))
                        .withType(BlacklistItem.class).withSeparator(',')
                            .build().parse();
        return blacklist;
    }
    
    public static List<EmusHistory> extractEmus (String fileName)
        throws FileNotFoundException{
        List<EmusHistory> emus =
                    new CsvToBeanBuilder(new FileReader(fileName))
                        .withType(BlacklistItem.class).withSeparator(',')
                            .build().parse();
        return emus;
    }
    
    public static void sortScenarios(
            List<BlacklistItem> blacklist
            , List<Scenario> scenarios
            , Category  internalFraud 
            , Category  employmentPractices 
            , Category  execution
            , Category  clientPractices
            , Category  businessDisruption
            , Category  externalFraud
            , Category  damageToAssest
            , Category  notSet
            ){
        for (Scenario scenario : scenarios){
            int s = scenario.getRiskType();
            double d = scenario.getMax();
            for (BlacklistItem b : blacklist){
                if(scenario.getRiskardID().equals(b.getRiskCardID())){
                    if(b.getScenarioNumber() == 0){
                        s = 0;
                    }else{
                       if(scenario.getScenarioNumber()==b.getScenarioNumber()){
                       s = 0;
                        }
                    }
                }
            }
        switch(s) {
            case 1:
                internalFraud .addToList(scenario);
                if(internalFraud .getMaxRange()<d){
                    internalFraud .setMaxRange(d);
                }
                break;
            case 2:
                employmentPractices .addToList(scenario);
                if(employmentPractices .getMaxRange()<d){
                    employmentPractices .setMaxRange(d);
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
                clientPractices.addToList(scenario);
                if(clientPractices.getMaxRange()<d){
                    clientPractices.setMaxRange(d);
                }
                break;
            case 5:
                businessDisruption.addToList(scenario);
                if(businessDisruption.getMaxRange()<d){
                    businessDisruption.setMaxRange(d);
                }
                break;
            case 6:
                externalFraud.addToList(scenario);
                if(externalFraud.getMaxRange()<d){
                    externalFraud.setMaxRange(d);
                }
                break;
            case 7:
                damageToAssest.addToList(scenario);
                if(damageToAssest.getMaxRange()<d){
                    damageToAssest.setMaxRange(d);
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
        blacklist.clear();
    }
}
