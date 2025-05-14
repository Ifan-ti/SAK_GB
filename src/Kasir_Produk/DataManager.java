/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kasir_Produk;

/**
 *
 * @author achmad ifan
 */
public class DataManager {
    private static DataManager instance;
    private String nilai;
    
    private DataManager() {}
    
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
    public void setNilai(String nilai) {
        this.nilai = nilai;
    }
    
    public String getNilai() {
        return nilai;
    }
}