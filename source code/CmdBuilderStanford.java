/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socialmedia_pos_v1;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 *
 * @author Satanu
 */
public class CmdBuilderStanford {
    
    public void Runner() throws Exception {
        
        String str="";
        String str_file1="",str_file2="",str_file3="",str_file4="";
        String str1="",str2="",str3="",str4="";
        String rootdir = new File("").getAbsolutePath();
        str = rootdir+"\\"+"Resources\\dataset\\Training\\";
        
        str_file1 = "java -mx1g edu.stanford.nlp.tagger.maxent.MaxentTagger -props indian-lang-model_en.props";
        str_file2 = "java -mx1g edu.stanford.nlp.tagger.maxent.MaxentTagger -props indian-lang-model_bn.props";
        str_file3 = "java -mx1g edu.stanford.nlp.tagger.maxent.MaxentTagger -props indian-lang-model_hi.props";
        str_file4 = "java -mx1g edu.stanford.nlp.tagger.maxent.MaxentTagger -props indian-lang-model_ta.props";
        
        str1 = "java -mx1g edu.stanford.nlp.tagger.maxent.MaxentTagger -props english-pos-tagger-model.tagger.props";
        str2 = "java -mx1g edu.stanford.nlp.tagger.maxent.MaxentTagger -props bengali-pos-tagger-model.tagger.props";
        str3 = "java -mx1g edu.stanford.nlp.tagger.maxent.MaxentTagger -props hindi-pos-tagger-model.tagger.props";
        str4 = "java -mx1g edu.stanford.nlp.tagger.maxent.MaxentTagger -props tamil-pos-tagger-model.tagger.props";
        
        System.out.println(str);
        ProcessBuilder builder1 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"stanford-corenlp-3.4.1\" && " + str_file1 + "&& " + str1);
        ProcessBuilder builder2 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"stanford-corenlp-3.4.1\" && " + str_file2 + "&& " + str2);
        ProcessBuilder builder3 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"stanford-corenlp-3.4.1\" && " + str_file3 + "&& " + str3);
        ProcessBuilder builder4 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"stanford-corenlp-3.4.1\" && " + str_file4 + "&& " + str4);
        //ProcessBuilder builder = new ProcessBuilder(
        //    "cmd.exe", "dir", "cd CRF++-0.58\\CF++-0.58", "dir");
        builder1.redirectErrorStream(true);
        builder2.redirectErrorStream(true);
        builder3.redirectErrorStream(true);
        builder4.redirectErrorStream(true);
        Process p1 = builder1.start();
        Process p2 = builder2.start();
        Process p3 = builder3.start();
        Process p4 = builder4.start();
        BufferedReader r1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
        BufferedReader r2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        BufferedReader r3 = new BufferedReader(new InputStreamReader(p3.getInputStream()));
        BufferedReader r4 = new BufferedReader(new InputStreamReader(p4.getInputStream()));
        String line1,line2,line3,line4;
        while (true) {
            line1 = r1.readLine();
            line2 = r2.readLine();
            line3 = r3.readLine();
            line4 = r4.readLine();
            if (line1 == null && line2==null && line3==null && line4==null) { break; }
            System.out.println(line1+"\t"+line2+"\t"+line3+"\t"+line4);
        }
    }
    
    public static void main(String args[]) throws Exception{
        CmdBuilderStanford cms = new CmdBuilderStanford();
        cms.Runner();
    }
    
}
