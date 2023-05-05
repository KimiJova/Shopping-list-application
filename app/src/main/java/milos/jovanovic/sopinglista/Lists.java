package milos.jovanovic.sopinglista;

public class Lists {

    private String name;
    private String share;

    public Lists(String n, String s) {
        this.name = n;
        this.share = s;
    }

    public String getName() {
        return name;
    }

    public String getShare() {
        return share;
    }

    void setName(String n) {
        this.name = n;
    }

    void setShare(String s) {
        this.share = s;
    }

}
