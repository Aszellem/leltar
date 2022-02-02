package leltar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import panel.Panel;

/**
 *
 * @author Czégel Vanessza
 */
public class DB {

    final String db = "jdbc:mysql://localhost:3306/nyilvantartas" + "?useUnicode=true&characterEncoding=UTF-8";
    final String user = "raktaros";
    final String pass = "raktaros";

    public void terem_be(ObservableList<Terem> tabla, ObservableList<String> lista) {
        String s = "SELECT * FROM termek ORDER BY terem";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ps.executeQuery();
            tabla.clear();
            lista.clear();
            while (eredmeny.next()) {
                tabla.add(new Terem(
                        eredmeny.getInt("teremid"),
                        eredmeny.getString("terem"),
                        eredmeny.getString("felhasznalas")
                ));
                lista.add(eredmeny.getString("terem"));

            }

        } catch (Exception ex) {
            Panel.hiba("Terem belvasás", ex.getMessage());
        }
    }

    public void eszkoz_be(ObservableList<Eszkoz> tabla, ObservableList<String> lista) {
        String s = "SELECT * FROM eszkozok ORDER BY nev";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ps.executeQuery();
            tabla.clear();
            lista.clear();
            while (eredmeny.next()) {
                tabla.add(new Eszkoz(
                        eredmeny.getInt("eszkozid"),
                        eredmeny.getString("nev"),
                        eredmeny.getString("jellemzok")
                ));
                lista.add(eredmeny.getString("nev"));

            }

        } catch (Exception ex) {
            Panel.hiba("Eszköz belvasás", ex.getMessage());
        }
    }

    public void leltar_be(ObservableList<Tetel> tabla) {
        String s = "SELECT leltarid,terem,nev,ar,ev,megjegyzes FROM leltar JOIN eszkozok ON leltar.eszkozid=eszkozok.eszkozid JOIN termek ON leltar.teremid=termek.teremid ORDER BY terem, nev,leltarid;";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ps.executeQuery();
            tabla.clear();
            while (eredmeny.next()) {
                Tetel t = new Tetel();
                t.setID(eredmeny.getInt("leltarid"));
                t.setTerem(eredmeny.getString("terem"));
                t.setEszkoz(eredmeny.getString("nev"));
                int ar = eredmeny.getInt("ar");
                if (!eredmeny.wasNull()) {
                    t.setAr(ar);
                }
                int ev = eredmeny.getInt("ev");
                if (!eredmeny.wasNull()) {
                    t.setEv(ev);
                }
                t.setMegjegyzes(eredmeny.getString("megjegyzes"));
                tabla.add(t);
            }

        } catch (Exception ex) {
            Panel.hiba("Leltár beolvasás", ex.getMessage());
        }
    }

    public int terem_hozzaad(String terem, String felh) {
        String s = "INSERT INTO termek (terem,felhasznalas) VALUES (?,?)";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ps.setString(1, terem);
            if (felh.equals("")) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, felh);
            }
            return ps.executeUpdate();

        } catch (Exception ex) {
            Panel.hiba("Terem hozzáadás", ex.getMessage());
            return 0;
        }

    }

    public int eszkoz_hozzaad(String nev, String jellemzok) {
        String s = "INSERT INTO eszkozok (nev,jellemzok) VALUES (?,?)";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ps.setString(1, nev);
            if (jellemzok.equals("")) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, jellemzok);
            }
            return ps.executeUpdate();

        } catch (Exception ex) {
            Panel.hiba("Terem hozzáadás", ex.getMessage());
            return 0;
        }
    }

    public int leltar_hozzaad(int teremid, int eszkozid, Integer ar, Integer ev, String megjegyzes) {
        String s = "INSERT INTO leltar(teremid,eszkozid,ar,ev,megjegyzes) VALUES(?,?,?,?,?);";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ps.setInt(1, teremid);
            ps.setInt(2, eszkozid);
            if (ar == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, ar);
            }
            if (ev == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, ev);
            }
            if (megjegyzes == null) {
                ps.setNull(5, java.sql.Types.VARCHAR);
            } else {
                ps.setString(5, megjegyzes);
            }

            return ps.executeUpdate();

        } catch (Exception ex) {
            Panel.hiba("Tétel hozzáadása", ex.getMessage());
            return 0;
        }
    }

    public int terem_modosit(int teremid, String terem, String felh) {
        String s = "UPDATE termek SET terem=?, felhasznalas=? WHERE teremid=?";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ps.setString(1, terem);
            if (felh.isEmpty()) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, felh);
            }
            ps.setInt(3, teremid);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            Panel.hiba("Terem módosítása", ex.getMessage());
            return 0;
        }
    }

    public int eszkoz_modosit(int eszkozid, String nev, String jellemzok) {
        String s = "UPDATE eszkozok SET nev=?, jellemzok=? WHERE eszkozid=?";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ps.setString(1, nev);
            if (jellemzok.isEmpty()) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, jellemzok);
            }
            ps.setInt(3, eszkozid);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            Panel.hiba("Eszköz módosítása", ex.getMessage());
            return 0;
        }
    }

    public int leltar_modosit(int leltarid, int teremid, int eszkozid, Integer ar, Integer ev, String megjegyzes) {
        String s = "UPDATE leltar SET teremid=?, eszkozid=?, ar=?,ev=?,megjegyzes=? WHERE leltarid=?";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ps.setInt(1, teremid);
            ps.setInt(2, eszkozid);
            if (ar == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, ar);
            }
            if (ev == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, ev);
            }
            if (megjegyzes == null) {
                ps.setNull(5, java.sql.Types.VARCHAR);
            } else {
                ps.setString(5, megjegyzes);
            }
            ps.setInt(6, leltarid);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            Panel.hiba("Leltár módosítása", ex.getMessage());
            return 0;
        }
    }

    public int terem_torol(int teremid) {
        String s = "DELETE FROM termek WHERE teremid=?;";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ps.setInt(1, teremid);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            Panel.hiba("Terem törlése", ex.getMessage());
            return 0;
        }
    }
    
    public int eszkoz_torol(int eszkozid) {
        String s = "DELETE FROM eszkozok WHERE eszkozid=?;";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ps.setInt(1, eszkozid);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            Panel.hiba("Eszköz törlése", ex.getMessage());
            return 0;
        }
    }
    
    public int leltar_torol(int leltarid) {
        String s = "DELETE FROM leltar WHERE leltarid=?;";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ps = kapcs.prepareStatement(s)) {
            ps.setInt(1, leltarid);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            Panel.hiba("Leltár törlése", ex.getMessage());
            return 0;
        }
    }

}
