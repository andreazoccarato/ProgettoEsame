<?php

namespace App\Http\Controllers\Api\Auth;

use PDO;

class Database {

    private $conn;
    private $preparedStatement;

    function _construct() {
        $this->conn = null;
    }

    function connect() {
        if ($this->conn == null) {
            $this->conn = new PDO('sqlite:../storage/dbProgettoEsame.sqlite');
        }
    }

    function disconnect() {
        if ($this->conn != null) {
            $this->conn = null;
        }
    }

    function existStud($nome, $cognome) {
        $qry = "SELECT count(*) FROM Studente WHERE Nome='" . $nome . "' AND Cognome='" . $cognome . "'";
        $result = $this->conn->query($qry);
        $count = $result->fetchColumn(0);
        if ($count!=0) {
            return true;
        } else {
            return false;
        }
    }

    function existStudByCF($CodiceFiscale) {
        $qry = "SELECT count(*) FROM Studente WHERE CodiceFiscale='" . $CodiceFiscale . "'";
        $result = $this->conn->query($qry);
        $count = $result->fetchColumn(0);
        if ($count!=0) {
            return true;
        } else {
            return false;
        }
    }

    function existDoc($nome, $cognome) {
        $qry = "SELECT count(*) FROM Docente WHERE Nome='" . $nome . "' AND Cognome='" . $cognome . "'";
        $result = $this->conn->query($qry);
        $count = $result->fetchColumn(0);
        if ($count!=0) {
            return true;
        } else {
            return false;
        }
    }

    function existDocByCF($CodiceFiscale) {
        $qry = "SELECT count(*) FROM Docente WHERE CodiceFiscale='" . $CodiceFiscale . "'";
        $result = $this->conn->query($qry);
        $count = $result->fetchColumn(0);
        if ($count!=0) {
            return true;
        } else {
            return false;
        }
    }

    function login($username, $password) {
        echo ' ' . $username . ' ';
        echo ' ' . $password . ' ';
        $qryLogin = "SELECT Studente.CodiceFiscale FROM Credenziali INNER JOIN Studente ON Credenziali.ID=Studente.IdCredenziali WHERE Username='" . $username . "' AND Password='" . $password . "'";
        $result = $this->conn->query($qryLogin);
        
        $CF = $result->fetchColumn(0);
        echo ' ' . $CF . ' ';
        if ($this->existDocByCF($CF)) {
            return "Docente";
        } else if ($this->existStudByCF($CF)) {
            return "Studente";
        } else {
            return "Username e/o Password errati";
        }
    }

    function insertStud($CodiceFiscale, $Nome, $Cognome, $DataNascita, $Sezione, $IdCredenziali) {
        $this->preparedStatement->prepare("INSERT INTO Studente VALUES('" . $CodiceFiscale . "' "
                . "'" . $Nome . "' "
                . "'" . $Cognome . "' "
                . "'" . $DataNascita . "' "
                . "'" . $Sezione . "' "
                . "'" . $IdCredenziali . "')");
        $this->preparedStatement->execute();
    }

    function insertDoc($CodiceFiscale, $Nome, $Cognome, $DataNascita, $IdCredenziali) {
        $this->preparedStatement->prepare("INSERT INTO Docente VALUES('" . $CodiceFiscale . "' "
                . "'" . $Nome . "' "
                . "'" . $Cognome . "' "
                . "'" . $DataNascita . "' "
                . "'" . $IdCredenziali . "')");
        $this->preparedStatement->execute();
    }

    function insertScuola($CodiceScuola, $Nome, $Indirizzo, $Città) {
        $this->preparedStatement->prepare("INSERT INTO Scuola VALUES('" . $CodiceScuola . "' "
                . "'" . $Nome . "' "
                . "'" . $Indirizzo . "' "
                . "'" . $Città . "')");
        $this->preparedStatement->execute();
    }

    function insertClasse($Sezione, $Indirizzo, $CodiceScuola) {
        $this->preparedStatement->prepare("INSERT INTO Scuola VALUES('" . $Sezione . "' "
                . "'" . $Indirizzo . "' "
                . "'" . $CodiceScuola . "')");
        $this->preparedStatement->execute();
    }

    function insertLezione($ID, $Data, $Ora, $Materia, $Descrizione, $CFDocente) {
        $this->preparedStatement->prepare("INSERT INTO Studente VALUES('" . $ID . "' "
                . "'" . $Data . "' "
                . "'" . $Ora . "' "
                . "'" . $Materia . "' "
                . "'" . $Descrizione . "' "
                . "'" . $CFDocente . "')");
        $this->preparedStatement->execute();
    }

    function insertValutazione($ID, $Voto, $Data, $Ora, $CFStudente, $CFDocente) {
        $this->preparedStatement->prepare("INSERT INTO Studente VALUES('" . $ID . "' "
                . "'" . $Voto . "' "
                . "'" . $Data . "' "
                . "'" . $Ora . "' "
                . "'" . $CFStudente . "' "
                . "'" . $CFDocente . "')");
        $this->preparedStatement->execute();
    }

    function insertGiustifica($IdGiustifica, $Data, $IdAssenza, $CFDocente) {
        $this->preparedStatement->prepare("INSERT INTO Studente VALUES('" . $IdGiustifica . "' "
                . "'" . $Data . "' "
                . "'" . $IdAssenza . "'"
                . "'" . $CFDocente . "')");
        $this->preparedStatement->execute();
    }

}
