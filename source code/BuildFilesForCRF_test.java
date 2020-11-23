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
public class BuildFilesForCRF_test {
    
    // Extracts words from the input file by language and saves them in files
    // for input to CRF
    public void createFiles(String input) throws IOException{
        Scanner scanner;
        String en_list = "", bn_list = "", hi_list = "", ta_list = "";
        String acro_list = "", univ_list = "", undef_list = "", ne_list = "";
        int count = 0;
        int flag1, flag2, flag3, flag4; 
        flag1 = flag2 = flag3 = flag4 =1;
        scanner = new Scanner(input);
        //System.out.println(input);
        while(scanner.hasNextLine()){
            String newline = scanner.nextLine();
            String arr[] = newline.split("\t");
            
            //System.out.println(arr[0]);
            if(arr.length==2)
            {
                count+=1;
                String word = arr[0];
                String lang = arr[1];
                if(lang.equalsIgnoreCase("en")||lang.equalsIgnoreCase("en+bn_suffix")||lang.equalsIgnoreCase("E")){
                    en_list+= word+"\n";
                    flag1 = 0;
                }
                else if(lang.equalsIgnoreCase("bn")){
                    bn_list+= word+"\n";
                    flag2 = 0;
                }
                else if(lang.equalsIgnoreCase("hi")){
                    hi_list+= word+"\n";
                    flag3 = 0;
                }
                else if(lang.equalsIgnoreCase("T")){
                    ta_list+= word+"\n";
                    flag4 = 0;
                }
                else {                    
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
        
        rw.writeToFile(rel.removeBlankLines(en_list), "English_forCRF_test_raw", "txt");
        rw.writeToFile(rel.removeBlankLines(bn_list), "Bengali_forCRF_test_raw", "txt");
        rw.writeToFile(rel.removeBlankLines(hi_list), "Hindi_forCRF_test_raw", "txt");
        rw.writeToFile(rel.removeBlankLines(ta_list), "Tamil_forCRF_test_raw", "txt");      
        
    }    
    
    public static void main(String args[]) throws IOException{
        // Obtaining the content of input files
        ReaderWriter rw = new ReaderWriter();
        String text[] = new String[3];
        text[0] = rw.readFile("Resources\\dataset\\Test\\BN_EN.txt","");
        text[1] = rw.readFile("Resources\\dataset\\Test\\HI_EN.txt","");
        text[2] = rw.readFile("Resources\\dataset\\Test\\TA_EN.txt","");
        
        BuildFilesForCRF_test bfct = new BuildFilesForCRF_test();
        bfct.createFiles(text[0]);
        bfct.createFiles(text[1]);
        bfct.createFiles(text[2]);
    }
}
