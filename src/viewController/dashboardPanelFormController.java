package viewController;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class dashboardPanelFormController {
    public Label lblDate;
    public Label lblTime;
    public PieChart pieChartCadetScl;

    public void initialize(){
        loadDateAndTime();

        ObservableList<PieChart.Data> schoolPieChart = FXCollections.observableArrayList(
                new PieChart.Data("Cadet School", 54),
                new PieChart.Data("Non Cadet School", 40)
        );
        pieChartCadetScl.getData().forEach(data ->{
            String percentage =String.format("%.2f%%",data.getPieValue()/100);
            Tooltip tooltip=new Tooltip(percentage);
            Tooltip.install(data.getNode(),tooltip);
        });
        pieChartCadetScl.setData(schoolPieChart);
        pieChartCadetScl.autosize();
    }
    private void loadDateAndTime() {
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("E, dd MMMM yyyy");
        lblDate.setText(f.format(date));
        lblDate.setStyle("-fx-background-position: center");

        Timeline time =new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime=LocalTime.now();
            String sec="00";
            String min="00";
            String hr="00";

            sec= (currentTime.getSecond()<=9) ? "0"+currentTime.getSecond(): String.valueOf(currentTime.getSecond()) ;
            min= (currentTime.getMinute()<=9) ? "0"+currentTime.getMinute():String.valueOf(currentTime.getMinute()) ;
            hr=(currentTime.getHour()<=9) ? "0"+currentTime.getHour() :String.valueOf(currentTime.getHour());

            lblTime.setText(hr+":"+min+":"+sec);
            lblTime.setStyle("-fx-background-position: center");
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
}
