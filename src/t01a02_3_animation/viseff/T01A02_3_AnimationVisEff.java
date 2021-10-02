/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t01a02_3_animation.viseff;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

/**
 *
 * @author jose
 */
public class T01A02_3_AnimationVisEff extends Application {
    
    
    @Override
    public void start(Stage primaryStage){
        // Creamos un grupo. En este caso es apropiado para tener un nodo raiz
        //      ya que los circulos seran nodos superpuestos.
        //      El tamaño del grupo sera la suma del tamaño de los nodos en el contenidos
        Group root = new Group();
        // Creamos la escena
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        // Añadimos la escena al escenario principal
        primaryStage.setScene(scene);
        
        // Creamos un nuevo grupo para los circulos
        Group circles = new Group();
        // Bucle para crear los circulos (ya que cada circulo es un nodo mas en el grupo circulos)
        for(int i = 0; i < 30; i++)
        {
            /* Creamos un ciruclo con:
                    - 150 pixeles de radio
                    - Color de relleno blanco
                    - Nivel de opacidad 0.05 (5 % de opacidad, casi transparente)
            */
            Circle circle = new Circle(150, Color.web("white", 0.05));
            // Creamos un borde al rededor de los circulos que vaya hacia el exterior (es decir el borde comienza cuando termina el circulo, está fuera del circulo)
            circle.setStrokeType(StrokeType.OUTSIDE);
            // Le damos color y nivel de opacidad al borde (el borde es mas opaco que el interior, por lo tanto brillará mas
            circle.setStroke(Color.web("white", 0.16));
            // Le damos ancho al borde, 4 pixeles de ancho
            circle.setStrokeWidth(4);
            // Añadimos el circulo como un hijo del grupo circulos
            circles.getChildren().add(circle);
        }
        
        // Agregamos aqui un rectangulo relleno de un degradado lineal, lo agregamos antes de agregar los ciruclos para que quede por detras de estos
        
        
        // Añadimos el grupo circulos al grupo raiz (al root)
        root.getChildren().add(circles);
        
        // Efefcto de desenfoque a los circulos (al grupo entero, por lo tanto afecta a los hijos del grupo, 
        //con esto la linea del borde no sera tan nitida y se veramas gradual
        // Le damos de desenfoque 10 pixeles de ancho y 10 de alto, y la iteracion del desenfoque la ponemos a 3 (desenfoque gaussiano)
        circles.setEffect(new BoxBlur(10, 10, 3));
        
        // Mostramos el escenario
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
