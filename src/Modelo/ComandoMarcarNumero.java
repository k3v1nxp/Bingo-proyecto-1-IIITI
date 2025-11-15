/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Braya
 */
public class ComandoMarcarNumero implements IComando{
    private GestorMemoria gestor;
    private int numero;

    public ComandoMarcarNumero( int numero) {
        this.gestor = GestorMemoria.obtenerInstancia();
        this.numero = numero;
    }
    
    @Override
    public void ejecutar() {
        for(Carton carton : gestor.obtenerCartones()){
            marcarEnCarton(carton, numero, true);
        }
        
        Tablero tablero = gestor.obtenerTablero();
        tablero.getNumeroMarcados()[numero] = true;
        
        Tombola tombola = gestor.obtenerTombola();
        tombola.setUltimoNumero(numero);
        tombola.getNumerosSalidos().add(numero);
        tombola.getNumerosDisponibles().remove(Integer.valueOf(numero));
    }

    @Override
    public void deshacer() {
        for(Carton carton : gestor.obtenerCartones()) {
            marcarEnCarton(carton, numero, false);
        }
        
        Tablero tablero = gestor.obtenerTablero();
        tablero.getNumeroMarcados()[numero] = false;
    }

    private void marcarEnCarton(Carton carton, int num, boolean marcar) {
        int[][] numeros = carton.getNumerosCarton();
        boolean[][] marcados = carton.getNumeroMarcados();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (numeros[i][j] == num) {
                    marcados[i][j] = marcar;
                    return;
                }

            }
        }

    }
}
