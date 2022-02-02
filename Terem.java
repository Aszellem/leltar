package leltar;

/**
 *
 * @author Joe
 */
public class Terem {
    private int teremid;
    private String terem;
    private String felhasznalas;

    public Terem(int teremid, String terem, String felhasznalas) {
        this.teremid = teremid;
        this.terem = terem;
        this.felhasznalas = felhasznalas;
    }

    public int getTeremid() {
        return teremid;
    }

    public void setTeremid(int teremid) {
        this.teremid = teremid;
    }

    public String getTerem() {
        return terem;
    }

    public void setTerem(String terem) {
        this.terem = terem;
    }

    public String getFelhasznalas() {
        return felhasznalas;
    }

    public void setFelhasznalas(String felhasznalas) {
        this.felhasznalas = felhasznalas;
    }
}
