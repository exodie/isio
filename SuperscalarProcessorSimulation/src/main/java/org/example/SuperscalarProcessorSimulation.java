package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SuperscalarProcessorSimulation extends Application {

    private ExecutorService processExecutor;
    private ExecutorService threadExecutor;
    private TextArea logTextArea;

    @Override
    public void start(Stage primaryStage) {
        processExecutor = Executors.newFixedThreadPool(3);
        threadExecutor = Executors.newFixedThreadPool(5);

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPrefSize(400, 300);

        Button startButton = new Button("Start Simulation");
        startButton.setOnAction(event -> startSimulation());

        logTextArea = new TextArea();
        logTextArea.setEditable(false);

        root.getChildren().addAll(startButton, logTextArea);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Superscalar Processor Simulation");
        primaryStage.show();
    }

    private void startSimulation() {
        log("Simulation started.");

        for (int i = 0; i < 2; i++) {
            processExecutor.execute(new ProcessRunnable(i + 1));
        }
    }

    private void log(String message) {
        logTextArea.appendText(message + "\n");
    }

    private class ProcessRunnable implements Runnable {
        private int processId;

        public ProcessRunnable(int processId) {
            this.processId = processId;
        }

        @Override
        public void run() {
            log("Process " + processId + " started.");
            for (int i = 0; i < 3; i++) {
                threadExecutor.execute(new ThreadRunnable(processId, i + 1));
            }
        }
    }

    private class ThreadRunnable implements Runnable {
        private int processId;
        private int threadId;

        public ThreadRunnable(int processId, int threadId) {
            this.processId = processId;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            log("Thread " + threadId + " of Process " + processId + " started execution.");
            // Simulate execution
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("Thread " + threadId + " of Process " + processId + " completed execution.");
        }
    }

    @Override
    public void stop() {
        processExecutor.shutdown();
        threadExecutor.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
