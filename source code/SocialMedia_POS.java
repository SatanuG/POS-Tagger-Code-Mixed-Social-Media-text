/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socialmedia_pos_v1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import socialmedia_pos_v1.evaluate.EvaluateResults;

/**
 *
 * @author Satanu
 */
public class SocialMedia_POS {

    int MAX_LANG = 4;
    LanguageLexicons ll[] = new LanguageLexicons[MAX_LANG];
    
    HashMap<String, String> hm_NE = new HashMap<>();
    HashMap<String, String> hm_Univ = new HashMap<>();
    HashMap<String, String> hm_Acro = new HashMap<>();
    HashMap<String, String> hm_Undef = new HashMap<>();
    HashMap<String, String> hm_Beng = new HashMap<>();
    HashMap<String, String> hm_Hindi = new HashMap<>();
    HashMap<String, String> hm_Tamil = new HashMap<>();
    HashMap<String, String> hm_Eng = new HashMap<>();
    HashMap<String, Integer> hm_Smileys = new HashMap<>();
    
    public void training() throws IOException, Exception{
        
        int MAX_FILES = 3;
        FileVariables fv[] = new FileVariables[MAX_FILES];
        
        for(int i=0; i<MAX_FILES; i++){
            fv[i] = new FileVariables();
        }
        
        for(int i=0; i<MAX_LANG; i++){
            ll[i] = new LanguageLexicons();
        }
        
        // Obtaining the content of input files
        ReaderWriterv2 rw = new ReaderWriterv2();
        String text[] = new String[MAX_FILES];
        text[0] = rw.readFile("Resources\\dataset\\Training\\BN_EN_TRAIN.txt","");
        text[1] = rw.readFile("Resources\\dataset\\Training\\HI_EN_TRAIN.txt","");
        text[2] = rw.readFile("Resources\\dataset\\Training\\TA_EN_TRAIN.txt","");
        //System.out.println(text[0]);
        
        
        for(int i=0; i<3; i++){
            // Loading the input text
            fv[i].inputtext = text[i];
            //System.out.println(fv[i].inputtext);
            
            BuildWL bwl = new BuildWL();
            
            // Getting the number of sentences for statistical purposes
            fv[i].numberOfSentences = bwl.getNumOfSentences(text[i]);
            System.out.println("Number of sentences "+fv[i].numberOfSentences);
            
            //Prepare the wordlists which are written to files
            bwl.createWordPOSPairs(text[i]);
            
            //Get the frequencies of language and postags
            GetLangPOSFreq glpf = new GetLangPOSFreq();
            fv[i] = glpf.prepareFrequencies(fv[i]);
            //System.out.println(fv[i].hm_langcount.toString());
            //System.out.println(fv[i].hm_postags.toString());
       
            // Prepare input files for CRF++
            BuildFilesForCRF bfc = new BuildFilesForCRF();
            bfc.createFiles(text[i]);
                       
        }
        
        //Removing blank lines at the start of any file,if any
        RemoveExtraLines rel = new RemoveExtraLines();
        rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\English_forCRF1.txt", "")),"English_forCRF","txt");
        rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\Bengali_forCRF1.txt", "")),"Bengali_forCRF","txt");
        rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\Hindi_forCRF1.txt", "")),"Hindi_forCRF","txt");
        rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\Tamil_forCRF1.txt", "")),"Tamil_forCRF","txt");
        
        
        String lexicon[] = new String[MAX_LANG];
        lexicon[0] = rw.readFile("Resources\\Output\\Bengali_forCRF.txt", "");
        lexicon[1] = rw.readFile("Resources\\Output\\English_forCRF.txt", "");
        lexicon[2] = rw.readFile("Resources\\Output\\Hindi_forCRF.txt", "");
        lexicon[3] = rw.readFile("Resources\\Output\\Tamil_forCRF.txt", "");
        
        // Loop for preparing lexicons for all the four languages
        // CRF++ models are also built at the same time
        for(int j = 0; j< MAX_LANG; j++){
            // Prepare the lexicon lists
            ll[j].inputtext = lexicon[j];
            PrepareLexiconList pll = new PrepareLexiconList();
            ll[j] = pll.prepareLexicons(ll[j]);
            FeatureTraining ft = new FeatureTraining();
            ft.train(ll[j],j);
        }
        
        // Obtaining CRF++ output
        CmdBuilder cm = new CmdBuilder();
        cm.Runner();
        
        // Obtaining Stanford output
        /*CmdBuilderStanford cms = new CmdBuilderStanford();
        cms.Runner();*/
        
    }    
    
