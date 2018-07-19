/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Al3x3i
 */
public class FileLoaderManager {
    
    private static FileLoaderManager fileLoaderManager;
    List<Map<String, String>> loadedData = null;
    
    private FileLoaderManager(){}
    
    
    public static FileLoaderManager getInstance(){
        
        if (fileLoaderManager == null) {
            fileLoaderManager = new FileLoaderManager();
        }
        return fileLoaderManager;
    }
    
    
    public StringBuffer readDataFromFile(File file, String searchPattern){
        
        String extension = "";
        int index = file.getName().lastIndexOf('.');
        if (index > 0) {
            extension = file.getName().substring(index+1);
        }
        
        IFileReader fileReader = null;
        if (extension.equals(LoadedFileType.CSV.getValue())) {
            fileReader = new CSVFileReader();
        }else if (extension.equals(LoadedFileType.Excel.getValue())) {
            fileReader = new ExcelREader();
        }else{
            
            //TODO
        }
        if(fileReader != null){
            loadedData = fileReader.readFile(file.getPath());
            if(loadedData.size() > 0){
                return parseData(searchPattern);
            }
        }
        return null;
    }
    
    private StringBuffer parseData(String pattern){
        StringBuffer result = new StringBuffer();
        for(Map<String, String> row_item: loadedData){
            boolean hasPostCode = false;
            for (Map.Entry<String, String> entry : row_item.entrySet())
            {
                hasPostCode = entry.getValue().toString().matches(pattern);
                if(hasPostCode){
                      break;  
                }    
            }
            row_item.forEach((k,v)->result.append(" " + v));
            result.append("\n");

        }
        return result;
    }
    
}
