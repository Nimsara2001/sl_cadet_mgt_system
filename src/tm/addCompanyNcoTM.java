package tm;

import com.jfoenix.controls.JFXButton;

public class addCompanyNcoTM {
    private String regNo;
    private String rank;
    private String name;
    private JFXButton remove=new JFXButton("Remove");

    public addCompanyNcoTM() {
    }

    public addCompanyNcoTM(String regNo, String rank, String name, JFXButton remove) {
        this.regNo = regNo;
        this.rank = rank;
        this.name = name;
        this.remove = remove;
        remove.setStyle("-fx-background-color:#6f89a9; -fx-background-radius: 10; -fx-font-size: 11;-fx-pref-height: 15");
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JFXButton getRemove() {
        return remove;
    }

    public void setRemove(JFXButton remove) {
        this.remove = remove;
    }
}