    public void testing() throws IOException, Exception {
        int MAX_FILES = 3;
        FileVariables fv[] = new FileVariables[MAX_FILES];
        
        for(int i=0; i<MAX_FILES; i++){
            fv[i] = new FileVariables();
        }
        
        // Obtaining the content of input files
        ReaderWriter rw = new ReaderWriter();
        String text[] = new String[MAX_FILES];
        text[0] = rw.readFile("Resources\\dataset\\Test\\BN_EN.txt","");
        text[1] = rw.readFile("Resources\\dataset\\Test\\HI_EN.txt","");
        text[2] = rw.readFile("Resources\\dataset\\Test\\TA_EN.txt","");
        
        //Build hashmaps for non-prime language tags
        String input = rw.readFile("Resources\\Output\\Acronym.txt", "");
        hm_Acro = createHashmap(input);
        input = rw.readFile("Resources\\Output\\NE.txt", "");
        hm_NE = createHashmap(input);
        input = rw.readFile("Resources\\Output\\Univ.txt", "");
        hm_Univ = createHashmap(input);
        input = rw.readFile("Resources\\Output\\Undef.txt", "");
        hm_Undef = createHashmap(input);
        input = rw.readFile("Resources\\Output\\Bengali.txt", "");
        hm_Beng = createHashmap(input);
        input = rw.readFile("Resources\\Output\\Hindi.txt", "");
        hm_Hindi = createHashmap(input);
        input = rw.readFile("Resources\\Output\\Tamil.txt", "");
        hm_Tamil = createHashmap(input);
        input = rw.readFile("Resources\\Output\\English.txt", "");
        hm_Eng = createHashmap(input);
        input = rw.readFile("Resources\\dataset\\smileyList.txt", "");
        hm_Smileys = createHashmap_WL(input);
        
        System.out.println("Hashmaps created");
        
        for(int i=0; i<3; i++){            
            // Loading the input text
            fv[i].inputtext = text[i];
            //System.out.println(fv[i].inputtext);
            
            BuildWL bwl = new BuildWL();
            
            // Getting the number of sentences for statistical purpose
            fv[i].numberOfSentences = bwl.getNumOfSentences(text[i]);
            System.out.println("Number of sentences "+fv[i].numberOfSentences);
            
            // Prepare test files for Stanford Parser
            BuildFilesforSP_test bfs = new BuildFilesforSP_test();
            bfs.createFiles(text[i]);
            System.out.println("Raw files prepared for Stanford Parser");
            
            // Prepare test files for CRF++
            BuildFilesForCRF_test bfct = new BuildFilesForCRF_test();
            bfct.createFiles(text[i]);
            System.out.println("CRF Chunking done");
            
            
            // CRF++ output is obtained here
            FeatureTest ft = new FeatureTest();
            int counter;
            String text1 = "";
            String text2 = "";
            
            RemoveExtraLines rel = new RemoveExtraLines();
            
            if(i==0){
                counter = 0;
                rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\English_forCRF_test_raw.txt", "")),"English_forCRF_test","txt");
                rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\Bengali_forCRF_test_raw.txt", "")),"Bengali_forCRF_test","txt");
                text1 = rw.readFile("Resources\\Output\\Bengali_forCRF_test","txt");
                text2 = rw.readFile("Resources\\Output\\English_forCRF_test","txt");
                System.out.println(counter);
                ft.test(text1,ll[0],counter);
                counter++;
                System.out.println(counter);
                ft.test(text2,ll[1], counter);
            }
            else if(i==1){
                counter = 2;     
                rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\English_forCRF_test_raw.txt", "")),"English_forCRF_test","txt");
                rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\Hindi_forCRF_test_raw.txt", "")),"Hindi_forCRF_test","txt");
                text1 = rw.readFile("Resources\\Output\\Hindi_forCRF_test","txt");
                text2 = rw.readFile("Resources\\Output\\English_forCRF_test","txt");
                System.out.println(counter);
                ft.test(text1,ll[2],counter);
                counter++;
                System.out.println(counter);
                ft.test(text2,ll[1], counter);
            }
            else if(i==2){
                counter = 4;
                rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\English_forCRF_test_raw.txt", "")),"English_forCRF_test","txt");
                rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\Tamil_forCRF_test_raw.txt", "")),"Tamil_forCRF_test","txt");                
                text1 = rw.readFile("Resources\\Output\\Tamil_forCRF_test","txt");
                text2 = rw.readFile("Resources\\Output\\English_forCRF_test","txt");
                System.out.println(counter);
                ft.test(text1,ll[3], counter);
                counter++;
                System.out.println(counter);
                ft.test(text2,ll[1], counter);                
            }            
            else{                
            }       
        }
        
        //Removing blank lines at the start of any file,if any from Stanford files
        RemoveExtraLines rel = new RemoveExtraLines();
        rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\English_forSP_test_raw.txt", "")),"English_forSP","txt");
        rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\Bengali_forSP_test_raw.txt", "")),"Bengali_forSP","txt");
        rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\Hindi_forSP_test_raw.txt", "")),"Hindi_forSP","txt");
        rw.writeToFile(rel.removestartingBlanks(rw.readFile("Resources\\Output\\Tamil_forSP_test_raw.txt", "")),"Tamil_forSP","txt");
        System.out.println("Stanford files written");
        
        CmdBuilderTest cmt = new CmdBuilderTest();
        cmt.Runner();
        
        // Building the result files without space
        String result[] = new String[6];
        result[0]=rw.readFile("Resources\\Output\\BN1","txt");
        result[1]=rw.readFile("Resources\\Output\\EN1","txt");
        result[2]=rw.readFile("Resources\\Output\\HI1","txt");
        result[3]=rw.readFile("Resources\\Output\\EN2","txt");
        result[4]=rw.readFile("Resources\\Output\\TA1","txt");
        result[5]=rw.readFile("Resources\\Output\\EN3","txt");
        
        Scanner scnr;
        for(int i=0;i<6;i++){
            scnr=new Scanner(result[i]);
            String line="", res="";
            int lineNum=0;
            while(scnr.hasNextLine()){
                
                line=scnr.nextLine();
                lineNum++;
                
                if(!line.equals("") && !line.startsWith(".")){
                    String arr[]=line.split("\t");
                    res+= arr[7]+"\t"+arr[23]+"\n";
                }
                //System.out.println(lineNum+" "+line);
            }
            if(i==0){
                rw.writeToFile(res, "CRF_BN_res", "txt");
            }
            else if(i==1){
                rw.writeToFile(res, "CRF_BN_EN_res", "txt");
            }
            else if(i==2){
                rw.writeToFile(res, "CRF_HI_res", "txt");
            }
            else if(i==3){
                rw.writeToFile(res, "CRF_HI_EN_res", "txt");
            }
            else if(i==4){
                rw.writeToFile(res, "CRF_TA_res", "txt");
            }
            else if(i==5){
                rw.writeToFile(res, "CRF_TA_EN_res", "txt");
            }            
        }
        
        /*
        // Sending the English words to Stanford Parser for tagging
        ParserTagging pt = new ParserTagging();
        String en_tagged = pt.postag(rw.readFile("Resources\\Output\\English_forSP_test","txt"));
        rw.writeToFileLocation(en_tagged, "Resources\\Stanford_Tagged\\English_Stanford_Tagged", "txt");
        */
        
        //new loop for post processing
        for(int i=0; i<3; i++){
            // Processing and Writing output of CRF
            if (i==0)
                processOutputCRF_BN_EN(text[i]);
            else if (i==1)
                processOutputCRF_HI_EN(text[i]);
            else if (i==2)
                processOutputCRF_TA_EN(text[i]);
            else{}
        }
        
        CmdBuilderStanford_test cmst = new CmdBuilderStanford_test();
        cmst.Runner();
        
        // Processing and Writing output of CRF
        processOutputSP(text);            
        
    }
    
