<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

include '..\app\Http\Controllers\Api\Auth\Database.php';

class gestioneEventi extends Controller {

    public function __construct() {
        $this->database = new Database();
    }

    public function getEventiByCred(Request $request) {
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');
        $data = $request->input('data');

        $result = $this->database->getEventsByCred($username, $password, $data);
        $this->database->disconnect();
        
        $risposta = Array("Eventi" => $result);
        echo json_encode($risposta);
    }

    public function getEventiByClass(Request $request) {
        $this->database->connect();

        $classe = $request->input('idClasse');
        
        $result = $this->database->getEventsByClass($classe);
        $this->database->disconnect();
        
        $risposta = Array("Eventi" => $result);
        echo json_encode($risposta);
    }

}
