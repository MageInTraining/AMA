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
    private double[] bucketRatios;
    
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
    public double getBucketRatio(int index){
        return bucketRatios[index];
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
    public void addToDistribution(int index){
        distribution[index]++;
    }
    public void setCategoryName(String name){
        categoryName = name;
    }
    public void setBucketRatio(int index, double value){
        bucketRatios[index]= value;
    }
    
    /**Other methods**/
    public void calculateDistribution(CSVWriter writer){
        distribution = new int[(int)maxRange];
        buckets = new double[5];
        bucketRatios = new double[5];
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
//                    //logging simulation output into a csv file
//                    String entry = scenario.getScenarioNumber()
//                                + ","
//                                + scenario.getRiskType()
//                                + ","
//                                + scenario.getEstimated()
//                                + ","
//                                + scenario.getProbability()
//                                + ","
//                                + scenario.getMax()
//                                + ","
//                                + d
//                                ;                                   
//                    String[] entries = entry.split(",");
//                    writer.writeNext(entries);
                }
            }
        }
        Distributor.putInBucket(distribution, buckets, (int)maxRange);
        double sumOfBuckets = 0.0;
        for(Double bucket:buckets){
            sumOfBuckets = sumOfBuckets + bucket; 
        }
        for(int i=0;i<5;i++){
            bucketRatios[i]=buckets[i]/sumOfBuckets;
            System.out.format("Buckets ratio of buckets number " + (i+1) + " : " + "%.5f%n", bucketRatios[i]);
        }
    }
}
