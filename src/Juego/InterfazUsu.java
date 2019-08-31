/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import armatupalabrap.ArmaTuPalabraP;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Cloja
 */
public class InterfazUsu {

    /**
     * atributos y colecciones que seran usados para dar vida al juego
     */
    public List<String> palasEscogidas = new ArrayList();
    public HashMap<Integer, Button> indicesBotones = new HashMap();
    public Set<Integer> indices;
    public List<Integer> valoresIndices = new ArrayList();
    public List<Button> botonesEscogidos = new ArrayList();
    public static ArrayList<Button> letrasPeligro = new ArrayList<>();
    public static ArrayList<Button> letrasBono = new ArrayList<>();
    private BorderPane root = new BorderPane();
    private GridPane espacioPalabras = new GridPane();
    private VBox caracteristicas = new VBox();
    private VBox level = new VBox();
    private Label txtLevel = new Label("Level");
    private Label actualizacionLevel = new Label("0");
    private VBox score = new VBox();
    private Label txtScore = new Label("Score");
    private Label actualizacionScore = new Label("0");
    private Label bono = new Label("Bono: ");
    private Label pena = new Label("Pena: ");
    private VBox time = new VBox();
    private Label txtTime = new Label("Time");
    private Label actualizacionTime = new Label("0");
    private Button enviar = new Button("Enviar Palabra");
    private Button salir = new Button("Salir");
    private String letrasRestantes = "";
    private String palaEscogida = "";
    public static int puntajeActual=0 ;
    public static int contadorBono = 0;
    public static int contadorPena = 0;
    public static int contadorBotones = 1;//
    public static int botonInicial = 0;
    public static int indiceActu = 0;
    public static List<String> palabrasIdio;
    
    
    /**
     * constructor que organiza los elementos de la interfaz, inicializa las thread para la bonificacion y penalizacion
     */
    public InterfazUsu() {
        organizarElementos();
        Thread tpenal = new Thread(new Penalizacion());
        Thread tbono = new Thread(new Bonificacion());
        tpenal.start();
        tbono.start();
    }
    public static void cargarDatosIdioma(String nombreIdio){
        try{
        palabrasIdio=Files.readAllLines(Paths.get("src/archivos/"+nombreIdio+".csv"));
     
        }
        catch(IOException e){
            System.out.println("Archivo no encontrado");
        }
    }
    /**
     * metodo que organiza los elementos de nuestra interfaz
     */
    public void organizarElementos() {
        crearBotones();
        llenarBotones(indicesBotones);
        llenarCaracteristicas();
        root.setCenter(espacioPalabras);
        root.setLeft(caracteristicas);
        puntajeActual=Inicio.player.getPuntaje();
        actualizacionScore.setText(String.valueOf(puntajeActual));
        enviar.setOnAction(e -> enviarPalabra());
        salir.setOnAction(e-> salir());
    }