    public void processOutputCRF_BN_EN(String text) throws IOException{
        ReaderWriter rw = new ReaderWriter();
        
        // Reading the CRF output files
        String CRF_BN = rw.readFile("Resources\\Output\\CRF_BN_res", "txt");
        String CRF_EN = rw.readFile("Resources\\Output\\CRF_BN_EN_res", "txt");
            
        // Preparing the output
        String op = "";
        Scanner sc_inp = new Scanner(text);
        Scanner sc_crf_bn = new Scanner(CRF_BN);
        Scanner sc_crf_en = new Scanner(CRF_EN);
        
        while(sc_inp.hasNextLine()){
            String line = sc_inp.nextLine();
            String[] tokens = line.split("\t");
            if(tokens.length==2){
                String word = tokens[0];
                String lang = tokens[1];
                String postag = "";
                if(lang.equalsIgnoreCase("en")||lang.equalsIgnoreCase("en+bn_suffix")||lang.equalsIgnoreCase("E")){
                    if(sc_crf_en.hasNextLine()){
                        String line2 = sc_crf_en.nextLine();
                        String[] tokens2 = line2.split("\t");
                        String word2 = tokens2[0];
                        String postag2 = tokens2[1];
                        if(word.equalsIgnoreCase(word2)){
                            postag = postag2;
                        }
                        else{
                            postag = "Null";
                        }
                    }
                }
                else if(lang.equalsIgnoreCase("bn")){
                    if(sc_crf_bn.hasNextLine()){
                        String line2 = sc_crf_bn.nextLine();
                        String[] tokens2 = line2.split("\t");
                        String word2 = tokens2[0];
                        String postag2 = tokens2[1];
                        if(word.equalsIgnoreCase(word2)){
                            postag = postag2;
                        }
                        else{
                            postag = "Null";
                        }
                    }
                }
                else if(lang.equalsIgnoreCase("hi")){
                    if(hm_Hindi.containsKey(word)){
                        postag = hm_Hindi.get(word);
                    }
                    else{
                        postag = "N_NN";
                    }
                }
                else if(lang.equalsIgnoreCase("T")){
                    if(hm_Tamil.containsKey(word)){
                        postag = hm_Tamil.get(word);
                    }
                    else{
                        postag = "N_NN";
                    }
                }
                else if(lang.equalsIgnoreCase("ne")||lang.equalsIgnoreCase("ne+en_suffix")||lang.equalsIgnoreCase("ne+bn_suffix")||lang.equalsIgnoreCase("N")){
                    if(hm_NE.containsKey(word)){
                        postag = hm_NE.get(word);
                    }
                    else{
                        postag = "N_NNP";
                    }
                }
                else if(lang.equalsIgnoreCase("univ")||lang.equalsIgnoreCase("R")){
                    if(hm_Univ.containsKey(word)){
                        postag = hm_Univ.get(word);
                    }
                    else{
                        if(hm_Smileys.containsKey(word) || word.contains(":)")){
                            postag = "E";
                        }
                        else if (word.equalsIgnoreCase("&")){
                            postag = "CC";
                        }
                        else if (word.contains("www.") || word.contains("http")){
                            postag = "U";
                        }
                        else if (word.contains("#")){
                            postag = "#";
                        }
                        else if (word.contains("@")){
                            postag = "@";
                        }
                        else if (word.matches(".*\\d+.*")){
                            postag = "$";
                        }
                        else if (word.contains("ha") || word.contains("he") || word.contains("hmm") || word.contains("ahh") || word.contains("lol") || word.contains("uff") ){
                            postag = "RP_INJ";
                        }
                        else 
                            postag = "RD_PUNC";
                    }
                }
                else if(lang.equalsIgnoreCase("acro")||lang.equalsIgnoreCase("acro+en_suffix")||lang.equalsIgnoreCase("acro+bn_suffix")){
                    if(hm_Acro.containsKey(word)){
                        postag = hm_Acro.get(word);
                    }
                    else{
                        postag = "N_NNP";
                    }
                }
                else {
                    if(hm_Undef.containsKey(word)){
                        postag = hm_Undef.get(word);
                    }
                    else{
                        if(hm_Smileys.containsKey(word)){
                            postag = "E";
                        }
                        else if(word.contains("#")){
                            postag = "#";
                        }
                        else
                            postag = "N_NNP";
                    }
                }
                postag = convertTag(postag);
                op+= word + "\t" + postag + "\n";
            }
            else{
                op+=line;
            }
        }
        rw.writeToFile(op, "BN_EN_FinalOp_CRF", "");        
    }
    
