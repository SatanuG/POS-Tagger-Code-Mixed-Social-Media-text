/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socialmedia_pos_v1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Satanu
 */
public class ReaderWriter {
        
    // Reads the file and returns a string
    public String readFile(String filename, String ext) throws IOException {
        String filepath;
        if(ext==""){
            filepath = filename;
        }
        else{
            filepath = filename+"."+ext;
        }
        String rootdir = new File("").getAbsolutePath();
        filepath = rootdir+"\\"+filepath;
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
    
    public String readFile(String filename) throws IOException {
        String filepath;
        filepath = filename;
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }    
    
    // Writes output string to file
    public void writeToFile(String str,String filename, String ext){
        String path = "";
        if(ext.equalsIgnoreCase("")){
            path = "Resources\\Output\\"+filename+".txt";
        }
        else{
            path= "Resources\\Output\\"+filename+"."+ext;
        }         
        try{
            PrintWriter out = new PrintWriter(path);
            out.println(str);
            out.close();
        }
        catch(FileNotFoundException e){System.out.println(e);}
    }
    
    // Writes output string to file
    public void writeToFileLocation(String str,String locfilename, String ext){
        String path = "";
        if(ext.equalsIgnoreCase("")){
            path = locfilename+".txt";
        }
        else{
            path= locfilename+"."+ext;
        }         
        try{
            PrintWriter out = new PrintWriter(path);
            out.println(str);
            out.close();
        }
        catch(FileNotFoundException e){System.out.println(e);}
    }
    
    // Writes output string to file
    public void appendToFile(String str,String filename, String ext) throws IOException{
        String path = "Resources\\Output\\"+filename+"."+ext;
        
        try(
                FileWriter fw = new FileWriter(path, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)){
                    out.println(str);
                } 
        catch (IOException e) {System.err.println(e);}        
    }
    
}
