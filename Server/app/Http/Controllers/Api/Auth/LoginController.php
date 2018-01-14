<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Laravel\Passport\Client;
use Illuminate\Http\Request;

include '..\app\Http\Controllers\Api\Auth\Database.php';

class LoginController extends Controller {

    private $client;
    private $database;

    public function __construct() {
        $pathToDatabase='database\dbProgettoEsame.sqlite';
        $this->client = Client::find(1);
        $this->database = new Database("sqlite:/" . $pathToDatabase);
    }

    public function login(Request $request) {
        echo 'Dentro Login';
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');
        echo "Username: " . $username;
        echo "Password: " . $password;


        $app = $this->database->login($username, $password);
        $this->database->disconnect();
        if ($app === 'Docente') {
            $this->risposta('Docente');
        } else if ($app === 'Studente') {
            $this->risposta('Studente');
        } else {
            $this->risposta('nessuno');
        }
    }

    private function risposta($ruolo) {
        echo "Ruolo: " . $ruolo;
        return response()->json([
                    'Ruolo' => '' . $ruolo . ''
        ]);
    }

}
