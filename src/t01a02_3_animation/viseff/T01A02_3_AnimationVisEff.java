/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t01a02_3_animation.viseff;


import static java.lang.Math.random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

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
        
        // Agregamos aqui un rectangulo relleno de un degradado lineal, lo agregamos antes de agregar los ciruclos para que quede por detras de estos.
        // El rectangulo, el degradado, comienza en la esquina inferior izquierda (0, 1) -> 0f, 1f.
        // Y termina en la esquina superior derecha (1, 0) -> 1f, 0f.
        // El valor true indica que el degradado es proporcional al rectangulo.
        // NO_CYCLE indica que el ciclo de color no se repetira, sera un gradiente con los distintos colores sin repetirse.
        // La secuencia Stop[], indica cual deberia ser el color del degradado en un lugar en particular.
        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
            new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, 
                new Stop[]{
                    new Stop(0, Color.web("#f8bd55")),
                    new Stop(0.14, Color.web("#c0fe56")),
                    new Stop(0.28, Color.web("#5dfbc1")),
                    new Stop(0.43, Color.web("#64c2f8")),
                    new Stop(0.57, Color.web("#be4af7")),
                    new Stop(0.71, Color.web("#ed5fc2")),
                    new Stop(0.85, Color.web("#ef504c")),
                    new Stop(1, Color.web("#f2660f")),
                }));
        
        // Le damos al rectangulo el mismo ancho que la escena, si la escena se redimensiona, tambien se redimensionara el rectangulo.
        colors.widthProperty().bind(scene.widthProperty());
        // Le damos al rectangulo el mismo alto que la escena, si la escena se redimensiona, tambien se redimensionara el rectangulo.
        colors.heightProperty().bind(scene.heightProperty());
        
        // Añadimos el refctangulo al grupo raiz
    //    root.getChildren().add(colors); -> Lo sustituimos por una mezcla de superposicion
        
        // Añadimos el grupo circulos al grupo raiz (al root)
    //    root.getChildren().add(circles); -> Lo sustituimos por una mezcla de superposicion
    
        // Mezcla de superposicion -> Permite oscurecer una imagen, agregar reflejos, o ambos (depende de los colores que se combinen)
        // El grupo blandModeGroup configura la estructura de la mezcla de superposicion.
        // El grupo tiene dos hijos
        //      1er hijo: Un grupo nuevo, sin nombre, que contiene un nuevo rectangulo negro sin nombre tambien, y el grupo con los circulos que creamos antes
        //      2o hijo: Es el rectangulo de colores que creamos antes.
        Group blendModeGroup =
                new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),
                    Color.BLACK), circles), colors);
        
        // El metodo setBlendMode(), aplica la mexcla de superposicion que hemos creado justo arriba al rectangulo de colores
        colors.setBlendMode(BlendMode.OVERLAY);
        // Agregamos el grupo de la mezcla de superposicion a la escena como hijo del nodo raiz.
        root.getChildren().add(blendModeGroup);
        
        /*  Explicacion superposicion
            Con esto hemos conseguido que el rectangulo del degradado se utilice como la capa de superposicion (overlay).
            El rectangulo negro sirve para mantener el fondo oscuro, es el que se utiliza como capa de base
            mientras que los circulos casi transparentes recogen colores del degradado (no olvidemos que el grupo del rectangulo negro con los dos circulos estan en la capa superior
            al ser transparentes los circulos, dejan visible la capa de overlay (superposicion) que hay debajo con los colores en gradiente (tambien se oscurecen)
        */
        
        // Efefcto de desenfoque a los circulos (al grupo entero, por lo tanto afecta a los hijos del grupo, 
        //con esto la linea del borde no sera tan nitida y se veramas gradual
        // Le damos de desenfoque 10 pixeles de ancho y 10 de alto, y la iteracion del desenfoque la ponemos a 3 (desenfoque gaussiano)
        circles.setEffect(new BoxBlur(10, 10, 3));
        
        /*
            Ahora agregamos el efecto de animacion para mover los circulos por la pantalla
        */
        // La animacion esta controlada por un Timeline, lo creamos
        Timeline timeline = new Timeline();
        // Bucle for para recorrer todos los circulos del grupo cricles y aplicarle la animacion
        for(Node circle: circles.getChildren())
        {
            // Añadimos dos KeyFrames al timeline que le da animacion a los circulos
            timeline.getKeyFrames().addAll(
                // El primer KeyFrame se inicia en el segundo 0, por lo tanto es su posicion inicial, la ubicacion donde se inician los circulos en la pantalla
                new KeyFrame(Duration.ZERO,
                    // La posicion donde se inician los circulos se determinan mediante la funcion random() de la clase Math, la funcion devuelve un numero etre 1 y 0
                    // y se multiplica por el tamaño de la pantalla, por lo tanto si ampliamos la pantalla se vera solamente en dimensiones de 800x600, lo demas estara negro
                    // la primera linea marca la posicion de inicio del eje X, y la segunda la del eje Y
                    new KeyValue(circle.translateXProperty(), random() * 800),
                    new KeyValue(circle.translateYProperty(), random() * 600)
                ),
                // Este KeyFrame define la posicion hacia la que se movera cada circulo, vuelve a ser aleatoria, y por el mismo margen,
                // por lo tanto el movimiento de los circulos al terminar la transicion en 40 segundos (momento en el que se pararán los ciruclos),
                // tambien se apreciaran margenes negros si ampliamos la pantalla (resize).
                new KeyFrame(new Duration(40000),
                    new KeyValue(circle.translateXProperty(), random() * 800),
                    new KeyValue(circle.translateYProperty(), random() * 600)
                )
            );
        }
        // Comienza la animacion
        timeline.play();
        
        
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
