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
public class BuildWL {
    
    // Extracts words from the input file and saves them in wordlists
    // according to language tags present
    public void createWordPOSPairs(String input) throws IOException{
        Scanner scanner;
        String en_list = "", bn_list = "", hi_list = "", ta_list = "";
        String acro_list = "", univ_list = "", undef_list = "", ne_list = "";
        int count = 0, flag =0, linenum=0;
        scanner = new Scanner(input);
        while(scanner.hasNextLine()){
            String newline = scanner.nextLine();
            String arr[] = newline.split("\t");
            if(arr.length==3)
            {
                count+=1;
                String word = arr[0];
                String lang = arr[1];
                String post = arr[2];
                String lineinfo = Integer.toString(linenum);
                
                if(lang.equalsIgnoreCase("en")||lang.equalsIgnoreCase("en+bn_suffix")||lang.equalsIgnoreCase("E")){
                    en_list+= word+"\t"+post+"\n";
                }
                else if(lang.equalsIgnoreCase("bn")){
                    bn_list+= word+"\t"+post+"\n";
                    flag = 0;
                }
                else if(lang.equalsIgnoreCase("hi")){
                    hi_list+= word+"\t"+post+"\n";
                }
                else if(lang.equalsIgnoreCase("T")){
                    ta_list+= word+"\t"+post+"\n";
                }
                else if(lang.equalsIgnoreCase("ne")||lang.equalsIgnoreCase("ne+en_suffix")||lang.equalsIgnoreCase("ne+bn_suffix")||lang.equalsIgnoreCase("N")){
                    ne_list+= word+"\t"+post+"\n";
                }
                else if(lang.equalsIgnoreCase("univ")||lang.equalsIgnoreCase("R")){
                    univ_list+= word+"\t"+post+"\n";
                }
                else if(lang.equalsIgnoreCase("acro")||lang.equalsIgnoreCase("acro+en_suffix")||lang.equalsIgnoreCase("acro+bn_suffix")){
                    acro_list+= word+"\t"+post+"\n";
                }
                else {
                    undef_list+= word+"\t"+post+"\n";
                }
            }
            linenum++;
        } 
        ReaderWriter rw = new ReaderWriter();
        
        //System.out.println(en_list);
        
        rw.appendToFile(en_list, "English", "txt");
        rw.appendToFile(bn_list, "Bengali", "txt");
        rw.appendToFile(hi_list, "Hindi", "txt");
        rw.appendToFile(ta_list, "Tamil", "txt");
        rw.appendToFile(ne_list, "NE", "txt");
        rw.appendToFile(univ_list, "Univ", "txt");
        rw.appendToFile(acro_list, "Acronym", "txt");
        rw.appendToFile(undef_list, "Undef", "txt");        
    }    
    
    // Returns the number of sentences in file
    public int getNumOfSentences(String input){
        String op = "";
        int linenum = 0;
        int flag = 0;
        Scanner sc = new Scanner(input);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.length()==0){
                if(flag==0){
                    linenum+=1;            
                }
                flag = 1; // for successive blank lines
            }
            else{
                flag=0;
            }
        }
        //System.out.println("Number of sentences = "+linenum);
        return linenum;
    }
    
    
}
