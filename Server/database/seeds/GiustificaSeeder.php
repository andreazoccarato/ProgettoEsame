<?php

use Illuminate\Database\Seeder;

class GiustificaSeeder extends Seeder {

    public function run() {
        DB::table('Giustifica')->delete();
        DB::table('Giustifica')->insert(array(
            'Data' => '21/12/2017',
            'Descrizione' => 'Febbre',
            'TipologiaGiustifica' => 'S',
            'IdAssenza' => '1',
            'CFDocente' => 'CRLSVG60S20L736G'
        ));
    }

}