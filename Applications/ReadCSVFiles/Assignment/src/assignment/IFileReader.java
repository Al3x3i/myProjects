/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Al3x3i
 */
public interface IFileReader {
    
    List<Map<String,String>> readFile(String filePath);
}
