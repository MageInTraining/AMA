/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;
import static ama.Constants.EXP_PERCENTILE;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

/**
 *
 * @author cen62777
 */
public class AMA extends Application{
    
    /*
    private static Double in1000years;
    private static double x;
    private static double mu;
    private static double sigma;
    */
    //TODO: fill maxExpectedRange from load
    private static int maxExpectedRange = 5000;

    @Override
    public void start(Stage stage) throws FileNotFoundException{
        
        //TODO: give path to csv file as parametr
        String file_name= "C:\\Users\\cen62777\\Documents\\Rizika_test.csv";
        PercentileSeeker pSeeker = new PercentileSeeker();        
        List<Scenario> scenarios = new CsvToBeanBuilder(new FileReader(file_name)).withType(Scenario.class).withSeparator('\t').build().parse();
        
        //array of lognormal distributions for individual scenario 
        double[] distribution = new double[(maxExpectedRange/10)+1];
        
        //TODO: replace with method to simulate scenarios
        for (Scenario scenario : scenarios) {
            
            System.out.println("Scenario " + scenario.getScenarioNumber());
            System.out.println(scenario.getEstimated());
            System.out.println(scenario.getProbability());
            System.out.println(scenario.getMax());
            
            scenario.setMu(log(scenario.getEstimated()));
            scenario.setSigma(pSeeker);
            
            System.out.println(scenario.getMu());
            System.out.println(scenario.getSigma());
            
            //creates lognormal distribution for the scenario
            LogNormalDistribution ln1 = new LogNormalDistribution(scenario.getMu(),
                    scenario.getSigma());
            
            //simulates scenario in the span of 1000 years
            for(int i = 0; i < (scenario.getProbability() * 1000); i++ ){
                Double d = ln1.sample();
                //System.out.println(d);
                
                //replaces values from 0.991+ with value from 0.990
                if(d.intValue()>scenario.getMax()){
                    d=(double)scenario.getMax();
                }
                try{
                    distribution[d.intValue()/10]++;
                }catch(Exception e){
                    System.out.println("Scenario " + scenario.getScenarioNumber() +
                            ": " + e);
                }
            }
        }
        
        Mean mean = new Mean();
        
        StandardDeviation sd1 = new StandardDeviation();
        
//        LogNormalDistribution ln2 = new LogNormalDistribution(5.1781, 0.2755);
        LogNormalDistribution ln2 = new LogNormalDistribution(
                log(mean.evaluate(distribution))
                , pSeeker.getSigmaPerPercentile(
                        0.99, maxExpectedRange, log(mean.evaluate(distribution)
                        )
                )
        );
                                        
        double[] distribution2 = new double[(maxExpectedRange/10)+1];
        for(int i=0; i < 5000; i++){
           Double d = ln2.sample();
           //replaces values from 0.991+ with value from 0.990
            if(d.intValue()>(maxExpectedRange/10)){
                d=(double)(maxExpectedRange/10);
            }
            System.out.println("Master distribution "+ d);
            try{
                distribution2[d.intValue()/10]++;
            }catch(Exception e){
                System.out.println("Master distribution " + e);
            }
        }
        
        //Create graph of functions
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Damage");
        xAxis.setLabel("Density");
        
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);
        
        XYChart.Series series = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        
        for(int i=0;i < (maxExpectedRange/10)+1;i++){
            series.getData().add(new XYChart.Data(i, distribution[i]));
            //series2.getData().add(new XYChart.Data(i, distribution2[i]));
        }
        Scene scene  = new Scene(lineChart,700,350);
        lineChart.setCreateSymbols(false);
        lineChart.getData().addAll(series, series2);
       
        stage.setScene(scene);
        stage.show();
    }
    
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