    public void processOutputCRF_HI_EN(String text) throws IOException{
        ReaderWriter rw = new ReaderWriter();
        
        // Reading the CRF output files
        String CRF_HI = rw.readFile("Resources\\Output\\CRF_HI_res", "txt");
        String CRF_EN = rw.readFile("Resources\\Output\\CRF_HI_EN_res", "txt");
            
        // Preparing the output
        String op = "";
        Scanner sc_inp = new Scanner(text);
        Scanner sc_crf_hi = new Scanner(CRF_HI);
        Scanner sc_crf_en = new Scanner(CRF_EN);
        
        while(sc_inp.hasNextLine()){
            String line = sc_inp.nextLine();
            String[] tokens = line.split("\t");
            if(tokens.length==2){
                String word = tokens[0];
                String lang = tokens[1];
                String postag = "";
                if(lang.equalsIgnoreCase("en")||lang.equalsIgnoreCase("en+bn_suffix")||lang.equalsIgnoreCase("E")){
                    if(sc_crf_en.hasNextLine()){
                        String line2 = sc_crf_en.nextLine();
                        String[] tokens2 = line2.split("\t");
                        String word2 = tokens2[0];
                        String postag2 = tokens2[1];
                        if(word.equalsIgnoreCase(word2)){
                            postag = postag2;
                        }
                        else{
                            postag = "Null";
                        }
                    }
                }
                else if(lang.equalsIgnoreCase("bn")){
                    if(hm_Beng.containsKey(word)){
                        postag = hm_Beng.get(word);
                    }
                    else{
                        postag = "N_NN";
                    }                    
                }
                else if(lang.equalsIgnoreCase("hi")){
                    if(sc_crf_hi.hasNextLine()){
                        String line2 = sc_crf_hi.nextLine();
                        String[] tokens2 = line2.split("\t");
                        String word2 = tokens2[0];
                        String postag2 = tokens2[1];
                        if(word.equalsIgnoreCase(word2)){
                            postag = postag2;
                        }
                        else{
                            postag = "Null";
                        }
                    }
                }
                else if(lang.equalsIgnoreCase("T")){
                    if(hm_Tamil.containsKey(word)){
                        postag = hm_Tamil.get(word);
                    }
                    else{
                        postag = "N_NN";
                    }
                }
                else if(lang.equalsIgnoreCase("ne")||lang.equalsIgnoreCase("ne+en_suffix")||lang.equalsIgnoreCase("ne+bn_suffix")||lang.equalsIgnoreCase("N")){
                    if(hm_NE.containsKey(word)){
                        postag = hm_NE.get(word);
                    }
                    else{
                        postag = "N_NNP";
                    }
                }
                else if(lang.equalsIgnoreCase("univ")||lang.equalsIgnoreCase("R")){
                    if(hm_Univ.containsKey(word)){
                        postag = hm_Univ.get(word);
                    }
                    else{
                        if(hm_Smileys.containsKey(word) || word.contains(":)")){
                            postag = "E";
                        }
                        else if (word.equalsIgnoreCase("&")){
                            postag = "CC";
                        }
                        else if (word.contains("www.") || word.contains("http")){
                            postag = "U";
                        }
                        else if (word.contains("#")){
                            postag = "#";
                        }
                        else if (word.contains("@")){
                            postag = "@";
                        }
                        else if (word.matches(".*\\d+.*")){
                            postag = "$";
                        }
                        else if (word.contains("ha") || word.contains("he") || word.contains("hmm") || word.contains("ahh") || word.contains("lol") || word.contains("uff") ){
                            postag = "RP_INJ";
                        }
                        else 
                            postag = "RD_PUNC";
                    }
                }
                else if(lang.equalsIgnoreCase("acro")||lang.equalsIgnoreCase("acro+en_suffix")||lang.equalsIgnoreCase("acro+bn_suffix")){
                    if(hm_Acro.containsKey(word)){
                        postag = hm_Acro.get(word);
                    }
                    else{
                        postag = "N_NNP";
                    }
                }
                else {
                    if(hm_Undef.containsKey(word)){
                        postag = hm_Undef.get(word);
                    }
                    else{
                        if(hm_Smileys.containsKey(word)){
                            postag = "E";
                        }
                        else if(word.contains("#")){
                            postag = "#";
                        }
                        else
                            postag = "N_NNP";
                    }
                }
                postag = convertTag(postag);
                op+= word + "\t" + postag + "\n";
            }
            else{
                op+=line;
            }
        }
        rw.writeToFile(op, "HI_EN_FinalOp_CRF", "");      
    }
     
