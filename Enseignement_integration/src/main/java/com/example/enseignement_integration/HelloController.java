package com.example.enseignement_integration;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public static String[] getLatestUpdate(String id) throws IOException {

        URL url = new URL("https://ei-ddi.uc.r.appspot.com/read");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = id;
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        String s = content.toString();
        String[] t=s.split("[{}]");
        String[] tx=t[1].split("[:\",]");
        return tx;
    };
    public static LineChart<String, Number> getUserGlycemia(Stage stage, String id, int sizeindex) throws IOException {
        URL url = new URL("https://ei-ddi.uc.r.appspot.com/read");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = id;
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        String s = content.toString();
        String[] t=s.split("[{}]");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Taux de glycémie"); //for example
        for(int x=1;x<t.length;x+=2) {
            String[] tx=t[x].split("[:\",]");
            series.getData().add(new XYChart.Data<>(tx[22]+":"+tx[23]+":"+tx[24], Double.parseDouble(tx[16])));
//            System.out.println(tx[1]);
//            System.out.println(tx[12]);
//            System.out.println(tx[3]);
//            System.out.println(tx[4]);
//            System.out.println(tx[5]);
//            System.out.println(tx[6]);
//            System.out.println(tx[7]);
//            System.out.println(tx[8]);
//            System.out.println(tx[9]);
//            System.out.println(tx[10]);
//            System.out.println(tx[11]);
//            System.out.println(tx[12]);
//            System.out.println(tx[13]);
//            System.out.println(tx[14]);
//            System.out.println(tx[15]);
//            System.out.println(tx[16]);
//            System.out.println(tx[17]);
//            System.out.println(tx[18]);
//            System.out.println(tx[19]);
//            System.out.println(tx[20]);
//            System.out.println(tx[21]);
//            System.out.println(tx[22]);
//            System.out.println(tx[23]);
//            System.out.println(tx[24]);
//            System.out.println(tx[25]);
//            System.out.println(tx[26]);
//            System.out.println(tx[27]);
//            System.out.println(tx[28]);
//            System.out.println(tx[29]);
//            System.out.println(tx[30]);


        }

        CategoryAxis xAxis = new CategoryAxis(); // on trace par rapport au temps
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Time");// il va falloir revoir l'unité peut-être
        //xAxis.setAnimated(true); //
        yAxis.setLabel("Value");
        //yAxis.setAnimated(true); //

        LineChart<String, Number> LineChart = new LineChart<>(xAxis, yAxis);
        LineChart.setTitle("Realtime Evolution ");
        LineChart.setAnimated(false);
        //c'est series qui va nous permettre d'afficher des trucs
        series.setName("Taux de glycémie"); //for example
        LineChart.getData().add(series);
        int window_size = 0;
        if(sizeindex==0){
            window_size=24*3600;
        }
        if(sizeindex==1){
            window_size=24*3600*7;
        }
        if(sizeindex==2){
            window_size=24*3600*30;
        }

        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        int finalWindow_size = window_size;
        scheduledExecutorService.scheduleAtFixedRate(() -> {

            // Update the chart
            Platform.runLater(() -> {
                try {
                    String[] l=getLatestUpdate(id);
                    series.getData().add(new XYChart.Data<>(l[22]+":"+l[23]+":"+l[24], Double.parseDouble(l[16])));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (series.getData().size() > finalWindow_size)
                    series.getData().remove(0);
                System.out.println("A"+finalWindow_size+"A");

            });
        }, 0, 1, TimeUnit.SECONDS);

        return LineChart;
    }

    public static BarChart<String, Number> getUserInsulin(Stage stage, String id, int sizeindex) throws IOException {
        URL url = new URL("https://ei-ddi.uc.r.appspot.com/read");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = id;
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        String s = content.toString();
        String[] t=s.split("[{}]");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Insulin"); //for example

        for(int x=1;x<t.length;x+=2) {
            String[] tx=t[x].split("[:\",]");
            series.getData().add(new XYChart.Data<>(tx[22]+":"+tx[23]+":"+tx[24], Double.parseDouble(tx[30])));
        }
        //defining the axes
        CategoryAxis xAxis = new CategoryAxis(); // on trace par rapport au temps
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Time/s");// il va falloir revoir l'unité peut-être
        //xAxis.setAnimated(true); //
        yAxis.setLabel("Value");
        //yAxis.setAnimated(true); //

        BarChart<String, Number> BarChart = new BarChart<>(xAxis, yAxis);
        BarChart.setTitle("Realtime Evolution ");
        BarChart.setAnimated(false);
        //c'est series qui va nous permettre d'afficher des trucs
        series.setName("Insulin"); //for example
        int WINDOW_SIZE = 10;

        BarChart.getData().add(series);


        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {

            // Update the chart
            Platform.runLater(() -> {
                try {
                    String[] l=getLatestUpdate(id);
                    series.getData().add(new XYChart.Data<>(l[22]+":"+l[23]+":"+l[24], Double.parseDouble(l[30])));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            });
        }, 0, 1, TimeUnit.SECONDS);

        Scene scene = new Scene(BarChart, 800, 600);
        return BarChart;
    }
}