package com.example.optimization;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
// IDEA: e3ml center tany 7ot feh el Qeyam
/*
* You have further Intial guess, stopping condition+niterations+absolutereerror, format of LU for abdo */
public class Manager
{
    Stage st = new Stage();
    ManagerHelper managerHelper = new ManagerHelper();
    Solver solver = new Solver();
    int n, precision = 10;
    void Welcome()
    {
        Button WelcomeButton = new Button("Welcome!");
        WelcomeButton.setText("Hello!");
        WelcomeButton.setOnAction(e ->
                screen()
        );
        StackPane layout = new StackPane();
        layout.getChildren().add(WelcomeButton);
        Scene scene = new Scene(layout, 300, 300);
        st.setScene(scene);
        st.show();
    }
    void screen()
    {

        /** Container arrays */
        ArrayList<Node> container = new ArrayList<>();

        /** First Block: Number of equations */
        Node noEquation = new Label("Insert the number of equations!");
        container.add(noEquation);
        TextField noequations = new TextField();
        container.add(noequations);
        Button noEq = new Button("insert");
        container.add(noEq);

        /** Second Block: System */
        Node Equation = new Label("Insert the equations!");
        container.add(Equation);
        TextField equations = new TextField();
        container.add(equations);
        Button Eq = new Button("insert");
        container.add(Eq);

        /** Third Block: Method */
        Node methodPick = new Label("Insert the method you want");
        container.add(methodPick);
        ChoiceBox<String> methodpick = new ChoiceBox<>();
        methodpick.getItems().addAll("Gauss Elimination", "Gauss Jordan", "LU Decomposition"
                , "Gauss Seidil", "Jacobi Iteration");
        methodpick.setValue("Gauss Elimination");
        container.add(methodpick);
        Button methpick = new Button("insert");
        container.add(methpick);

        /** Fourth Block: Precision */
        Node parametersGet = new Label("Insert the precision you want");
        container.add(parametersGet);
        TextField parametersget = new TextField();
        parametersget.setText("10");
        container.add(parametersget);
        Button pg = new Button("insert");
        container.add(pg);

        /** Fifth Block: Initial Guess */
        Node initialGuess = new Label("Insert the initial guess you want");
        container.add(initialGuess);
        TextField initialguess = new TextField();
        container.add(initialguess);
        Button ing = new Button("insert");
        container.add(ing);

        /** Sixth Block: Stopping condition */
        Node stopPick = new Label("Insert the condition you want to terminate the iterations");
        container.add(stopPick);
        ChoiceBox<String> stoppick = new ChoiceBox<>();
        stoppick.getItems().addAll("Number of Iterations", "Absolute Relative Error");
        stoppick.setValue("Number of Iterations");
        container.add(stoppick);
        Button stp = new Button("insert");
        container.add(stp);

        /**  Seventh Block: case no. Iterations */
        Node noIterations = new Label("Insert the no. Iterations you want");
        container.add(noIterations);
        TextField noiterations = new TextField();
        noiterations.setText("10");
        container.add(noiterations);
        Button noi = new Button("insert");
        container.add(noi);

        /**  Seventh Block: case absolute relative error */
        Node absRelative = new Label("Insert the absolute relative error you want");
        container.add(absRelative);
        TextField absrelative = new TextField();
        absrelative.setText(".0001");
        container.add(absrelative);
        Button absrl = new Button("insert");
        container.add(absrl);

        /** Eighth Block: LU format */
        Node formatLu = new Label("Insert the formation of LU you want");
        container.add(formatLu);
        ChoiceBox<String> formatlu = new ChoiceBox<>();
        formatlu.getItems().addAll("Cholesky Form", "Downlittle Form", " Crout Form");
        formatlu.setValue("Cholesky Form");
        container.add(formatlu);
        Button forlu = new Button("insert");
        container.add(forlu);

        /** Ninth Block: solve */
        Node finSolve = new Label("Finaly, we're here!");
        container.add(finSolve);
        Node finsolve = new Label("Please press solve to solve");
        container.add(finsolve);
        Button fslv = new Button("insert");
        container.add(fslv);

        /** Initial State of screen */
        for(int i = 3; i < container.size(); i++)
            container.get(i).setVisible(false);

        BorderPane borderPane = new BorderPane();
        VBox center = new VBox();
        /** Showing the screen */
        center.getChildren().addAll(noEquation, noequations, noEq, equations, Equation, Eq,
                methodPick, methodpick, methpick, parametersGet, parametersget, pg,
                initialGuess, initialguess, ing, stopPick, stoppick, stp, noIterations, noiterations, noi,
                absRelative, absrelative, absrl, formatLu, formatlu, forlu,
                finSolve, finsolve, fslv); // parameters : precision
        borderPane.setCenter(center);
        Scene scene = new Scene(borderPane, 500, 1000);
        st.setScene(scene);
        st.show();

        /** Events handling */

        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                String x = noequations.getText();
                System.out.println(x);
                if(x.equals(""))
                    x = "0";
                if (managerHelper.isInteger(x))
                {
                    if(Integer.parseInt(x) <= 0)
                    {
                        managerHelper.displayError("non-Positive Integer?", "This is a joke, right? please insert a number bigger than one");

                    }
                    else {

                        n = Integer.parseInt(x);
                        System.out.println(n);
                        managerHelper.displayError("fine! :)", "Now it's time to enter the equations, press next Please!");
                        /** Disabling all  */
                        for(int i = 0; i < container.size(); i++)
                            container.get(i).setVisible(false);
                        /**Enabling second part*/
                        for(int i = 3; i < 6; i++)
                            container.get(i).setVisible(true);
                    }
                }
                else {

                    for(int i = 3; i < container.size(); i++)
                        container.get(i).setDisable(true);
                    managerHelper.displayError("Error :(", "Wrong number format, Integer Needed.");
                }
            }
        };
        /* Handling the button of the equation itself */
        ArrayList<Double> row = new ArrayList();
        ArrayList<ArrayList<Double>> All = new ArrayList<>();
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>(){
            int i = 0;
            int j = 0;
            public void handle(ActionEvent e) {
                String x = equations.getText();
                System.out.println(x);
                if (x.equals(""))
                    x = "0";
                if (i < n) {
                    if (managerHelper.isDouble(x)) {
                        j++;
                        row.add(Double.parseDouble(x));
                        System.out.println(All);
                        if (j == n+1) {
                            j = 0;
                            All.add((ArrayList<Double>) row.clone());
                            row.clear();
                            i++;
                        }
                        if(i == n) {
                            managerHelper.displayError("fine! :)", "Now it's time to pick the method, press next Please!");
                            for(int i = 0; i < container.size(); i++)
                                container.get(i).setVisible(false);
                            for (int i = 6; i < 9; i++)
                                container.get(i).setVisible(true);
                        }
                        /**Enabling second part*/


                    } else {
                        managerHelper.displayError("Error :(", "Wrong number format, Double Needed.");
                    }
                }
            }
        };
        /* the method select button and its dependencies */
        String[] method = {"Gauss Elimination"};
        EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                method[0] = methodpick.getValue();
                System.out.println(method[0]);
                for(int i = 0; i < container.size(); i++)
                    container.get(i).setVisible(false);
                for (int i = 9; i < 12; i++)
                    container.get(i).setVisible(true);
            }
        };
        /* Getting the precision */
        EventHandler<ActionEvent> event4 = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                String x = parametersget.getText();
                System.out.println(x);
                if(x.equals(""))
                    x = "0";
                if (managerHelper.isInteger(x))
                {
                    if(Integer.parseInt(x) <= 0)
                    {
                        managerHelper.displayError("non-Positive Integer?", "This is a joke, right? please insert a number bigger than one");

                    }
                    else {

                        precision = Integer.parseInt(x);
                        System.out.println(n);
                        managerHelper.displayError("fine! :)", "Now it's time for the next step");
                        /**Enabling second part*/
                        for(int i = 0; i < container.size(); i++)
                            container.get(i).setVisible(false);
                        if(method[0].equals("Gauss Seidil") || method[0].equals("Jacobi Iteration")) {
                            for (int i = 12; i < 15; i++)
                                container.get(i).setVisible(true);
                        }
                        else if(method[0].equals("LU Decomposition"))
                        {
                            for (int i = 24; i < 27; i++)
                                container.get(i).setVisible(true);
                        }
                        else // Solution Directly for the boys: Gauss, Jordan
                        {
                            for (int i = 27; i < 30; i++) // Solve
                                container.get(i).setVisible(true);
                        }
                    }
                }
                else{
                    for(int i = 3; i < container.size(); i++)
                        container.get(i).setDisable(true);
                    managerHelper.displayError("Error :(", "Wrong number format, Integer Needed.");
                }
            }
        };
        /* Getting the initial guess for seidel, jacobi */
        ArrayList<Double> initGuess = new ArrayList<>();
        EventHandler<ActionEvent> event5 = new EventHandler<ActionEvent>(){
            int i = 0;
            public void handle(ActionEvent e) {
                String x = initialguess.getText();
                System.out.println(x);
                if(x.equals(""))
                    x = "0";
                if(i < n) {
                    if (managerHelper.isDouble(x)) {
                        initGuess.add(Double.parseDouble(x));
                        i++;
                        if(i==n)
                        {
                            managerHelper.displayError("fine! :)", "Now it's time to choose how to terminate the iterations");
                            for(int i = 0; i < container.size(); i++)
                                container.get(i).setVisible(false);
                            for(int i = 15; i < 18; i++)
                                container.get(i).setVisible(true);
                        }
                    }
                    else {
                        managerHelper.displayError("Error :(", "Wrong number format, Double Needed.");
                    }

                }
                else {

                }

            }
        };
        /* Getting the stopping condition for seidel, jacobi */
        String[] stopcond = {"Number of Iterations"};
        EventHandler<ActionEvent> event6 = new EventHandler<ActionEvent>(){

            public void handle(ActionEvent e) {
                stopcond[0] = stoppick.getValue();
                System.out.println(stopcond[0]);
                for(int i = 0; i < container.size(); i++)
                    container.get(i).setVisible(false);
                if(stopcond[0].equals("Number of Iterations"))
                {
                    for(int i = 18; i < 21; i++)
                        container.get(i).setVisible(true);
                }
                else // Absoluter realive Error
                {
                    for(int i = 21; i < 24; i++)
                        container.get(i).setVisible(true);
                }

            }
        };
        /* Case 1: no Iterations */
        int[] noiterat = {10};
        EventHandler<ActionEvent> event7 = new EventHandler<ActionEvent>(){

            public void handle(ActionEvent e) {
                String x = noiterations.getText();
                System.out.println(x);
                if(x.equals(""))
                    x = "0";
                if (managerHelper.isInteger(x))
                {
                    if(Integer.parseInt(x) <= 0)
                    {
                        managerHelper.displayError("non-Positive Integer?", "This is a joke, right? please insert a number bigger than one");
                    }
                    else {
                        noiterat[0] = Integer.parseInt(x);
                        System.out.println(noiterat[0]);
                        managerHelper.displayError("fine! :)", "Now it's time for seeing the solution");
                        /**Enabling second part*/
                        for(int i = 0; i < container.size(); i++)
                            container.get(i).setVisible(false);
                        for (int i = 27; i < 30; i++) // Solve
                            container.get(i).setVisible(true);
                        }
                }
                else{
                    managerHelper.displayError("Error :(", "Wrong number format, Integer Needed.");
                }
            }
        };
        /* Case 2: absolute relative error */
        double[] absrelat = {.0001};
        EventHandler<ActionEvent> event8 = new EventHandler<ActionEvent>(){

            public void handle(ActionEvent e) {
                String x = absrelative.getText();
                System.out.println(x);
                if(x.equals(""))
                    x = "0";
                if (managerHelper.isDouble(x))
                {
                    if(Double.parseDouble(x) == 0)
                    {
                        managerHelper.displayError("non-Positive Integer?", "This is a joke, right? please insert a number bigger than one");
                    }
                    else {
                        if(Double.parseDouble(x) < 0)
                            absrelat[0] = -Double.parseDouble(x);
                        else
                            absrelat[0] = Double.parseDouble(x);
                        System.out.println(absrelat[0]);
                        managerHelper.displayError("fine! :)", "Now it's time for seeing the solution");
                        /**Enabling second part*/
                        for(int i = 0; i < container.size(); i++)
                            container.get(i).setVisible(false);
                        for (int i = 27; i < 30; i++) // Solve
                            container.get(i).setVisible(true);
                    }
                }
                else{
                    managerHelper.displayError("Error :(", "Wrong number format, Double Needed.");
                }
            }
        };
        /* LU format picker*/
        String[] format = {"Cholesky Form"};
        EventHandler<ActionEvent> event9 = new EventHandler<ActionEvent>(){

            public void handle(ActionEvent e) {
                format[0] = formatlu.getValue();
                System.out.println(format[0]);
                for(int i = 0; i < container.size(); i++)
                    container.get(i).setVisible(false);
                for(int i = 27; i < 30; i++)
                    container.get(i).setVisible(true);
            }
        };
        /* Solve stage */
        ArrayList<ArrayList<Double>>[] solution = new ArrayList[]{new ArrayList<>()};
        EventHandler<ActionEvent> event10 = new EventHandler<ActionEvent>(){

            public void handle(ActionEvent e) {
                for(int i = 0; i < container.size(); i++)
                    container.get(i).setVisible(false);
                if(method[0].equals("Gauss Elimination"))
                    solution[0] = solver.gaussElimination(All, precision);
                else if(method[0].equals("Gauss Jordan"))
                    solution[0] = solver.gaussJordanelimination(All, precision);
                System.out.println(solution[0]);
                /*
                    Rest of el regala
                */

            }
        };
        /** Handling each button */
        noEq.setOnAction(event1);
        Eq.setOnAction(event2);
        methpick.setOnAction(event3);
        pg.setOnAction(event4);
        ing.setOnAction(event5);
        stp.setOnAction(event6);
        noi.setOnAction(event7);
        absrl.setOnAction(event8);
        forlu.setOnAction(event9);
        fslv.setOnAction(event10);
    }
}
