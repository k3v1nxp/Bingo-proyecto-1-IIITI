/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Braya
 */
public class SujetoJuegoObserver {
    private List<IObservadorJuego> observadores = new ArrayList<>();
    
    public void agregarObservador(IObservadorJuego obs){
        observadores.add(obs);
    }
    
    public void removerObservador(IObservadorJuego obs){
        observadores.remove(obs);
    }
    
    protected void notificarNumeroMarcado(int numero){
        for(IObservadorJuego obs : observadores){
            obs.onNumeroMarcado(numero);
        }
    }
    
    protected void notificarCartonGanador(String id, String tipo){
        for(IObservadorJuego obs : observadores){
            obs.onCartonGanador(id, tipo);
        }
    }
    
    protected void notificarJuegoReiniciado(){
        for(IObservadorJuego obs : observadores){
           obs.onJuegoReiniciado();
        }
    }
}

