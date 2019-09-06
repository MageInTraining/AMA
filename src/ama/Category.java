/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;

import static ama.Constants.NUMBER_OF_YEARS;
import com.opencsv.CSVWriter;
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
    private String categoryName;
    private double[] buckets;
    
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
    
    public Category(String name, double t){
        scenarios = new ArrayList();
        threshold = t;
        maxRange = 0.0;
        categoryName = name;
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
    public int getDistribution(int index){
        return distribution[index];
    }    
    public String getCategoryName(){
        return categoryName;
    }
    public double getBucket(int index){
        return buckets[index];
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
    public void setCategoryName(String name){
        categoryName = name;
    }
    
    /**Other methods**/
    public void calculateDistribution(CSVWriter writer){
        distribution = new int[(int)maxRange];
        buckets = new double[5];
        for (Scenario scenario : scenarios){
            scenario.setMu(log(scenario.getEstimated()));
            scenario.setSigma();
            LogNormalDistribution lnd =
                    new LogNormalDistribution(
                            scenario.getMu(),scenario.getSigma());
            for(int i = 0; i < (scenario.getProbability() * NUMBER_OF_YEARS); i++ ){
                Double d = lnd.sample();
                if(d<=scenario.getMax() && scenario.getMax() >= threshold){
                    Distributor.distribute(distribution, threshold, d);
                    String entry = scenario.getScenarioNumber()
                                + ","
                                + scenario.getRiskType()
                                + ","
                                + scenario.getEstimated()
                                + ","
                                + scenario.getProbability()
                                + ","
                                + scenario.getMax()
                                + ","
                                + d
                                ;                                   
                    String[] entries = entry.split(",");
                    writer.writeNext(entries);
                }
            }
        }
        Distributor.putInBucket(distribution, buckets, (int)maxRange);
    }
}
