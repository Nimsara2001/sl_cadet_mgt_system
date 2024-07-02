package tm;

import javafx.scene.control.CheckBox;

public class allCompanyTM {
    private CheckBox checkBox =new CheckBox();
    private String comCode;

    public allCompanyTM() {
    }

    public allCompanyTM(CheckBox checkBox, String comCode) {
        this.checkBox = checkBox;
        this.comCode = comCode;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }
}
