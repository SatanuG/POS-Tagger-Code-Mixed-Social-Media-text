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
 * @author Souvick
 */
public class GetLangPOSFreq {
    
    public FileVariables prepareFrequencies(FileVariables fv){
        HashMap<String,Integer> hm1, hm2;
        
        hm1 = fv.hm_langcount;
        hm2 = fv.hm_postags;
        String text = fv.inputtext;
        Scanner sc = new Scanner(text);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String arr[] = line.split("\t");
            
            if(arr.length==3){
                String lang = arr[1];
                String postag = arr[2];
                // Getting language statistics
                if(hm1.containsKey(lang)){
                    int i = hm1.get(lang);
                    hm1.put(lang, i+1);
                }
                else{
                    hm1.put(lang,1);
                }
                
                //Getting pos tag statistics
                if(hm2.containsKey(postag)){
                    int i = hm2.get(postag);
                    hm2.put(postag, i+1);
                }
                else{
                    hm2.put(postag,1);
                }
            }            
        }        
        SortHashMapv2 shm= new SortHashMapv2();
        hm1 = shm.sortHashMapByValuesD(hm1);
        hm2 = shm.sortHashMapByValuesD(hm2);
        fv.hm_langcount = hm1;
        fv.hm_postags = hm2;
        //System.out.println(hm1);
        //System.out.println(hm2);
        return fv;
    }
    
       
}
