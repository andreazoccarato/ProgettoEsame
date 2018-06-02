<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

include '..\app\Http\Controllers\Api\Auth\Database.php';

class AssenzeController extends Controller {

    private $database;

    public function __construct() {
        $this->database = new Database();
    }

    public function getAssenze(Request $request) {
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');

        $assenze = $this->database->getAssenze($username, $password);
        $this->database->disconnect();

        $risposta = Array("Assenze" => $assenze);
        echo json_encode($risposta);
    }

    public function getAssenzeNonGiustificate(Request $request) {
        $this->database->connect();

        $codF = $request->input('codiceFiscaleStudente');

        $assenze = $this->database->getAssenzeNonGiustificate($codF);
        $this->database->disconnect();

        $risposta = Array("Assenze" => $assenze);
        echo json_encode($risposta);
    }

    public function giustificaAssenza(Request $request) {
        $this->database->connect();

        $idAssenza = $request->input('idAssenza');
        $descrizione = $request->input('descrizione');
        $username = $request->input('username');
        $password = $request->input('password');
        $tipologia = $request->input('tipologia');
        $data = date("j/m/Y", time());

        $ris = $this->database->giustifica($idAssenza, $descrizione, $data, $tipologia, $username, $password);

        $risposta = Array("RisultatoGiustifica" => $ris);
        echo json_encode($risposta);
    }

    public function setPresenzaAssenza(Request $request) {
        $this->database->connect();

        $codF = $request->input('cfStud');
        $tipologia = $request->input('tipologia');
        $data = date("j/m/Y", time());

        $credenziali = $this->database->getUsernamePasswordStud($codF);

        $username = $credenziali[0][0];
        $password = $credenziali[0][1];

        if ($tipologia == "assenza") {
            $this->insertAssenza($data, $username, $password);
        } else {
            $this->insertPresenza($data, $username, $password);
        }

        $this->database->disconnect();
    }

    private function insertPresenza($data, $username, $password) {
        $ris = $this->database->insertPresenza($data, $username, $password);
        if ($ris) {
            $risposta = Array("qrCodeCheck" => "Presenza inserita correttamente");
            echo json_encode($risposta);
        } else {
            $risposta = Array("qrCodeCheck" => "Studente già presente");
            echo json_encode($risposta);
        }
    }

    private function insertAssenza($data, $username, $password) {
        $ris = $this->database->insertAssenza($data, $username, $password);
        if ($ris) {
            $risposta = Array("qrCodeCheck" => "Assenza inserita correttamente");
            echo json_encode($risposta);
        } else {
            $risposta = Array("qrCodeCheck" => "Studente già assenza");
            echo json_encode($risposta);
        }
    }

}
