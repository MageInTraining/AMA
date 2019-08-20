/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;

import static ama.Constants.EXP_PERCENTILE;
import static ama.Constants.MAGNIFICATION;
import com.opencsv.bean.CsvBindByPosition;

/**
 *
 * @author cen62777
 */
public class Scenario implements java.io.Serializable{
    @CsvBindByPosition(position = 0)
    private int scenarioNumber;
    @CsvBindByPosition(position = 1)
    private int riskTypeBL2;
    @CsvBindByPosition(position = 2)
    private double estimated;
    @CsvBindByPosition(position = 3)
    private double probability;
    @CsvBindByPosition(position = 4)
    private double max;
    
    private double mu;
    private double sigma;
    
    public Scenario(){
    }
    
    /**Getters**/
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
    public void setScenarioNumber(int n){
       scenarioNumber = n; 
    }
    public void setEstimated(double e){
        estimated = e * MAGNIFICATION;
    }
    public void setProbability(double p){
        probability = p;
    }
    public void setMax(double m){
        max = m * MAGNIFICATION;
    }
    public void setMu(double m){
        mu = m;
    }
    public void setSigma(PercentileSeeker ps){
        sigma =ps.getSigmaPerPercentile(EXP_PERCENTILE,
                                                max, mu);
    }
    public void setRiskType(int r){
        riskTypeBL2 = r;
    }
}