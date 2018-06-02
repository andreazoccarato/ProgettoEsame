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
        $clSez = $request->input('password');

        $result = $this->database->requestQrCode($username, $password);

        if (count($result) == 0) {
            $idDoc = $this->database->getIdDocente($username, $password);
            $scuola = $this->database->getScuola($username, $password);
            $classe = $this->database->getClasseByClsezAndScuola($clSez, $scuola);
            $time = date("H", time());
            $data = date("j/m/Y", time());
            $random = rand(1000, 10000);


            $strQrCode = $idDoc . "-" . $scuola . "-" . $classe . "-" . $data . "-" . $random;
            $this->database->insertQrCode($data, $random, $scuola, $classe, $idDoc);

            $app = $this->database->requestQrCode($username, $password);

            QRcode::png($strQrCode, "test.png", "L", 5, 5);

            $risposta = Array("qrCodeRequest" => $app);
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
        $scuola = $request->input('scuola');
        $classe = $request->input('classe');
        $cod = $request->input('codice');
        $data = $request->input('data');
        $presenzaOassenza = $request->input('presenzaOassenza');

        $this->database->connect();
        $valido = $this->database->checkQrCode($data, $scuola, $classe, $cod);
        if ($valido == true) {
            if ($presenzaOassenza == "presenza") {
                $this->insertPresenza($data, $username, $password);
            } else {
                $this->insertAssenza($data, $username, $password);
            }
        } else {
            $risposta = Array("qrCodeCheck" => "QrCode non valido");
            echo json_encode($risposta);
        }
        $this->database->disconnect();
    }

    private function insertPresenza($data, $username, $password) {
        $ris = $this->database->insertPresenza($data, $username, $password);
        if ($ris) {
            $risposta = Array("qrCodeCheck" => "Presenza inserita correttamente");
            echo json_encode($risposta);
        } else {
            $risposta = Array("qrCodeCheck" => "Studente già presente");
            echo json_encode($risposta);
        }
    }

    private function insertAssenza($data, $username, $password) {
        $ris = $this->database->insertAssenza($data, $username, $password);
        if ($ris) {
            $risposta = Array("qrCodeCheck" => "Assenza inserita correttamente");
            echo json_encode($risposta);
        } else {
            $risposta = Array("qrCodeCheck" => "Studente già assenza");
            echo json_encode($risposta);
        }
    }

}
