/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Objects;

/**
 *
 * @author Braya
 */
public class Carton {
    private String id;
    private int[][] numerosCarton;
    private boolean[][] numeroMarcados;

    public String getId() {
        return id;
    }

    public int[][] getNumerosCarton() {
        return numerosCarton;
    }

    public boolean[][] getNumeroMarcados() {
        return numeroMarcados;
    }

    public Carton(String id) {
        this.id = Objects.requireNonNull(id,"El id no puede ser un valor nulo");
        this.numerosCarton = numerosCarton;
        this.numeroMarcados = numeroMarcados;
    }
    
    

    
}
