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
            'Descrizione' => 'Database e Interrogazioni SQL',
            'CFStudente' => 'ZCCNDR99S22F241A',
            'CfDocente' => 'CRLSVG60S20L736G'
        ));
        DB::table('Valutazione')->insert(array(
            'Voto' => '8',
            'Data' => '30/01/2017',
            'Ora' => '5',
            'Materia' => 'Informatica',
            'Descrizione' => 'Database e normalizzazione',
            'CFStudente' => 'ZCCNDR99S22F241A',
            'CfDocente' => 'CRLSVG60S20L736G'
        ));
        DB::table('Valutazione')->insert(array(
            'Voto' => '6+',
            'Data' => '03/03/2018',
            'Ora' => '2',
            'Materia' => 'Lingua e letteratura italiana',
            'Descrizione' => 'Tema Storico',
            'CFStudente' => 'ZCCNDR99S22F241A',
            'CfDocente' => 'BENLUP60S20L736G'
        ));
        DB::table('Valutazione')->insert(array(
            'Voto' => '5',
            'Data' => '05/02/2018',
            'Ora' => '3',
            'Materia' => 'Scienze motorie e sportive',
            'Descrizione' => 'Preparazione Pallamano',
            'CFStudente' => 'ZCCNDR99S22F241A',
            'CfDocente' => 'DONBOR60S20L736G'
        ));
        DB::table('Valutazione')->insert(array(
            'Voto' => '9-',
            'Data' => '23/01/2018',
            'Ora' => '1',
            'Materia' => 'Matematica',
            'Descrizione' => 'Serie numeriche',
            'CFStudente' => 'ZCCNDR99S22F241A',
            'CfDocente' => 'DONELI60S20L736G'
        ));
    }

}