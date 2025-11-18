/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Carton;
import Modelo.CartonAutomaticoFactory;
import Modelo.CartonManualFactory;
import Modelo.ComandoMarcarNumero;
import Modelo.EnumModoJuego;
import Modelo.EnumTipoJuego;
import static Modelo.EnumTipoJuego.CARTON_LLENO;
import static Modelo.EnumTipoJuego.CUATRO_ESQUINAS;
import static Modelo.EnumTipoJuego.NORMAL;
import Modelo.EstrategiaCartonLleno;
import Modelo.EstrategiaCuatroEsquinas;
import Modelo.EstrategiaNormal;
import Modelo.GestorMemoria;
import Modelo.ICartonFactory;
import Modelo.IComando;
import Modelo.IEstrategiaGanador;
import Modelo.SujetoJuegoObserver;
import Modelo.Tablero;
import Modelo.Tombola;
import Vista.FrameJuego;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author kevin
 */
public class Controladorjuego extends SujetoJuegoObserver {
    private GestorMemoria gestor;
    private EnumModoJuego modoJuego;
    private EnumTipoJuego tipoJuego;
    private IEstrategiaGanador estrategiaGanador;
    private FrameJuego frameJuego;
    
    public Controladorjuego(EnumModoJuego modoJuego, EnumTipoJuego tipoJuego) {
        this.gestor = GestorMemoria.obtenerInstancia();
        this.modoJuego = modoJuego;
        this.tipoJuego = tipoJuego;
        configurarEstrategia();
    }
    
    private void configurarEstrategia() {
        switch (tipoJuego) {
            case NORMAL:
                estrategiaGanador = new EstrategiaNormal();
                break;
            case CUATRO_ESQUINAS:
                estrategiaGanador = new EstrategiaCuatroEsquinas();
                break;
            case CARTON_LLENO:
                estrategiaGanador = new EstrategiaCartonLleno();
                break;
        }
    }
    
    public void setFrameJuego(FrameJuego frame) {
        this.frameJuego = frame;
    }
    
    public EnumModoJuego getModoJuego() {
        return modoJuego;
    }
    
    public EnumTipoJuego getTipoJuego() {
        return tipoJuego;
    }
    
    /**
     * Crea un nuevo cartón según el modo de creación
     */
    public Carton crearCarton(String id) {
        ICartonFactory factory;
        if (modoJuego == EnumModoJuego.AUTOMATICO) {
            factory = new CartonAutomaticoFactory();
        } else {
            factory = new CartonManualFactory();
        }
        
        Carton carton = factory.crearCarton(id);
        gestor.agregarCarton(carton);
        return carton;
    }
    
    /**
     * Elimina un cartón
     */
    public void eliminarCarton(String id) {
       // gestor.eliminarCartonPorId(id);
    }
    
    /**
     * Marca un número en todos los cartones
     */
    public void marcarNumero(int numero) {
        if (numero < 1 || numero > 75) {
            JOptionPane.showMessageDialog(null, "El número debe estar entre 1 y 75");
            return;
        }
        
        IComando comando = new ComandoMarcarNumero(numero);
        comando.ejecutar();
        
        // Verificar ganadores
        verificarGanadores();
        
        
        notificarNumeroMarcado(numero);
    }
    
    /**
     * Desmarca un número en todos los cartones
     */
    public void desmarcarNumero(int numero) {
        IComando comando = new ComandoMarcarNumero(numero);
        comando.deshacer();
        
        // Actualizar tablero
        Tablero tablero = gestor.obtenerTablero();
        //tablero.desmarcarNumero(numero);
    }
    
    /**
     * Saca un número de la tómbola (automático o manual)
     */
    public Integer sacarNumeroTombola() {
        Tombola tombola = gestor.obtenerTombola();
        
        if (modoJuego == EnumModoJuego.AUTOMATICO) {
            Integer numero = tombola.sacarNumeroAleatorio();
            if (numero != null) {
                marcarNumero(numero);
            }
            return numero;
        } else {
            // En modo manual, el usuario ingresa el número
            return null;
        }
    }
    
    /**
     * Ingresa un número manualmente en la tómbola
     */
    public boolean ingresarNumeroManual(int numero) {
        Tombola tombola = gestor.obtenerTombola();
        if (tombola.ingresarNumeroManual(numero)) {
            marcarNumero(numero);
            return true;
        }
        return false;
    }
    
    /**
     * Verifica si hay cartones ganadores
     */
    private void verificarGanadores() {
        List<Carton> cartones = gestor.obtenerCartones();
        for (Carton carton : cartones) {
            if (estrategiaGanador.esGanador(carton)) {
                String tipoVictoria = estrategiaGanador.obtenerTipoVictoria(carton);
                notificarCartonGanador(carton.getId(), tipoVictoria);
                // Mostrar mensaje de ganador
                if (frameJuego != null) {
                    javax.swing.JOptionPane.showMessageDialog(
                        frameJuego,
                        "¡BINGO! Cartón " + carton.getId() + " ganó con: " + tipoVictoria,
                        "¡GANADOR!",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        }
    }
    
    /**
     * Reinicia el juego
     */
    public void reiniciarJuego() {
        gestor.reiniciarJuego();
        notificarJuegoReiniciado();
    }
    
    /**
     * Obtiene el último número de la tómbola
     */
    public Integer getUltimoNumero() {
        return gestor.obtenerTombola().getUltimoNumero();
    }
    
  
    public List<Carton> obtenerCartones() {
        return gestor.obtenerCartones();
    }

    public Tablero obtenerTablero() {
        return gestor.obtenerTablero();
    }
    
   
    public Tombola obtenerTombola() {
        return gestor.obtenerTombola();
    }
}
