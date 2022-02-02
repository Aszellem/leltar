//
//
package leltar;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import panel.Panel;

/**
 *
 * @author Czégel Vanessza
 */
public class FXMLDocumentController implements Initializable {

    DB ab = new DB();

    @FXML
    private TableView<Terem> tblTermek;

    @FXML
    private TableColumn<Terem, String> oTerem;

    @FXML
    private TableColumn<Terem, String> oFelhasznalas;

    @FXML
    private TextField txtTerem;

    @FXML
    private TextField txtFelhasznalas;

    @FXML
    private TableView<Eszkoz> tblEszkozok;

    @FXML
    private TableColumn<Eszkoz, String> oNev;

    @FXML
    private TableColumn<Eszkoz, String> oJellemzok;

    @FXML
    private TextField txtNev;

    @FXML
    private TextField txtJellemzok;

    @FXML
    private ComboBox<String> cbxTerem;

    @FXML
    private ComboBox<String> cbxEszkoz;

    @FXML
    private TextField txtAr;

    @FXML
    private TextField txtEv;

    @FXML
    private TextField txtMegjegyzes;

    @FXML
    private TableView<Tetel> tblLeltar;

    @FXML
    private TableColumn<Tetel, Integer> oID;

    @FXML
    private TableColumn<Tetel, String> oTerem2;

    @FXML
    private TableColumn<Tetel, String> oEszkoz2;

    @FXML
    private TableColumn<Tetel, Integer> oAr;

    @FXML
    private TableColumn<Tetel, Integer> oEv;

    @FXML
    private TableColumn<Tetel, String> oMegjegyzes;

    @FXML
    void eszkoz_hozzaad() {
        String nev = txtNev.getText().trim();
        if (nev.length() < 1 || nev.length() > 50) {
            Panel.hiba("Hiba!", "Az eszköz neve 1-50 karakter lehet!");
            txtNev.requestFocus();
            return;
        }
        String jellemzok = txtJellemzok.getText().trim();
        if (jellemzok.length() > 255) {
            Panel.hiba("Hiba!", "A jellemzők hossza legfeljebb 255 karakter lehet!!");
            txtFelhasznalas.requestFocus();
            return;
        }
        int sor = ab.eszkoz_hozzaad(nev, jellemzok);
        if (sor > 0) {
            ab.eszkoz_be(tblEszkozok.getItems(), cbxEszkoz.getItems());
            for (int i = 0; i < tblEszkozok.getItems().size(); i++) {
                if (tblEszkozok.getItems().get(i).getNev().equals(nev)) {
                    tblEszkozok.getSelectionModel().select(i);
                    break;
                }
            }
        }

    }

    @FXML
    void eszkoz_modosit() {
        int index = tblEszkozok.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        int id = tblEszkozok.getItems().get(index).getEszkozid();
        String nev = txtNev.getText().trim();
        if (nev.length() < 1 || nev.length() > 50) {
            Panel.hiba("Hiba!", "A eszköz neve 1-50 karakter lehet!");
            txtNev.requestFocus();
            return;
        }
        String jellemzok = txtJellemzok.getText().trim();
        if (jellemzok.length() > 255) {
            Panel.hiba("Hiba!", "A jellmezők hossza legfejlebb 255 karakter lehet!");
            txtJellemzok.requestFocus();
            return;
        }
        int sor = ab.eszkoz_modosit(id, nev, jellemzok);
        if (sor > 0) {
            ab.eszkoz_be(tblEszkozok.getItems(), cbxEszkoz.getItems());
            for (int i = 0; i < tblEszkozok.getItems().size(); i++) {
                if (tblEszkozok.getItems().get(i).getEszkozid() == id) {
                    tblEszkozok.getSelectionModel().select(i);
                    break;
                }

            }
        }

    }

