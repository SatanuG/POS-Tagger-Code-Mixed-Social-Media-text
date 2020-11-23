/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socialmedia_pos_v1;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Satanu
 */
public class PrepareLexiconList {
    
    public LanguageLexicons prepareLexicons(LanguageLexicons ll){
        HashMap<String,Integer> hm1, hm2, hm3, hm4, hm5, hm6, hm7, hm8, hm9, hm10;
        HashMap<String,Integer> hm11, hm12, hm13, hm14, hm15, hm16, hm17, hm18;
        
        hm1 = ll.hm_noun;
        hm2 = ll.hm_propnoun;
        hm3 = ll.hm_vbfinite;
        hm4 = ll.hm_vbaux;
        hm5 = ll.hm_adj;
        hm6 = ll.hm_pronoun;
        hm7 = ll.hm_psp;
        hm8 = ll.hm_adverb;
        hm9 = ll.hm_det;
        hm10 = ll.hm_conj;
        hm11 = ll.hm_particles;
        hm12 = ll.hm_interj;
        hm13 = ll.hm_punc;
        hm14 = ll.hm_RD;
        hm15 = ll.hm_dollar;
        hm16 = ll.hm_Q;
        hm17 = ll.hm_U;
        hm18 = ll.hm_X;
                
        String text = ll.inputtext;
        Scanner sc = new Scanner(text);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String arr[] = line.split("\t");
            
            if(arr.length==2){
                String word = arr[0];
                String postag = arr[1];
                //Nouns
                if (postag.equalsIgnoreCase("N_NN")||postag.equalsIgnoreCase("NOUN")){
                    if(hm1.containsKey(word)){
                        int i = hm1.get(word);
                        hm1.put(word, i+1);
                    }
                    else{
                        hm1.put(word,1);
                    }
                }
                // Proper Noun
                else if (postag.equalsIgnoreCase("N_NNP")||postag.equalsIgnoreCase("PROPN")){
                    if(hm2.containsKey(word)){
                        int i = hm2.get(word);
                        hm2.put(word, i+1);
                    }
                    else{
                        hm2.put(word,1);
                    }
                }
                // Finite verbs
                else if (postag.equalsIgnoreCase("V_VM")||postag.equalsIgnoreCase("VERB")){
                    if(hm3.containsKey(word)){
                        int i = hm3.get(word);
                        hm3.put(word, i+1);
                    }
                    else{
                        hm3.put(word,1);
                    }
                }
                // Aux verb
                // Finite verbs
                else if (postag.equalsIgnoreCase("V_VAUX")){
                    if(hm4.containsKey(word)){
                        int i = hm4.get(word);
                        hm4.put(word, i+1);
                    }
                    else{
                        hm4.put(word,1);
                    }
                }
                // Adjectives
                else if (postag.equalsIgnoreCase("JJ")||postag.equalsIgnoreCase("ADJ")){
                    if(hm5.containsKey(word)){
                        int i = hm5.get(word);
                        hm5.put(word, i+1);
                    }
                    else{
                        hm5.put(word,1);
                    }
                }
                // Pronouns
                else if (postag.equalsIgnoreCase("PR_PRP")||postag.equalsIgnoreCase("PR_PRQ")||
                        postag.equalsIgnoreCase("PR_PRF")||postag.equalsIgnoreCase("PR_PRL")||
                        postag.equalsIgnoreCase("PRON")){
                    if(hm6.containsKey(word)){
                        int i = hm6.get(word);
                        hm6.put(word, i+1);
                    }
                    else{
                        hm6.put(word,1);
                    }
                }
                // Postpositions
                else if (postag.equalsIgnoreCase("PSP")){
                    if(hm7.containsKey(word)){
                        int i = hm7.get(word);
                        hm7.put(word, i+1);
                    }
                    else{
                        hm7.put(word,1);
                    }
                }
                // Adverb
                else if (postag.equalsIgnoreCase("RB_AMN")||postag.equalsIgnoreCase("RB_ALC")||postag.equalsIgnoreCase("ADV")){
                    if(hm8.containsKey(word)){
                        int i = hm8.get(word);
                        hm8.put(word, i+1);
                    }
                    else{
                        hm8.put(word,1);
                    }
                }
                // Determiner
                else if (postag.equalsIgnoreCase("DT")||postag.equalsIgnoreCase("DET")){
                    if(hm9.containsKey(word)){
                        int i = hm9.get(word);
                        hm9.put(word, i+1);
                    }
                    else{
                        hm9.put(word,1);
                    }
                }
                // Conjunction
                else if (postag.equalsIgnoreCase("CC")||postag.equalsIgnoreCase("CONJ")||postag.equalsIgnoreCase("SCONJ")){
                    if(hm10.containsKey(word)){
                        int i = hm10.get(word);
                        hm10.put(word, i+1);
                    }
                    else{
                        hm10.put(word,1);
                    }
                }
                // Particles
                else if (postag.equalsIgnoreCase("RP_RPD")||postag.equalsIgnoreCase("RP_NEG")||postag.equalsIgnoreCase("PART")){
                    if(hm11.containsKey(word)){
                        int i = hm11.get(word);
                        hm11.put(word, i+1);
                    }
                    else{
                        hm11.put(word,1);
                    }
                }
                // Interjection
                else if (postag.equalsIgnoreCase("RP_INJ")||postag.equalsIgnoreCase("INTJ")){
                    if(hm12.containsKey(word)){
                        int i = hm12.get(word);
                        hm12.put(word, i+1);
                    }
                    else{
                        hm12.put(word,1);
                    }
                }
                // Punctuation
                else if (postag.equalsIgnoreCase("RD_PUNC")){
                    if(hm13.containsKey(word)){
                        int i = hm13.get(word);
                        hm13.put(word, i+1);
                    }
                    else{
                        hm13.put(word,1);
                    }
                }
                // RD others
                else if (postag.equalsIgnoreCase("RD_RDF")||postag.equalsIgnoreCase("RD_SYM")){
                    if(hm14.containsKey(word)){
                        int i = hm14.get(word);
                        hm14.put(word, i+1);
                    }
                    else{
                        hm14.put(word,1);
                    }
                }
                // Dollar
                else if (postag.startsWith("$")){
                    if(hm15.containsKey(word)){
                        int i = hm15.get(word);
                        hm15.put(word, i+1);
                    }
                    else{
                        hm15.put(word,1);
                    }
                }
                // Q
                else if (postag.startsWith("Q")){
                    if(hm16.containsKey(word)){
                        int i = hm16.get(word);
                        hm16.put(word, i+1);
                    }
                    else{
                        hm16.put(word,1);
                    }
                }
                // U
                else if (postag.startsWith("U")){
                    if(hm17.containsKey(word)){
                        int i = hm17.get(word);
                        hm17.put(word, i+1);
                    }
                    else{
                        hm17.put(word,1);
                    }
                }
                // X
                else if (postag.startsWith("X")){
                    if(hm18.containsKey(word)){
                        int i = hm18.get(word);
                        hm18.put(word, i+1);
                    }
                    else{
                        hm18.put(word,1);
                    }
                }
            }            
        }        
        SortHashMapv2 shm= new SortHashMapv2();
        hm1 = shm.sortHashMapByValuesD(hm1);
        hm2 = shm.sortHashMapByValuesD(hm2);
        hm3 = shm.sortHashMapByValuesD(hm3);
        hm4 = shm.sortHashMapByValuesD(hm4);
        hm5 = shm.sortHashMapByValuesD(hm5);
        hm6 = shm.sortHashMapByValuesD(hm6);
        hm7 = shm.sortHashMapByValuesD(hm7);
        hm8 = shm.sortHashMapByValuesD(hm8);
        hm9 = shm.sortHashMapByValuesD(hm9);
        hm10 = shm.sortHashMapByValuesD(hm10);
        hm11 = shm.sortHashMapByValuesD(hm11);
        hm12 = shm.sortHashMapByValuesD(hm12);
        hm13 = shm.sortHashMapByValuesD(hm13);
        hm14 = shm.sortHashMapByValuesD(hm14);
        hm15 = shm.sortHashMapByValuesD(hm15);
        hm16 = shm.sortHashMapByValuesD(hm16);
        hm17 = shm.sortHashMapByValuesD(hm17);
        hm18 = shm.sortHashMapByValuesD(hm18);
        ll.hm_noun = hm1;
        ll.hm_propnoun = hm2;
        ll.hm_vbfinite = hm3;
        ll.hm_vbaux = hm4;
        ll.hm_adj = hm5;
        ll.hm_pronoun = hm6;
        ll.hm_psp = hm7;
        ll.hm_adverb = hm8;
        ll.hm_det = hm9;
        ll.hm_conj = hm10;
        ll.hm_particles = hm11;
        ll.hm_interj = hm12;
        ll.hm_punc = hm13;
        ll.hm_RD = hm14;
        ll.hm_dollar = hm15;
        ll.hm_Q = hm16;
        ll.hm_U = hm17;
        ll.hm_X = hm18;
        //System.out.println(hm1);
        //System.out.println(hm2);
        
        return ll;
    }
}
