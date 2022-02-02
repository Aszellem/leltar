package leltar;

/**
 *
 * @author Joe
 */
public class Eszkoz {
    private int eszkozid;
    private String nev;
    private String jellemzok;

    public Eszkoz(int eszkozid, String nev, String jellemzok) {
        this.eszkozid = eszkozid;
        this.nev = nev;
        this.jellemzok = jellemzok;
    }

    public int getEszkozid() {
        return eszkozid;
    }

    public void setEszkozid(int eszkozid) {
        this.eszkozid = eszkozid;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getJellemzok() {
        return jellemzok;
    }

    public void setJellemzok(String jellemzok) {
        this.jellemzok = jellemzok;
    }
       
}
