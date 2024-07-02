package tm;

public class ZoneSchoolTM {
    private String sclCode;
    private String sclName;
    private String sclAddress;
    private int numOfPlatoon;

    public ZoneSchoolTM() {
    }

    public ZoneSchoolTM(String sclCode, String sclName, String sclAddress, int numOfPlatoon) {
        this.sclCode = sclCode;
        this.sclName = sclName;
        this.sclAddress = sclAddress;
        this.numOfPlatoon = numOfPlatoon;
    }

    public String getSclCode() {
        return sclCode;
    }

    public void setSclCode(String sclCode) {
        this.sclCode = sclCode;
    }

    public String getSclName() {
        return sclName;
    }

    public void setSclName(String sclName) {
        this.sclName = sclName;
    }

    public String getSclAddress() {
        return sclAddress;
    }

    public void setSclAddress(String sclAddress) {
        this.sclAddress = sclAddress;
    }

    public int getNumOfPlatoon() {
        return numOfPlatoon;
    }

    public void setNumOfPlatoon(int numOfPlatoon) {
        this.numOfPlatoon = numOfPlatoon;
    }
}
