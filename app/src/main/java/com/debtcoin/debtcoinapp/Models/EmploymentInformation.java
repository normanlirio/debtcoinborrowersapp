package com.debtcoin.debtcoinapp.Models;

/**
 * Created by Lirio on 5/14/2018.
 */

public class EmploymentInformation extends Personal{
    private String status;
    private String empname;
    private String empnature;
    private String position;
    private String empaddress;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getEmpnature() {
        return empnature;
    }

    public void setEmpnature(String empnature) {
        this.empnature = empnature;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmpaddress() {
        return empaddress;
    }

    public void setEmpaddress(String empaddress) {
        this.empaddress = empaddress;
    }
}
