<?php

use Illuminate\Database\Seeder;

class GiustificaSeeder extends Seeder {

    public function run() {
        DB::table('Giustifica')->delete();
        DB::table('Giustifica')->insert(array(
            'Data' => '20/04/2018',
            'Descrizione' => 'Febbre',
            'TipologiaGiustifica' => 'A',
            'IdAssenza' => 1,
            'CFDocente' => 'CRLSVG60S20L736G'
        ));
        DB::table('Giustifica')->insert(array(
            'Data' => '03/04/2018',
            'Descrizione' => 'Manca Tattica',
            'TipologiaGiustifica' => 'U',
            'IdAssenza' => 2,
            'CFDocente' => 'CRLSVG60S20L736G'
        ));
        DB::table('Giustifica')->insert(array(
            'Data' => '01/01/2018',
            'Descrizione' => 'Motivi Familiari',
            'TipologiaGiustifica' => 'A',
            'IdAssenza' => 3,
            'CFDocente' => 'CRLSVG60S20L736G'
        ));
        DB::table('Giustifica')->insert(array(
            'Data' => '01/01/2018',
            'Descrizione' => 'Visita Medica',
            'TipologiaGiustifica' => 'R',
            'IdAssenza' => 4,
            'CFDocente' => 'CRLSVG60S20L736G'
        ));
    }

}