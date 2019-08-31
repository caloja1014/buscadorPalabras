/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import armatupalabrap.ArmaTuPalabraP;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 *
 * @author Freddy
 */
public class Registro {
    /**
     * atributos de la interfaz de registro
     */
    private VBox root3=new VBox();
    private Label verificacionCampos=new Label("");
    private HBox contenedor1=new HBox();
    private Label usuario=new Label("Usuario: ");
    private TextField txtuser=new TextField();
    private Label confirmaruser=new Label("");
    private HBox contenedor2=new HBox();
    private Label password=new Label("Contraseña: ");
    private TextField txtpassword=new TextField();
    private Button registro=new Button("Registrar");
    public static Inicio ini=new Inicio();
    
    /**
     * constructor que llama al metodo que ordena los atributos y define las acciones de cada boton
     */
    public Registro(){
        organizarElementos();
        registro.setOnAction(e->validarCampos());
    }
    
    /**
     * metodo que ordena los atributos de la interfaz registro 
     */
    public void organizarElementos(){
        contenedor1.getChildren().addAll(usuario,txtuser,confirmaruser);
        contenedor1.setAlignment(Pos.CENTER);
        contenedor2.getChildren().addAll(password,txtpassword);
        contenedor2.setAlignment(Pos.CENTER);
        root3.getChildren().addAll(contenedor1,contenedor2,registro);
        root3.setAlignment(Pos.CENTER);
    }
    
    /**
     * metodo que se asegura que esten completos todos los campos antes de registrar a alguien
     */
    public void validarCampos(){
        confirmaruser.setText("");
        if(txtuser.getText()==null && txtpassword.getText()==null){
            verificacionCampos.setStyle("-fx-background-color:red");
        }
        if(ini.validarUsuario(txtuser)){
            confirmaruser.setText("Usuario ya existente, intente de nuevo");
            txtuser.clear();
        }
        else{
            Jugador jug=new Jugador(txtuser.getText(),txtpassword.getText());
            ArmaTuPalabraP.jugadores.add(jug);
            System.out.println("Listo");
            Inicio.reg.close();
        }
    }
    /**
     * getter que devuelve el root que debe ser presentado 
     * @return 
     */
    public VBox getRoot3() {
        return root3;
    }
}
