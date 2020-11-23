/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socialmedia_pos_v1;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Satanu
 */
public class RemoveExtraLines {
    
    public String removeBlankLines(String input){
        String op = "";
        Scanner scanner;
        int flag = 0;
        scanner = new Scanner(input);
        while(scanner.hasNextLine()){
            String newline = scanner.nextLine();
            if (newline.length()==0){
                flag+=1;
                if (flag<2){
                    op+="\n";
                }
            }
            else{
                op+=newline+"\n";
                flag=0;
            }                
        }        
        return op;
    }
    
    public String removestartingBlanks(String input){
        String op = "";
        Scanner scanner;
        int flag = 0;
        
        scanner = new Scanner(input);
        String newline="";
        if(scanner.hasNextLine())
            newline = scanner.nextLine();
        else
            return op;
        while(newline.length()==0){
            if(scanner.hasNextLine())
                newline = scanner.nextLine();
        }
        do{
            op+=newline+"\n";
            if(scanner.hasNextLine())
                newline = scanner.nextLine();
        }
        while(scanner.hasNextLine());
        return op;
    }
    
    public static void main(String args[]) throws IOException{
        ReaderWriter rw = new ReaderWriter();
        String input = rw.readFile("Resources\\Output\\Tamil_forSP_test_raw.txt", "");
        RemoveExtraLines rel = new RemoveExtraLines();
        //System.out.println(rel.removestartingBlanks(rel.removeBlankLines(input)));
        rw.writeToFile((rel.removestartingBlanks(rel.removeBlankLines(input))), "Check", "");
    }
}
