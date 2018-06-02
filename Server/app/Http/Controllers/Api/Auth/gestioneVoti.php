<?php

namespace App\Http\Controllers\Api\Auth;

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

include '..\app\Http\Controllers\Api\Auth\Database.php';

class gestioneVoti extends Controller {

    private $database;

    public function __construct() {
        $this->database = new Database();
    }

    public function getVotiByCred(Request $request) {
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');

        $array = $this->database->getVotiByCred($username, $password);

        $risposta = Array("Voti" => $array);
        $this->database->disconnect();
        echo json_encode($risposta);
    }

    public function addVoto(Request $request) {
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');
        $voto = $request->input('voto');
        $materia = $request->input('materia');
        $descrizione = $request->input('descrizione');
        $cfStud = $request->input('cfStud');
        $data = date("j/m/Y", time());
        $ora = date("H", time());

        $result = $this->database->addVoto($voto, $materia, $descrizione, $data, $ora, $cfStud, $username, $password);

        $risposta = Array("RisultatoVoto" => $result);
        $this->database->disconnect();
        echo json_encode($risposta);
    }

}
