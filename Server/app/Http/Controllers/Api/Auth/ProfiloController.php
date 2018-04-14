<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

include '..\app\Http\Controllers\Api\Auth\Database.php';

class ProfiloController extends Controller {

    private $database;

    public function __construct() {
        $this->database = new Database();
    }

    public function updateProfilo(Request $request) {
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');
        $newPassword = $request->input('newPassword');
        $newUsername = $request->input('newUsername');

        $app = $this->database->modificaCredenziali($newUsername, $newPassword, $username, $password);
        $this->database->disconnect();

        if ($app == true) {
            $risposta = Array("RisultatoModifica" => "Modifica Riuscita");
            echo json_encode($risposta);
        } else {
            $risposta = Array("RisultatoModifica" => "Modifica Non Riuscita");
            echo json_encode($risposta);
        }
    }

    public function getProfilo(Request $request) {
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');

        $app = $this->database->getProfilo($username, $password);
        $this->database->disconnect();

        $risposta = Array("Info" => $app);
        echo json_encode($risposta);
    }

}
