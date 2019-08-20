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
    private double[] distribution;
    
    public Category(){
        scenarios = new ArrayList();
        distribution = new double[50];
    };
    
    /**Getters**/
    public List<Scenario> getList(){
        return scenarios;
    }
    public double getThreshold(){
        return threshold;
    }
    public double getMaxRange(){
        return maxRange;
    }
    public double getDistribution(int i){
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

                //adds count to distribution slot
                try{
                    distribution[d.intValue()/10]++;
                }catch(Exception e){
                    System.out.println("Scenario "
                            + scenario.getScenarioNumber() + ": " + e);
                }
            }
        }
    }
}
