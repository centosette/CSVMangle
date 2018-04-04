package test;


import algorithms.LineFileSplitter;
import filemanaging.CSVFile;
import filemanaging.CSVFilePool;
import filemanaging.ICSVFile;
import filemanaging.IllegalFieldNumberInLineException;
import filemanaging.IllegalReadingMethodException;
import filemanaging.IllegalWritingMethodException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import algorithms.IFileSplitter;
import algorithms.TextDelimitersMustBeEvenException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marco
 */
public class TestSplitter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, TextDelimitersMustBeEvenException {
        // TODO code application logic here
        /*
        if (args.length == 0)
        {
            System.out.println("Specify a file name to split");
            System.exit(-1);
        }
        */
        ICSVFile file;
        IFileSplitter splitter;
        CSVFilePool pool;
        
        file = new CSVFile("/home/marco/dev/csv/untitled.csv");
        
        for(;;)
        {
            try {
                String line = file.getLine();
                if (line == null) break;
                System.out.println(line);
            } catch (IllegalReadingMethodException | IllegalFieldNumberInLineException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
        
        splitter = new LineFileSplitter();
        try {
            file.rewind();
            pool = splitter.split(file, 50);
            System.out.println("Created " + pool.getFileCount() + " files.");
        } catch (IllegalReadingMethodException | IllegalFieldNumberInLineException | IllegalWritingMethodException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        
        
        
    }
    
}
