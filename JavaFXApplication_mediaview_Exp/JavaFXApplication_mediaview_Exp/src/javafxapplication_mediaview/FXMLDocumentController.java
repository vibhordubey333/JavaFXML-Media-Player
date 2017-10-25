package javafxapplication_mediaview;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;//for using image on button
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {
    
    //Declarations for playlist
    Scene playlistScene;
    //playlist code ends here
    @FXML
    private MediaView mv;
    private MediaPlayer mp;
    private Media me;
    private Label l1;
    boolean stopReuqested = false;
    //variables declaration for statement
    SliderBar timeSlider = new SliderBar();
    Duration dur;
    DecimalFormat formatter = new DecimalFormat("00.00");
    @FXML
    MenuItem q, of, ofm, ns,plist;
    Slider timeShift;
    @FXML
    Button playBut=new Button(">");
    @FXML
    Button pauseBut=new Button("||");
    @FXML
    Button fastBut=new Button(">>>");
    @FXML
    Button backBut=new Button("<<<");
    @FXML
    Button reBut=new Button("R");
    @FXML
    Label vol_label,totalTime,time;
    //Variable declaration ends here for slider , timer and play and pause
    Button mute;
    @FXML
    Slider vol;
    Scene sc1;
    Stage st1;
    //Variables for network streaming 
    @FXML
    Scene ns1;
    Stage nst1;
    MediaView ns_mv;
    private static final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
    @Override
    //initialize method is the first method which will run when class will be loaded
    public void initialize(URL url, ResourceBundle rb) {
        vol_label.setTextFill(Color.BLUE);
        vol_label.setText("0.00");
        vol_label.setVisible(true);
        //Code for setting image on button
        Image imgage = new Image(getClass().getResourceAsStream("volume1.jpg"));
        //Code ends here
        String path = "D:\\Ecstacy\\Gym\\BEST MOTIVATION WORKOUT MUSIC.mp4";
        int count = 0;
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        mv.resize(600, 500);
        mp.setAutoPlay(true);//for playing video
        /*
        playBut.setOnAction((event)->{
            System.out.println("erer");
        });*/
        DoubleProperty width = mv.fitWidthProperty();
        vol.setValue(mp.getVolume() * 100);//defualt value is 1     
        vol.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mp.setVolume(vol.getValue() / 100);
                double str = vol.getValue();
                System.out.println("Volume change= " + str);
                vol_label.setText(Double.toString(str));
            }
        });
        //code for displaying time
        timeSlider.sliderValueProperty().addListener((ov) -> {
            if (timeSlider.isTheValueChanging()) {
                if (null != mp)
                    // multiply duration by percentage calculated by
                    // slider position
                    mp.seek(dur.multiply(timeSlider.sliderValueProperty().getValue() / 100.0));
                else
                    timeSlider.sliderValueProperty().setValue(0);
            }
        });
        //setting duration on labels
        mp.setOnReady(()->
        {
           dur=mp.getMedia().getDuration();
           totalTime.setText(" / " + String.valueOf(formatter.format(Math.floor(dur.toSeconds()))));
        });        
    }
    @FXML
    private void handleButtonAction(ActionEvent ex) {
        if (ex.getSource() == of) {
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("MP4 Files(*.mp4)", "*.mp4");
            FileChooser.ExtensionFilter ex2 = new FileChooser.ExtensionFilter("MP3 Files(*.mp3)", "*.mp3");
            FileChooser.ExtensionFilter ex3 = new FileChooser.ExtensionFilter("3GP Files(*.3gp)", ".3gp");
            FileChooser.ExtensionFilter ex4 = new FileChooser.ExtensionFilter("AVI Files(*.avi)", ".avi");
            fc.getExtensionFilters().addAll(ex1, ex2, ex3, ex4);
            File f = fc.showOpenDialog(st1);
            String path = null;
            path = f.getAbsolutePath();
            System.out.println("Path is " + path);
            //Below code for playing video
            System.out.println("File name is" + path);
            me = new Media(new File(path).toURI().toString());
            mp = new MediaPlayer(me);
            mv.setMediaPlayer(mp);
            mp.setAutoPlay(true);//for playing video
        } else if (ex.getSource() == ofm) {
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter exFilter = new FileChooser.ExtensionFilter("MP4 Files(*.mp4)", "*.mp4");
            FileChooser.ExtensionFilter ex2 = new FileChooser.ExtensionFilter("MP3 Files(*.mp3)", "*.mp3");
            FileChooser.ExtensionFilter ex3 = new FileChooser.ExtensionFilter("3GP Files(*.mp3)", ".3gp");
            fc.getExtensionFilters().addAll(exFilter, ex2, ex3);
            File f = fc.showOpenDialog(st1);
        } else if (ex.getSource() == q) {
            Platform.exit();
            System.exit(0);
        } else if (ex.getSource() == ns) {
            nst1 = new Stage();
            //Parent np=FXMLLoader(getClass().getResource("FXML_Network Stream.fxml"))//FXMLLoader(getClass().getResource("FXML_Network Stream.fxml"));
            Group root = new Group();
            ns1 = new Scene(root, 500, 500);
            Media media = new Media(MEDIA_URL);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            ns_mv = new MediaView(mediaPlayer);
            ((Group) ns1.getRoot()).getChildren().add(ns_mv);
            nst1.setScene(ns1);
            nst1.show();
        } else if (ex.getSource() == mute) {
            mute.setGraphic(new ImageView("mute.png"));
        } else if (ex.getSource() == vol) {
            if (vol.isValueChanging()) {
                System.out.println(vol.valueChangingProperty());
            }
        } 
        else if (ex.getSource() == playBut) 
        {
            stopReuqested=true;
            mp.pause();
            
        } else if (ex.getSource() == pauseBut) 
        {
            System.out.println("Inside pauseBut method");
            if( stopReuqested == true)
               mp.play();
        }
        else if(ex.getSource()==fastBut)
        {
          mp.seek(mp.getCurrentTime().multiply(1.5));
        }
        else if(ex.getSource()==plist)
        {
            Stage stagep=new Stage();
            stagep.setScene(playlistScene);
            //stagep.initOwner(stagep);
            stagep.show();
            
        }
        else if(ex.getSource()==fastBut)
        {
            mp.seek(mp.getCurrentTime().multiply(1.5));
        }
        else if(ex.getSource()==backBut)
        {
            mp.seek(mp.getCurrentTime().divide(1.5));
        }
        else if(ex.getSource()==reBut)
        {
            mp.seek(mp.getStartTime());
        }
        
       // else if(ex.getSource()==)
    }
}
class SliderBar extends StackPane {

        @FXML
        private Slider slider = new Slider();

        private ProgressBar progressBar = new ProgressBar();

       /* public SliderBar() {
            getChildren().addAll(progressBar, slider);
            bindValues();
        }*/
        private void bindValues(){
            progressBar.prefWidthProperty().bind(slider.widthProperty());
            progressBar.progressProperty().bind(slider.valueProperty().divide(100));
        }

        public DoubleProperty sliderValueProperty() {
            return slider.valueProperty();
        }

        public boolean isTheValueChanging() {
            return slider.isValueChanging();
        }
    }
    /*public static void main(String[] args) {
        launch(args);
    }*/