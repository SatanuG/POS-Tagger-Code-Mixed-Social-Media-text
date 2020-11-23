/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socialmedia_pos_v1;

/**
 *
 * @author Satanu
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


    String word="";
    String post="";
    String sentence="";
    String tags="";
    int MAX = 10000;
    int wordLenFeat[]=new int[MAX];
    
    String prevWord1[] = new String[MAX];
    String prevWord2[] = new String[MAX];

    String nextWord1[] = new String[MAX];
    String nextWord2[] = new String[MAX];
    
    String suffixFeat[]=new String[MAX];
    String prefixFeat[]=new String[MAX];
    
    String currentWordFeat[]=new String[MAX];
    String posTagFeat[]=new String[MAX];
    
    int wordHasSym[]=new int[MAX];
    int wordHasDig[]=new int[MAX];
    
    int isNounFeat[]=new int[MAX];
    int isAdjectiveFeat[]=new int[MAX];
    int isVerbFeat[]=new int[2000];
    //int isVBfinFeat[]=new int[MAX];
    //int isVBauxFeat[]=new int[MAX];
    int isPSPFeat[] = new int[MAX];
    int isPronounFeat[]=new int[MAX];
    int isConjFeat[]=new int[MAX];
    int isAdverbFeat[]=new int[MAX];
    int isDeterminerFeat[]=new int[MAX];
    int isDollarFeat[]=new int[MAX];
    int isQFeat[]=new int[MAX];
    int isUFeat[]=new int[MAX];
    int isXFeat[]=new int[MAX];
    int isPuncFeat[]=new int[MAX];
    int isParticlesFeat[]=new int[MAX];
    int isInterjectionFeat[]=new int[MAX];
    int isRDFeat[]=new int[MAX];
    int isPNP[]=new int[MAX];
    
    
    //String feature="";
    LanguageLexicons dict;
    int lang_identifier;
    
    public void train(LanguageLexicons l, int i) throws FileNotFoundException, IOException {
        // TODO code application logic here
        //File text = new File(f);
        dict = l;
        lang_identifier = i;
        Scanner scnr = new Scanner(dict.inputtext);
        String line="";
        dict = l;
        int flag=0;
        
        //featureTraining ob = new FeatureTraining();
        int lineNumber=0;
        while(scnr.hasNextLine()){
            lineNumber++;
            line=scnr.nextLine();
            
            if(line.equals("") && flag==0){
                lineProcess();
                sentence="";
                tags="";
                flag=1;
            }
            else if(!line.equals("")){
                String arr[]=line.split("\t");
                word=arr[0];
                post=arr[1];
                sentence+=word+" ";
                tags+=post+" ";
                flag=0;
             }
            
            System.out.println(lineNumber);
  
        }
        
        
    }
    
    public void wordLen(String arr[]) throws IOException{
        
        for(int i=0;i<arr.length;i++){
            
            int count=arr[i].length();
            int feature;
            if(count<=3){
                wordLenFeat[i]=0;
                
            }
            else
                wordLenFeat[i]=1;
        }
        
        
        //writeToFile(feature);
    }
    
    public void suffix(String arr[]) throws IOException{
        
        for(int i=0;i<arr.length;i++){
        
            int len=arr[i].length();
            String feature="";
            if(len>3){
                String suffix1=arr[i].substring(len-3, len);
                String suffix2=arr[i].substring(len-2, len);
                //System.out.println(suffix1+" "+suffix2);
                feature=suffix1+"\t"+suffix2;
                suffixFeat[i]=feature;
        }
        
        else
            suffixFeat[i]=null+"\t"+null;
        
        }
        
        //writeToFile(feature);
        
    }
    
    public void prefix(String arr[]) throws IOException{
        for(int i=0;i<arr.length;i++){
            
            int len=arr[i].length();
            String feature="";
            if(len>3){
                String prefix1=arr[i].substring(0, 3);
                String prefix2=arr[i].substring(0, 2);
            
                feature=prefix1+"\t"+prefix2;
                prefixFeat[i]=feature;
            
        }
        
        else
            prefixFeat[i]=null+"\t"+null;
        
        
        }
        
        
    }
    
    public void lineProcess() throws IOException{
        
        sentence=sentence.trim();
        tags=tags.trim();
        
        String arr1[]=sentence.split(" ");
        String arr2[]=tags.split(" ");
        
        wordLen(arr1);
        suffix(arr1);
        prefix(arr1);
        prev1Word(arr1);
        prev2Word(arr1);
        next1Word(arr1);
        next2Word(arr1);
        currentWord(arr1);
        posTag(arr2);
        hasSymbol(arr1);
        hasDig(arr1);
        isNoun(arr1);
        isAdjective(arr1);
        //isVB_finite(arr1);
        //isVB_aux(arr1);
        isVerb(arr1);
        isPronoun(arr1);
        isConj(arr1);
        isAdverb(arr1);
        isDeterminer(arr1);
        isDollar(arr1);
        isQ(arr1);
        isU(arr1);
        isX(arr1);
        //isPSP(arr1);
        //isRD_punc(arr1);
        //isParticles(arr1);
        //isInterjection(arr1);
        //isRD(arr1);
        //isPNP(arr1);
        
        writeCRFFile(arr1);
        
        //for(int i=0;i<arr1.length;i++){
            
            //System.out.println(isAdjectiveFeat[i]);
        //}
    }
    
    public void next1Word(String arr[]){
        
        for(int i=0;i<arr.length;i++){
             
             int j = i+1;
             if(j>=arr.length)
                 nextWord1[i]=null;
             else
                 nextWord1[i]=arr[j];
         }
    }
    
    public void next2Word(String arr[]){
        
        for(int i=0;i<arr.length;i++){
             
             int j = i+2;
             if(j>=arr.length)
                 nextWord2[i]=null;
             else
                 nextWord2[i]=arr[j];
         }
    }
    
    public void currentWord(String arr[]){
        
        for(int i=0;i<arr.length;i++){
            currentWordFeat[i]=arr[i];
        }
    }
    
    public void prev1Word(String arr[]){
        
         for(int i=0;i<arr.length;i++){
             
             int j = i-1;
             //System.out.println(i+" "+j);
             if(j < 0)
                 prevWord1[i]=null;
             else if(j >= 0)
                 prevWord1[i]=arr[j];
         }
    }
   
    public void prev2Word(String arr[]){
        
        for(int i=0;i<arr.length;i++){
             
             int j = i-2;
             //System.out.println(i+" "+j);
             if(j < 0)
                 prevWord2[i]=null;
             else if(j >= 0)
                 prevWord2[i]=arr[j];
         }
        
    }       
    
    public void posTag(String arr[]){
        
        for(int i=0;i<arr.length;i++){
            posTagFeat[i]=arr[i];
        }
    }
    
    public void hasSymbol(String arr[]){
        
        for(int i=0;i<arr.length;i++){
            
            String s = arr[i];
            int len = s.length();
            if(len>1){
                for(int j = 0;j<len;j++){
                    char c = s.charAt(j);
                
                    if((c=='.' || c==',' || c==';' || c==':' || c=='?' || c=='!' || c=='\'' || c=='/' || c=='\\' || c=='-')){
                    
                        wordHasSym[i]=1;
                        //System.out.println(s);
                        break;
                    }
                    else
                        wordHasSym[i]=0;
                }
            }
            else wordHasSym[i]=0;
        }
    }
     
    public void hasDig(String arr[]){
        
        for(int i=0;i<arr.length;i++){
            
            String s = arr[i];
            int len = s.length();
            
            for(int j = 0;j<len;j++){
                char c = s.charAt(j);
                
                if(c=='0' || c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' || c=='9'){
                    
                    wordHasDig[i]=1;
                    break;
                }
                else
                    wordHasDig[i]=0;
            }
        }
    }
     
    public void isNoun(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_noun.containsKey(arr[i]))
                isNounFeat[i]=1;
            else
                isNounFeat[i]=0;
            
        }
     }
     
    public void isAdjective(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_adj.containsKey(arr[i]))
                isAdjectiveFeat[i]=1;
            else
                isAdjectiveFeat[i]=0;
            
        }
     }
     
    /*public void isVB_finite(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_vbfinite.containsKey(arr[i]))
                isVBfinFeat[i]=1;
            else
                isVBfinFeat[i]=0;
            
        }
     }
    
    public void isVB_aux(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_vbaux.containsKey(arr[i]))
                isVBauxFeat[i]=1;
            else
                isVBauxFeat[i]=0;
            
        }
     }*/
    
    public void isVerb(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_vbaux.containsKey(arr[i]))
                isVerbFeat[i]=1;
            else if(dict.hm_vbaux.containsKey(arr[i]))
                isVerbFeat[i]=0;
            
        }
     }
    
    
    /*public void isPSP(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_psp.containsKey(arr[i]))
                isPSPFeat[i]=1;
            else
                isPSPFeat[i]=0;
            
        }
     }
    
    public void isRD_punc(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_punc.containsKey(arr[i]))
                isPuncFeat[i]=1;
            else
                isPuncFeat[i]=0;
            
        }
     }
    
    public void isParticles(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_particles.containsKey(arr[i]))
                isParticlesFeat[i]=1;
            else
                isParticlesFeat[i]=0;
            
        }
     }
    
    public void isInterjection(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_interj.containsKey(arr[i]))
                isInterjectionFeat[i]=1;
            else
                isInterjectionFeat[i]=0;
            
        }
     }
    
    public void isRD(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_RD.containsKey(arr[i]))
                isRDFeat[i]=1;
            else
                isRDFeat[i]=0;
            
        }
     }*/
    
    public void isPronoun(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_pronoun.containsKey(arr[i]))
                isPronounFeat[i]=1;
            else
                isPronounFeat[i]=0;
            
        }
     }
    
    public void isConj(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_conj.containsKey(arr[i]))
                isConjFeat[i]=1;
            else
                isConjFeat[i]=0;
            
        }
     }
    
    public void isAdverb(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_adverb.containsKey(arr[i]))
                isAdverbFeat[i]=1;
            else
                isAdverbFeat[i]=0;
            
        }
     }
    
    public void isDeterminer(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_det.containsKey(arr[i]))
                isDeterminerFeat[i]=1;
            else
                isDeterminerFeat[i]=0;
            
        }
     }
    
    public void isDollar(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_dollar.containsKey(arr[i]))
                isDollarFeat[i]=1;
            else
                isDollarFeat[i]=0;
            
        }
     }
    
    public void isQ(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_Q.containsKey(arr[i]))
                isQFeat[i]=1;
            else
                isQFeat[i]=0;
            
        }
     }
     
    public void isU(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_U.containsKey(arr[i]))
                isUFeat[i]=1;
            else
                isUFeat[i]=0;
            
        }
     }
    
    public void isX(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_X.containsKey(arr[i]))
                isXFeat[i]=1;
            else
                isXFeat[i]=0;
            
        }
     }
    
    /*public void isPNP(String arr[]) throws FileNotFoundException{
         
         for(int i=0;i<arr.length;i++){
            if(dict.hm_propnoun.containsKey(arr[i]))
                isPNP[i]=1;
            else
                isPNP[i]=0;
            
        }
     }*/
    
