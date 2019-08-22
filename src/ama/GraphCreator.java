/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author cen62777
 */
public class GraphCreator {
    public GraphCreator(){
        
    }
    public Scene seScene(Category c){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Damage");
        yAxis.setLabel("Density");
        
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);
        
        XYChart.Series series = new XYChart.Series();
        int r = c.getMaxRange().intValue();
        for(int i=0;i < r;i++){
            series.getData().add(new XYChart.Data(i, c.getDistribution(i)));
        }
        lineChart.setCreateSymbols(false);
        lineChart.getData().addAll(series);
        Scene scene  = new Scene(lineChart,700,350);  
        return scene;
    }

}
