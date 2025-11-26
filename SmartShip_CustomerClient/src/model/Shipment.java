package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Shipment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int shipmentId;
    private int userId;
    private String senderInfo;
    private String recipientInfo;
    private String recipientAddress;
    private double weight;
    private String dimensions;
    private String packageType;
    private int zone;
    private String status;
    private String trackingNumber;
    private double cost;
    private String paymentStatus;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private Integer vehicleId;
    
    public Shipment() {
    }
    
    public Shipment(int shipmentId, int userId, String senderInfo, String recipientInfo,
                   String recipientAddress, double weight, String dimensions, String packageType,
                   int zone, String status, String trackingNumber, double cost,
                   String paymentStatus, String paymentMethod, LocalDateTime createdAt) {
        this.shipmentId = shipmentId;
        this.userId = userId;
        this.senderInfo = senderInfo;
        this.recipientInfo = recipientInfo;
        this.recipientAddress = recipientAddress;
        this.weight = weight;
        this.dimensions = dimensions;
        this.packageType = packageType;
        this.zone = zone;
        this.status = status;
        this.trackingNumber = trackingNumber;
        this.cost = cost;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
    }
    
    public int getShipmentId() {
        return shipmentId;
    }
    
    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getSenderInfo() {
        return senderInfo;
    }
    
    public void setSenderInfo(String senderInfo) {
        this.senderInfo = senderInfo;
    }
    
    public String getRecipientInfo() {
        return recipientInfo;
    }
    
    public void setRecipientInfo(String recipientInfo) {
        this.recipientInfo = recipientInfo;
    }
    
    public String getRecipientAddress() {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public String getDimensions() {
        return dimensions;
    }
    
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
    
    public String getPackageType() {
        return packageType;
    }
    
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
    
    public int getZone() {
        return zone;
    }
    
    public void setZone(int zone) {
        this.zone = zone;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public double getCost() {
        return cost;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Integer getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    @Override
    public String toString() {
        return "Shipment{" +
                "shipmentId=" + shipmentId +
                ", userId=" + userId +
                ", senderInfo='" + senderInfo + '\'' +
                ", recipientInfo='" + recipientInfo + '\'' +
                ", recipientAddress='" + recipientAddress + '\'' +
                ", weight=" + weight +
                ", dimensions='" + dimensions + '\'' +
                ", packageType='" + packageType + '\'' +
                ", zone=" + zone +
                ", status='" + status + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", cost=" + cost +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", createdAt=" + createdAt +
                ", vehicleId=" + vehicleId +
                '}';
    }
}
