/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.beans;

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
        return this.riskCardID;
    }
    public int getScenarioNumber(){
        return this.scenarioNumber;
    }
    public double getEstimated(){
        return this.estimated;
    }
    public double getProbability(){
        return this.probability;
    }
    public double getMax(){
        return this.max;
    }
    public double getMu(){
        return this.mu;
    }
    public double getSigma(){
        return this.sigma;
    }
    public int getRiskType(){
        return this.riskTypeBL2;
    }
    /**Setters*/
    public void setRiskCardID(String id){
        this.riskCardID = id;
    }
    public void setScenarioNumber(int n){
       this.scenarioNumber = n; 
    }
    public void setEstimated(double e){
        this.estimated = e;
    }
    public void setProbability(double p){
        this.probability = p;
    }
    public void setMax(double m){
        this.max = m;
    }
    public void setMu(double m){
        this.mu = m;
    }
    public void setSigma(double sigma){
        this.sigma = sigma;
    }
    public void setRiskType(int r){
        this.riskTypeBL2 = r;
    }
}