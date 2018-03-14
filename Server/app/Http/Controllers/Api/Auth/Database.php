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
        $codF = getIdDocente($username, $password);
        $currentData = date("j-n-Y", time());
        $qry = "SELECT tblQrCode.Giorno "
                . "FROM tblQrCode "
                . "WHERE CodiceFiscale='" . $codF . "' AND Giorno='" . $currentData . "'";
        $result = $this->conn->query($qry);
        $app = $result->fetchColum(0);
        if ($app != "") {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Metodo che controlla l'esistenza del qrCode
     */

    function checkQrCode($username, $password, $giorno, $scuola, $classe, $cod) {
        $isPresente = studenteGi�Presente($username, $password, $giorno, $scuola, $classe);
        if ($isPresente == TRUE) {
            return "Studente gi� presente";
        } else {
            $qryCheck = "SELECT tblQrCode.* "
                    . "FROM tblQrCode "
                    . "WHERE Giorno='" . $giorno . "' AND CodiceControllo=" . $cod . " AND IdClasse=" . $classe . " AND IdScuola=" . $scuola;
            $resultQry = $this->conn->query($qryCheck);
            $count = $resultQry->fetchColumn(0);
            if ($count != 0) {
                return;
            } else {
                return "NOK";
            }
        }
    }

    /*
     * Metodo che controlla se lo studente � gi� presente
     */

    function studenteGi�Presente($username, $password, $giorno, $scuola, $classe) {
        
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
                . "WHERE IdClasse='" . $idClasse . "' AND Data='".$data."';";
        $arrayCompiti = $this->conn->query($qryCompiti)->fetchAll(PDO::FETCH_ASSOC);
        $ris = array('Lezioni', $arrayLezioni, 'Compiti', $arrayCompiti);
        return $ris;
    }

    /*
     * Metodo che restituisce tutti gli eventi a partire dalla classe
     */

    function getEventsByClass($idClasse) {
        
    }

    /*
     * METODI DI INSERIMENTO
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

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

    function insertScuola($CodiceScuola, $Nome, $Indirizzo, $Citt�) {
        $this->preparedStatement = $this->conn->prepare("INSERT INTO Scuola VALUES('" . $CodiceScuola . "',"
                . "'" . $Nome . "',"
                . "'" . $Indirizzo . "',"
                . "'" . $Citt� . "')");
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
     * Metodo che inserisce la lezione specificata all'interno del database
     */

    function insertLezione($ID, $Data, $Ora, $Materia, $Descrizione, $CFDocente) {
        $this->preparedStatement = $this->conn->prepare("INSERT INTO Studente VALUES('" . $ID . "',"
                . "'" . $Data . "',"
                . "'" . $Ora . "',"
                . "'" . $Materia . "' "
                . "'" . $Descrizione . "' "
                . "'" . $CFDocente . "')");
        $this->preparedStatement->execute();
    }

    /*
     * Metodo che inserisce la valutazione specificata all'interno del database
     */

    function insertValutazione($ID, $Voto, $Data, $Ora, $CFStudente, $CFDocente) {
        $this->preparedStatement = $this->conn->prepare("INSERT INTO Studente VALUES('" . $ID . "',"
                . "'" . $Voto . "',"
                . "'" . $Data . "',"
                . "'" . $Ora . "',"
                . "'" . $CFStudente . "',"
                . "'" . $CFDocente . "')");
        $this->preparedStatement->execute();
    }

    /*
     * Metodo che inserisce la giustifica di assenza all'interno del database
     */

    function insertGiustifica($IdGiustifica, $Data, $IdAssenza, $CFDocente) {
        $this->preparedStatement = $this->conn->prepare("INSERT INTO Studente VALUES('" . $IdGiustifica . "' "
                . "'" . $Data . "',"
                . "'" . $IdAssenza . "',"
                . "'" . $CFDocente . "')");
        $this->preparedStatement->execute();
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

    function insertPresenza($username, $password, $useDocente, $passDocente, $data, $scuola, $classe, $cod) {
        
    }

}
