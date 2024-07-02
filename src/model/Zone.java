package model;

public class Zone {
    private String zoneCode;
    private String zoneName;
    private int totalScl;

    public Zone() {
    }

    public Zone(String zoneCode, String zoneName, int totalScl) {
        this.zoneCode = zoneCode;
        this.zoneName = zoneName;
        this.totalScl = totalScl;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getTotalScl() {
        return totalScl;
    }

    public void setTotalScl(int totalScl) {
        this.totalScl = totalScl;
    }
}
