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

        $result = $this->database->requestQrCode($username, $password);

        if (count($result) == 0) {
            $idDoc = $this->database->getIdDocente($username, $password);
            $scuola = $this->database->getScuola($username, $password);
            $classe = $this->database->getClasse($username, $password);
            $time = date("H", time());
            $data = date("j-n-Y", time());
            $random = rand(1000, 10000);

            $strQrCode = $idDoc . "-" . $scuola . "-" . $classe . "-" . $time . "-" . $random;
            $this->database->insertQrCode($data, $random, $scuola, $classe, $idDoc);

            QRcode::png($strQrCode, "test.png", "L", 5, 5);

            $risposta = Array("qrCodeRequest" => $strQrCode);
            echo json_encode($risposta);
        } else {
            $risposta = Array("qrCodeRequest" => $result);
            echo json_encode($risposta);
        }
        $this->database->disconnect();
    }

    public function setPresenza(Request $request) {
        $username = $request->input('username');
        $password = $request->input('password');
        $useDocente = $request->input('useDocente');
        $passDocente = $request->input('passDocente');
        $scuola = $request->input('scuola');
        $classe = $request->input('classe');
        $cod = $request->input('codice');
        $data = $request->input('data');
        $presenzaOassenza = $request->input('presenzaOassenza');

        $valido = $this->database->checkQrCode($data, $scuola, $classe, $cod);
        if ($valido == true) {
            if ($presenzaOassenza == "presenza") {
                $this->insertPresenza($data, $scuola, $classe, $username, $password, $useDocente, $passDocente);
            } else {
                $this->insertAssenza($data, $scuola, $classe, $username, $password, $useDocente, $passDocente);
            }
        } else {
            $risposta = Array("qrCodeCheck" => "QrCode non valido");
            echo json_encode($risposta);
        }
    }

    private function insertPresenza($data, $scuola, $classe, $username, $password, $useDocente, $passDocente) {
        $this->database->connect();
        $ris = $this->database->insertPresenza($data, $scuola, $classe, $username, $password, $useDocente, $passDocente);
        if ($ris) {
            $risposta = Array("qrCodeCheck" => "Presenza inserita correttamente");
            echo json_encode($risposta);
        } else {
            $risposta = Array("qrCodeCheck" => "Studente gi� presente");
            echo json_encode($risposta);
        }
        $this->database->disconnect();
    }

    private function insertAssenza($data, $scuola, $classe, $username, $password, $useDocente, $passDocente) {
        $this->database->connect();
        $ris = $this->database->insertAssenza($data, $scuola, $classe, $username, $password, $useDocente, $passDocente);
        if ($ris) {
            $risposta = Array("qrCodeCheck" => "Assenza inserita correttamente");
            echo json_encode($risposta);
        } else {
            $risposta = Array("qrCodeCheck" => "Studente gi� assenza");
            echo json_encode($risposta);
        }
        $this->database->disconnect();
    }

}
