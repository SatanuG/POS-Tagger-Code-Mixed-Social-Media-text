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
public class BuildFilesForCRF {
    
    // Extracts words from the input file by language and saves them in files
    // for input to CRF
    // Includes blank lines between sentences
    public void createFiles(String input) throws IOException{
        Scanner scanner;
        String en_list = "", bn_list = "", hi_list = "", ta_list = "";
        String acro_list = "", univ_list = "", undef_list = "", ne_list = "";
        int count = 0;
        int flag1, flag2, flag3, flag4; 
        flag1 = flag2 = flag3 = flag4 =1;
        scanner = new Scanner(input);
        while(scanner.hasNextLine()){
            String newline = scanner.nextLine();
            if(newline.length()!=0){
                String arr[] = newline.split("\t");
                if(arr.length==3)
                {
                    count+=1;
                    String word = arr[0];
                    String lang = arr[1];
                    String post = arr[2];
                    if(lang.equalsIgnoreCase("en")||lang.equalsIgnoreCase("en+bn_suffix")||lang.equalsIgnoreCase("E")){
                        en_list+= word+"\t"+post+"\n";
                        flag1 = 0;
                    }
                    else if(lang.equalsIgnoreCase("bn")){
                        bn_list+= word+"\t"+post+"\n";
                        flag2 = 0;
                    }
                    else if(lang.equalsIgnoreCase("hi")){
                        hi_list+= word+"\t"+post+"\n";
                        flag3 = 0;
                    }
                    else if(lang.equalsIgnoreCase("T")){
                        ta_list+= word+"\t"+post+"\n";
                        flag4 = 0;
                    }
                    else {
                    }
                }
            }
            else{
                if (flag1==0){
                    en_list +="\n";
                    flag1 = 1;
                }
                if (flag2==0){
                    bn_list +="\n";
                    flag2 = 1;
                }
                if (flag3==0){
                    hi_list +="\n";
                    flag3 = 1;
                }
                if (flag4==0){
                    ta_list +="\n";
                    flag4 = 1;
                }
            }
        } 
        ReaderWriter rw = new ReaderWriter();
        
        //System.out.println(en_list);
        RemoveExtraLines rel = new RemoveExtraLines();
        
        rw.appendToFile(rel.removeBlankLines(en_list), "English_forCRF1", "txt");
        rw.appendToFile(rel.removeBlankLines(bn_list), "Bengali_forCRF1", "txt");
        rw.appendToFile(rel.removeBlankLines(hi_list), "Hindi_forCRF1", "txt");
        rw.appendToFile(rel.removeBlankLines(ta_list), "Tamil_forCRF1", "txt");
        
        //rw.appendToFile(rel.removestartingBlanks(rel.removeBlankLines(en_list)), "English_forCRF", "txt");
        //rw.appendToFile(rel.removestartingBlanks(rel.removeBlankLines(bn_list)), "Bengali_forCRF", "txt");
        //rw.appendToFile(rel.removestartingBlanks(rel.removeBlankLines(hi_list)), "Hindi_forCRF", "txt");
        //rw.appendToFile(rel.removestartingBlanks(rel.removeBlankLines(ta_list)), "Tamil_forCRF", "txt");
    }    
}
