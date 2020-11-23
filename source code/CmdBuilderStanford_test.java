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
public class CmdBuilderStanford_test {
    
    public void Runner() throws Exception {
        String str="";
        String str_file1="",str_file2="",str_file3="",str_file4="";
        String file1="",file2="",file3="",file4="";
        String opfile1="",opfile2="",opfile3="",opfile4="";
        
        String rootdir = new File("").getAbsolutePath();
                
        file1 = rootdir+"\\"+"Resources\\Output\\English_forSP.txt";
        file2 = rootdir+"\\"+"Resources\\Output\\Bengali_forSP.txt";
        file3 = rootdir+"\\"+"Resources\\Output\\Hindi_forSP.txt";
        file4 = rootdir+"\\"+"Resources\\Output\\Tamil_forSP.txt";
        
        opfile1 = rootdir + "\\" + "Resources\\Stanford_Tagged\\English_tagged.txt";
        opfile2 = rootdir + "\\" + "Resources\\Stanford_Tagged\\Bengali_tagged.txt";
        opfile3 = rootdir + "\\" + "Resources\\Stanford_Tagged\\Hindi_tagged.txt";
        opfile4 = rootdir + "\\" + "Resources\\Stanford_Tagged\\Tamil_tagged.txt";
        
        str_file1 = "java edu.stanford.nlp.tagger.maxent.MaxentTagger -model english-pos-tagger-model.tagger -textFile " + file1 +" > " + opfile1;
        str_file2 = "java edu.stanford.nlp.tagger.maxent.MaxentTagger -model bengali-pos-tagger-model.tagger -textFile " + file2 +" > " + opfile2;
        str_file3 = "java edu.stanford.nlp.tagger.maxent.MaxentTagger -model hindi-pos-tagger-model.tagger -textFile " + file3 +" > " + opfile3;
        str_file4 = "java edu.stanford.nlp.tagger.maxent.MaxentTagger -model tamil-pos-tagger-model.tagger -textFile " + file4 +" > " + opfile4;
        
        System.out.println(str);
        ProcessBuilder builder1 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"stanford-corenlp-3.4.1\" && " + str_file1);
        ProcessBuilder builder2 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"stanford-corenlp-3.4.1\" && " + str_file2);
        ProcessBuilder builder3 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"stanford-corenlp-3.4.1\" && " + str_file3);
        ProcessBuilder builder4 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"stanford-corenlp-3.4.1\" && " + str_file4);
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
        CmdBuilderStanford_test cmst = new CmdBuilderStanford_test();
        cmst.Runner();
    }
    
}