    public void processOutputCRF_TA_EN(String text) throws IOException{
        ReaderWriter rw = new ReaderWriter();
        
        // Reading the CRF output files
        String CRF_TA = rw.readFile("Resources\\Output\\CRF_TA_res", "txt");
        String CRF_EN = rw.readFile("Resources\\Output\\CRF_TA_EN_res", "txt");
            
        // Preparing the output
        String op = "";
        Scanner sc_inp = new Scanner(text);
        Scanner sc_crf_ta = new Scanner(CRF_TA);
        Scanner sc_crf_en = new Scanner(CRF_EN);
        
        while(sc_inp.hasNextLine()){
            String line = sc_inp.nextLine();
            String[] tokens = line.split("\t");
            if(tokens.length==2){
                String word = tokens[0];
                String lang = tokens[1];
                String postag = "";
                if(lang.equalsIgnoreCase("en")||lang.equalsIgnoreCase("en+bn_suffix")||lang.equalsIgnoreCase("E")){
                    if(sc_crf_en.hasNextLine()){
                        String line2 = sc_crf_en.nextLine();
                        String[] tokens2 = line2.split("\t");
                        String word2 = tokens2[0];
                        String postag2 = tokens2[1];
                        if(word.equalsIgnoreCase(word2)){
                            postag = postag2;
                        }
                        else{
                            postag = "Null";
                        }
                    }
                }
                else if(lang.equalsIgnoreCase("bn")){
                    if(hm_Beng.containsKey(word)){
                        postag = hm_Beng.get(word);
                    }
                    else{
                        postag = "N_NN";
                    }                    
                }
                else if(lang.equalsIgnoreCase("hi")){
                    if(hm_Hindi.containsKey(word)){
                        postag = hm_Hindi.get(word);
                    }
                    else{
                        postag = "N_NN";
                    }
                }
                else if(lang.equalsIgnoreCase("T")){
                    if(sc_crf_ta.hasNextLine()){
                        String line2 = sc_crf_ta.nextLine();
                        String[] tokens2 = line2.split("\t");
                        String word2 = tokens2[0];
                        String postag2 = tokens2[1];
                        if(word.equalsIgnoreCase(word2)){
                            postag = postag2;
                        }
                        else{
                            postag = "Null";
                        }
                    }                    
                }
                else if(lang.equalsIgnoreCase("ne")||lang.equalsIgnoreCase("ne+en_suffix")||lang.equalsIgnoreCase("ne+bn_suffix")||lang.equalsIgnoreCase("N")){
                    if(hm_NE.containsKey(word)){
                        postag = hm_NE.get(word);
                    }
                    else{
                        postag = "N_NNP";
                    }
                }
                else if(lang.equalsIgnoreCase("univ")||lang.equalsIgnoreCase("R")){
                    if(hm_Univ.containsKey(word)){
                        postag = hm_Univ.get(word);
                    }
                    else{
                        if(hm_Smileys.containsKey(word) || word.contains(":)")){
                            postag = "E";
                        }
                        else if (word.equalsIgnoreCase("&")){
                            postag = "CC";
                        }
                        else if (word.contains("www.") || word.contains("http")){
                            postag = "U";
                        }
                        else if (word.contains("#")){
                            postag = "#";
                        }
                        else if (word.contains("@")){
                            postag = "@";
                        }
                        else if (word.matches(".*\\d+.*")){
                            postag = "NUM";
                        }
                        else if (word.contains("ha") || word.contains("he") || word.contains("hmm") || word.contains("ahh") || word.contains("lol") || word.contains("uff") ){
                            postag = "X";
                        }
                        else 
                            postag = "RD_PUNC";
                    }
                }
                else if(lang.equalsIgnoreCase("acro")||lang.equalsIgnoreCase("acro+en_suffix")||lang.equalsIgnoreCase("acro+bn_suffix")){
                    if(hm_Acro.containsKey(word)){
                        postag = hm_Acro.get(word);
                    }
                    else{
                        postag = "N_NNP";
                    }
                }
                else {
                    if(hm_Undef.containsKey(word)){
                        postag = hm_Undef.get(word);
                    }
                    else{
                        if(hm_Smileys.containsKey(word)){
                            postag = "E";
                        }
                        else if(word.contains("#")){
                            postag = "#";
                        }
                        else
                            postag = "N_NNP";
                    }
                }
                postag = convertTagReverse(postag);
                op+= word + "\t" + postag + "\n";
            }
            else{
                op+=line;
            }
        }
        rw.writeToFile(op, "TA_EN_FinalOp_CRF", "");   
    }
    
