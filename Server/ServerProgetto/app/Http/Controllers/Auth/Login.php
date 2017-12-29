<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;

include Database . php;

class Login extends Controller {

    private $database;

    public function autenticazione(Illuminate\Http\Request $request) {
        $this->database = new Database();

        $username = $request->input('username');
        $password = $request->input('password');
        
        $app = $this->database->login($username, $password);
        if ($app === 'Docente') {
            risposta('Docente');
        } else if ($app === 'Studente') {
            risposta('Studente');
        } else {
            risposta('nessuno');
        }
    }

    private function risposta(String $ruolo) {
        return response()->json(array('Ruolo' => $ruolo));
    }

}
