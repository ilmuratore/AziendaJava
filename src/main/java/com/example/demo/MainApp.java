package com.example.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApp extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        Thread springThread = new Thread(() ->
                springContext = new SpringApplicationBuilder(SpringBootApp.class).run()
        );
        springThread.setDaemon(true); // non blocca la chiusura del processo
        springThread.start();
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("JavaFX: UI in avvio...");
        StackPane root = new StackPane(new Label("Hello world"));
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("AziendaJava (JavaFX + Spring Boot)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Ferma correttamente Spring e JavaFX
        if (springContext != null) {
            springContext.close();
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