    public void processOutputSP(String text[]) throws IOException{
        ReaderWriter rw = new ReaderWriter();
        for(int i = 0; i<3; i++){
            // Reading the CRF output files
            String SP_BN = rw.readFile("Resources\\Stanford_Tagged\\Bengali_tagged", "txt");
            String SP_HI = rw.readFile("Resources\\Stanford_Tagged\\Hindi_tagged", "txt");
            String SP_TA = rw.readFile("Resources\\Stanford_Tagged\\Tamil_tagged", "txt");
            String SP_EN = rw.readFile("Resources\\Stanford_Tagged\\English_tagged", "txt");
            
            // Preparing the output
            String op = "";
            Scanner sc_inp = new Scanner(text[i]);
            Scanner sc_crf_bn = new Scanner(SP_BN);
            Scanner sc_crf_hi = new Scanner(SP_HI);
            Scanner sc_crf_ta = new Scanner(SP_TA);
            Scanner sc_crf_en = new Scanner(SP_EN);
            
            while(sc_inp.hasNextLine()){
                String line = sc_inp.nextLine();
                String[] tokens = line.split("\t");
                if(tokens.length==2){
                    String word = tokens[0];
                    String lang = tokens[1];
                    String postag = "";
                    if(lang.equalsIgnoreCase("en")||lang.equalsIgnoreCase("en+bn_suffix")||lang.equalsIgnoreCase("E")){
                        if(sc_crf_en.hasNextLine()){
                            String line2 = sc_crf_en.nextLine();
                            String[] tokens2 = line2.split("/");
                            String word2 = tokens2[0];
                            String postag2 = tokens2[1];
                            if(word.equalsIgnoreCase(word2)){
                                postag = postag2;
                            }
                            else{
                                postag = "Null";
                            }
                        }
                    }
                    else if(lang.equalsIgnoreCase("bn")){
                        if(sc_crf_bn.hasNextLine()){
                            String line2 = sc_crf_bn.nextLine();
                            String[] tokens2 = line2.split("/");
                            String word2 = tokens2[0];
                            String postag2 = tokens2[1];
                            if(word.equalsIgnoreCase(word2)){
                                postag = postag2;
                            }
                            else{
                                postag = "Null";
                            }
                        }
                    }
                    else if(lang.equalsIgnoreCase("hi")){
                        if(sc_crf_hi.hasNextLine()){
                            String line2 = sc_crf_hi.nextLine();
                            String[] tokens2 = line2.split("/");
                            String word2 = tokens2[0];
                            String postag2 = tokens2[1];
                            if(word.equalsIgnoreCase(word2)){
                                postag = postag2;
                            }
                            else{
                                postag = "Null";
                            }
                        }
                    }
                    else if(lang.equalsIgnoreCase("T")){
                        if(sc_crf_ta.hasNextLine()){
                            String line2 = sc_crf_ta.nextLine();
                            String[] tokens2 = line2.split("/");
                            String word2 = tokens2[0];
                            String postag2 = tokens2[1];
                            if(word.equalsIgnoreCase(word2)){
                                postag = postag2;
                            }
                            else{
                                postag = "Null";
                            }
                        }
                    }
                    else if(lang.equalsIgnoreCase("ne")||lang.equalsIgnoreCase("ne+en_suffix")||lang.equalsIgnoreCase("ne+bn_suffix")||lang.equalsIgnoreCase("N")){
                        if(hm_NE.containsKey(word)){
                            postag = hm_NE.get(word);
                        }
                        else{
                            postag = "N_NNP";
                        }
                    }
                    else if(lang.equalsIgnoreCase("univ")||lang.equalsIgnoreCase("R")){
                        if(hm_Univ.containsKey(word)){
                            postag = hm_Univ.get(word);
                        }
                        else{
                            if(hm_Smileys.containsKey(word) || word.contains(":)")){
                                postag = "E";
                            }
                            else if (word.equalsIgnoreCase("&")){
                                postag = "CC";
                            }
                            else if (word.contains("www.") || word.contains("http")){
                                postag = "U";
                            }
                            else if (word.contains("#")){
                                postag = "#";
                            }
                            else if (word.contains("@")){
                                postag = "@";
                            }
                            else if (word.matches(".*\\d+.*")){
                                postag = "$";
                            }
                            else if (word.contains("ha") || word.contains("he") || word.contains("hmm") || word.contains("ahh") || word.contains("lol") || word.contains("uff") ){
                                postag = "RP_INJ";
                            }
                            else 
                                postag = "RD_PUNC";
                        }
                    }
                    else if(lang.equalsIgnoreCase("acro")||lang.equalsIgnoreCase("acro+en_suffix")||lang.equalsIgnoreCase("acro+bn_suffix")){
                        if(hm_Acro.containsKey(word)){
                            postag = hm_Acro.get(word);
                        }
                        else{
                            postag = "N_NNP";
                        }
                    }
                    else {
                        if(hm_Undef.containsKey(word)){
                            postag = hm_Undef.get(word);
                        }
                        else{
                            if(hm_Smileys.containsKey(word)){
                                postag = "E";
                            }
                            else if(word.contains("#")){
                                postag = "#";
                            }
                            else
                                postag = "N_NNP";
                        }
                    }
                    op+= word + "\t" + postag + "\n";
                    //System.out.println(op);
                }
                else{
                    op+=line;
                }
            }
            ConversionChart cc = new ConversionChart();
            if(i==0){
                op = cc.convert(op);
                rw.writeToFile(op, "BN_EN_FinalOp_SP", "");                
            }
            else if(i==1){
                op = cc.convert(op);
                rw.writeToFile(op, "HI_EN_FinalOp_SP", "");                
            }
            else if(i==2){
                cc.convertReverse(op);
                rw.writeToFile(op, "TA_EN_FinalOp_SP", "");                
            }
            else{
                System.err.println("Error. Invalid file");
            }
        }
    }
    
