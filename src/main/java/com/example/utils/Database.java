package com.example.utils;

import com.example.ApplicationProject;
import com.example.filters.StudentFilter;
import com.example.filters.KluboviStudenataFilter;
import com.example.dogadaji.*;
import com.example.enumeracije.Fakultet;
import com.example.enumeracije.Grad;
import com.example.enumeracije.Spol;
import com.example.iznimke.NepostojeciIDException;
import com.example.model.KlubStudenata;
import com.example.model.Student;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Database {

    public synchronized static Connection connectToDatabase() throws SQLException, IOException {
        Properties svojstva = new Properties();
        svojstva.load(new FileReader("conf//database.properties"));
        String urlBazePodataka = svojstva.getProperty("databaseUrl");
        String korisnickoIme = svojstva.getProperty("username");
        String lozinka = svojstva.getProperty("password");
        return DriverManager.getConnection(urlBazePodataka,
                korisnickoIme,lozinka);
    }
    public static void disconnectFromDatabase(Connection veza, Statement stmt, ResultSet rs) {
        try {
            veza.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Student> getStudentiFilter (StudentFilter filter) {
        List<Student> studenti = new ArrayList<>();
        Map<Integer, Object> queryParams = new HashMap<>();
        Integer position = 1;

        try (Connection connection = connectToDatabase()) {
            String querry = "SELECT * FROM STUDENTI WHERE 1=1 ";

            if(!filter.getIme().isEmpty()) {
                querry += " AND IME = ?";
                queryParams.put(position++, filter.getIme());
            }
            if(Optional.ofNullable(filter.getFakultet()).isPresent()) {
                querry += " AND FAKULTET_ID = ?";
                queryParams.put(position++, filter.getFakultet().getRedniBroj());
            }
            if(Optional.ofNullable(filter.getGodinaStudijaString()).isPresent()) {
                querry += " AND GODINA_STUDIJA = ?";
                queryParams.put(position, Integer.valueOf(filter.getGodinaStudijaString()));
            }

            PreparedStatement pstmt = connection.prepareStatement(querry);

            for (Integer key : queryParams.keySet()) {
                if (queryParams.get(key) instanceof String str) pstmt.setString(key, str);
                else if (queryParams.get(key) instanceof Integer num) pstmt.setInt(key, num);
            }

            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();

            mapResultSetToStudenti(rs, studenti);

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            System.out.println(e.getMessage());
        }

        return studenti;
    }

    private static void mapResultSetToStudenti(ResultSet rs, List<Student> studenti) throws SQLException {
        while(rs.next()) {
            Integer id = rs.getInt("ID");
            String ime = rs.getString("IME");
            Integer godinaStudija = rs.getInt("GODINA_STUDIJA");
            int spolID = rs.getInt("SPOL_ID");
            Spol spol = Spol.MUSKO;
            if (spolID == 2) spol = Spol.ZENSKO;
            String jmbag = rs.getString("JMBAG");
            BigDecimal prosjek = rs.getBigDecimal("PROSJEK_OCJENA");
            Fakultet fakultet = getFakultetByID(rs);
            Student student = new Student.Builder(id, ime)
                    .godinaStudijaBuilder(godinaStudija)
                    .spolBuilder(spol)
                    .jmbagBuilder(jmbag)
                    .prosjekOcjenaBuilder(prosjek)
                    .fakultetBuilder(fakultet)
                    .build();

            studenti.add(student);
        }
    }

    private static Fakultet getFakultetByID(ResultSet rs) throws SQLException {
        int fakultetID = rs.getInt("FAKULTET_ID");
        return switch (fakultetID) {
            case 2 -> Fakultet.TVZ;
            case 3 -> Fakultet.FOI;
            case 4 -> Fakultet.LIBERTAS;
            case 5 -> Fakultet.ETF;
            case 6 -> Fakultet.FESB;
            default -> Fakultet.FER;
        };
    }

    public static Student getStudentByID (Integer id) throws NepostojeciIDException {
        List<Student> studenti = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM STUDENTI WHERE ID = ? "
            );
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            mapResultSetToStudenti(rs, studenti);

            if(studenti.isEmpty()) throw new NepostojeciIDException();

        }catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            System.out.println(e.getMessage());
        }

        return studenti.getFirst();
    }

    public static List<KlubStudenata> getKluboviStudenataFilter (KluboviStudenataFilter filter) {
        List<KlubStudenata> kluboviStudenata = new ArrayList<>();
        Map<Integer, Object> queryParams = new HashMap<>();
        Integer position = 1;

        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM KLUBOVI_STUDENATA WHERE 1=1";

            if(!filter.getNazivKluba().isEmpty()) {
                sqlQuery += " AND NAZIV = ?";
                queryParams.put(position++, filter.getNazivKluba());
            }
            if(Optional.ofNullable(filter.getFakultet()).isPresent()) {
                sqlQuery += " AND FAKULTET_ID = ?";
                queryParams.put(position, filter.getFakultet().getRedniBroj());
            }

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);

            for (Integer key : queryParams.keySet()) {
                if (queryParams.get(key) instanceof String str) pstmt.setString(key, str);
                else if (queryParams.get(key) instanceof Integer num) pstmt.setInt(key, num);
            }

            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            mapResultSetToKluboviStudenata(rs, kluboviStudenata);

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            System.out.println(e.getMessage());
        }

        return kluboviStudenata;
    }

    public static void mapResultSetToKluboviStudenata (ResultSet rs, List<KlubStudenata> kluboviStudenata) throws SQLException {
        while (rs.next()) {
            Integer id = rs.getInt("ID");
            String nazivKluba = rs.getString("NAZIV");
            Fakultet fakultet = getFakultetByID(rs);
            Grad grad = getGradByID(rs);
            kluboviStudenata.add(new KlubStudenata(id, nazivKluba, fakultet, grad));
        }
    }

    private static Grad getGradByID(ResultSet rs) throws SQLException {
        int gradID = rs.getInt("GRAD_ID");
        return switch (gradID) {
            case 2 -> Grad.SPLIT;
            case 3 -> Grad.VARAZDIN;
            case 4 -> Grad.RIJEKA;
            case 5 -> Grad.OSIJEK;
            default -> Grad.ZAGREB;
        };
    }

    public static List<KlubStudenata> getKluboviStudenata() {
        List<KlubStudenata> kluboviStudenata = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM KLUBOVI_STUDENATA";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();

            mapResultSetToKluboviStudenata(rs, kluboviStudenata);

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            System.out.println(e.getMessage());
        }

        return kluboviStudenata;
    }

    public static List<Predavanje> getPredavanja() {
        List<Predavanje> predavanja = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM PREDAVANJA";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();

            mapResultSetToPredavanja(rs, kluboviStudenata, predavanja);

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            throw new RuntimeException(e);
        }

        return predavanja;
    }

    private static void mapResultSetToPredavanja(ResultSet rs, List<KlubStudenata> kluboviStudenata, List<Predavanje> predavanja) throws SQLException {
        while (rs.next()) {
            DogadajZajednickiPodaci helper = getZajednickiPodaci(kluboviStudenata, rs);

            String tema = rs.getString("TEMA_PREDAVANJA");

            predavanja.add(new Predavanje(
                    helper.getId(),
                    helper.getNaziv(),
                    helper.getKlubOrganizator(),
                    helper.getLokacijaOdrzavanja(),
                    helper.getDatumPocetka(),
                    helper.getDatumZavrsetka(),
                    tema
                    ));
        }
    }

    public static List<Projekt> getProjekti() {
        List<Projekt> projekti = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM PROJEKTI";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();

            mapResultSetToProjekti(rs, kluboviStudenata, projekti);

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            throw new RuntimeException(e);
        }

        return projekti;
    }

    private static void mapResultSetToProjekti(ResultSet rs, List<KlubStudenata> kluboviStudenata, List<Projekt> projekti) throws SQLException {
        while (rs.next()) {
            DogadajZajednickiPodaci helper = getZajednickiPodaci(kluboviStudenata, rs);

            String tema = rs.getString("TEMA_PROJEKTA");

            projekti.add(new Projekt(
                    helper.getId(),
                    helper.getNaziv(),
                    helper.getKlubOrganizator(),
                    helper.getLokacijaOdrzavanja(),
                    helper.getDatumPocetka(),
                    helper.getDatumZavrsetka(),
                    tema
                    ));
        }
    }

    public static List<Natjecanje> getNatjecanja() {
        List<Natjecanje> natjecanja = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM NATJECANJA";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();

            mapResultSetToNatjecanja(rs, kluboviStudenata, natjecanja);

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            throw new RuntimeException(e);
        }

        return natjecanja;
    }

    public static DogadajZajednickiPodaci getZajednickiPodaci(List<KlubStudenata> kluboviStudenata, ResultSet rs) throws SQLException {
        Integer id = rs.getInt("ID");
        String naziv = rs.getString("NAZIV");
        Integer organizatorID = rs.getInt("KLUB_ORGANIZATOR_ID");
        KlubStudenata klubStudenata = kluboviStudenata.getFirst();
        if (!klubStudenata.getId().equals(organizatorID)) {
            for (int i = 1; i < kluboviStudenata.size(); i++) {
                if (kluboviStudenata.get(i).getId().equals(organizatorID)) {
                    klubStudenata = kluboviStudenata.get(i);
                    break;
                }
            }
        }
        Grad grad = getGradByID(rs);
        LocalDate datumPocetka = rs.getDate("DATUM_POCETKA").toLocalDate();
        LocalDate datumZavrsetka = rs.getDate("DATUM_ZAVRSETKA").toLocalDate();

        return new DogadajZajednickiPodaci(id, naziv, klubStudenata, grad, datumPocetka, datumZavrsetka);
    }

    private static void mapResultSetToNatjecanja(ResultSet rs, List<KlubStudenata> kluboviStudenata, List<Natjecanje> natjecanja) throws SQLException {
        while (rs.next()) {
            DogadajZajednickiPodaci helper = getZajednickiPodaci(kluboviStudenata, rs);

            String tema = rs.getString("TEMA_NATJECANJA");
            Integer nagrada = rs.getInt("NAGRADA_EUR");

            natjecanja.add(new Natjecanje(
                    helper.getId(),
                    helper.getNaziv(),
                    helper.getKlubOrganizator(),
                    helper.getLokacijaOdrzavanja(),
                    helper.getDatumPocetka(),
                    helper.getDatumZavrsetka(),
                    tema,
                    nagrada
                    ));
        }
    }

    public static List<Kviz> getKvizovi() {
        List<Kviz> kvizovi = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM KVIZOVI";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();

            mapResultSetToKvizovi(rs, kluboviStudenata, kvizovi);

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            throw new RuntimeException(e);
        }

        return kvizovi;
    }

    private static void mapResultSetToKvizovi(ResultSet rs, List<KlubStudenata> kluboviStudenata, List<Kviz> kvizovi) throws SQLException {
        while (rs.next()) {
            DogadajZajednickiPodaci helper = getZajednickiPodaci(kluboviStudenata, rs);

            Integer nagrada = rs.getInt("NAGRADA_EUR");

            kvizovi.add(new Kviz(
                    helper.getId(),
                    helper.getNaziv(),
                    helper.getKlubOrganizator(),
                    helper.getLokacijaOdrzavanja(),
                    helper.getDatumPocetka(),
                    helper.getDatumZavrsetka(),
                    nagrada
                    ));
        }
    }

    public static List<Putovanje> getPutovanja() {
        List<Putovanje> putovanja = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM PUTOVANJA";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();

            mapResultSetToPutovanja(rs, kluboviStudenata, putovanja);

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            throw new RuntimeException(e);
        }

        return putovanja;
    }

    private static void mapResultSetToPutovanja(ResultSet rs, List<KlubStudenata> kluboviStudenata, List<Putovanje> putovanja) throws SQLException {
        while (rs.next()) {
            DogadajZajednickiPodaci helper = getZajednickiPodaci(kluboviStudenata, rs);

            String tema = rs.getString("POVOD_PUTOVANJA");

            putovanja.add(new Putovanje(
                    helper.getId(),
                    helper.getNaziv(),
                    helper.getKlubOrganizator(),
                    helper.getLokacijaOdrzavanja(),
                    helper.getDatumPocetka(),
                    helper.getDatumZavrsetka(),
                    tema
                    ));
        }
    }

    public static List<Zabava> getZabave() {
        List<Zabava> zabave = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM ZABAVE";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();

            mapResultSetToZabave(rs, kluboviStudenata, zabave);

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            throw new RuntimeException(e);
        }

        return zabave;
    }

    private static void mapResultSetToZabave(ResultSet rs, List<KlubStudenata> kluboviStudenata, List<Zabava> zabave) throws SQLException {
        while (rs.next()) {
            DogadajZajednickiPodaci helper = getZajednickiPodaci(kluboviStudenata, rs);

            String lokal = rs.getString("NAZIV_LOKALA");

            zabave.add(new Zabava(
                    helper.getId(),
                    helper.getNaziv(),
                    helper.getKlubOrganizator(),
                    helper.getLokacijaOdrzavanja(),
                    helper.getDatumPocetka(),
                    helper.getDatumZavrsetka(),
                    lokal
            ));
        }
    }

    public static void saveStudent(Student student) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO STUDENTI(IME, GODINA_STUDIJA, SPOL_ID, JMBAG, PROSJEK_OCJENA, FAKULTET_ID)" +
                            " VALUES (?, ?, ?, ?, ?, ?)"
            );
            pstmt.setString(1, student.getNaziv());
            pstmt.setInt(2, student.getGodinaStudija());
            pstmt.setInt(3, student.getSpol().getRedniBroj());
            pstmt.setString(4, student.getJmbag());
            pstmt.setBigDecimal(5, student.getProsjekOcjena());
            pstmt.setInt(6, student.getFakultet().getRedniBroj());

            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void ukloniStudenta(Integer id) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "DELETE FROM STUDENTI WHERE ID = ?"
            );
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void izmjeniGodinuStudija (Integer id, Integer godinaStudija) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "UPDATE STUDENTI SET GODINA_STUDIJA = ? WHERE ID = ?"
            );
            pstmt.setInt(1, godinaStudija);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void izmjeniProsjekOcjena (Integer id, BigDecimal prosjekOcjena) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "UPDATE STUDENTI SET PROSJEK_OCJENA = ? WHERE ID = ?"
            );
            pstmt.setBigDecimal(1, prosjekOcjena);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void izmjeniFakultet (Integer id, Integer fakultetID) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "UPDATE STUDENTI SET FAKULTET_ID = ? WHERE ID = ?"
            );
            pstmt.setInt(1, fakultetID);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void dodajPredavanje (Predavanje predavanje) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO PREDAVANJA (NAZIV, KLUB_ORGANIZATOR_ID, GRAD_ID, DATUM_POCETKA, DATUM_ZAVRSETKA, TEMA_PREDAVANJA) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            setZajednickiPodaci(pstmt, predavanje.getNaziv(), predavanje.getKlubOrganizator(), predavanje.getLokacijaOdrzavanja(), predavanje.getDatumPocetka(), predavanje.getDatumZavrsetka());
            pstmt.setString(6, predavanje.getTemaPredavanja());

            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void dodajProjekt (Projekt projekt) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO PROJEKTI (NAZIV, KLUB_ORGANIZATOR_ID, GRAD_ID, DATUM_POCETKA, DATUM_ZAVRSETKA, TEMA_PROJEKTA) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            setZajednickiPodaci(pstmt, projekt.getNaziv(), projekt.getKlubOrganizator(), projekt.getLokacijaOdrzavanja(), projekt.getDatumPocetka(), projekt.getDatumZavrsetka());
            pstmt.setString(6, projekt.getTemaProjekta());

            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    private static void setZajednickiPodaci(PreparedStatement pstmt, String naziv, KlubStudenata klubOrganizator, Grad lokacijaOdrzavanja, LocalDate datumPocetka, LocalDate datumZavrsetka) throws SQLException {
        pstmt.setString(1, naziv);
        Integer klubOrganizatorID = klubOrganizator.getId();
        pstmt.setInt(2, klubOrganizatorID);
        pstmt.setInt(3, lokacijaOdrzavanja.getRedniBr());
        pstmt.setDate(4, new Date(datumPocetka.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        pstmt.setDate(5, new Date(datumZavrsetka.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
    }

    public static void dodajNatjecanje (Natjecanje natjecanje) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO NATJECANJA (NAZIV, KLUB_ORGANIZATOR_ID, GRAD_ID, DATUM_POCETKA, DATUM_ZAVRSETKA, TEMA_NATJECANJA, NAGRADA_EUR) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            setZajednickiPodaci(pstmt, natjecanje.getNaziv(), natjecanje.getKlubOrganizator(), natjecanje.getLokacijaOdrzavanja(), natjecanje.getDatumPocetka(), natjecanje.getDatumZavrsetka());
            pstmt.setString(6, natjecanje.getTemaNatjecanja());
            pstmt.setInt(7, natjecanje.getNagradaEUR());

            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void dodajKviz (Kviz kviz) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO KVIZOVI (NAZIV, KLUB_ORGANIZATOR_ID, GRAD_ID, DATUM_POCETKA, DATUM_ZAVRSETKA, NAGRADA_EUR) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            setZajednickiPodaci(pstmt, kviz.getNaziv(), kviz.getKlubOrganizator(), kviz.getLokacijaOdrzavanja(), kviz.getDatumPocetka(), kviz.getDatumZavrsetka());
            pstmt.setInt(6, kviz.getNagradaEUR());

            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void dodajPutovanje (Putovanje putovanje) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO PUTOVANJA (NAZIV, KLUB_ORGANIZATOR_ID, GRAD_ID, DATUM_POCETKA, DATUM_ZAVRSETKA, POVOD_PUTOVANJA) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            setZajednickiPodaci(pstmt, putovanje.getNaziv(), putovanje.getKlubOrganizator(), putovanje.getLokacijaOdrzavanja(), putovanje.getDatumPocetka(), putovanje.getDatumZavrsetka());
            pstmt.setString(6, putovanje.getPovodPutovanja());

            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void dodajZabavu (Zabava zabava) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO ZABAVE (NAZIV, KLUB_ORGANIZATOR_ID, GRAD_ID, DATUM_POCETKA, DATUM_ZAVRSETKA, NAZIV_LOKALA) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            setZajednickiPodaci(pstmt, zabava.getNaziv(), zabava.getKlubOrganizator(), zabava.getLokacijaOdrzavanja(), zabava.getDatumPocetka(), zabava.getDatumZavrsetka());
            pstmt.setString(6, zabava.getNazivLokala());

            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static Natjecanje getNatjecanjebyID (Integer id) throws NepostojeciIDException {
        List<Natjecanje> natjecanja = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM NATJECANJA WHERE ID = ? "
            );
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            mapResultSetToNatjecanja(rs, kluboviStudenata, natjecanja);

            if(natjecanja.isEmpty()) throw new NepostojeciIDException();

        }catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            System.out.println(e.getMessage());
        }

        return natjecanja.getFirst();
    }

    public static Predavanje getPredavanjebyID (Integer id) throws NepostojeciIDException {
        List<Predavanje> predavanja = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM PREDAVANJA WHERE ID = ? "
            );
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            mapResultSetToPredavanja(rs, kluboviStudenata, predavanja);

            if(predavanja.isEmpty()) throw new NepostojeciIDException();

        }catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            System.out.println(e.getMessage());
        }

        return predavanja.getFirst();
    }

    public static Projekt getProjektbyID (Integer id) throws NepostojeciIDException {
        List<Projekt> projekti = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM PROJEKTI WHERE ID = ? "
            );
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            mapResultSetToProjekti(rs, kluboviStudenata, projekti);

            if(projekti.isEmpty()) throw new NepostojeciIDException();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            System.out.println(e.getMessage());
        }

        return projekti.getFirst();
    }

    public static Kviz getKvizbyID (Integer id) throws NepostojeciIDException {
        List<Kviz> kvizovi = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM KVIZOVI WHERE ID = ? "
            );
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            mapResultSetToKvizovi(rs, kluboviStudenata, kvizovi);

            if(kvizovi.isEmpty()) throw new NepostojeciIDException();

        }catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            System.out.println(e.getMessage());
        }

        return kvizovi.getFirst();
    }

    public static Putovanje getPutovanjeByID(Integer id) throws NepostojeciIDException {
        List<Putovanje> putovanja = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM PUTOVANJA WHERE ID = ? "
            );
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            mapResultSetToPutovanja(rs, kluboviStudenata, putovanja);

            if(putovanja.isEmpty()) throw new NepostojeciIDException();

        }catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            System.out.println(e.getMessage());
        }

        return putovanja.getFirst();
    }

    public static void izmjeniNazivLokalaZabave (Integer id, String nazivLokala) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "UPDATE ZABAVE SET NAZIV_LOKALA = ? WHERE ID = ?"
            );
            pstmt.setString(1, nazivLokala);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static Zabava getZabaveByID(Integer id) throws NepostojeciIDException {
        List<Zabava> zabave = new ArrayList<>();
        List<KlubStudenata> kluboviStudenata = getKluboviStudenata();
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM ZABAVE WHERE ID = ? "
            );
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            mapResultSetToZabave(rs, kluboviStudenata, zabave);

            if(zabave.isEmpty()) throw new NepostojeciIDException();

        }catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja baze podataka");
            System.out.println(e.getMessage());
        }

        return zabave.getFirst();
    }

    public static void izmjeniGrad (String entitet, Integer id, Integer gradID) {
        try (Connection connection = connectToDatabase()) {
            String querry = "UPDATE " + entitet + " SET GRAD_ID = ? WHERE ID = ?";

            PreparedStatement pstmt = connection.prepareStatement(querry);
            pstmt.setInt(1, gradID);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void izmjeniDatumPocetka (String entitet, Integer id, LocalDate datumPocetka) {
        try (Connection connection = connectToDatabase()) {
            String querry = "UPDATE " + entitet + " SET DATUM_POCETKA = ? WHERE ID = ?";

            PreparedStatement pstmt = connection.prepareStatement(querry);
            pstmt.setDate(1, Date.valueOf(datumPocetka));
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void izmjeniDatumZavrsetka (String entitet, Integer id, LocalDate datumZavrsetka) {
        try (Connection connection = connectToDatabase()) {
            String querry = "UPDATE " + entitet + " SET DATUM_ZAVRSETKA = ? WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(querry);
            pstmt.setDate(1, Date.valueOf(datumZavrsetka));
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void izmjeniNagradu (String entitet, Integer id, Integer nagrada) {
        try (Connection connection = connectToDatabase()) {
            String querry = "UPDATE " + entitet + " SET NAGRADA_EUR = ? WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(querry);
            pstmt.setInt(1, nagrada);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static void ukloniDogadaj(String dogadaj, Integer id) {
        try (Connection connection = connectToDatabase()) {
            String querry = "DELETE FROM " + dogadaj + " WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(querry);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }

    public static List<Student> getStudentiIzKluba(Integer klubID) {
        List<Student> studenti = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM STUDENTI_PO_KLUBOVIMA WHERE KLUB_STUDENATA_ID = ?"
            );
            pstmt.setInt(1, klubID);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();

            List<Integer> studentIDList = new ArrayList<>();
            while (rs.next()) {
                studentIDList.add(rs.getInt("STUDENT_ID"));
            }

            for(Integer id : studentIDList) {
                studenti.add(getStudentByID(id));
            }

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }

        return studenti;
    }

    public static void uclaniStudentaUKlub(Integer studentID, Integer klubID) {
        try (Connection connection = connectToDatabase()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM STUDENTI_PO_KLUBOVIMA WHERE STUDENT_ID = ?"
            );
            pstmt.setInt(1, studentID);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            if(rs.next()) {
                PreparedStatement izmjeni = connection.prepareStatement(
                        "UPDATE STUDENTI_PO_KLUBOVIMA SET KLUB_STUDENATA_ID = ? WHERE STUDENT_ID = ?"
                );
                izmjeni.setInt(1, klubID);
                izmjeni.setInt(2, studentID);
                izmjeni.executeUpdate();
            }
            else {
                PreparedStatement dodaj = connection.prepareStatement(
                        "INSERT INTO STUDENTI_PO_KLUBOVIMA(KLUB_STUDENATA_ID, STUDENT_ID) " +
                                "VALUES(?, ?)"
                );
                dodaj.setInt(1, klubID);
                dodaj.setInt(2, studentID);
                dodaj.executeUpdate();
            }

        } catch (SQLException | IOException e) {
            ApplicationProject.logger.error("Greška prilikom zapisivanja u bazu podataka");
            System.out.println(e.getMessage());
        }
    }
}
