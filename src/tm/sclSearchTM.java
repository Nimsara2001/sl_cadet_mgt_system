package tm;

import javafx.scene.control.CheckBox;

public class sclSearchTM {
    private CheckBox checkBox =new CheckBox();
    private String sclCode;
    private String sclName;

    public sclSearchTM() {
    }

    public sclSearchTM(CheckBox checkBox, String sclCode, String sclName) {
        this.checkBox = checkBox;
        this.sclCode = sclCode;
        this.sclName = sclName;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getSclName() {
        return sclName;
    }

    public void setSclName(String sclName) {
        this.sclName = sclName;
    }

    public String getSclCode() {
        return sclCode;
    }

    public void setSclCode(String sclCode) {
        this.sclCode = sclCode;
    }
}
