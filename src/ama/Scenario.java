/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;

import static ama.Constants.EXP_PERCENTILE;
import com.opencsv.bean.CsvBindByPosition;
import static java.lang.Math.log;

/**
 *
 * @author cen62777
 */
public class Scenario implements java.io.Serializable{
    @CsvBindByPosition(position = 0)
    private int scenarioNumber;
    @CsvBindByPosition(position = 1)
    private int estimated;
    @CsvBindByPosition(position = 2)
    private double probability;
    @CsvBindByPosition(position = 3)
    private int max;
    private double mu;
    private double sigma;
    
    public Scenario(){
        this.mu = log(estimated);
    }
    
    /**Getters**/
    public int getScenarioNumber(){
        return this.scenarioNumber;
    }
    public int getEstimated(){
        return this.estimated;
    }
    public double getProbability(){
        return this.probability;
    }
    public int getMax(){
        return this.max;
    }
    public double getMu(){
        return this.mu;
    }
    public double getSigma(){
        return this.sigma;
    }
    /**Setter*/
    public void setScenarioNumber(int n){
       this.scenarioNumber = n; 
    }
    public void setEstimated(int e){
        this.estimated = e;
    }
    public void setProbability(double p){
        this.probability = p;
    }
    public void setMax(int m){
        this.max = m;
    }
    public void setMu(double m){
        this.mu = m;
    }
    public void setSigma(PercentileSeeker ps){
        sigma = ps.getSigmaPerPercentile(EXP_PERCENTILE, max, log(estimated));
    }
}