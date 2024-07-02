package model;

public class School {
    private String sclCode;
    private String name;
    private String address;
    private String contactNo;
    private String eMail;
    private String zoneCode;

    public School() {
    }

    public School(String sclCode, String name, String address, String contactNo, String eMail, String zoneCode) {
        this.sclCode = sclCode;
        this.name = name;
        this.address = address;
        this.contactNo = contactNo;
        this.eMail = eMail;
        this.zoneCode = zoneCode;
    }

    public String getSclCode() {
        return sclCode;
    }

    public void setSclCode(String sclCode) {
        this.sclCode = sclCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }
}
