package com.tetris;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private TimerTask task;
    private Timer timer;
    protected int timeCounter;
    private Label timeLabel;

    public GameTimer(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    public void gameTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                ++timeCounter;

                String time = setTime(timeCounter);

                Platform.runLater(() -> {
                    timeLabel.setText(time);
                });

            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public String setTime(int timeCounter) {
        int hours = timeCounter / 3600;
        int minutes = (timeCounter % 3600) / 60;
        int seconds = timeCounter % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
