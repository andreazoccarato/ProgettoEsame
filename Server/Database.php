<?php

class Database extends PDO {

    private $conn;
    private $preparedStatement;

    function Database() {
        $this->conn = new PDO('mysql:host=localhost;dbname=dbProgettoEsame');
    }

    function existStud($nome, $cognome) {
        $qry = "SELECT Studente.* FROM Studente WHERE Nome='" . $nome . "' AND Cognome='" . $cognome . "'";
        if ($this->conn->exec($qry) == 1) {
            return true;
        } else {
            return false;
        }
    }

    function existStudByCF($CodiceFiscale) {
        $qry = "SELECT Studente.* FROM Studente WHERE CodiceFiscale='" . $CodiceFiscale . "'";
        if ($this->conn->exec($qry) == 1) {
            return true;
        } else {
            return false;
        }
    }

    function existDoc($nome, $cognome) {
        $qry = "SELECT Docente.* FROM Docente WHERE Nome='" . $nome . "' AND Cognome='" . $cognome . "'";
        if ($this->conn->exec($qry) == 1) {
            return true;
        } else {
            return false;
        }
    }

    function existDocByCF($CodiceFiscale) {
        $qry = "SELECT Docente.* FROM Docente WHERE CodiceFiscale='" . $CodiceFiscale . "'";
        if ($this->conn->exec($qry) == 1) {
            return true;
        } else {
            return false;
        }
    }

    function login($username, $password) {
        $qryLogin = "SELECT Credenziali.* FROM Credenziali WHERE Username='" . $username . "' AND Password='" . $password . "'";
        if ($this->conn->exec($qryLogin) == 1) {
            $result = $this->conn->query(qryLogin);
            $CF = $result['CodiceFiscale'];
            if (existDocByCF($CF)) {
                return "Docente";
            } else if (existStudByCF($CF)) {
                return "Studente";
            }
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

?>