    @FXML
    void eszkoz_torol() {
        int index = tblEszkozok.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        if (!Panel.igennem("Törlés", "Biztosan törölni akarok ezt a eszközt?")) {
            return;
        }
        int id = tblEszkozok.getItems().get(index).getEszkozid();
        int sor = ab.eszkoz_torol(id);
        if (sor > 0) {
            ab.eszkoz_be(tblEszkozok.getItems(), cbxEszkoz.getItems());
        }

    }

    @FXML
    void eszkoz_uj() {
        txtNev.setText("");
        txtJellemzok.setText("");
        tblEszkozok.getSelectionModel().select(null);
        txtNev.requestFocus();
    }

    @FXML
    void export() {
        FileChooser fv = new FileChooser();
        fv.setTitle("Exportálás");
        FileChooser.ExtensionFilter szuro = new FileChooser.ExtensionFilter("CSV fájl", "*csv");
        fv.getExtensionFilters().add(szuro);
        fv.setInitialDirectory(new File("."));
        File f = fv.showSaveDialog(null);
        if (f != null) {
            try (PrintWriter ki = new PrintWriter(f.getAbsoluteFile(), "cp1250")) {
                ki.println("Id;Terem;Név;Ár;Év;Megjegyzés");
                for (Tetel t : tblLeltar.getItems()) {
                    ki.println(t);
                }

            } catch (Exception ex) {
                Panel.hiba("Hiba", "Nem tudtam exportálni!:(");
            }
        }
    }

    @FXML
    void leltar_hozzaad() {
        if (cbxTerem.getValue() == null) {
            Panel.hiba("Hiba!", "Válaszd ki a termet!");
            cbxTerem.requestFocus();
            return;
        }
        if (cbxEszkoz.getValue() == null) {
            Panel.hiba("Hiba!", "Válaszd ki az eszközt!");
            cbxEszkoz.requestFocus();
            return;
        }
        int teremid = get_teremid(cbxTerem.getValue());
        int eszkozid = get_eszkozid(cbxEszkoz.getValue());

        Integer ar, ev;
        if (txtAr.getText().isEmpty()) {
            ar = null;
        } else {
            try {
                ar = Integer.parseInt(txtAr.getText());
                if (ar < 0) {
                    Panel.hiba("Hiba!", "Az ár nem lehet negatív!");
                    txtAr.requestFocus();
                    return;
                }
            } catch (NumberFormatException ex) {
                Panel.hiba("Hiba!", "Az ár nem szám!");
                txtAr.requestFocus();
                return;
            }
        }
        //-----------------
        if (txtEv.getText().isEmpty()) {
            ev = null;
        } else {
            try {
                ev = Integer.parseInt(txtEv.getText().trim());
                if (ev < 1980 || ev > LocalDate.now().getYear()) {
                    Panel.hiba("Hiba!", "Hibás év!");
                    txtEv.requestFocus();
                    return;
                }
            } catch (NumberFormatException ex) {
                Panel.hiba("Hiba!", "Az év nem szám!");
                txtEv.requestFocus();
                return;
            }
        }
        //-----------------
        String megjegyzes = txtMegjegyzes.getText().trim();
        int sor = ab.leltar_hozzaad(teremid, eszkozid, ar, ev, megjegyzes);
        if (sor > 0) {
            ab.leltar_be(tblLeltar.getItems());
            int max = 0, hely = 0;
            for (int i = 0; i < tblLeltar.getItems().size(); i++) {
                int id = tblLeltar.getItems().get(i).getID();
                if (id > max) {
                    max = id;
                    hely = i;
                }
            }
            tblLeltar.getSelectionModel().select(hely);
        }

    }

