/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;

import static ama.Constants.NUMBER_OF_YEARS;
import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.distribution.LogNormalDistribution;

/**
 *
 * @author cen62777
 */
public class Category {
    private List<Scenario> scenarios;
    private double threshold;
    private double maxRange;
    private int[] distribution;
    
    public Category(){
        scenarios = new ArrayList();
        threshold = 1;
        maxRange = 0.0;  
    };
    
    public Category(double t){
        scenarios = new ArrayList();
        threshold = t;
        maxRange = 0.0;  
    };
    
    /**Getters**/
    public List<Scenario> getList(){
        return scenarios;
    }
    public double getThreshold(){
        return threshold;
    }
    public Double getMaxRange(){
        return maxRange;
    }
    public int getDistribution(int i){
        return distribution[i];
    }
    
    /**Setters**/
    public void addToList(Scenario e){
        scenarios.add(e);
    }
    public void setThreshold(double t){
        threshold = t;
    }
    public void setMaxRange(double r){
        maxRange = r;
    }
    public void addToDistribution(int position){
        distribution[position]++;
    }
    
    /**Other methods**/
    public void calculateDistribution(PercentileSeeker pSeeker){
        distribution = new int[5];
        for (Scenario scenario : scenarios){
            //creates lognormal distribution for the scenario
            scenario.setMu(log(scenario.getEstimated()));
            scenario.setSigma(pSeeker);
            LogNormalDistribution lnd =
                    new LogNormalDistribution(
                            scenario.getMu(),scenario.getSigma());
            //simulates scenario in the span of 1000 years
            for(int i = 0; i < (scenario.getProbability() * NUMBER_OF_YEARS); i++ ){
                Double d = lnd.sample();
                if(d<=scenario.getMax() && d>= threshold){
                    try{
                        if(d>50){
                            distribution[4]++;
                        }else{
                            if(d>30){
                                distribution[3]++;
                            }else{
                                if(d>10){
                                    distribution[2]++;
                                }else{
                                    if(d>5){
                                        distribution[1]++;
                                    }else{
                                        distribution[0]++;
                                    }
                                }
                            }
                        }
                    }catch(Exception e){
                        System.out.println("Scenario "
                            + scenario.getScenarioNumber() + ": " + e + ":" +
                                d);
                    }
                }
            }
        }
    }
}
