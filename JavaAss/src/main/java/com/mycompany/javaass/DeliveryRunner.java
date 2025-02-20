/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author honokaoi
 */
import java.util.ArrayList;

public class DeliveryRunner {
    private String name;
    private String id;
    private boolean available;
    private ArrayList<DeliveryTask> assignedTasks; // List of tasks assigned to this runner
    private double totalRevenue; // Revenue earned by the runner
    private ArrayList<String> customerReviews; // List of reviews from customers

    public DeliveryRunner(String name, String id) {
        this.name = name;
        this.id = id;
        this.available = true; // Runner starts as available
        this.assignedTasks = new ArrayList<>();
        this.totalRevenue = 0.0;
        this.customerReviews = new ArrayList<>();
    }

    public void setAvailability(boolean availability) {
        this.available = availability;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void assignTask(DeliveryTask task) {
        assignedTasks.add(task);
        setAvailability(false); // Runner becomes unavailable when assigned a task
    }

    public void viewTasks() {
        System.out.println("Current Tasks for Runner " + name + ":");
        for (DeliveryTask task : assignedTasks) {
            System.out.println("Order ID: " + task.getOrderId() + ", Status: " + task.getStatus());
        }
    }

    public void acceptTask(DeliveryTask task) {
        if (assignedTasks.contains(task)) {
            task.updateStatus("In Progress");
            System.out.println("Task with Order ID: " + task.getOrderId() + " accepted.");
        } else {
            System.out.println("Task not found in your assigned list.");
        }
    }

    public void declineTask(DeliveryTask task) {
        if (assignedTasks.contains(task)) {
            task.updateStatus("Declined");
            assignedTasks.remove(task);
            setAvailability(true); // Runner becomes available again
            System.out.println("Task with Order ID: " + task.getOrderId() + " declined.");
        } else {
            System.out.println("Task not found in your assigned list.");
        }
    }

    public void updateTaskStatus(DeliveryTask task, String status) {
        if (assignedTasks.contains(task)) {
            task.updateStatus(status);
            if (status.equals("Completed")) {
                totalRevenue += 5.0; // Assume a fixed delivery fee per order
                setAvailability(true); // Runner becomes available after completing the task
                System.out.println("Task with Order ID: " + task.getOrderId() + " marked as Completed.");
            }
        } else {
            System.out.println("Task not found in your assigned list.");
        }
    }

    public void viewTaskHistory() {
        System.out.println("Task History for Runner " + name + ":");
        for (DeliveryTask task : assignedTasks) {
            System.out.println("Order ID: " + task.getOrderId() + ", Status: " + task.getStatus());
        }
    }

    public void addCustomerReview(String review) {
        customerReviews.add(review);
    }

    public void viewCustomerReviews() {
        System.out.println("Customer Reviews for Runner " + name + ":");
        for (String review : customerReviews) {
            System.out.println("- " + review);
        }
    }

    public void viewRevenueDashboard() {
        System.out.println("Total Revenue for Runner " + name + ": $" + totalRevenue);
    }
}



