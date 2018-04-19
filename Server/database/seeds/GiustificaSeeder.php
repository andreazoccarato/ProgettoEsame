<?php

use Illuminate\Database\Seeder;

class GiustificaSeeder extends Seeder {

    public function run() {
        DB::table('Giustifica')->delete();
        DB::table('Giustifica')->insert(array(
            'Data' => '20/04/2018',
            'Descrizione' => 'Febbre',
            'TipologiaGiustifica' => 'S',
            'IdAssenza' => 1,
            'CFDocente' => 'CRLSVG60S20L736G'
        ));
        DB::table('Giustifica')->insert(array(
            'Data' => '03/04/2018',
            'Descrizione' => 'Uscita Anticipata',
            'TipologiaGiustifica' => 'U',
            'IdAssenza' => 3,
            'CFDocente' => 'CRLSVG60S20L736G'
        ));
        DB::table('Giustifica')->insert(array(
            'Data' => '01/01/2018',
            'Descrizione' => 'Motivi Familiari',
            'TipologiaGiustifica' => 'F',
            'IdAssenza' => 4,
            'CFDocente' => 'CRLSVG60S20L736G'
        ));
    }

}