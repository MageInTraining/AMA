/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ama;
import java.io.IOException;
/**
 *
 * @author cen62777
 */
public class FileData {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        
        String file_name = "C:\\Users\\cen62777\\Documents\\test.txt";
        
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
        
        try {
        WriteFile data = new WriteFile (file_name, true);
        data.writeToFile("This is another line of text");
        System.out.println("Text file written into");
        }
        catch (IOException e) {
           System.out.println(e.getMessage());
        }
        
    }
    
}
