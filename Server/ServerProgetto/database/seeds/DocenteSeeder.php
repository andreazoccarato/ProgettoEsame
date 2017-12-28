<?php

use Illuminate\Database\Seeder;

class DocenteSeeder extends Seeder {

    public function run() {
        DB::table('Docente')->delete();
        DB::table('Docente')->insert(array(
            'CodiceFiscale' => 'CRLSVG60S20L736G',
            'Nome' => 'Carlo',
            'Cognome' => 'Salvagno',
            'DataNascita' => '20/11/1960',
            'IdCredenziali' => '2'
        ));
    }

}