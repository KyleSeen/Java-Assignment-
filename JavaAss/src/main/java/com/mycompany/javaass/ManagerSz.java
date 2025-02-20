/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.javaass;

import java.util.List;
import java.util.ArrayList;

public class ManagerSz {
    private List<Vendor> vendors;
    private List<DeliveryRunner> runners;
    private List<CustomerComplaint> complaints;
    private List<VendorItem> vendorItems;

    public ManagerSz() {
        this.vendors = new ArrayList<>();
        this.runners = new ArrayList<>();
        this.complaints = new ArrayList<>();
        this.vendorItems = new ArrayList<>();
    }

    public void addVendor(Vendor vendor) {
        vendors.add(vendor);
    }

    public void addRunner(DeliveryRunner runner) {
        runners.add(runner);
    }

    public void addComplaint(CustomerComplaint complaint) {
        complaints.add(complaint);
    }

    public void addDiscarded(VendorItem item) {
        vendorItems.add(item);
    }

    public void monitorVendorPerformance() {
        System.out.println("Vendor Performance:");
        for (Vendor vendor : vendors) {
            System.out.println("Vendor: " + vendor.getName() + " | Revenue: $" + vendor.getRevenue());
        }
    }

    public void monitorRunnerPerformance() {
        System.out.println("Delivery Runner Performance:");
        for (DeliveryRunner runner : runners) {
            System.out.println("Runner: " + runner.getName() + " | Rating: " + runner.getRating());
        }
    }

    public void resolveCustomerComplaints() {
        System.out.println("Resolving Customer Complaints:");
        for (CustomerComplaint complaint : complaints) {
            System.out.println("Resolved Complaint: " + complaint.getComplaint());
        }
        complaints.clear();
    }

    public void removeInappropriateItems() {
        System.out.println("Removing Inappropriate Items:");
        vendorItems.removeIf(item -> !item.isAppropriate());
    }

    public static void main(String[] args) {
        ManagerSz manager = new ManagerSz();

        // Example vendors
        manager.addVendor(new Vendor("Jane", 10000));
        manager.addVendor(new Vendor("Chriss", 12000));

        // Example runners
        manager.addRunner(new DeliveryRunner("Noel", 4.9));
        manager.addRunner(new DeliveryRunner("Barb", 4.5));

        // Example complaints
        manager.addComplaint(new CustomerComplaint("When i got my order, the pizza box was already open,I want a refund"));
        manager.addComplaint(new CustomerComplaint("They always deliver the wrong item "));

        // Example vendor items
        manager.addDiscarded(new VendorItem("Banned Item", false));
        manager.addDiscarded(new VendorItem("Safe Item", true));

        // Call methods
        manager.monitorVendorPerformance();
        manager.monitorRunnerPerformance();
        manager.resolveCustomerComplaints();
        manager.removeInappropriateItems();
    }
}

// Assuming these are the required classes

class Vendor {
    private String name;
    private double revenue;

    public Vendor(String name, double revenue) {
        this.name = name;
        this.revenue = revenue;
    }

    public String getName() {
        return name;
    }

    public double getRevenue() {
        return revenue;
    }
}

class DeliveryRunner {
    private String name;
    private double rating;

    public DeliveryRunner(String name, double rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }
}

class CustomerComplaint {
    private String complaint;

    public CustomerComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getComplaint() {
        return complaint;
    }
}

class VendorItem {
    private String name;
    private boolean isAppropriate;

    public VendorItem(String name, boolean isAppropriate) {
        this.name = name;
        this.isAppropriate = isAppropriate;
    }

    public boolean isAppropriate() {
        return isAppropriate;
    }
}




