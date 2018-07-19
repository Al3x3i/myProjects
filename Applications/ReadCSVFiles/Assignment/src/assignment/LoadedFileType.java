/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

/**
 *
 * @author Al3x3i
 */
public enum LoadedFileType {
    CSV("csv"),
    Excel("xlsx");

    private String value;    

    private LoadedFileType(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
}
