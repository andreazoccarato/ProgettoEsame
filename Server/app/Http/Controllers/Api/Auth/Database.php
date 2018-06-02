<?php

namespace App\Http\Controllers\Api\Auth;

use PDO;

class Database {

    private $conn;
    private $preparedStatement;

    function _construct() {
        $this->conn = null;
    }

    /*
     * Metodo che inizializza la connessione con il database
     */

    function connect() {
        if ($this->conn == null) {
            $this->conn = new PDO('sqlite:../storage/dbProgettoEsame.sqlite');
        }
    }

    /*
     * Metodo che chiude la connessione con il database
     */

    function disconnect() {
        if ($this->conn != null) {
            $this->conn = null;
        }
    }

    /*
     * METODI DI CONTROLLO
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

    /*
     * Metodo che controlla l'esistenza dello studente specificato a partire dal suo nominativo
     */

    function existStud($nome, $cognome) {
        $qry = "SELECT count(*) FROM Studente WHERE Nome='" . $nome . "' AND Cognome='" . $cognome . "'";
        $result = $this->conn->query($qry);
        $count = $result->fetchColumn(0);
        if ($count != 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Metodo che controlla l'esistenza dello studente specificato a partire dal suo codice fiscale
     */

    function existStudByCF($CodiceFiscale) {
        $qry = "SELECT count(*) FROM Studente WHERE CodiceFiscale='" . $CodiceFiscale . "'";
        $result = $this->conn->query($qry);
        $count = $result->fetchColumn(0);
        if ($count != 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Metodo che controlla l'esistenza del docente specificato a partire dal suo nominativo
     */

    function existDoc($nome, $cognome) {
        $qry = "SELECT count(*) FROM Docente WHERE Nome='" . $nome . "' AND Cognome='" . $cognome . "'";
        $result = $this->conn->query($qry);
        $count = $result->fetchColumn(0);
        if ($count != 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Metodo che controlla l'esistenza del docente specificato a partire dal suo codice fiscale
     */

    function existDocByCF($CodiceFiscale) {
        $qry = "SELECT count(*) FROM Docente WHERE CodiceFiscale='" . $CodiceFiscale . "'";
        $result = $this->conn->query($qry);
        $count = $result->fetchColumn(0);
        if ($count != 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Metodo che effetta il login
     */

    function login($username, $password) {
        $qryStud = "SELECT Studente.CodiceFiscale FROM Credenziali INNER JOIN Studente ON Credenziali.ID=Studente.IdCredenziali WHERE Username='" . $username . "' AND Password='" . $password . "'";
        $resultStud = $this->conn->query($qryStud);
        $CFStud = $resultStud->fetchColumn(0);
        if ($CFStud != "") {
            return "Studente";
        }
        $qryDoc = "SELECT Docente.CodiceFiscale FROM Credenziali INNER JOIN Docente ON Credenziali.ID=Docente.IdCredenziali WHERE Username='" . $username . "' AND Password='" . $password . "'";
        $resultDoc = $this->conn->query($qryDoc);
        $CFDoc = $resultDoc->fetchColumn(0);
        if ($CFDoc != "") {
            return "Docente";
        }
        return "Username e/o Password errati";
    }

    /*
     * Metodo che controlla se accettare o meno la richiesta per il QrCode
     */

    function requestQrCode($username, $password) {
        $codF = $this->getIdDocente($username, $password);
        $currentData = date("j/m/Y", time());
        $qry = "SELECT Giorno,CodiceControllo,IdScuola,IdClasse,CodiceFiscale "
                . "FROM tblQrCode "
                . "WHERE CodiceFiscale='" . $codF . "' AND Giorno='" . $currentData . "'";
        $array = $this->conn->query($qry)->fetchAll(PDO::FETCH_ASSOC);
        return $array;
    }

    /*
     * Metodo che controlla l'esistenza del qrCode
     */

    function checkQrCode($giorno, $scuola, $classe, $cod) {
        $qryCheck = "SELECT Count(*) AS qrCode FROM tblQrCode WHERE Giorno='" . $giorno . "' AND CodiceControllo=" . $cod . " AND IdClasse=" . $classe . " AND IdScuola=" . $scuola . " GROUP BY IdQrCode";
        $count = $this->conn->query($qryCheck)->fetchColumn(0);
        if ($count == 0) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Metodo che controlla se lo studente è già presente
     */

    function studenteGiàAssente($username, $password, $giorno) {
        $cfStud = $this->getIdStudente($username, $password);
        $qryAssente = "SELECT Count(*) "
                . "FROM Assenza "
                . "WHERE CFStudente='" . $cfStud . "' AND Data='" . $giorno . "' "
                . "GROUP BY ID";
        $resultQry = $this->conn->query($qryAssente);
        $count = $resultQry->fetchColumn(0);
        if ($count == 0) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * METODI DI GETTER
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

    /*
     * Metodo che mi restitisce il codice fiscale del docente, se esiste.
     */

    function getIdDocente($username, $password) {
        $qry = "SELECT Docente.CodiceFiscale "
                . "FROM Docente INNER JOIN Credenziali ON Docente.IdCredenziali=Credenziali.ID "
                . "WHERE Username='" . $username . "' AND Password='" . $password . "'";
        $result = $this->conn->query($qry);
        return $result->fetchColumn(0);
    }

    /*
     * Metodo che mi restitisce il codice fiscale dello studente, se esiste.
     */

    function getIdStudente($username, $password) {
        $qry = "SELECT Studente.CodiceFiscale "
                . "FROM Studente INNER JOIN Credenziali ON Studente.IdCredenziali=Credenziali.ID "
                . "WHERE Username='" . $username . "' AND Password='" . $password . "'";
        $result = $this->conn->query($qry);
        return $result->fetchColumn(0);
    }

    /*
     * Metodo che restituisce l'identificativo della classe
     */

    function getClasse($username, $password) {
        $qryStudente = "SELECT Studente.IdClasse "
                . "FROM Studente INNER JOIN Credenziali ON Studente.IdCredenziali=Credenziali.ID "
                . "WHERE Username='" . $username . "' AND Password='" . $password . "'";
        $resultStudente = $this->conn->query($qryStudente);
        $result = $resultStudente->fetchColumn(0);
        if ($result != "") {
            return $result;
        }

        $qryDocente = "SELECT tbrDocenteClasse.IdClasse "
                . "FROM Docente INNER JOIN Credenziali ON Docente.IdCredenziali=Credenziali.ID INNER JOIN tbrDocenteClasse ON Docente.CodiceFiscale=tbrDocenteClasse.CodiceFiscale "
                . "WHERE Username='" . $username . "' AND Password='" . $password . "'";
        $resultDocente = $this->conn->query($qryDocente);
        $resultD = $resultDocente->fetchColumn(0);
        if ($resultD != "") {
            return $resultD;
        }
    }

    function getClasseByClsezAndScuola($clsez, $scuola) {
        $qry = "SELECT IdClasse "
                . "FROM Classe "
                . "WHERE CodiceScuola='" . $scuola . "' AND Sezione='" . $clsez . "'";
        $resultStudente = $this->conn->query($qry);
        $result = $resultStudente->fetchColumn(0);
        if ($result != "") {
            return $result;
        }
    }

    /*
     * Metodo che restituisce l'identificativo della classe a partire dal codiceFiscale
     */

    function getClasseByCF($codF) {
        $qryStudente = "SELECT Studente.IdClasse "
                . "FROM Studente  "
                . "WHERE CodiceFiscale='" . $codF . "'";
        $resultStudente = $this->conn->query($qryStudente);
        $result = $resultStudente->fetchColumn(0);
        if ($result != "") {
            return $result;
        }
    }

    /*
     * Metodo che restituisce l'identificativo della classe a partire dalla sezione e dalla scuola
     */

    function getClasseByClsez($clSez, $scuola) {
        $qryClasse = "SELECT IdClasse FROM Classe WHERE Sezione='" . $clSez . "' AND CodiceScuola='" . $scuola . "'";
        $result = $this->conn->query($qryClasse);
        $idClasse = $result->fetchColumn(0);
        if ($idClasse != "") {
            return $idClasse;
        }
    }

    /*
     * Metodo che restituisce l'identificativo della scuola
     */

    function getScuola($username, $password) {
        $qryStudente = "SELECT Studente.CodiceScuola "
                . "FROM Studente INNER JOIN Credenziali ON Studente.IdCredenziali=Credenziali.ID "
                . "WHERE Username='" . $username . "' AND Password='" . $password . "'";
        $resultStudente = $this->conn->query($qryStudente);
        $result = $resultStudente->fetchColumn(0);
        if ($result != "") {
            return $result;
        }

        $qryDocente = "SELECT Docente.CodiceScuola "
                . "FROM Docente INNER JOIN Credenziali ON Docente.IdCredenziali=Credenziali.ID "
                . "WHERE Username='" . $username . "' AND Password='" . $password . "'";
        $resultDocente = $this->conn->query($qryDocente);
        $resultD = $resultDocente->fetchColumn(0);
        if ($resultD != "") {
            return $resultD;
        }
    }

    /*
     * Metodo per prelevare i voti dello studente a partire dalle sue credenziali
     */

    function getVotiByCred($username, $password) {
        $qryVoti = "SELECT Valutazione.Voto,Valutazione.Data,Valutazione.Materia,Valutazione.Descrizione  "
                . "FROM Valutazione INNER JOIN Studente ON Valutazione.CFStudente=Studente.CodiceFiscale INNER JOIN Credenziali ON Studente.IdCredenziali=Credenziali.ID "
                . "WHERE Username='" . $username . "' AND Password='" . $password . "'";

        $array = $this->conn->query($qryVoti)->fetchAll(PDO::FETCH_ASSOC);
        return $array;
    }

    /*
     * Metodo che restituisce tutti gli eventi a partire dalle credenziali
     */

    function getEventsByCred($username, $password, $data) {
        $idClasse = $this->getClasse($username, $password);
        $qryLezione = "SELECT Lezione.* "
                . "FROM Lezione INNER JOIN Docente ON Lezione.CFDocente=Docente.CodiceFiscale INNER JOIN tbrDocenteClasse ON Docente.CodiceFiscale=tbrDocenteClasse.CodiceFiscale "
                . "WHERE tbrDocenteClasse.IdClasse='" . $idClasse . "' AND Lezione.Data='" . $data . "';";
        $arrayLezioni = $this->conn->query($qryLezione)->fetchAll(PDO::FETCH_ASSOC);
        $qryCompiti = "SELECT Agenda.* "
                . "FROM Agenda "
                . "WHERE IdClasse='" . $idClasse . "' AND Data='" . $data . "';";
        $arrayCompiti = $this->conn->query($qryCompiti)->fetchAll(PDO::FETCH_ASSOC);
        $ris = array(Array("Lezioni" => $arrayLezioni), Array("Compiti" => $arrayCompiti));
        return $ris;
    }

    /*
     * Metodo che restituisce tutti gli eventi a partire dalla classe
     */

    function getEventsByClass($idClasse) {
        $qryLezione = "SELECT Lezione.* "
                . "FROM Lezione INNER JOIN Docente ON Lezione.CFDocente=Docente.CodiceFiscale INNER JOIN tbrDocenteClasse ON Docente.CodiceFiscale=tbrDocenteClasse.CodiceFiscale "
                . "WHERE tbrDocenteClasse.IdClasse=" . $idClasse . ";";
        $arrayLezioni = $this->conn->query($qryLezione)->fetchAll(PDO::FETCH_ASSOC);
        $qryCompiti = "SELECT Agenda.* "
                . "FROM Agenda "
                . "WHERE IdClasse=" . $idClasse;
        $arrayCompiti = $this->conn->query($qryCompiti)->fetchAll(PDO::FETCH_ASSOC);
        $ris = array(Array("Lezioni" => $arrayLezioni), Array("Compiti" => $arrayCompiti));
        return $ris;
    }

    /*
     * Metodo che prende le assenze dello studente a partire dalle sue credenziali
     */

    function getAssenze($username, $password) {
        $cfStud = $this->getIdStudente($username, $password);
        $qryAssenzeNonGiustificate = "SELECT Assenza.* "
                . "FROM Assenza "
                . "WHERE CFStudente='" . $cfStud . "' AND Giustificato='false'";
        $arrayAssenzeNonGiustificate = $this->conn->query($qryAssenzeNonGiustificate)->fetchAll(PDO::FETCH_ASSOC);
        $qryAssenzeGiustificate = "SELECT Assenza.*,Giustifica.Descrizione,Giustifica.TipologiaGiustifica "
                . "FROM Assenza INNER JOIN Giustifica ON Assenza.ID=Giustifica.IdGiustifica "
                . "WHERE CFStudente='" . $cfStud . "'";
        $arrayAssenzeGiustificate = $this->conn->query($qryAssenzeGiustificate)->fetchAll(PDO::FETCH_ASSOC);
        $ris = array(Array("Giustificate" => $arrayAssenzeGiustificate), Array("NonGiustificate" => $arrayAssenzeNonGiustificate));
        return $ris;
    }

    /*
     * Metodo che prende le assenze non giustificate dello studente a partire dalle sue credenziali
     */

    function getAssenzeNonGiustificate($codF) {
        $qryAssenzeNonGiustificate = "SELECT Assenza.* "
                . "FROM Assenza "
                . "WHERE CFStudente='" . $codF . "' AND Giustificato='false'";
        return $this->conn->query($qryAssenzeNonGiustificate)->fetchAll(PDO::FETCH_ASSOC);
    }

    /*
     * Metodo che preleva le informazioni dell'utente
     */

    function getProfilo($username, $password) {
        $qryInfoStud = "SELECT Nome,Cognome,DataNascita "
                . "FROM Studente INNER JOIN Credenziali ON Studente.IdCredenziali=Credenziali.ID "
                . "WHERE Username='" . $username . "' AND Password='" . $password . "'";
        $arrayInfoStud = $this->conn->query($qryInfoStud)->fetchAll(PDO::FETCH_ASSOC);
        if (sizeof($arrayInfoStud) != 0) {
            return $arrayInfoStud;
        } else {
            $qryInfoDoc = "SELECT Nome,Cognome,DataNascita "
                    . "FROM Docente INNER JOIN Credenziali ON Docente.IdCredenziali=Credenziali.ID "
                    . "WHERE Username='" . $username . "' AND Password='" . $password . "'";
            return $arrayInfoDoc = $this->conn->query($qryInfoDoc)->fetchAll(PDO::FETCH_ASSOC);
        }
    }

    /*
     * Metodo che restituisce tutte le classi del docente
     */

    function getClassiDocente($username, $password) {
        $CFDocente = $this->getIdDocente($username, $password);
        $qryClassi = "SELECT Classe.IdClasse,Classe.Sezione,Classe.Indirizzo,Scuola.Nome "
                . "FROM Classe INNER JOIN tbrDocenteClasse ON Classe.IdClasse=tbrDocenteClasse.IdClasse INNER JOIN Scuola ON Classe.CodiceScuola=Scuola.CodiceScuola "
                . "WHERE CodiceFiscale='" . $CFDocente . "';";
        return $arrayClassi = $this->conn->query($qryClassi)->fetchAll(PDO::FETCH_ASSOC);
    }

    /*
     * Metodo che restituisce gli studenti a partire dalla classe
     */

    function getStudentiByClasse($codClasse) {
        $qryStudenti = "SELECT CodiceFiscale,Nome,Cognome,DataNascita "
                . "FROM Studente "
                . "WHERE IdClasse=" . $codClasse . " "
                . "ORDER BY Cognome,Nome";
        return $arrayStudenti = $this->conn->query($qryStudenti)->fetchAll(PDO::FETCH_ASSOC);
    }

    /*
     * Metodo che restituisce lo username e la password dello studente a partire dal suo codice fiscale
     */

    function getUsernamePasswordStud($codF) {
        $qry = "SELECT Username,Password FROM Credenziali INNER JOIN Studente ON Credenziali.ID=Studente.IdCredenziali WHERE CodiceFiscale='" . $codF . "'";
        return $arrayStudenti = $this->conn->query($qry)->fetchAll();
    }

    function giustificaPresente($idAssenza) {
        $qry = "SELECT IdGiustifica FROM Giustifica WHERE IdAssenza='" . $idAssenza . "'";
        $resultQry = $this->conn->query($qry);
        $count = $resultQry->fetchColumn(0);
        if ($count == 0) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * METODI DI INSERIMENTO
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

    /*
     * Metodo che inserisce un voto allo studente
     */

    function addVoto($voto, $materia, $descrizione, $data, $ora, $cfStud, $username, $password) {
        $codF = $this->getIdDocente($username, $password);
        $this->preparedStatement = $this->conn->prepare("INSERT INTO Valutazione(Voto,Data,Ora,Materia,Descrizione,CFStudente,CFDocente) VALUES("
                . "'" . $voto . "',"
                . "'" . $data . "',"
                . "'" . $ora . "',"
                . "'" . $materia . "',"
                . "'" . $descrizione . "',"
                . "'" . $cfStud . "',"
                . "'" . $codF . "')");
        $this->preparedStatement->execute();

        $qryVoto = "SELECT ID "
                . "FROM Valutazione "
                . "WHERE Voto='" . $voto . "' AND Materia='" . $materia . "' AND Data='" . $data . "' AND Ora='" . $ora . "' AND Descrizione='" . $descrizione . "' AND CFStudente='" . $cfStud . "' AND CFDocente='" . $codF . "'";
        $resultQry = $this->conn->query($qryVoto);
        $id = $resultQry->fetchColumn(0);
        if ($id != "") {
            return "Voto inserito con successo";
        } else {
            return "Voto non inserito";
        }
    }

    /*
     * Metodo che inserisce lo studente specificato all'interno del database
     */

    function insertStud($CodiceFiscale, $Nome, $Cognome, $DataNascita, $Sezione, $IdCredenziali) {
        $this->preparedStatement = $this->conn->prepare("INSERT INTO Studente VALUES('" . $CodiceFiscale . "',"
                . "'" . $Nome . "',"
                . "'" . $Cognome . "',"
                . "'" . $DataNascita . "',"
                . "'" . $Sezione . "',"
                . "'" . $IdCredenziali . "')");
        $this->preparedStatement->execute();
    }

    /*
     * Metodo che inserisce il docente specificato all'interno del database
     */

    function insertDoc($CodiceFiscale, $Nome, $Cognome, $DataNascita, $IdCredenziali) {
        $this->preparedStatement = $this->conn->prepare("INSERT INTO Docente VALUES('" . $CodiceFiscale . "',"
                . "'" . $Nome . "',"
                . "'" . $Cognome . "',"
                . "'" . $DataNascita . "',"
                . "'" . $IdCredenziali . "')");
        $this->preparedStatement->execute();
    }

    /*
     * Metodo che inserisce la scuola specificata all'interno del database
     */

    function insertScuola($CodiceScuola, $Nome, $Indirizzo, $Città) {
        $this->preparedStatement = $this->conn->prepare("INSERT INTO Scuola VALUES('" . $CodiceScuola . "',"
                . "'" . $Nome . "',"
                . "'" . $Indirizzo . "',"
                . "'" . $Città . "')");
        $this->preparedStatement = $this->conn->execute();
    }

    /*
     * Metodo che inserisce la classe specificata all'interno del database
     */

    function insertClasse($Sezione, $Indirizzo, $CodiceScuola) {
        $this->preparedStatement = $this->conn->prepare("INSERT INTO Scuola VALUES('" . $Sezione . "',"
                . "'" . $Indirizzo . "',"
                . "'" . $CodiceScuola . "')");
        $this->preparedStatement->execute();
    }

    /*
     * Metodo che inserisce un compito
     */

    function insertCompito($data, $materia, $descrizione, $username, $password, $idClasse) {
        $codF = $this->getIdDocente($username, $password);
        $scuola = $this->getScuola($username, $password);
        $this->preparedStatement = $this->conn->prepare("INSERT INTO Agenda(Data,Materia,Descrizione,CFDocente,IdClasse) VALUES('" . $data . "','"
                . $materia . "','" . $descrizione . "','" . $codF . "'," . $idClasse . ")");
        $this->preparedStatement->execute();
        $qry = "SELECT Agenda.ID FROM Agenda WHERE Data='" . $data . "' AND Materia='" . $materia . "' AND Descrizione='" . $descrizione . "' AND CFDocente='" . $codF . "' AND IdClasse='" . $idClasse . "'";
        $resultQry = $this->conn->query($qry);
        $count = $resultQry->fetchColumn(0);
        if ($count == 0) {
            return "Compito non inserito";
        } else {
            return "Compito Inserito";
        }
    }

    /*
     * Metodo che inserisce, all'interno del database, le informazioni riguardanti il qrCode richiesto
     */

    function insertQrCode($Data, $codiceControllo, $idScuola, $idClasse, $CFDocente) {
        $this->preparedStatement = $this->conn->prepare("INSERT INTO tblQrCode(Giorno,CodiceControllo,IdScuola,IdClasse,CodiceFiscale) VALUES("
                . "'" . $Data . "',"
                . "'" . $codiceControllo . "',"
                . "'" . $idScuola . "',"
                . "'" . $idClasse . "',"
                . "'" . $CFDocente . "')");
        $this->preparedStatement->execute();
    }

    /*
     * Metodo che inserisce all'interno del database la presenza dello studente specificato
     */

    function insertPresenza($data, $username, $password) {
        if ($this->studenteGiàAssente($username, $password, $data)) {
            $codF = $this->getIdStudente($username, $password);
            $qryAssenza = "SELECT Orario,ID FROM Assenza WHERE CFStudente='" . $codF . "' AND Data='" . $data . "'";
            $assenza = $this->conn->query($qryAssenza)->fetchColumn(0);
            $idAssenza = $this->conn->query($qryAssenza)->fetchColumn(1);
            date_default_timezone_set("CET");
            $time = date("H", time());
            for ($i = $time; $i < 24; $i++) {
                $assenza[$i] = "0";
            }
            $this->preparedStatement = $this->conn->prepare("UPDATE Assenza "
                    . "SET Orario='" . $assenza . "' "
                    . "WHERE ID=" . $idAssenza);
            $this->preparedStatement->execute();
            return true;
        } else {
            return true;
        }
    }

    /*
     * Metodo che setta l'assenza dello studente
     */

    function insertAssenza($data, $username, $password) {
        $codF = $this->getIdStudente($username, $password);
        if (!$this->studenteGiàAssente($username, $password, $data)) {
            $qryAssenza = "SELECT Count(*),ID,Orario FROM Assenza WHERE CFStudente='" . $codF . "' AND Data='" . $data . "'";
            $count = $this->conn->query($qryAssenza)->fetchColumn(0);
            $idAssenza = $this->conn->query($qryAssenza)->fetchColumn(1);
            $orario = $this->conn->query($qryAssenza)->fetchColumn(2);
            if ($count == 0) {
                $this->preparedStatement = $this->conn->prepare("INSERT INTO Assenza(CFStudente,Data,Orario,Giustificato) VALUES("
                        . "'" . $codF . "',"
                        . "'" . $data . "',"
                        . "'111111111111111111111111',"
                        . "'false')");
                $this->preparedStatement->execute();
            } else {
                date_default_timezone_set("CET");
                $time = date("H", time());
                for ($i = $time; $i < 24; $i++) {
                    $orario[$i] = "1";
                }
                $this->preparedStatement = $this->conn->prepare("UPDATE Assenza SET Orario='" . $orario . "' WHERE ID=" . $idAssenza);
                $this->preparedStatement->execute();
            }
            return true;
        } else {
            return false;
        }
    }

    /*
     * Metodo che giustifica un'assenza
     */

    function giustifica($idAssenza, $descrizione, $data, $tipologia, $username, $password) {
        if (!$this->giustificaPresente($idAssenza)) {
            $codF = $this->getIdDocente($username, $password);
            $sql = "UPDATE Assenza SET Giustificato='true' WHERE ID=" . $idAssenza;
            $this->preparedStatement = $this->conn->prepare($sql);
            $this->preparedStatement->execute();
            $sqlInserisciGiustifica = "INSERT INTO Giustifica(Data,Descrizione,TipologiaGiustifica,IdAssenza,CFDocente) VALUES ('" . $data . "',"
                    . "'" . $descrizione . "',"
                    . "'" . $tipologia . "',"
                    . "'" . $idAssenza . "',"
                    . "'" . $codF . "')";
            $this->preparedStatement = $this->conn->prepare($sqlInserisciGiustifica);
            $this->preparedStatement->execute();
            if (!$this->giustificaPresente($idAssenza)) {
                return "Giustifica non effettuata";
            } else {
                return "Giustifica effettuata";
            }
        } else {
            return "Assenza gia giustificata";
        }
    }

    /*
     * Metodo che effettua la firma
     */



    /*
     * METODI DI MODIFICA
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

    function modificaCredenziali($newUsername, $newPassword, $oldUsername, $oldPassword) {
        $sql = "UPDATE Credenziali "
                . "SET Username='" . $newUsername . "', Password='" . $newPassword . "' "
                . "WHERE Username='" . $oldUsername . "' AND Password='" . $oldPassword . "';";
        $this->preparedStatement = $this->conn->prepare($sql);
        $this->preparedStatement->execute();
        $qry = " SELECT * FROM Credenziali "
                . "WHERE Username='" . $newUsername . "' AND Password='" . $newPassword . "'";
        $result = $this->conn->query($qry);
        $app = $result->fetchColumn(0);
        return $app != "";
    }

    /*
     * METODI DA PROVARE
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

    /*
     * Metodo che inserisce la firma
     */

    function insertFirma($data, $username, $password, $nOre) {
        $codF = $this->getIdDocente($username, $password);
        $ora = date("H", time());
        if ($this->lezioneGiaPresente($data, $username, $password, $ora)) {
            $idLezione = $this->getIdLezione($codF, $data, $ora);

            if ($this->firmaGiaPresente($data, $codF, $idLezione)) {
                return "firma gia inserita";
            } else {
                $this->preparedStatement = $this->conn->prepare("INSERT INTO Firma (Data,CFDocente,IdLezione,nOre) VALUES('" . $data . "','"
                        . $codF . "',"
                        . $idLezione . ","
                        . $nOre . ")");
                $this->preparedStatement->execute();
                if ($this->firmaGiaPresente($data, $codF, $idLezione)) {
                    return "firma inserita";
                } else {
                    return "firma non inserita";
                }
            }
        } else {
            return "lezione gia presente";
        }
    }

    /*
     * Metodo che inserisce la lezione specificata all'interno del database
     */

    function insertLezione($data, $materia, $descrizione, $username, $password, $idClasse) {
        $codF = $this->getIdDocente($username, $password);
        date_default_timezone_set("CET");
        $ora = date("H", time());
        if ($this->lezioneGiaPresente($data, $username, $password, $ora)) {
            return false;
        } else {
            $this->preparedStatement = $this->conn->prepare("INSERT INTO Lezione (Data,Ora,Materia,Descrizione,CFDocente,IdClasse) VALUES('" . $data . "',"
                    . "'" . $ora . "',"
                    . "'" . $materia . "', "
                    . "'" . $descrizione . "', "
                    . "'" . $codF . "',"
                    . $idClasse . ")");
            $this->preparedStatement->execute();
            if ($this->lezioneGiaPresente($data, $username, $password, $ora)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 
     * @param type $data
     * @param type $username
     * @param type $password
     * @param type $ora
     * @return boolean
     * 
     * Metodo che controlla se esiste gia la lezione
     * 
     */
    function lezioneGiaPresente($data, $username, $password, $ora) {
        $codF = $this->getIdDocente($username, $password);
        $qryAssente = "SELECT Count(*) "
                . "FROM Lezione WHERE Data='" . $data . "' AND CFDocente='" . $codF . "' AND Ora=" . $ora . " "
                . "GROUP BY ID";
        $resultQry = $this->conn->query($qryAssente);
        $count = $resultQry->fetchColumn(0);
        if ($count == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 
     * @param type $data
     * @param type $codF
     * @param type $idLezione
     * @return boolean
     * 
     * Metodo che controlla se c'è gia la firma
     * 
     */
    function firmaGiaPresente($data, $codF, $idLezione) {
        $qryAssente = "SELECT Count(*) FROM Firma "
                . "WHERE Data='" . $data . "' AND CFDocente='" . $codF . "' AND IdLezione=" . $idLezione . " "
                . "GROUP BY CodiceFirma";
        $resultQry = $this->conn->query($qryAssente);
        $count = $resultQry->fetchColumn(0);
        if ($count == 0) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Metodo che preleva l'id della lezione 
     */

    function getIdLezione($codF, $data, $ora) {
        $qryInfoStud = "SELECT ID "
                . "FROM Lezione "
                . "WHERE CFDocente='" . $codF . "' AND Data='" . $data . "' AND Ora=" . $ora . "";
        $idLezione = $this->conn->query($qryInfoStud)->fetchColumn(0);
        return $idLezione;
    }

    /**
     * 
     * @param type $username
     * @param type $password
     * 
     * @return boolean
     * 
     * cancella la firma
     * 
     */
    function deleteFirma($data, $username, $password) {
        $codF = $this->getIdDocente($username, $password);
        date_default_timezone_set("CET");
        $ora = date("H", time());
        $idLezione = $this->getIdLezione($codF, $data, $ora);
        echo $idLezione;
        $qryInfoStud = "DELETE FROM Firma WHERE Data='" . $data . "' AND CFDocente='" . $codF . "' AND IdLezione=" . $idLezione . "";
        $this->preparedStatement = $this->conn->prepare($qryInfoStud);
        $this->preparedStatement->execute();
    }

    /**
     * 
     * @param type $username
     * @param type $password
     * @param type $data
     * @return string
     * 
     * cancella la Lezione
     * 
     */
    function deleteLezione($username, $password, $data) {
        $codF = $this->getIdDocente($username, $password);
        date_default_timezone_set("CET");
        $ora = date("H", time());
        $qryInfoStud = "DELETE FROM Lezione WHERE CFDocente='" . $codF . "' AND Data='" . $data . "' AND Ora=" . $ora . "";
        $this->preparedStatement = $this->conn->prepare($qryInfoStud);
        $this->preparedStatement->execute();
        if (!$this->lezioneGiaPresente($data, $username, $password, $ora)) {
            return "firma cancellata";
        } else {
            return "firma non cancellata";
        }
    }

}
