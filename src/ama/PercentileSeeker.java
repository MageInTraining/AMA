//LOAD FROM FILE BRANCH
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;

import static java.lang.Math.log;

/**
 *
 * @author cen62777
 */
public class PercentileSeeker {
    // get sigma parametr of lognormal distribution with give mu parametr amd
    // given variable x being in given percentile of this distribution
    double getSigmaPerPercentile(double expectPercentile, double x, double mu){
        double p = 1;
        double s = 0;
        while (p > expectPercentile){
            s = s + 0.0001;
            p = getPercentile(x, mu, s);
        }
        return s;
    }
    
    // get percentile for x variable being placed in lognormal distribution with
    // mu and sigma parameters, e.g. probability, that random variable is
    // bellow x on lognormal distribution curve  
    private double getPercentile(double x, double mu, double sigma){
        return CNDF((log(x)-mu)/sigma);
    }
    
    // returns the cumulative normal distribution function (CNDF)
    // for a standard normal: N(0,1)
    private double CNDF(double x)
    {
        int neg = (x < 0d) ? 1 : 0;
        if ( neg == 1) 
            x *= -1d;

        double k = (1d / ( 1d + 0.2316419 * x));
        double y = (((( 1.330274429 * k - 1.821255978) * k + 1.781477937) *
                       k - 0.356563782) * k + 0.319381530) * k;
        y = 1.0 - 0.398942280401 * Math.exp(-0.5 * x * x) * y;

        return (1d - neg) * y + neg * (1d - y);
    }
    
}
