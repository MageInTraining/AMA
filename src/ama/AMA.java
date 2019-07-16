/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;
import java.io.IOException;
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
    
    private static int maxExpectedRange = 5000;

    @Override
    public void start(Stage stage){
        
        String file_name;
        file_name = "C:\\Users\\cen62777\\Documents\\test.txt";
        
        try {
            ReadFile file = new ReadFile(file_name);
            String[] aryLines = file.OpenFile();
            
            int i;
            for (i=0; i < aryLines.length; i++) {
                System.out.println(aryLines[i]);
            }
        }
        
        catch (IOException e) {
            System.out.println( e.getMessage() );
        }
        
        PercentileSeeker pSeeker = new PercentileSeeker();
        List<Scenario> scenarios = new ArrayList<>();
        
        //TODO: replace with method to fill List from provided css file
        //TODO: fill maxExpectedRange from load
        scenarios.add(new Scenario(1, 50, 1, 1000));
        scenarios.add(new Scenario(2, 300, 0.2, 1300));
        scenarios.add(new Scenario(3, 100, 0.5, 200));
        scenarios.add(new Scenario(4, 200, 0.01, 5000));
        scenarios.add(new Scenario(5, 200, 2, 300));
        scenarios.add(new Scenario(6, 200, 0.333, 2000));
        
        //array of lognormal distributions for individual scenario 
        double[] distribution = new double[(maxExpectedRange/10)+1];
        
        //TODO: replace with method to simulate scenarios
        for (Scenario scenario : scenarios) {
            
            scenario.setSigma(pSeeker);
            
            //creates lognormal distribution for the scenario
            LogNormalDistribution ln1 = new LogNormalDistribution(scenario.mu,
                    scenario.sigma);
            
            //simulates scenario in the span of 1000 years
            for(int i = 0; i < (scenario.probability * 1000); i++ ){
                Double d = ln1.sample();
                
                //replaces values from 0.991+ with value from 0.990
                if(d.intValue()>scenario.max){
                    d=(double)scenario.max;
                }
                try{
                    distribution[d.intValue()/10]++;
                }catch(Exception e){
                    System.out.println("Scenario " + scenario.scenarioNumber +
                            ": " + e);
                }
            }
        }
        
        Mean mean = new Mean();
        StandardDeviation sd1 = new StandardDeviation();
        
        LogNormalDistribution ln2 = new LogNormalDistribution(5.1781, 0.2755);
                                        
        double[] distribution2 = new double[(maxExpectedRange/10)+1];
        for(int i=0; i < 5000; i++){
           Double d = ln2.sample();
           try{
                distribution2[d.intValue()/10]++;
            }catch(Exception e){
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
            series2.getData().add(new XYChart.Data(i, distribution2[i]));
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
