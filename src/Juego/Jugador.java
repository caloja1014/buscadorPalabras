/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import armatupalabrap.ArmaTuPalabraP;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.Button;

/**
 *
 * @author Estudiante
 */
public class Jugador implements Serializable{
    /**
     * atributos del jugador
     */
    private String usuario;
    private String contrasena;
    private int puntaje;
    //private Level nivel;
    
    /**
     * Constructor de jugador 
     * @param usuario
     * @param contrasena 
     */
    public Jugador(String usuario,String contrasena){
        this.usuario=usuario;
        this.contrasena=contrasena;
        puntaje=0;
    }
    
    /**
     * constructor para poder llamar al metodo serializar
     */
    public Jugador(){
        Desearilizar();
        
    }
    
    /**
     * metodo que serializa la lista de los jugadores registrados con sus respectivos puntaje(metodo de guardado)
     */
    public void Serializar(){
        try(ObjectOutputStream ob=new ObjectOutputStream(new FileOutputStream("src/archivos/jugadores.dat"))){
            ob.writeObject(ArmaTuPalabraP.jugadores);
        }
        catch(FileNotFoundException ex){
            System.out.println(ex);
        }
        catch(IOException e){
            System.out.println(e);
        }
        
        
       
    }
    
    /**
     * metodo que lee el archivo guardado para continuar jugando
     */
    public void Desearilizar(){
        try(ObjectInputStream ob=new ObjectInputStream(new FileInputStream("src/archivos/jugadores.dat"))){
            ArmaTuPalabraP.jugadores=(ArrayList<Jugador>)ob.readObject();
        }
        catch(FileNotFoundException ex){
            System.out.println(ex);
        }
        catch(ClassNotFoundException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    
    /**
     * getters
     * @return 
     */
    public String getUsuario() {
        return usuario;
    }

    public int getPuntaje() {
        return puntaje;
    }

    /**
     * setter
     * @param puntaje 
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
