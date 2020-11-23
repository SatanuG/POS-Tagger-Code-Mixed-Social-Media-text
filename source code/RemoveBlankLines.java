/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socialmedia_pos_v1.evaluate;

import socialmedia_pos_v1.ReaderWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Satanu
 */
public class RemoveBlankLines {
    
    public String removeBlankLines(String input){
        String op = "";
        Scanner scanner;
        int flag = 0;
        scanner = new Scanner(input);
        while(scanner.hasNextLine()){
            String newline = scanner.nextLine();
            if (newline.length()==0){                
            }
            else{
                op+=newline+"\n";               
            }                
        }        
        return op;
    }
    
    public static void main(String args[]) throws IOException{
        ReaderWriter rw = new ReaderWriter();
        String input = rw.readFile("Resources\\Output\\Tamil_forCRF", "txt");
        RemoveBlankLines rel = new RemoveBlankLines();
        System.out.println(rel.removeBlankLines(input));
    }
}
