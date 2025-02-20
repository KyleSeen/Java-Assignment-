/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author honokaoi
 */


import java.util.ArrayList;

public class DeliverySystem {
    private ArrayList<DeliveryRunner> runners;
    private ArrayList<DeliveryTask> tasks;

    public DeliverySystem() {
        runners = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public void addRunner(DeliveryRunner runner) {
        runners.add(runner);
    }

    public void addTask(DeliveryTask task) {
        tasks.add(task);
        assignTask(task);
    }

    private void assignTask(DeliveryTask task) {
        for (DeliveryRunner runner : runners) {
            if (runner.isAvailable()) {
                runner.assignTask(task);
                notifyUser(task.getCustomerId(), "Your order is being delivered by Runner: " + runner.getName());
                break; // Exit after assigning the task
            }
        }
    }

    private void notifyUser(String userId, String message) {
        // Implementation of notification logic here
        System.out.println("Notification to Customer ID " + userId + ": " + message);
    }
}