    @FXML
    void leltar_modosit() {
        int index = tblLeltar.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        int teremid = get_teremid(cbxTerem.getValue());
        int eszkozid = get_eszkozid(cbxEszkoz.getValue());
        Integer ar, ev;
        if (txtAr.getText().isEmpty()) {
            ar = null;
        } else {
            try {
                ar = Integer.parseInt(txtAr.getText());
                if (ar < 0) {
                    Panel.hiba("Hiba!", "Az ár nem lehet negatív!");
                    txtAr.requestFocus();
                    return;
                }
            } catch (NumberFormatException ex) {
                Panel.hiba("Hiba!", "Az ár nem szám!:(");
                txtAr.requestFocus();
                return;
            }
        }
        if (txtEv.getText().isEmpty()) {
            ev = null;
        } else {
            try {
                ev = Integer.parseInt(txtEv.getText());
                if (ev < 1980 || ev > LocalDate.now().getYear()) {
                    Panel.hiba("Hiba!", "Hibás év!");
                    txtEv.requestFocus();
                    return;
                }
            } catch (NumberFormatException ex) {
                Panel.hiba("Hiba!", "Az év nem szám!:(");
                txtEv.requestFocus();
                return;
            }
        }
        String megjegyzes = txtMegjegyzes.getText().trim();
        int leltarid = tblLeltar.getItems().get(index).getID();
        int sor = ab.leltar_modosit(leltarid, teremid, eszkozid, ar, ev, megjegyzes);

        if (sor > 0) {
            ab.leltar_be(tblLeltar.getItems());
            for (int i = 0; i < tblLeltar.getItems().size(); i++) {
                if (tblLeltar.getItems().get(i).getID() == leltarid) {
                    tblLeltar.getSelectionModel().select(i);
                    break;
                }
            }
        }
    }

    @FXML
    void leltar_torol() {
        int index = tblLeltar.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        if (!Panel.igennem("Törlés", "Biztosan törölni akarok ezt a leltárat?")) {
            return;
        }
        int id = tblLeltar.getItems().get(index).getID();
        int sor = ab.leltar_torol(id);
        if (sor > 0) {
            ab.leltar_be(tblLeltar.getItems());
        }

    }

    @FXML
    void leltar_uj() {
        cbxTerem.setValue(null);
        cbxEszkoz.setValue(null);
        txtAr.setText("");
        txtEv.setText("");
        txtMegjegyzes.setText("");
        tblLeltar.getSelectionModel().select(null);
        cbxTerem.requestFocus();
    }

    @FXML
    void terem_hozzaad() {
        String terem = txtTerem.getText().trim();
        if (terem.length() < 1 || terem.length() > 4) {
            Panel.hiba("Hiba!", "A terem azonosítójs 1-4 kerakter lehet!");
            txtTerem.requestFocus();
            return;
        }
        String felh = txtFelhasznalas.getText().trim();
        if (felh.length() > 30) {
            Panel.hiba("Hiba!", "A felhasználás legfeljebb 30 kerakter lehet!");
            txtFelhasznalas.requestFocus();
            return;
        }
        int sor = ab.terem_hozzaad(terem, felh);
        if (sor > 0) {
            ab.terem_be(tblTermek.getItems(), cbxTerem.getItems());
            for (int i = 0; i < tblTermek.getItems().size(); i++) {
                if (tblTermek.getItems().get(i).getTerem().equals(terem)) {
                    tblTermek.getSelectionModel().select(i);
                    break;
                }
            }
        }
    }

    @FXML
    void terem_modosit() {
        int index = tblTermek.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        int id = tblTermek.getItems().get(index).getTeremid();
        String terem = txtTerem.getText().trim();
        if (terem.length() < 1 || terem.length() > 4) {
            Panel.hiba("Hiba!", "A terem azonosítója 1-4 karakter lehet!");
            txtTerem.requestFocus();
            return;
        }
        String felh = txtFelhasznalas.getText().trim();
        if (felh.length() > 30) {
            Panel.hiba("Hiba!", "A felhasználás legfeljebb 30 karakter lehet!");
            txtFelhasznalas.requestFocus();
            return;
        }
        int sor = ab.terem_modosit(id, terem, felh);
        if (sor > 0) {
            ab.terem_be(tblTermek.getItems(), cbxTerem.getItems());
            for (int i = 0; i < tblTermek.getItems().size(); i++) {
                if (tblTermek.getItems().get(i).getTeremid() == id) {
                    tblTermek.getSelectionModel().select(i);
                }

            }
        }
    }