/*    public boolean isFound(String fileName, String word) throws FileNotFoundException{
        
        File srch = new File("C:\\Users\\DIPANAKR\\Desktop\\Satanu\\ICON15\\Lexicon_Files\\HI\\"+fileName+".txt");
        Scanner scnr = new Scanner(srch);
        String dicWord;
        while(scnr.hasNextLine()){
            dicWord = scnr.nextLine();
            if(word.equals(dicWord))
                return true;
        }
        return false;
    }*/
    
    public void writeCRFFile(String arr[]) throws FileNotFoundException, IOException{
            
        String s="Resources\\Output\\";
        
        if(lang_identifier == 0){
            s+= "Bengali_CRF_Training";
        }
        else if(lang_identifier == 1){
            s+= "English_CRF_Training";
        }
        else if(lang_identifier == 2){
            s+= "Hindi_CRF_Training";
        }
        else if(lang_identifier == 3){
            s+= "Tamil_CRF_Training";
        }
        File file = new File(s+".txt");
         
        int n=arr.length;
        FileWriter fileWritter = new FileWriter(file,true);
    	BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
         
        for(int i=0;i<n;i++){
            
            bufferWritter.write(wordLenFeat[i]+"\t"+suffixFeat[i]+"\t"+prefixFeat[i]+"\t"+prevWord2[i]+"\t"+prevWord1[i]+"\t"+currentWordFeat[i]+"\t"+nextWord1[i]+"\t"+nextWord2[i]+"\t"+wordHasSym[i]+"\t"+wordHasDig[i]+"\t"+isNounFeat[i]+"\t"+isAdjectiveFeat[i]+"\t"+isVerbFeat[i]+"\t"+isPronounFeat[i]+"\t"+isAdverbFeat[i]+"\t"+isConjFeat[i]+"\t"+isDeterminerFeat[i]+"\t"+isDollarFeat[i]+"\t"+isQFeat[i]+"\t"+isUFeat[i]+"\t"+isXFeat[i]+"\t"+posTagFeat[i]+"\n");
            //bufferWritter.write(wordLenFeat[i]+"\t"+suffixFeat[i]+"\t"+prefixFeat[i]+"\t"+prevWord2[i]+"\t"+prevWord1[i]+"\t"+currentWordFeat[i]+"\t"+nextWord1[i]+"\t"+nextWord2[i]+"\t"+wordHasSym[i]+"\t"+wordHasDig[i]+"\t"+isNounFeat[i]+"\t"+isAdjectiveFeat[i]+"\t"+isVBfinFeat[i]+"\t"+isVBauxFeat[i]+"\t"+isPronounFeat[i]+"\t"+isAdverbFeat[i]+"\t"+isConjFeat[i]+"\t"+isDeterminerFeat[i]+"\t"+isDollarFeat[i]+"\t"+isQFeat[i]+"\t"+isUFeat[i]+"\t"+isXFeat[i]+"\t"+isPSPFeat[i]+"\t"+isPuncFeat[i]+"\t"+isParticlesFeat[i]+"\t"+isInterjectionFeat[i]+"\t"+isRDFeat[i]+"\t"+isPNP[i]+"\t"+posTagFeat[i]+"\n");
             //bufferWritter.write(word[i]+"\t"+func1[i]+"\t"+func2[i]+"\t"+func3[i]+"\t"+func4[i]+"\t"+func5[i]+"\t"+prevWord[i]+"\t"+nextWord[i]+"\t"+"."+"\n" );
             }
            for(int i=0;i<23;i++){
                bufferWritter.write(".\t");
            }
            bufferWritter.write("O\n");
            //bufferWritter.write(".\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\t.\n" );
            bufferWritter.write("\n");
            bufferWritter.close();
           
     }
    
}
