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
import Modelo.Servicio.ServicioCartones;
import Modelo.Servicio.ServicioJuego;
import Modelo.Servicio.ServicioTombola;
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
    private GestorMemoria repositorio;;
    private EnumModoJuego modoJuego;
    private EnumTipoJuego tipoJuego;
    private IEstrategiaGanador estrategiaGanador;
    private FrameJuego frameJuego;
    private ServicioJuego servicioJuego;
    private ServicioCartones servicioCartones;
    private ServicioTombola servicioTombola;
    
    public Controladorjuego(EnumModoJuego modoJuego, EnumTipoJuego tipoJuego) {
        this.repositorio = GestorMemoria.obtenerInstancia();
        this.modoJuego = modoJuego;
        this.tipoJuego = tipoJuego;
        configurarEstrategia();
        inicializarServicios();
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
    
    private void inicializarServicios() {
        servicioJuego = new ServicioJuego(repositorio, estrategiaGanador);
        servicioCartones = new ServicioCartones(repositorio);
        servicioTombola = new ServicioTombola(repositorio);
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
        return servicioCartones.crearCarton(id, modoJuego);
    }
    
    /**
     * Elimina un cartón
     */
    public void eliminarCarton(String id) {
        servicioCartones.eliminarCarton(id);
        notificarCartonEliminado(id);
    }
    
    /**
     * Marca un número en todos los cartones
     */
    public String marcarNumero(int numero) {
        if (!servicioJuego.validarNumero(numero)) {
            return "El número debe estar entre 1 y 75";
        }
        
        servicioJuego.marcarNumero(numero);
        
        // Verificar ganadores
        List<ServicioJuego.ResultadoGanador> ganadores = servicioJuego.verificarGanadores();
        for (ServicioJuego.ResultadoGanador ganador : ganadores) {
            notificarCartonGanador(ganador.getIdCarton(), ganador.getTipoVictoria());
        }
        
        notificarNumeroMarcado(numero);
        return null; // Exitoso
    }
    
    /**
     * Desmarca un número en todos los cartones
     */
    public void desmarcarNumero(int numero) {
        servicioJuego.desmarcarNumero(numero);
        notificarNumeroDesmarcado(numero);
    }
    
    /**
     * Saca un número de la tómbola (automático o manual)
     */
    public Integer sacarNumeroTombola() {
        if (modoJuego == EnumModoJuego.AUTOMATICO) {
            Integer numero = servicioTombola.generarNumeroAleatorio();
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
    public String ingresarNumeroManual(int numero) {
        if (!servicioTombola.ingresarNumeroManual(numero)) {
            return "El número no es válido o ya salió";
        }
        return marcarNumero(numero); // Retorna null si es exitoso, mensaje de error si no
    }  
    /**
     * Reinicia el juego
     */
    public void reiniciarJuego() {
        servicioJuego.reiniciarJuego();
        notificarJuegoReiniciado();
    }
    
    public void limpiarCartones() {
        servicioJuego.limpiarCartones();
        notificarCartonesLimpiados();
    }
       
    public void reiniciarTablero() {
        servicioJuego.reiniciarTablero();
        notificarTableroReiniciado();
    }
    
   public void reiniciarTombola() {
        servicioTombola.reiniciarTombola();
        notificarTombolaReiniciada();
    }
    /**
     * Obtiene el último número de la tómbola
     */
       public Integer getUltimoNumero() {
        return servicioTombola.obtenerUltimoNumero();
    }
    
    /**
     * Obtiene todos los cartones
     */
    public List<Carton> obtenerCartones() {
        return servicioCartones.obtenerCartones();
    }

    /**
     * Obtiene el tablero
     */
    public Tablero obtenerTablero() {
        return repositorio.obtenerTablero();
    }
    
    /**
     * Obtiene la tómbola
     */
    public Tombola obtenerTombola() {
        return repositorio.obtenerTombola();
    }
    
    /**
     * Obtiene los servicios (para acceso directo si es necesario)
     */
    public ServicioJuego getServicioJuego() {
        return servicioJuego;
    }
    
    public ServicioCartones getServicioCartones() {
        return servicioCartones;
    }
    
    public ServicioTombola getServicioTombola() {
        return servicioTombola;
    }
}
