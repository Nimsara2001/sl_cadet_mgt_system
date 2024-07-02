package tm;

public class companyDetailNcoTM {
    private String regNo;
    private String rank;
    private String name;

    public companyDetailNcoTM() {
    }

    public companyDetailNcoTM(String regNo, String rank, String name) {
        this.regNo = regNo;
        this.rank = rank;
        this.name = name;
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
}
