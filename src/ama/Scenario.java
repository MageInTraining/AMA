/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;

import static ama.Constants.EXP_PERCENTILE;
import static java.lang.Math.log;

/**
 *
 * @author cen62777
 */
public class Scenario {
    public int scenarioNumber;
    public int estimated;
    public double probability;
    public int max;
    public double mu;
    public double sigma;
    
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