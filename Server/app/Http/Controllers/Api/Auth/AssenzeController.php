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

}
