<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class ClassiController extends Controller {

    private $database;

    public function __construct() {
        $this->database = new Database();
    }

    public function getClassi(Request $request) {
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');

        $classi = $this->database->getClassiDocente($username, $password);
        $this->database->disconnect();
        
        $risposta = Array("Classi" => $classi);
        echo json_encode($risposta);
    }

}
