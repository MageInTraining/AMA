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
    public int scenarioNumber;
    @CsvBindByPosition(position = 1)
    public int estimated;
    @CsvBindByPosition(position = 2)
    public double probability;
    @CsvBindByPosition(position = 3)
    public int max;
    public double mu;
    public double sigma;
    
    public Scenario(){
        mu = log(estimated);
    }
    
    public Scenario(int n, int e, double p, int m){
        
        scenarioNumber = n;
        estimated = e;
        probability = p;
        max = m;
        mu = log(estimated);
    }
    
    protected void setSigma(PercentileSeeker ps){
        sigma = ps.getSigmaPerPercentile(EXP_PERCENTILE, max, log(estimated));
    }
}