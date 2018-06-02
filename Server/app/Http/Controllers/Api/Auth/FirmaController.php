<?php

namespace App\Http\Controllers\Api\Auth;

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

include '..\app\Http\Controllers\Api\Auth\Database.php';

class FirmaController extends Controller {

    private $database;

    public function __construct() {
        $this->database = new Database();
    }

    public function firma(Request $request) {
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');
        $materia = $request->input('materia');
        $descrizione = $request->input('descrizione');
        $idClasse = $request->input('idClasse');
        $nOre = $request->input('nOre');

        $data = date("j/m/Y", time());

        $this->database->insertLezione($data, $materia, $descrizione, $username, $password, $idClasse);
        $ris = $this->database->insertFirma($data, $username, $password, $nOre);
        $this->database->disconnect();

        $risposta = Array("RispostaFirma" => $ris);
        echo json_encode($risposta);
    }

    public function cancellaFirma(Request $request) {
        $this->database->connect();
        $data = $data = date("j/m/Y", time());

        $username = $request->input('username');
        $password = $request->input('password');

        $this->database->deleteFirma($data, $username, $password);
        $ris = $this->database->deleteLezione($username, $password, $data);
        $this->database->disconnect();
        $risposta = Array("RispostaCancellaFirma" => $ris);
        echo json_encode($risposta);
    }

}
