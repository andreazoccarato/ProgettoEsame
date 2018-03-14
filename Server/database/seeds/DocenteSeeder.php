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
            'CodiceScuola' => 123,
            'IdCredenziali' => '2'
        ));
        DB::table('Docente')->insert(array(
            'CodiceFiscale' => 'DONBOR60S20L736G',
            'Nome' => 'Donata',
            'Cognome' => 'Borsato',
            'DataNascita' => '20/11/1960',
            'CodiceScuola' => 123,
            'IdCredenziali' => '3'
        ));
        DB::table('Docente')->insert(array(
            'CodiceFiscale' => 'BENLUP60S20L736G',
            'Nome' => 'Benedetta',
            'Cognome' => 'Luppi',
            'DataNascita' => '20/11/1960',
            'CodiceScuola' => 123,
            'IdCredenziali' => '4'
        ));
        DB::table('Docente')->insert(array(
            'CodiceFiscale' => 'DONELI60S20L736G',
            'Nome' => 'Elisabetta',
            'Cognome' => 'Doni',
            'DataNascita' => '20/11/1960',
            'CodiceScuola' => 123,
            'IdCredenziali' => '5'
        ));

    }

}