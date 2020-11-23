/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socialmedia_pos_v1.evaluate;

import java.io.IOException;
import java.util.Scanner;
import socialmedia_pos_v1.ReaderWriter;

/**
 *
 * @author Satanu
 */
public class EvaluateResults {
    
    // Evaluate the results of CRF annotated file
    public void evaluate_CRF_results() throws IOException{
        RemoveBlankLines rbl = new RemoveBlankLines();
        ReaderWriter rw = new ReaderWriter();
        String op = "";
        
        // Obtaining the results of CRF
        String results_bn_en, results_hi_en, results_ta_en;
        results_bn_en = rbl.removeBlankLines(rw.readFile("Resources\\Output\\Output_BN_EN.txt",""));
        results_hi_en = rbl.removeBlankLines(rw.readFile("Resources\\Output\\Output_HI_EN.txt",""));
        results_ta_en = rbl.removeBlankLines(rw.readFile("Resources\\Output\\Output_TA_EN.txt",""));
        
        String gold_bn_en, gold_hi_en, gold_ta_en;
        gold_bn_en = rbl.removeBlankLines(rw.readFile("Resources\\dataset\\Test\\BN_EN_TEST.txt", ""));
        gold_hi_en = rbl.removeBlankLines(rw.readFile("Resources\\dataset\\Test\\HI_EN_TEST.txt", ""));
        gold_ta_en = rbl.removeBlankLines(rw.readFile("Resources\\dataset\\Test\\TA_EN_TEST.txt", ""));
        
        System.out.println("-----------------CRF--------------------");
        System.out.println("----------------------------------------");
        System.out.println("EVAlUATING RESULTS OF BN_EN mixed text");
        op += "\nEVAlUATING RESULTS OF BN_EN mixed text\n";
        String res = compareFiles(gold_bn_en, results_bn_en);
        System.out.println(res);
        op += res + "\n";
        System.out.println("----------------------------------------");
        
        System.out.println("----------------------------------------");
        System.out.println("EVAlUATING RESULTS OF HI_EN mixed text");
        op += "\nEVAlUATING RESULTS OF HI_EN mixed text\n";
        res = compareFiles(gold_hi_en, results_hi_en);
        System.out.println(res);
        op += res + "\n";
        System.out.println();
        System.out.println("----------------------------------------");
        
        System.out.println("----------------------------------------");
        System.out.println("EVAlUATING RESULTS OF TA_EN mixed text");
        op += "\nEVAlUATING RESULTS OF TA_EN mixed text\n";
        res = compareFiles(gold_ta_en, results_ta_en);
        System.out.println(res);
        op += res + "\n";
        System.out.println(compareFiles(gold_ta_en, results_ta_en));
        System.out.println("----------------------------------------");
        rw.writeToFile(op, "CRF Results", "txt");
    }
    
    // Evaluate the results of CRF annotated file
    public void evaluate_Stanford_results() throws IOException{
        RemoveBlankLines rbl = new RemoveBlankLines();
        ReaderWriter rw = new ReaderWriter();
        String op = "";
        
        // Obtaining the results of CRF
        String results_bn_en, results_hi_en, results_ta_en;
        results_bn_en = rbl.removeBlankLines(rw.readFile("Resources\\Output\\BN_EN_FinalOp_SP.txt",""));
        results_hi_en = rbl.removeBlankLines(rw.readFile("Resources\\Output\\HI_EN_FinalOp_SP.txt",""));
        results_ta_en = rbl.removeBlankLines(rw.readFile("Resources\\Output\\TA_EN_FinalOp_SP.txt",""));
        
        String gold_bn_en, gold_hi_en, gold_ta_en;
        gold_bn_en = rbl.removeBlankLines(rw.readFile("Resources\\dataset\\Test\\BN_EN_TEST.txt", ""));
        gold_hi_en = rbl.removeBlankLines(rw.readFile("Resources\\dataset\\Test\\HI_EN_TEST.txt", ""));
        gold_ta_en = rbl.removeBlankLines(rw.readFile("Resources\\dataset\\Test\\TA_EN_TEST.txt", ""));
        
        System.out.println("-----------------Stanford--------------------");
        System.out.println("----------------------------------------");
        System.out.println("EVAlUATING RESULTS OF BN_EN mixed text");
        op += "\nEVAlUATING RESULTS OF BN_EN mixed text\n";
        String res = compareFiles(gold_bn_en, results_bn_en);
        System.out.println(res);
        op += res + "\n";
        System.out.println("----------------------------------------");
        
        System.out.println("----------------------------------------");
        System.out.println("EVAlUATING RESULTS OF HI_EN mixed text");
        op += "\nEVAlUATING RESULTS OF HI_EN mixed text\n";
        res = compareFiles(gold_hi_en, results_hi_en);
        System.out.println(res);
        op += res + "\n";
        System.out.println("----------------------------------------");
        
        System.out.println("----------------------------------------");
        System.out.println("EVAlUATING RESULTS OF TA_EN mixed text");
        op += "\nEVAlUATING RESULTS OF TA_EN mixed text\n";
        res = compareFiles(gold_ta_en, results_ta_en);
        System.out.println(res);
        op += res + "\n";
        System.out.println("----------------------------------------");
        rw.writeToFile(op, "SP Results", "txt");
    }
    
    //Compare the gold standard file with results file
    public String compareFiles(String gold, String results){
        Scanner sc_gold, sc_res;
        String output = "";
        sc_gold = new Scanner(gold);
        sc_res = new Scanner(results);
        
        String eval = "";
        int correct, incorrect;
        correct = incorrect = 0;
                
        while(sc_res.hasNextLine()){
            String line = sc_res.nextLine();
            String gold_line = sc_gold.nextLine();
            if(line.equals("") && gold_line.equals("")){
            }
            else{
                String[] tokens = line.split("\t");
                String word = tokens[0];
                String tag = tokens[2];
                
                //System.out.println(gold_line+"\t"+gold_line.length()); 
                
                String[] goldtokens = gold_line.split("\t");
                String ans_word = goldtokens[0];
                String ans_tag = goldtokens[2];
                
                if(word.equalsIgnoreCase(ans_word)){   
                    if (ans_tag.equalsIgnoreCase(tag)){
                        correct+=1;
                    }
                    else{
                        //System.out.println(word + "\t" + tag + "\t" + ans_tag);
                        //eval += goldtokens[1] + "\t" + word + "\t" + tag + "\t" + ans_tag;
                        //eval += "\n";
                        incorrect+=1;
                    }
                }
                else{
                    System.out.println(word+"\t"+ans_word);
                    eval += word+"\t"+ans_word;
                    eval += "\n";
                }                               
            }            
        }
        Float accuracy = (float)(correct)/(correct+incorrect);
        
        eval+= "Correct:"+correct+"\nIncorrect:"+incorrect+"\nAccuracy:"+accuracy;
        //System.out.println(eval);            
        return eval;
    }       
    
}
