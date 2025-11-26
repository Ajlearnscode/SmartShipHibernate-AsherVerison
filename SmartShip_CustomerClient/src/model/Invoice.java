package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int invoiceId;
    private int customerId;
    private int shipmentId;
    private String trackingNumber;
    private double subtotal;
    private double tax;
    private double discount;
    private double surcharge;
    private double total;
    private String status;
    private LocalDateTime invoiceDate;
    
    public Invoice() {
    }
    
    public Invoice(int invoiceId, int customerId, int shipmentId, String trackingNumber,
                  double subtotal, double tax, double discount, double surcharge,
                  double total, String status, LocalDateTime invoiceDate) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.shipmentId = shipmentId;
        this.trackingNumber = trackingNumber;
        this.subtotal = subtotal;
        this.tax = tax;
        this.discount = discount;
        this.surcharge = surcharge;
        this.total = total;
        this.status = status;
        this.invoiceDate = invoiceDate;
    }
    
    public int getInvoiceId() {
        return invoiceId;
    }
    
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public int getShipmentId() {
        return shipmentId;
    }
    
    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    public double getTax() {
        return tax;
    }
    
    public void setTax(double tax) {
        this.tax = tax;
    }
    
    public double getDiscount() {
        return discount;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    public double getSurcharge() {
        return surcharge;
    }
    
    public void setSurcharge(double surcharge) {
        this.surcharge = surcharge;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }
    
    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    
    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", customerId=" + customerId +
                ", shipmentId=" + shipmentId +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", subtotal=" + subtotal +
                ", tax=" + tax +
                ", discount=" + discount +
                ", surcharge=" + surcharge +
                ", total=" + total +
                ", status='" + status + '\'' +
                ", invoiceDate=" + invoiceDate +
                '}';
    }
}

