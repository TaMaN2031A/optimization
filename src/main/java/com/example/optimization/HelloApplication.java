package com.example.optimization;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       Abstract anAbstract = new Abstract();
       anAbstract.Welcome("phase one");
    }

    public static void main(String[] args) {
        launch();
    }
}