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
public class CmdBuilderTest {
    
    public void Runner() throws Exception {
        
        String str="",str_filebn1="",str_filebn2="",str_filebn3="",str_filebn4="",str_filebn5="",str_filebn6="";
        String rootdir = new File("").getAbsolutePath();
        str = rootdir+"\\"+"Resources\\Output\\";
        str_filebn1 = str+"Bengali_CRF_Test.txt -o "+str+"BN1.txt";
        str_filebn2 = str+"English_CRF_Test1.txt -o "+str+"EN1.txt";
        str_filebn3 = str+"Hindi_CRF_Test.txt -o "+str+"HI1.txt";
        str_filebn4 = str+"Tamil_CRF_Test.txt -o "+str+"TA1.txt";
        str_filebn5 = str+"English_CRF_Test2.txt -o "+str+"EN2.txt";
        str_filebn6 = str+"English_CRF_Test3.txt -o "+str+"EN3.txt";
        //System.out.println(str);
        ProcessBuilder builder1 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"CRF++-0.58\\CRF++-0.58\" && crf_test -m BN_Model " + str_filebn1);
        ProcessBuilder builder2 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"CRF++-0.58\\CRF++-0.58\" && crf_test -m EN_Model " + str_filebn2);
        ProcessBuilder builder3 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"CRF++-0.58\\CRF++-0.58\" && crf_test -m HI_Model " + str_filebn3);
        ProcessBuilder builder4 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"CRF++-0.58\\CRF++-0.58\" && crf_test -m TA_Model " + str_filebn4);
        ProcessBuilder builder5 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"CRF++-0.58\\CRF++-0.58\" && crf_test -m EN_Model " + str_filebn5);
        ProcessBuilder builder6 = new ProcessBuilder("cmd.exe", "/c", "dir && cd \"CRF++-0.58\\CRF++-0.58\" && crf_test -m EN_Model " + str_filebn6);
        //ProcessBuilder builder = new ProcessBuilder(
        //    "cmd.exe", "dir", "cd CRF++-0.58\\CF++-0.58", "dir");
        builder1.redirectErrorStream(true);
        builder2.redirectErrorStream(true);
        builder3.redirectErrorStream(true);
        builder4.redirectErrorStream(true);
        builder5.redirectErrorStream(true);
        builder6.redirectErrorStream(true);
        
        Process p1 = builder1.start();
        Process p2 = builder2.start();
        Process p3 = builder3.start();
        Process p4 = builder4.start();
        Process p5 = builder5.start();
        Process p6 = builder6.start();
        
        BufferedReader r1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
        BufferedReader r2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        BufferedReader r3 = new BufferedReader(new InputStreamReader(p3.getInputStream()));
        BufferedReader r4 = new BufferedReader(new InputStreamReader(p4.getInputStream()));
        BufferedReader r5 = new BufferedReader(new InputStreamReader(p5.getInputStream()));
        BufferedReader r6 = new BufferedReader(new InputStreamReader(p6.getInputStream()));
        
        String line1,line2,line3,line4,line5,line6;
        while (true) {
            line1 = r1.readLine();
            line2 = r2.readLine();
            line3 = r3.readLine();
            line4 = r4.readLine();
            line5 = r5.readLine();
            line6 = r6.readLine();
            if (line1 == null && line2==null && line3==null && line4==null && line5==null && line6==null) { break; }
            System.out.println(line1+"\t"+line2+"\t"+line3+"\t"+line4+"\t"+line5+"\t"+line6);
            //if (line1 == null){break;}
            //System.out.println(line1);
        }
    }
    
}
