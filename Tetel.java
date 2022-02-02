package leltar;

/**
 *
 * @author Joe
 */
public class Tetel {
    private Integer ID;
    private String terem;
    private String nev;
    private Integer ar;
    private Integer ev;
    private String megjegyzes;

    public Tetel(Integer ID, String terem, String nev, 
                 Integer ar, Integer ev, String megjegyzes) {
        this.ID = ID;
        this.terem = terem;
        this.nev = nev;
        this.ar = ar;
        this.ev = ev;
        this.megjegyzes = megjegyzes;
    }
    
    public Tetel() {
        this.ID = null;
        this.terem = null;
        this.nev = null;
        this.ar = null;
        this.ev = null;
        this.megjegyzes = null;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTerem() {
        return terem;
    }

    public void setTerem(String terem) {
        this.terem = terem;
    }

    public String getEszkoz() {
        return nev;
    }

    public void setEszkoz(String nev) {
        this.nev = nev;
    }

    public Integer getAr() {
        return ar;
    }

    public void setAr(Integer ar) {
        this.ar = ar;
    }

    public Integer getEv() {
        return ev;
    }

    public void setEv(Integer ev) {
        this.ev = ev;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }
    
    //EXPORTÁLÁS
    public String toString(){
        String s = ID +";"+ terem +";"+ nev +";"+ ar +";"+ ev +";"+ megjegyzes;
        return s.replace("null", "");
    }

}
