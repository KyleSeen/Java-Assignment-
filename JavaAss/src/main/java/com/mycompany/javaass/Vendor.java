package com.mycompany.javaass;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendor {

    private int vendorId;
    private String vendorName;
    private String password;
    private List<Order> orders;
    private List<FoodItem> menu;
    private static boolean vendorsInitialized = false;
    private static int vendorIdCounter = 1;

    public Vendor(String vendorName, String password) {
        if (!vendorsInitialized) {
            resetVendorIdCounter();
            vendorsInitialized = true;
        }
        this.vendorId = vendorIdCounter++;
        this.vendorName = vendorName;
        this.password = password;
        this.orders = new ArrayList<>();
        this.menu = new ArrayList<>();
    }

    public static void resetVendorIdCounter() {
        vendorIdCounter = 1;
    }

    public int getVendorId() {
        return vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getVendorPassword() {
        return password;
    }

    public List<Order> getOrders() {
        return orders;
    }
    
    public List<FoodItem> getMenu() {
        return menu;
    }
    
    public void addItemToMenu(FoodItem item) {
        menu.add(item);
    }
    
    public void removeItemFromMenu(String itemName) {
        menu.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }
    
    public void manageVendorOperations() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n+----------------------------------+");
            System.out.println("| Vendor Management Menu          |");
            System.out.println("+----------------------------------+");
            System.out.println("| 1. Create Item                  |");
            System.out.println("| 2. Read/View Menu               |");
            System.out.println("| 3. Update Item                  |");
            System.out.println("| 4. Delete Item                  |");
            System.out.println("| 5. Accept/Cancel Order          |");
            System.out.println("| 6. Update Order Status          |");
            System.out.println("| 7. Check Order History          |");
            System.out.println("| 8. Read Customer Reviews        |");
            System.out.println("| 9. Revenue Dashboard            |");
            System.out.println("| 0. Logout                       |");
            System.out.println("+----------------------------------+");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createItem(scanner);
                    break;
                case 2:
                    viewMenu();
                    break;
                case 3:
                    updateItem(scanner);
                    break;
                case 4:
                    System.out.print("Enter item name to delete: ");
                    removeItemFromMenu(scanner.nextLine());
                    break;
                case 5:
                    acceptOrCancelOrder(scanner);
                    break;
                case 6:
                    updateOrderStatus(scanner);
                    break;
                case 7:
                    checkOrderHistory(scanner);
                    break;
                case 8:
                    readCustomerReviews();
                    break;
                case 9:
                    showRevenueDashboard(scanner);
                    break;
                case 0:
                    System.out.println("Logging out... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void createItem(Scanner scanner) {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter item price: ");
        double itemPrice = scanner.nextDouble();
        addItemToMenu(new FoodItem(itemName, itemPrice));
        System.out.println("Item added successfully.");
    }
    
    private void viewMenu() {
        System.out.println("Menu Items:");
        for (FoodItem item : menu) {
            System.out.println("- " + item.getName() + " ($" + item.getPrice() + ")");
        }
    }
    
    private void updateItem(Scanner scanner) {
        System.out.print("Enter item name to update: ");
        String oldName = scanner.nextLine();
        for (FoodItem item : menu) {
            if (item.getName().equalsIgnoreCase(oldName)) {
                System.out.print("Enter new name: ");
                item.setName(scanner.nextLine());
                System.out.print("Enter new price: ");
                item.setPrice(scanner.nextDouble());
                System.out.println("Item updated successfully.");
                return;
            }
        }
        System.out.println("Item not found.");
    }
    
    private void acceptOrCancelOrder(Scanner scanner) {
        //need to compile with order
        System.out.println("Accepting or Canceling Orders...");
        if (orders.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }
        
        System.out.println("Pending Orders:");
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getOrderStatus().equalsIgnoreCase("Pending")) {
                System.out.println((i + 1) + ". Order ID: " + order.getOrderId() + " - Customer: " + order.getCustomerName());
            }
        }
        
        System.out.print("Enter the order number to process: ");
        int orderIndex = scanner.nextInt() - 1;
        scanner.nextLine(); 
        
        if (orderIndex >= 0 && orderIndex < orders.size()) {
            Order selectedOrder = orders.get(orderIndex);
            System.out.print("Accept or Cancel order? (A/C): ");
            String decision = scanner.nextLine().trim().toUpperCase();
            
            if (decision.equals("A")) {
                selectedOrder.setStatus("Accepted");
                System.out.println("Order accepted successfully.");
            } else if (decision.equals("C")) {
                selectedOrder.setStatus("Canceled");
                System.out.println("Order canceled.");
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Invalid order selection.");
            }
        }
    
    @Override
    public String toString() {
        return "Vendor ID: " + vendorId + ", Vendor Name: " + vendorName;
    }
    
    
    private void updateOrderStatus(Scanner scanner) {
        if (orders.isEmpty()) {
            System.out.println("No orders available to update.");
            return;
        }
        
        System.out.println("Orders:");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println((i + 1) + ". Order ID: " + orders.get(i).getOrderId() + " - Status: " + orders.get(i).getOrderStatus());
        }
        
        System.out.print("Enter the order number to update status: ");
        int orderIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline
        
        if (orderIndex >= 0 && orderIndex < orders.size()) {
            Order selectedOrder = orders.get(orderIndex);
            System.out.println("Current Status: " + selectedOrder.getOrderStatus());
            System.out.print("Enter new status (Processing, Shipped, Delivered, Cancelled): ");
            String newStatus = scanner.nextLine().trim();
            
            selectedOrder.setStatus(newStatus);
            System.out.println("Order status updated successfully.");
        } else {
            System.out.println("Invalid order selection.");
        }
    }
    
    @Override
    public String toString() {
        return "Vendor ID: " + vendorId + ", Vendor Name: " + vendorName;
    }
    
    private void checkOrderHistory(Scanner scanner) {
        System.out.println("Order History:");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderId() + " - Date: " + order.getOrderDate() + " - Status: " + order.getOrderStatus());
        }
    }

    @Override
    public String toString() {
        return "Vendor ID: " + vendorId + ", Vendor Name: " + vendorName;
    }
    
    private void readCustomerReviews() {
        System.out.println("Reading Customer Reviews...");
        // Implement logic after getting customer.java
    }
    
    private void showRevenueDashboard() {
        double totalRevenue = 0;
        System.out.println("Revenue Dashboard:");
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                totalRevenue += item.getQuantity() * item.getPrice();
            }
        }
        System.out.println("Total Revenue: $" + totalRevenue);
    }
    
    @Override
    public String toString() {
        return "Vendor ID: " + vendorId + ", Vendor Name: " + vendorName;
    }
}