    @FXML
    void terem_torol() {
        int index = tblTermek.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        if (!Panel.igennem("Törlés", "Biztosan törölni akarok ezt a termet?")) {
            return;
        }
        int id = tblTermek.getItems().get(index).getTeremid();
        int sor = ab.terem_torol(id);
        if (sor > 0) {
            ab.terem_be(tblTermek.getItems(), cbxTerem.getItems());
        }
    }

    @FXML
    void terem_uj() {
        txtTerem.setText("");
        txtFelhasznalas.setText("");
        tblTermek.getSelectionModel().select(null);
        txtTerem.requestFocus();
    }

    private void terem_tablabol(int i) {
        if (i == -1) {
            return;
        }
        Terem t = tblTermek.getItems().get(i);
        txtTerem.setText(t.getTerem());
        if (t.getFelhasznalas() == null) {
            txtFelhasznalas.setText("");
        } else {
            txtFelhasznalas.setText(t.getFelhasznalas());
        }
    }

    private void eszkoz_tablabol(int i) {
        if (i == -1) {
            return;
        }
        Eszkoz e = tblEszkozok.getItems().get(i);
        txtNev.setText(e.getNev());
        if (e.getJellemzok() == null) {
            txtJellemzok.setText("");
        } else {
            txtJellemzok.setText(e.getJellemzok());
        }
    }

    private void leltar_tablabol(int i) {
        if (i == -1) {
            return;
        }
        Tetel t = tblLeltar.getItems().get(i);
        cbxTerem.setValue(t.getTerem());
        cbxEszkoz.setValue(t.getEszkoz());
        if (t.getAr() != null) {
            txtAr.setText(t.getAr().toString());
        } else {
            txtAr.setText("");
        }
        //---------------------------
        if (t.getEv() != null) {
            txtEv.setText(t.getEv().toString());
        } else {
            txtEv.setText("");
        }
        //---------------------------
        if (t.getMegjegyzes() != null) {
            txtMegjegyzes.setText(t.getMegjegyzes());
        } else {
            txtMegjegyzes.setText("");
        }
    }

    //A terem azonosítója, illetve az eszköz neve alapján megkeresik a hozzájuk tartozó teremid-t, illetve eszkozid-t.
    private int get_teremid(String terem) {
        int i = 0;
        while (!tblTermek.getItems().get(i).getTerem().equals(terem)) {
            i++;
        }
        return tblTermek.getItems().get(i).getTeremid();
    }

    private int get_eszkozid(String nev) {
        int i = 0;
        while (!tblEszkozok.getItems().get(i).getNev().equals(nev)) {
            i++;
        }
        return tblEszkozok.getItems().get(i).getEszkozid();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ab.terem_be(tblTermek.getItems(), cbxTerem.getItems());
        ab.eszkoz_be(tblEszkozok.getItems(), cbxEszkoz.getItems());
        ab.leltar_be(tblLeltar.getItems());

        oTerem.setCellValueFactory(new PropertyValueFactory<>("terem"));
        oFelhasznalas.setCellValueFactory(new PropertyValueFactory<>("felhasznalas"));

        oNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oJellemzok.setCellValueFactory(new PropertyValueFactory<>("jellemzok"));

        oID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        oTerem2.setCellValueFactory(new PropertyValueFactory<>("terem"));
        oEszkoz2.setCellValueFactory(new PropertyValueFactory<>("eszkoz"));
        oAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        oEv.setCellValueFactory(new PropertyValueFactory<>("ev"));
        oMegjegyzes.setCellValueFactory(new PropertyValueFactory<>("megjegyzes"));

        tblTermek.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> terem_tablabol(uj.intValue()));

        tblEszkozok.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> eszkoz_tablabol(uj.intValue()));

        tblLeltar.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> leltar_tablabol(uj.intValue()));

    }

}
