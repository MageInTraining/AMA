/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.beans;

import com.opencsv.bean.CsvBindByPosition;

/**
 *
 * @author cen62777
 */
public class EmusHistory implements java.io.Serializable{
    @CsvBindByPosition(position = 0)
    private String category;
    @CsvBindByPosition(position = 1)
    private double damage;
    
    public EmusHistory(){    
    }
    public EmusHistory(String category, double damage){    
        this.category = category;
        this.damage = damage;
    }
    public String getCategory(){
        return this.category;
    }
    public double getDamage(){
        return this.damage;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setDamage(double damage){
        this.damage = damage;
    }
}
