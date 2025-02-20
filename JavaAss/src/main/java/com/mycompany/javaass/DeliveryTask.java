/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author honokaoi
 */
public class DeliveryTask { //represnts a delivery task assigned to a runner
    
    private String orderId;
    private String customerId;
    private String status; // e.g., "Pending", "Accepted", "Completed", "Declined"

    public DeliveryTask(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = "Pending"; // Initially pending
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getStatus() {
        return status;
    }
}


