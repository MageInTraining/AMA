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
        this.riskCardID = riskCardID;
    }
    public BlacklistItem(int scenarioNumber){
        this.scenarioNumber = scenarioNumber;
    }
    public BlacklistItem(String riskCardID, int scenarioNumber){
        this.riskCardID = riskCardID;
        this.scenarioNumber = scenarioNumber;
    }
    /**Getters**/
    public String getRiskCardID(){
        return this.riskCardID;
    }
    public int getScenarioNumber(){
        return this.getScenarioNumber();
    }
}
