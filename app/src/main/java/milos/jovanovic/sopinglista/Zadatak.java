package milos.jovanovic.sopinglista;

public class Zadatak {

    private String naslov;
    private Boolean provera;

    private String id;

    public Zadatak(String naslov, Boolean provera, String id) {
        this.naslov = naslov;
        this.provera = provera;
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public Boolean getProvera() {
        return provera;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public void setProvera(Boolean provera) {
        this.provera = provera;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
