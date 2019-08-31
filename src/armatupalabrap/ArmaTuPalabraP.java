/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armatupalabrap;

import Data.data;
import Juego.Inicio;
import Juego.Jugador;
import java.util.ArrayList;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Cloja
 */
public class ArmaTuPalabraP {

    /**
     * Se declaran como static algunas listas y objetos que seran usados mas
     * adelante
     */
    public static ArrayList<Jugador> jugadores = new ArrayList<>();
    public static Jugador jg = new Jugador();
    static data data2 = new data();
    static Inicio ini = new Inicio();
    public static Stage principal;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch();
    }

    public void start(Stage primaryStage) {
        principal = primaryStage;
        Scene scene = new Scene(ini.getRoot());
        primaryStage.setTitle("ARMA PALABRAS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
