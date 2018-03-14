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

}
