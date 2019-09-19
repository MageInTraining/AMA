/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama.servitors;

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
}
