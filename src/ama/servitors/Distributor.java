/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.servitors;

import static ama.Constants.NUMBER_OF_YEARS;
import ama.containers.Category;
import ama.containers.Scenario;
import com.opencsv.CSVWriter;
import static java.lang.Math.log;
import java.util.List;
import org.apache.commons.math3.distribution.LogNormalDistribution;

/**
 *
 * @author cen62777
 */
public class Distributor {

    public static void distribute(int[] distribution, double threshold, double x){
        int b = (int)x;
        if(x>threshold){
            distribution[b]++;
        }
    }
    
    public static void putInBucket(int[] distribution, double [] buckets, int size){
        int b=0;
        for(int i = 0; i < size; i++){ 
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
//            System.out.println("Distribution slot " + i + " : " + distribution[i]);
            buckets[b]=buckets[b]+distribution[i];
        }
    }
        public static void calculateDistribution(CSVWriter writer, Category category, List<String> outputText){
            category.setDistribution(new int[category.getMaxRange().intValue()]);
            category.setBuckets(new double[5]);
            category.setBucketRatios(new double[5]);
            for (Scenario scenario : category.getScenarios()){
                scenario.setMu(log(scenario.getEstimated()));
                scenario.setSigma();
                LogNormalDistribution lnd =
                        new LogNormalDistribution(
                                scenario.getMu(),scenario.getSigma());
                for(int i = 0; i < (scenario.getProbability() * NUMBER_OF_YEARS); i++ ){
                    Double d = lnd.sample();
                    if(d<=scenario.getMax() && scenario.getMax() >= category.getThreshold()){
                        Distributor.distribute(category.getDistribution(), category.getThreshold(), d);
                        //logging simulation output into a csv file
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
            Distributor.putInBucket(category.getDistribution(), category.getBuckets()
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
                outputText.add("For category " + category.getCategoryName() +
                        ", events in bucket " + (i+1)+ " happen once every " + 
                            1/((category.getBucketRatio(i)*x)/100) + " years");
            }
        }
}