    /**
     * metodo que crea los botones que presentara el juego, siendo una matriz de 5x5
     * define la accion de cada boton
     */
    public void crearBotones() {
        int contador = 0;
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                //Image im =new Image("/imagenes/boton.jpg");
        //ImageView img= new ImageView(im);
        //img.setFitHeight(80);
        //img.setFitWidth(80);
                Button b = new Button();
                b.setFont(new Font("Arial", 20));
                b.setPrefSize(90, 90);
                //b.set
                espacioPalabras.add(b, i, j);
                indicesBotones.put(contador, b);
                b.setOnAction(e -> almacenarPalaEscogida(b));
                contador++;
            }
        }
    }

    /**
     * metodo que recorre la colecion de botones y les atribuye una letra en base al archivo anteriormente cargado
     * @param indicesBotones 
     */
    public void llenarBotones(HashMap<Integer, Button> indicesBotones) {
        Random rd = new Random();
        indices = indicesBotones.keySet();
        for (int k : indices) {
            valoresIndices.add(k);
        }
        int tamanoConjuntoIndices = valoresIndices.size();
        while (tamanoConjuntoIndices != 0) {
            tamanoConjuntoIndices = valoresIndices.size();
            int indice = rd.nextInt(palabrasIdio.size());
            String palaEscogida = palabrasIdio.get(indice);
            palasEscogidas.add(palaEscogida);
            palabrasIdio.remove(indice);
            if (palaEscogida.length() <= tamanoConjuntoIndices) {
                for (String i : palaEscogida.split("")) {
                    int indiceBoton = rd.nextInt(valoresIndices.size());
                    indicesBotones.get(valoresIndices.get(indiceBoton)).setText(i);
                    valoresIndices.remove(indiceBoton);
                }
            } else {
                letrasRestantes = palaEscogida.substring(tamanoConjuntoIndices);
                String nuevaPala = palaEscogida.substring(0, tamanoConjuntoIndices);
                System.out.println(nuevaPala + " " + letrasRestantes);
                for (String i : nuevaPala.split("")) {
                    int indiceBoton = rd.nextInt(valoresIndices.size());
                    indicesBotones.get(valoresIndices.get(indiceBoton)).setText(i);
                    valoresIndices.remove(indiceBoton);
                }
                break;
            }
        }
    }

    public void llenarBotones(List<Button> botones) {
        HashMap<Integer, Button> newBotones = new HashMap();
        for (int i = 0; i < botones.size(); i++) {
            newBotones.put(i, botones.get(i));
        }
        if (letrasRestantes.length() <= botones.size()) {
            int cont = 0;
            for (String i : letrasRestantes.split("")) {
                botones.get(cont).setText(i);
                botones.remove(cont);
            }
            letrasRestantes = "";
        }
        if (0 != botones.size() || letrasRestantes.length() > botones.size()) {
            llenarBotones(newBotones);
            botones.clear();
        }
    }

    /**
     * metodo que forma la palabra dependiendo si son botones adyacentes los que estan seleccionando
     * @param b 
     */
    public void almacenarPalaEscogida(Button b) {
        for (int i : indicesBotones.keySet()) {
            if (indicesBotones.get(i)==b) {
                indiceActu = i;
            }
        }
        boolean con=true;
        while (con) {
            if (contadorBotones == 1) {
                botonesEscogidos.add(b);
                contadorBotones += 1;
                botonInicial = indiceActu;
                b.setTextFill(Color.BLUE);
                palaEscogida += b.getText();
                con=false;
                break;
            }
            while (contadorBotones>1) {
                if (botonInicial == 0) {
                    if ((indiceActu == botonInicial + 1) || (indiceActu == botonInicial + 5)) {
                        botonesEscogidos.add(b);
                        botonInicial = indiceActu;
                        b.setTextFill(Color.BLUE);
                        contadorBotones ++;
                        palaEscogida += b.getText();
                        con=false;
                        break;
                    }
                } else if ((botonInicial == 5) || (botonInicial == 10) || (botonInicial == 15)) {
                    if ((indiceActu == botonInicial + 1) || (indiceActu == botonInicial - 5) || (indiceActu == botonInicial + 5)) {
                        botonesEscogidos.add(b);
                        botonInicial = indiceActu;
                        b.setTextFill(Color.BLUE);
                        contadorBotones++;
                        palaEscogida += b.getText();
                        con=false;
                        break;
                    }
                } else if (botonInicial == 20) {
                    if ((indiceActu == botonInicial + 1) || (indiceActu == botonInicial - 5)) {
                        botonesEscogidos.add(b);
                        botonInicial = indiceActu;
                        b.setTextFill(Color.BLUE);
                        contadorBotones++;
                        palaEscogida += b.getText();
                        con=false;
                        break;
                    }
                } else if (botonInicial == 4) {
                    if ((indiceActu == botonInicial - 1) || (indiceActu == botonInicial + 5)) {
                        botonesEscogidos.add(b);
                        botonInicial = indiceActu;
                        b.setTextFill(Color.BLUE);
                        contadorBotones++;
                        palaEscogida += b.getText();
                        con=false;
                        break;
                    }
                } else if ((botonInicial == 9) || (botonInicial == 14) || (botonInicial == 19)) {
                    if ((indiceActu == botonInicial - 1) || (indiceActu == botonInicial - 5) || (indiceActu == botonInicial + 5)) {
                        botonesEscogidos.add(b);
                        botonInicial = indiceActu;
                        b.setTextFill(Color.BLUE);
                        contadorBotones++;
                        palaEscogida += b.getText();
                        con=false;
                        break;
                    }
                } else if (botonInicial == 24) {
                    if ((indiceActu == botonInicial - 1) || (indiceActu == botonInicial - 5)) {
                        botonesEscogidos.add(b);
                        botonInicial = indiceActu;
                        b.setTextFill(Color.BLUE);
                        contadorBotones++;
                        palaEscogida += b.getText();
                        con=false;
                        break;
                    }
                } else if ((botonInicial == 1) || (botonInicial == 2) || (botonInicial == 3)) {
                    if ((indiceActu == botonInicial - 1) || (indiceActu == botonInicial + 5) || (indiceActu == botonInicial + 1)) {
                        botonesEscogidos.add(b);
                        botonInicial = indiceActu;
                        b.setTextFill(Color.BLUE);
                        contadorBotones++;
                        palaEscogida += b.getText();
                        con=false;
                        break;
                    }
                } else if ((botonInicial == 21) || (botonInicial == 22) || (botonInicial == 23)) {
                    if ((indiceActu == botonInicial - 1) || (indiceActu == botonInicial - 5) || (indiceActu == botonInicial + 5)) {
                        botonesEscogidos.add(b);
                        botonInicial = indiceActu;
                        b.setTextFill(Color.BLUE);
                        contadorBotones++;
                        palaEscogida += b.getText();
                        con=false;
                        break;
                    }
                } else {
                    if ((indiceActu == botonInicial - 1) || (indiceActu == botonInicial + 1) || (indiceActu == botonInicial - 5) || (indiceActu == botonInicial + 5)) {
                        botonesEscogidos.add(b);
                        botonInicial = indiceActu;
                        b.setTextFill(Color.BLUE);
                        contadorBotones++;
                        palaEscogida += b.getText();
                        con=false;
                        break;
                    }
                }
                contadorBotones=1;
                break;
            }
            con=false;
        }
        contadorBotones++;
        if (letrasPeligro.contains(b)) {
            contadorPena += 1;
            puntajeActual -= 1;
            pena.setText("Pena: " + contadorPena);
        }
        if (letrasBono.contains(b)) {
            contadorBono += 1;
            puntajeActual += 1;
            bono.setText("Bono: " + contadorBono);
        }
    }

    /**
     * metodo que eliminar el boton en caso de que sea correcto y le atribuye otro valor
     * @param v 
     */
    public void actualizarBotones(boolean v) {
        if (v) {
            llenarBotones(botonesEscogidos);
        }
        for (Button i : indicesBotones.values()) {
            i.setTextFill(Color.BLACK);
        }
    }

    /**
     * metodo que modifica los atributos para hacer la interfaz mas agradable y los agrupa
     */
    public void llenarCaracteristicas() {
        caracteristicas.setSpacing(0);
        caracteristicas.setMinSize(150, 150);
        level.setSpacing(10);
        score.setSpacing(10);
        time.setSpacing(10);
        level.setPadding(new Insets(50));
        score.setPadding(new Insets(50));
        time.setPadding(new Insets(50));
        txtLevel.setFont(new Font("Arial", 20));
        actualizacionLevel.setFont(new Font("Arial", 20));
        txtScore.setFont(new Font("Arial", 20));
        bono.setFont(new Font("Arial", 20));
        pena.setFont(new Font("Arial", 20));
        //actualizacionScore.setText(Integer.toString(Inicio.player.getPuntaje()));
        actualizacionScore.setFont(new Font("Arial", 20));
        txtTime.setFont(new Font("Arial", 20));
        actualizacionTime.setFont(new Font("Arial", 20));
        enviar.setFont(new Font("Arial", 20));
        enviar.setPadding(new Insets(25));
        salir.setFont(new Font("Arial", 20));
        salir.setPadding(new Insets(25));
        level.getChildren().addAll(txtLevel, actualizacionLevel);
        score.getChildren().addAll(txtScore, actualizacionScore, bono, pena);
        time.getChildren().addAll(txtTime, actualizacionTime);
        caracteristicas.getChildren().addAll(level, score, time, enviar, salir);
    }
    
    /**
     * metodo del boton enviar que verifica si la palabra que se ha formado es correcta y determina un puntaje dependiendo de la cantidad de letras
     * de la palabra
     */
    public void enviarPalabra() {
        boolean bandera = false;
        if (palabrasIdio.contains(palaEscogida) || palasEscogidas.contains(palaEscogida)) {
            System.out.println("Exitoso movimiento");
            bandera = true;
            actualizarBotones(bandera);
            int puntuacionPalabra = palaEscogida.length();
            
            puntajeActual += puntuacionPalabra;
            actualizacionScore.setText(Integer.toString(puntajeActual));
        } else {
            //metodo cool de Freddy :v
            System.out.println("Intentelo otra vez");
            actualizarBotones(bandera);
        }
        contadorBono = 0;
        contadorPena = 0;
        bono.setText("Bono: ");
        pena.setText("Pena: ");
        System.out.println(palaEscogida);
        palaEscogida = "";
        contadorBotones = 1;//
        botonInicial = 0;
        indiceActu = 0;
    }
    
  
    
    /**
     * metodo que guarda la partida y cierra el programa
     */
    public void salir(){
        Inicio.player.setPuntaje(puntajeActual);
        ArmaTuPalabraP.jg.Serializar();
        Platform.exit();
    }

    /**
     * metodo que devuelve el root para presentarlo
     * @return 
     */
    public BorderPane getRoot2() {
        return root;
    }

    /**
     * clase interna que genera botones peligro cada 30 segundos
     */
    class Penalizacion implements Runnable {

        Random rd = new Random();
        int i = 0;
        boolean confirmar;

        public void run() {
            try {
                while (i < 1) {
                    Thread.sleep(30000);
                    confirmar = false;
                    int num = rd.nextInt(20);
                    while (confirmar == false) {
                        if (!letrasBono.contains(num)) {
                            indicesBotones.get(num).setStyle("-fx-background-color:red");
                            letrasPeligro.add(indicesBotones.get(num));
                            confirmar = true;
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * clase interna que genera botones bono cada 45 segundos
     */
    class Bonificacion implements Runnable {

        Random rd = new Random();
        int i = 0;
        boolean confirmar;

        public void run() {
            try {
                while (i < 1) {
                    Thread.sleep(40000);
                    confirmar = false;
                    int num = rd.nextInt(20);
                    while (confirmar == false) {
                        if (!letrasPeligro.contains(num)) {
                            indicesBotones.get(num).setStyle("-fx-background-color:yellow");
                            letrasBono.add(indicesBotones.get(num));
                            confirmar = true;
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
