/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.containers;

import ama.servitors.Distributor;
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
    private double eventPerYear;
    
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
    
    public Category(String name, double t, double e){
        scenarios = new ArrayList();
        threshold = t;
        maxRange = 0.0;
        categoryName = name;
        eventPerYear = e;
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
    public double[] getBuckets(){
        return buckets;
    }
    public double getBucketRatio(int index){
        return bucketRatios[index];
    }
    public List<Scenario> getScenarios(){
        return scenarios;
    }
    public int[] getDistribution(){
        return distribution;
    }
    public double getEventsPerYear(){
        return eventPerYear;
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
    public void setBucketRatios(double[] bRatios){
        bucketRatios = bRatios;
    }
    public void setDistribution(int[] dist){
        distribution = dist;
    }
    public void setBuckets(double[] bucks){
        buckets = bucks;
    }
}
