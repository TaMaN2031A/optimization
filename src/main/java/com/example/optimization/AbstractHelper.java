package com.example.optimization;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AbstractHelper {

        boolean isInteger(String in)
        {
            try{
                int age = Integer.parseInt(in);
                return true;
            } catch (NumberFormatException e){
                return false;
            }
        }
        boolean isDouble(String in)
        {
            try{
                double age = Double.parseDouble(in);
                return true;
            } catch (NumberFormatException e){
                return false;
            }
        }

        void displayError(String title, String message)
        {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setMinWidth(200);
            Label label = new Label();
            label.setText(message);
            Button close = new Button("Return");
            close.setOnAction(e->stage.close());
            VBox vBox = new VBox(10);
            vBox.getChildren().addAll(label, close);
            vBox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }


