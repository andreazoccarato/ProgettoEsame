<?php

use Illuminate\Database\Seeder;

class ValutazioneSeeder extends Seeder {

    public function run() {
        DB::table('Valutazione')->delete();
        DB::table('Valutazione')->insert(array(
            'Voto' => '10',
            'Data' => '21/12/2017',
            'Ora' => '5',
            'Materia' => 'Informatica',
            'CFStudente' => 'ZCCNDR99S22F241A',
            'CfDocente' => 'CRLSVG60S20L736G'
        ));
    }

}