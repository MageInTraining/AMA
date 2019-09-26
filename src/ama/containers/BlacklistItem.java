/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.containers;

/**
 *
 * @author cen62777
 */
public class BlacklistItem {
    private String riskCardID;
    private int scenarioNumber;
    
    public BlacklistItem(String riskCardID){
        riskCardID = riskCardID;
        scenarioNumber = 0;
    }
//    public BlacklistItem(int scenarioNumber){
//        this.scenarioNumber = scenarioNumber;
//    }
    public BlacklistItem(String riskCardID, int scenarioNumber){
        riskCardID = riskCardID;
        scenarioNumber = scenarioNumber;
    }
    /**Getters**/
    public String getRiskCardID(){
        return riskCardID;
    }
    public int getScenarioNumber(){
        return getScenarioNumber();
    }
}
