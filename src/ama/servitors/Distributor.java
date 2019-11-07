/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.servitors;

import static ama.Constants.EXP_PERCENTILE;
import static ama.Constants.GLOBAL_UPPER_LIMIT;
import ama.beans.Category;
import ama.beans.Scenario;
import com.opencsv.CSVWriter;
import static java.lang.Math.log;
import java.util.List;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import static ama.gui.AMAController.numberOfYears;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author cen62777
 */
public class Distributor {

    public static void distribute(int[] distribution
            , double threshold, double x){
        int b = (int)x;
        if(true){
            if(b > GLOBAL_UPPER_LIMIT){
                b = GLOBAL_UPPER_LIMIT;
            }
            distribution[b]++;
        }
    }
    
    public static void putInBucket(int[] distribution
            , double [] buckets, int size){
        //size parameter has been deprecated
        int b=0;
        for(int i = 0; i <= GLOBAL_UPPER_LIMIT; i++){ 
            switch(i){
                case(5):
                    b=1;
                    break;
                case(10):
                    b=2;
                    break;
                case(30):
                    b=3;
                    break;
                case(50):
                    b=4;
                    break;
            }
            buckets[b]=buckets[b]+distribution[i];
        }
    }
    public static void calculateDistribution(CSVWriter writer, Category category
            , List<String> outputText, boolean toLog, ProgressBar progressBar){
        category.setDistribution(new int[GLOBAL_UPPER_LIMIT+1]);
        category.setBuckets(new double[5]);
        category.setBucketRatios(new double[5]);
        
//        progressBar.setProgress(0);
//        Double progressStep = (double)(100 / category.getScenarios().size());
        
        for (Scenario scenario : category.getScenarios()){
            scenario.setMu(log(scenario.getEstimated()));
            scenario.setSigma(PercentileSeeker.getSigmaPerPercentile(
                    EXP_PERCENTILE, scenario.getMax(), scenario.getMu()));
            LogNormalDistribution lnd =
                    new LogNormalDistribution(
                            scenario.getMu(),scenario.getSigma());
            for(int i = 0; i < (
                    scenario.getProbability() * numberOfYears); i++ ){
                Double d = lnd.sample();
                if(d>category.getThreshold()){
                    Distributor.distribute(category.getDistribution()
                                                , category.getThreshold(), d);
                    //logging simulation output into a csv file
                    if(toLog){
                        String entry =scenario.getRiskardID()
                            + ","
                            + scenario.getScenarioNumber()
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
//            progressBar.setProgress(progressBar.getProgress() + progressStep);
        }
        Distributor.putInBucket(category.getDistribution()
                , category.getBuckets()
                , category.getMaxRange().intValue());
        double sumOfBuckets = 0.0;
        for(Double bucket:category.getBuckets()){
            sumOfBuckets = sumOfBuckets + bucket; 
        }
        for(int i=0;i<5;i++){
            category.setBucketRatio(i, (category.getBucket(i)/sumOfBuckets));
            outputText.add("Buckets ratio of buckets number " + (i+1) + " : "
                    + category.getBucketRatio(i));
        }
        double x = (100/category.getBucketRatio(0))*category.getEventsPerYear();
        for(int i=0; i<5; i++){
            outputText.add("Events in bucket " + (i+1)+ " happen once every " + 
                        1/((category.getBucketRatio(i)*x)/100) + " years");
        }
    }
}
