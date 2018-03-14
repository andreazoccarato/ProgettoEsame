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

        $risposta = Array("Eventi" => $result);
        $this->database->disconnect();
        echo json_encode($risposta);
    }

}
