package com.example.enseignement_integration;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class menu extends  Application {

    public static void starta(Stage stage) throws IOException {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 720, 700);
        Label userName = new Label("Identifiant du patient:");
        grid.add(userName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 0, 2);
        Label Duration = new Label("Période");
        grid.add(Duration, 3, 1);
        ComboBox<String> comboBox = new ComboBox();
        comboBox.getItems().setAll("aujourd\'hui\'", "cette semaine", "ce mois");


        comboBox.getSelectionModel().selectedIndexProperty().addListener(
                observable -> {
                    int d;
                    d=comboBox.getSelectionModel().getSelectedIndex();

                    try {
                        LineChart<String, Number> glyc=HelloController.getUserGlycemia(stage,userTextField.getText(),d);
                        grid.add(glyc,0,4);
                        BarChart<String, Number> ins=HelloController.getUserInsulin(stage,userTextField.getText(),d);
                        grid.add(ins,0,5);
                        int WINDOW_SIZE = 10;



                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        grid.add(comboBox,3,2);

        stage.setScene(scene);
        stage.show();

            }



    public static void startb(Stage stage) throws FileNotFoundException {
        InputStream graph = new FileInputStream("src/main/java/com/example/enseignement_integration/graph.jpg");
        Image imagegraph = new Image(graph);
        InputStream stat = new FileInputStream("src/main/java/com/example/enseignement_integration/stat.jpg");
        Image imagestat = new Image(stat);
        InputStream alert = new FileInputStream("src/main/java/com/example/enseignement_integration/alert.png");
        Image imagealert = new Image(alert);
        ImageView imageViewGraph = new ImageView();
        imageViewGraph.setImage(imagegraph);
        ImageView imageViewStat = new ImageView();
        imageViewStat.setImage(imagestat);
        ImageView imageViewAlert = new ImageView();
        imageViewAlert.setImage(imagealert);
        imageViewGraph.setX(10);
        imageViewGraph.setY(10);
        imageViewGraph.setFitWidth(60);
        imageViewGraph.setPreserveRatio(true);
        imageViewStat.setX(10);
        imageViewStat.setY(10);
        imageViewStat.setFitWidth(60);
        imageViewStat.setPreserveRatio(true);
        imageViewAlert.setX(10);
        imageViewAlert.setY(10);
        imageViewAlert.setFitWidth(60);
        imageViewAlert.setPreserveRatio(true);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 720, 700);
        grid.add(imageViewGraph,0,1);
        grid.add(imageViewStat,0,3);
        grid.add(imageViewAlert,0,5);
        Button graphbtn = new Button("Graphiques");
        HBox hbBtng = new HBox(10);
        hbBtng.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtng.getChildren().add(graphbtn);
        grid.add(hbBtng, 1, 1);
        Button statbtn = new Button("Statistiques");
        HBox hbBtns = new HBox(10);
        hbBtns.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtns.getChildren().add(statbtn);
        grid.add(hbBtns, 1, 3);
        Button alertbtn = new Button("Alertes");
        HBox hbBtna = new HBox(10);
        hbBtna.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtna.getChildren().add(alertbtn);
        grid.add(hbBtna, 1, 5);
        stage.setScene(scene);
        stage.show();

        graphbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    menu.starta(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        statbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });


    }
    public void main(String[] args) {
       launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}


