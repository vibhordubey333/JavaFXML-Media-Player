
package javafxapplication_mediaview;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class JavaFXApplication_mediaview extends Application{
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
   
    public static void main(String[] args) {
        launch(args);
    }
}
