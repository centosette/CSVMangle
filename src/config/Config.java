/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;


import java.text.DateFormat;
/**
 *
 * @author marco
 */
public final class Config {
    public static final String DEFAULT_SEPARATOR = ";";
    public static final String DEFAULT_TEXT_DELIMITER = "\"";
    public static final int SAMPLE_SIZE_SMALL = 10;
    public static final int SAMPLE_SIZE_MID = 50;
    public static final int SAMPLE_SIZE_LARGE = 1000;
    public static final int SAMPLE_SIZE_XLARGE = 10000;
    public static final int SAMPLE_SIZE_HUGE = 100000;
    public static final int DEFAULT_NEWLINE_BYTES = 2;
    
    public static final String DEFAULT_WORKING_DIR = "/tmp/";
    
    public static enum DataType {NUMERIC, TEXT, DATE, UNDEFINED, ANY}
    public static final int dateFormats[] = {DateFormat.DEFAULT, DateFormat.FULL
                                            , DateFormat.LONG, DateFormat.MEDIUM
                                            , DateFormat.SHORT};
    public static final DataType DEFAULT_DATATYPE = DataType.TEXT;
}
