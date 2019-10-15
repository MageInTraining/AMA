/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;

/**
 *
 * @author cen62777
 */
public interface Constants {
    //mathematical constant :pi
    public static final double PI = 3.14159265359;
    //standart normal distribution for 0.99
    public static final double U = 2.33; 
    //percentile, for which we are looking
    public static final double EXP_PERCENTILE = 0.99;
    //number of years, for which we run simulation
    //15_10_2018 values for 5% tolerance:
    //internalFraud:        10M
    //employmentPractices:  100M
    //execution:            1M
    //clientPractices:      1M
    //businessDisruption:   10M
    //externalFraud:        10M
    //damageToAssets:       100M
    public static final int NUMBER_OF_YEARS = 100000000;
    //upper limit of buckets
    public static final int GLOBAL_UPPER_LIMIT = 50;
}
