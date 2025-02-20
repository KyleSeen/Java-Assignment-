/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.javaass;

import java.io.*;
import java.util.*;

public class OrderHistory {
    private static final String FILE_PATH = "order_history.txt";
    private List<Order> orders;

    public OrderHistory() {
        this.orders = new ArrayList<>();
        this.orders = getOrderHistory(); // Load existing orders from file
    }

    public void addOrder(Order order) {
        orders.add(order);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(order.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }

    public List<Order> getOrderHistory() {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orders.add(Order.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error reading order history: " + e.getMessage());
        }
        return orders;
    }

    public void displayOrderHistory() {
        if (orders.isEmpty()) {
            System.out.println("No order history found.");
            return;
        }
        System.out.println("Order History:");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Customer Name: " + order.getCustomerName());
            System.out.println("Food Item: " + order.getFoodItem());
            System.out.println("Amount: $" + order.getAmount());
            System.out.println("Status: " + order.getStatus());
            System.out.println("---------------------------");
        }
    }

    public static void main(String[] args) {
        OrderHistory history = new OrderHistory();
        
        // Add an example order
        Order newOrder = new Order("101", "John Doe", "Pizza", 12.99, "Delivered");
        history.addOrder(newOrder);

        // Display all orders
        history.displayOrderHistory();
    }

    // Embedded Order class inside OrderHistory
    public static class Order {
        private String orderId;
        private String customerName;
        private String foodItem;
        private double amount;
        private String status;

        public Order(String orderId, String customerName, String foodItem, double amount, String status) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.foodItem = foodItem;
            this.amount = amount;
            this.status = status;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getFoodItem() {
            return foodItem;
        }

        public double getAmount() {
            return amount;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return orderId + "," + customerName + "," + foodItem + "," + amount + "," + status;
        }

        public static Order fromString(String line) {
            String[] parts = line.split(",");
            if (parts.length != 5) {
                throw new IllegalArgumentException("Invalid order format");
            }
            return new Order(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]);
        }
    }
}
