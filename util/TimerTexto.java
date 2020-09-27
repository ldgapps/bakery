package util;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTexto {
    public static void setTimer(Label label){
        Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> label.setText(""));
                t.cancel();
            }
        };
        t.schedule(tt, 4000 );
    }
}
