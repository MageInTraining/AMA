/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.beans;

import com.opencsv.bean.CsvBindByPosition;

/**
 *
 * @author cen62777
 */
public class BlacklistItem implements java.io.Serializable{
    @CsvBindByPosition(position = 0)
    private String riskCardID;
    @CsvBindByPosition(position = 1)
    private int scenarioNumber;
    
    public BlacklistItem(){
    }
    
    public BlacklistItem(String riskCardID){
        this.riskCardID = riskCardID;
        this.scenarioNumber = 0;
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
        return this.scenarioNumber;
    }
    /**Setter*/
    public void setRiskCardID(String riskCardID){
        this.riskCardID = riskCardID;
    }
    public void setScenarioNumber(int scenarioNumber){
        this.scenarioNumber = scenarioNumber;
    }
}
