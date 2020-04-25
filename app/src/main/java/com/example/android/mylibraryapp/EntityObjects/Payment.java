package com.example.android.mylibraryapp.EntityObjects;

import java.util.Date;

public class Payment {

    private String invId;
    private Date invDate;
    private String itemDesc;
    private double amount;
    private double totAmountPaid;
    private boolean paidFlag;
    private String userID;

    // no argument constructor required for firestore operations
    public Payment() {

    }

    public Payment(String invId, Date invDate, String itemDesc, double amount, double totAmountPaid, boolean paidFlag, String userID) {
        this.invId = invId;
        this.invDate = invDate;
        this.itemDesc = itemDesc;
        this.amount = amount;
        this.totAmountPaid = totAmountPaid;
        this.paidFlag = paidFlag;
        this.userID = userID;
    }

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotAmountPaid() {
        return totAmountPaid;
    }

    public void setTotAmountPaid(double totAmountPaid) {
        this.totAmountPaid = totAmountPaid;
    }

    public boolean isPaidFlag() {
        return paidFlag;
    }

    public void setPaidFlag(boolean paidFlag) {
        this.paidFlag = paidFlag;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "invId='" + invId + '\'' +
                ", invDate=" + invDate +
                ", itemDesc='" + itemDesc + '\'' +
                ", amount=" + amount +
                ", totAmountPaid=" + totAmountPaid +
                ", paidFlag=" + paidFlag +
                ", userID=" + userID +
                '}';
    }
}