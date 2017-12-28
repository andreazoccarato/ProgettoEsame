<?php

use Illuminate\Database\Seeder;

class LezioneSeeder extends Seeder {

    public function run() {
        DB::table('Lezione')->delete();
        DB::table('Lezione')->insert(array(
            'Data' => '21/12/2017',
            'Ora' => '5',
            'Materia' => 'Informatica',
            'Descrizione' => 'Database e query',
            'CFDocente' => 'CRLSVG60S20L736G'
        ));
    }

}