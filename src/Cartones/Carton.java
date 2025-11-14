/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cartones;

/**
 *
 * @author UTN
 */
public class Carton {
    private String id; 
    private int[][] numeros;
    private boolean[][] marcados;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int[][] getNumeros() {
        return numeros;
    }

    public void setNumeros(int[][] numeros) {
        this.numeros = numeros;
    }

    public boolean[][] getMarcados() {
        return marcados;
    }

    public void setMarcados(boolean[][] marcados) {
        this.marcados = marcados;
    }
    
    
    public Carton(String id) {
        this.id = id;
        this.numeros = new int[5][5];
        this.marcados = new boolean[5][5];
        inicializarLibre();
    }
    
    private void inicializarLibre() {
        // La casilla central es [2][2] en la matriz 5x5
        marcados[2][2] = true;
    }
    
 

    // Método para marcar un número si se encuentra en el cartón
    public void marcarNumero(int numero) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (numeros[i][j] == numero) {
                    marcados[i][j] = true;
                }
            }
        }
    }

    // Método para desmarcar un número
    public void desmarcarNumero(int numero) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (numeros[i][j] == numero) {
                    marcados[i][j] = false;
                }
            }
        }
    }
    
    // Aquí se podrían agregar métodos para llenar los números manual o automáticamente
}
