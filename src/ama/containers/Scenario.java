/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.containers;

import ama.servitors.PercentileSeeker;
import static ama.Constants.EXP_PERCENTILE;
import com.opencsv.bean.CsvBindByPosition;

/**
 *
 * @author cen62777
 */
public class Scenario implements java.io.Serializable{
    @CsvBindByPosition(position = 0)
    private String riskCardID;
    @CsvBindByPosition(position = 1)
    private int scenarioNumber;
    @CsvBindByPosition(position = 2)
    private int riskTypeBL2;
    @CsvBindByPosition(position = 3)
    private double estimated;
    @CsvBindByPosition(position = 4)
    private double probability;
    @CsvBindByPosition(position = 5)
    private double max;
    
    private double mu;
    private double sigma;
    
    public Scenario(){
    }
    public Scenario(String id){
        this.riskCardID = id;
    }
    public Scenario(int scenarioNumber){
        this.scenarioNumber = scenarioNumber;
    }
    
    /**Getters**/
    public String getRiskardID(){
        return riskCardID;
    }
    public int getScenarioNumber(){
        return scenarioNumber;
    }
    public double getEstimated(){
        return estimated;
    }
    public double getProbability(){
        return probability;
    }
    public double getMax(){
        return max;
    }
    public double getMu(){
        return mu;
    }
    public double getSigma(){
        return sigma;
    }
    public int getRiskType(){
        return riskTypeBL2;
    }
    /**Setters*/
    public void setRiskCardID(String id){
        riskCardID = id;
    }
    public void setScenarioNumber(int n){
       scenarioNumber = n; 
    }
    public void setEstimated(double e){
        estimated = e;
    }
    public void setProbability(double p){
        probability = p;
    }
    public void setMax(double m){
        max = m;
    }
    public void setMu(double m){
        mu = m;
    }
    public void setSigma(){
        sigma =PercentileSeeker.getSigmaPerPercentile(EXP_PERCENTILE,
                                                max, mu);
    }
    public void setRiskType(int r){
        riskTypeBL2 = r;
    }
}