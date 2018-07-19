/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Al3x3i
 */
public class CSVFileReader implements IFileReader{
    
    public CSVFileReader(){}

    @Override
    public List<Map<String,String>> readFile(String filePath)  {
        Reader reader;
        List<Map<String,String>> rows = new ArrayList<Map<String,String>>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            
            Iterator<CSVRecord> csvRecord =  csvParser.iterator();
            CSVRecord headers = csvRecord.next();

            for (CSVRecord row : csvParser) {
                Map<String,String> item = new HashMap<String, String>();
                
                int colNr = 0;
                for (String header : headers) {
                    String r = "";
                    try{
                        r  = row.get(colNr);
                    }catch (Exception ex){
                        
                    }
                    item.put(header, r);
                    colNr++;
                }
                rows.add(item);
            }
        } catch (Exception ex) {
            
        }
        return rows;
    }
    
}
