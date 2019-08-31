/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import armatupalabrap.ArmaTuPalabraP;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Freddy
 */
public class Inicio {

    /**
     * elementos de nuestra interfaz de inicio
     */
    private HBox root = new HBox(20);
    private ImageView imagen = new ImageView(new Image("/imagenes/abeja.jpg"));
    private VBox inicio = new VBox(20);
    private HBox datos = new HBox(20);
    private Label usuario = new Label("Usuario");
    private TextField txtusuario = new TextField();
    private HBox datos2 = new HBox(20);
    private Label contrasena = new Label("Contraseña");
    private TextField txtcontrasena = new TextField();
    private Button registro = new Button("Registrar");
    private Button empieza = new Button("Inicio");
    public static Registro rg = new Registro();
    public static Stage reg = new Stage();
    public static boolean empez = false;
    public static Jugador player;
    private ComboBox<String> idiomas = new ComboBox();

    /**
     * constructor para ordenar nuestros elementos de la interfaz y definir las
     * acciones de los botones
     */
    public Inicio() {
        organizarElementos();
        idiomas.setOnAction(e -> InterfazUsu.cargarDatosIdioma("italiano"));
        empieza.setOnAction(e -> empezar());
        registro.setOnAction(e -> registrar());
    }

    /**
     * metodo que organiza los elementos de nuestra interfaz
     */
    public void organizarElementos() {
        llenarComboBox();
        imagen.setFitHeight(450);
        imagen.setFitWidth(350);
        datos.getChildren().addAll(usuario, txtusuario);
        datos.setAlignment(Pos.CENTER);
        datos2.getChildren().addAll(contrasena, txtcontrasena);
        datos2.setAlignment(Pos.CENTER);
        inicio.getChildren().addAll(datos, datos2, new Label("Idiomas"), idiomas, registro, empieza);
        inicio.setAlignment(Pos.CENTER);
        root.getChildren().addAll(imagen, inicio);
        root.setAlignment(Pos.CENTER);
    }

    /**
     * metodo para llenar el combobox que mostrara llos idimas
     */
    public void llenarComboBox() {
        idiomas.getItems().addAll(Data.data.idiomas);
    }

    /**
     * metodo del boton empezar que inicia el juego una vez verificado tus datos
     */
    public void empezar() {
        if (validarUsuario(txtusuario)) {
            for (Jugador j : ArmaTuPalabraP.jugadores) {
                if (txtusuario.getText().equalsIgnoreCase(j.getUsuario())) {
                    player = j;
                }
            }
            InterfazUsu iu = new InterfazUsu();
            empez = true;
            ArmaTuPalabraP.principal.hide();
            Stage s = new Stage();
            Scene sc2 = new Scene(iu.getRoot2());
            s.setScene(sc2);
            s.show();
        } else {
            registrar();
        }
    }
    
    /**
     * metodo que retorna un boolean dependiendo de la validez de tus datos
     * y te ordena registrarte en el caso de que no lo estes
     * @param texto
     * @return 
     */
    public boolean validarUsuario(TextField texto) {
        for (Jugador j : ArmaTuPalabraP.jugadores) {
            if (texto.getText().equalsIgnoreCase(j.getUsuario())) {
                player=j;
                return true;
            }
        }
        return false;
    }

    /**
     * metodo que abre una ventana en el caso de que no te encuentres registrado 
     */
    public void registrar(){
        Scene sc3=new Scene(rg.getRoot3());
        reg.setScene(sc3);
        reg.show();
    }
    
    /**
     *
     * @return getter que retorna el rut que se presentara por pantalla
     */
    public HBox getRoot() {
        return root;
    }
}
