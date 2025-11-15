/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Braya
 */
public class Tablero {
    private boolean[] numeroMarcados;

    public boolean[] getNumeroMarcados() {
        return numeroMarcados;
    }

    public void setNumeroMarcados(boolean[] numeroMarcados) {
        this.numeroMarcados = numeroMarcados;
    }
    
    public Tablero() {
        this.numeroMarcados = new boolean[76];
    }  
}
