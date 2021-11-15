package com.ttn.ecommerce.domain;


import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "seller")
@PrimaryKeyJoinColumn(name = "user_id")
public class Seller extends User {

    private String gst;

    private String company_name;
    private String company_contact;

    public Seller() {
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_contact() {
        return company_contact;
    }

    public void setCompany_contact(String company_contact) {
        this.company_contact = company_contact;
    }
}