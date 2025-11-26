package model;

import java.io.Serializable;


public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int customerId;
    private int userId;
    private String companyName;
    private double accountBalance;
    private String membershipTier;
    private int totalShipments;
    
    public Customer() {
    }
    
    public Customer(int customerId, int userId, String companyName, double accountBalance, 
                   String membershipTier, int totalShipments) {
        this.customerId = customerId;
        this.userId = userId;
        this.companyName = companyName;
        this.accountBalance = accountBalance;
        this.membershipTier = membershipTier;
        this.totalShipments = totalShipments;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public double getAccountBalance() {
        return accountBalance;
    }
    
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    
    public String getMembershipTier() {
        return membershipTier;
    }
    
    public void setMembershipTier(String membershipTier) {
        this.membershipTier = membershipTier;
    }
    
    public int getTotalShipments() {
        return totalShipments;
    }
    
    public void setTotalShipments(int totalShipments) {
        this.totalShipments = totalShipments;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", userId=" + userId +
                ", companyName='" + companyName + '\'' +
                ", accountBalance=" + accountBalance +
                ", membershipTier='" + membershipTier + '\'' +
                ", totalShipments=" + totalShipments +
                '}';
    }
}