/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socialmedia_pos_v1;

import java.util.Scanner;

/**
 *
 * @author Souvick
 */
public class ConversionChart {
    
    public String convert(String text){
        String op  = "";
        Scanner sc = new Scanner(text);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] tokens = line.split("\t");
            if(tokens.length==2){
                String word = tokens[0];
                String tag = tokens[1].trim();
                if (tag.equalsIgnoreCase("NOUN")){
                    tag = "N_NN";
                }
                else if (tag.equalsIgnoreCase("PROPN")){
                    tag = "N_NNP";
                }
                else if (tag.equalsIgnoreCase("VERB")){
                    tag = "V_VM";
                }
                else if (tag.equalsIgnoreCase("AUX")){
                    tag = "V_AUX";
                }
                else if (tag.equalsIgnoreCase("ADJ")){
                    tag = "JJ";
                }
                else if (tag.equalsIgnoreCase("PRON")){
                    tag = "PR_PRP";
                }
                else if (tag.equalsIgnoreCase("PUNCT")){
                    tag = "RD_PUNC";
                }
                else if (tag.equalsIgnoreCase("ADV")){
                    tag = "RB_AMN";
                }
                else if (tag.equalsIgnoreCase("DET")){
                    tag = "DT";
                }
                else if (tag.equalsIgnoreCase("CONJ")||tag.equalsIgnoreCase("SCONJ")){
                    tag = "CC";
                }
                else if (tag.equalsIgnoreCase("PART")){
                    tag = "RP";
                }
                else if (tag.equalsIgnoreCase("INTJ")){
                    tag = "RP_INJ";
                }
                else{                    
                }
                op+= word + "\t" + tag + "\n";
            }
            else{
                op+= line + "\n";
            }
        }
        return op;
    }
    
    public String convertReverse(String text){
        String op  = "";
        Scanner sc = new Scanner(text);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] tokens = line.split("\t");
            if(tokens.length==2){
                String word = tokens[0];
                String tag = tokens[1].trim();
                if (tag.equalsIgnoreCase("N_NN") || tag.equalsIgnoreCase("ADP") || tag.equalsIgnoreCase("N_NNV")) {
                    tag = "NOUN";
                }
                else if (tag.equalsIgnoreCase("N_NNP")){
                    tag = "PROPN";
                }
                else if (tag.equalsIgnoreCase("V_VM")){
                    tag = "VERB";
                }
                else if (tag.equalsIgnoreCase("V_AUX")){
                    tag = "AUX";
                }
                else if (tag.equalsIgnoreCase("JJ")){
                    tag = "ADJ";
                }
                else if (tag.equalsIgnoreCase("PR_PRP")){
                    tag = "PRON";
                }
                else if (tag.equalsIgnoreCase("RD_PUNC")){
                    tag = "PUNCT";
                }
                else if (tag.equalsIgnoreCase("RB_AMN")){
                    tag = "ADV";
                }
                else if (tag.equalsIgnoreCase("DT")){
                    tag = "DET";
                }
                else if (tag.equalsIgnoreCase("CC")){
                    tag = "CONJ";
                }
                else if (tag.equalsIgnoreCase("RP")){
                    tag = "PART";
                }
                else if (tag.equalsIgnoreCase("RP_INJ")){
                    tag = "INTJ";
                }
                else{                    
                }
                op+= word + "\t" + tag + "\n";
            }
            else{
                op+= line + "\n";
            }
        }
        return op;
    }
}