    public HashMap<String,String> createHashmap(String input){
        Scanner sc = new Scanner(input);
        HashMap<String, String> hm = new HashMap<>();
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            if(tokens.length==2){
                String word = tokens[0];
                String postag = tokens[1];
                if(hm.containsKey(line)){                
                }
                else{
                    hm.put(word, postag);
                }
            }
        }
        return hm;
    }
    
    public HashMap<String,Integer> createHashmap_WL(String input){
        Scanner sc = new Scanner(input);
        HashMap<String, Integer> hm = new HashMap<>();
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String word;
            word = line.trim();
            if(hm.containsKey(word)){                
            }
            else{
                hm.put(word, 1);
            }
        }        
        return hm;
    }
    
    public String convertTag(String tag){
        if (tag.equalsIgnoreCase("NOUN"))
            return "N_NN";
        if (tag.equalsIgnoreCase("PROPN"))
            return "N_NNP";
        if (tag.equalsIgnoreCase("VERB"))
            return "V_VM";
        if (tag.equalsIgnoreCase("AUX"))
            return "V_AUX";
        if (tag.equalsIgnoreCase("ADJ"))
            return "JJ";
        if (tag.equalsIgnoreCase("PRON"))
            return "PR_PRP";
        if (tag.equalsIgnoreCase("PUNCT"))
            return "RD_PUNC";
        if (tag.equalsIgnoreCase("ADV"))
            return "RB_AMN";
        if (tag.equalsIgnoreCase("DET"))
            return "DT";
        if (tag.equalsIgnoreCase("CONJ"))
            return "CC";
        if (tag.equalsIgnoreCase("PART"))
            return "RP";
        if (tag.equalsIgnoreCase("INTJ"))
            return "RP_INJ";
        return tag;
    }
    
    public String convertTagReverse(String tag){
        if (tag.equalsIgnoreCase("N_NN") || tag.equalsIgnoreCase("ADP") || tag.equalsIgnoreCase("N_NNV")) 
            return "NOUN";
        if (tag.equalsIgnoreCase("N_NNP"))
            return "PROPN";
        if (tag.equalsIgnoreCase("V_VM"))
            return "VERB";
        if (tag.equalsIgnoreCase("V_AUX"))
            return "AUX";
        if (tag.equalsIgnoreCase("JJ"))
            return "ADJ";
        if (tag.equalsIgnoreCase("PR_PRP"))
            return "PRON";
        if (tag.equalsIgnoreCase("RD_PUNC"))
            return "PUNCT";
        if (tag.equalsIgnoreCase("RB_AMN"))
            return "ADV";
        if (tag.equalsIgnoreCase("DT"))
            return "DET";
        if (tag.equalsIgnoreCase("CC"))
            return "CONJ";
        if (tag.equalsIgnoreCase("RP"))
            return "PART";
        if (tag.equalsIgnoreCase("RP_INJ"))
            return "INTJ";
        return tag;
    }
    
    public static void main(String[] args) throws IOException, Exception {
        // TODO code application logic here
        SocialMedia_POS sm = new SocialMedia_POS();
        
        sm.training();
        //System.out.println("----------------------------");
        //System.out.println("-----End of training--------");
        //System.out.println("----------------------------");
        
        sm.testing();
        
        // Evaluating results
        //EvaluateResults er = new EvaluateResults();
        //er.evaluate_CRF_results();
        //er.evaluate_Stanford_results();
        
    }
    
    
}
