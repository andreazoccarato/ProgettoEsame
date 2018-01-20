<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Laravel\Passport\Client;
use Illuminate\Http\Request;
use Illuminate\Http\Response;

include '..\app\Http\Controllers\Api\Auth\Database.php';

class LoginController extends Controller {

    private $client;
    private $database;

    public function __construct() {
        $this->client = Client::find(1);
        $this->database = new Database();
    }

    public function login(Request $request) {
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');

        $app = $this->database->login($username, $password);
        if ($app === 'Docente') {
            $this->risposta('Docente');
        } else if ($app === 'Studente') {
            $this->risposta('Studente');
        } else {
            $this->risposta('nessuno');
        }
    }

    private function risposta($ruolo) {
        $risposta = Array("Ruolo" => $ruolo);
        echo json_encode($risposta);
//        echo response()->json([
//            'Ruolo' => '' . $ruolo . '']);
//        $this->database->disconnect();
        return response()->json([
                    'Ruolo' => '' . $ruolo . ''
        ]);
    }

}
