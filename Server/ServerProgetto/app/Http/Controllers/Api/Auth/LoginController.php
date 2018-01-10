<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Laravel\Passport\Client;
use Illuminate\Http\Request;

class LoginController extends Controller {

    private $client;
    private $database;

    public function __construct() {
        $this->client = Client::find(1);
    }

    public function login(Request $request) {
        $this->database = new Database();

        $username = $request->input('username');
        $password = $request->input('password');

        $app = $this->database->login($username, $password);
        if ($app === 'Docente') {
            $this->risposta($request, 'Docente');
        } else if ($app === 'Studente') {
            $this->risposta($request, 'Studente');
        } else {
            $this->risposta($request, 'nessuno');
        }
    }

    private function risposta(Request $request, tring $ruolo) {
        $params['Ruolo'] = $ruolo;
        $request->request->add($params);
        $proxy = Request::create('oauth/token', 'POST');
        return Route::dispatch($proxy);
    }

}
