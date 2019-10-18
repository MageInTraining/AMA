/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.beans;

import java.util.ArrayList;
import java.util.List;

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
        this.scenarios = new ArrayList();
        this.threshold = 1;
        this.maxRange = 0.0;  
    };
    
    public Category(double t){
        this.scenarios = new ArrayList();
        this.threshold = t;
        this.maxRange = 0.0;  
    };
    
    public Category(String name, double t){
        this.scenarios = new ArrayList();
        this.threshold = t;
        this.maxRange = 0.0;
        this.categoryName = name;
    };
    
    public Category(String name, double t, double e){
        this.scenarios = new ArrayList();
        this.threshold = t;
        this.maxRange = 0.0;
        this.categoryName = name;
        this.eventPerYear = e;
    };
    
    /**Getters**/
    public List<Scenario> getList(){
        return this.scenarios;
    }
    public double getThreshold(){
        return this.threshold;
    }
    public Double getMaxRange(){
        return this.maxRange;
    }
    public int getDistribution(int index){
        return this.distribution[index];
    }    
    public String getCategoryName(){
        return this.categoryName;
    }
    public double getBucket(int index){
        return this.buckets[index];
    }
    public double[] getBuckets(){
        return this.buckets;
    }
    public double getBucketRatio(int index){
        return this.bucketRatios[index];
    }
    public List<Scenario> getScenarios(){
        return this.scenarios;
    }
    public int[] getDistribution(){
        return this.distribution;
    }
    public double getEventsPerYear(){
        return this.eventPerYear;
    }
    
    /**Setter*/
    public void addToList(Scenario e){
        this.scenarios.add(e);
    }
    public void setThreshold(double t){
        this.threshold = t;
    }
    public void setMaxRange(double r){
        this.maxRange = r;
    }
    public void addToDistribution(int index){
        this.distribution[index]++;
    }
    public void setCategoryName(String name){
        this.categoryName = name;
    }
    public void setBucketRatio(int index, double value){
        this.bucketRatios[index]= value;
    }
    public void setBucketRatios(double[] bRatios){
        this.bucketRatios = bRatios;
    }
    public void setDistribution(int[] dist){
        this.distribution = dist;
    }
    public void setBuckets(double[] bucks){
        this.buckets = bucks;
    }
}
