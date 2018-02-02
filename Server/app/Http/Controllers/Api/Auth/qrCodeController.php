<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use Laravel\Passport\Client;
use Illuminate\Http\Request;
use QRcode;

include '..\vendor\phpqrcode\qrlib.php';
include '..\app\Http\Controllers\Api\Auth\Database.php';

class qrCodeController extends Controller {

    private $client;
    private $database;

    public function __construct() {
        $this->client = Client::find(1);
        $this->database = new Database();
    }

    public function getQrCode(Request $request) {
        $this->database->connect();

        $username = $request->input('username');
        $password = $request->input('password');

        $idDoc = $this->database->getIdDocente($username, $password);
        $scuola = $this->database->getScuola($username, $password);
        $classe = $this->database->getClasse($username, $password);
        $time = date("H", time());
        $random = rand(1000, 10000);

        $strQrCode = $idDoc . "-" . $scuola . "-" . $classe . "-" . $time . "-" . $random;

        $this->database->disconnect();

        QRcode::png($strQrCode, "test.png", "L", 5, 5);
        
        $risposta = Array("qrCodeRequest" => $strQrCode);
        echo json_encode($risposta);
    }

    public function setPresenza(Request $request) {
        
    }

}
