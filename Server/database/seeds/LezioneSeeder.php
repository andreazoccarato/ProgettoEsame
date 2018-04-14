<?php

use Illuminate\Database\Seeder;

class LezioneSeeder extends Seeder {

    public function run() {
        DB::table('Lezione')->delete();
        DB::table('Lezione')->insert(array(
            'Data' => '15/03/2018',
            'Ora' => '5',
            'Materia' => 'Informatica',
            'Descrizione' => 'Database e query',
            'CFDocente' => 'CRLSVG60S20L736G',
            'IdClasse' => 1
        ));
        DB::table('Lezione')->insert(array(
            'Data' => '15/03/2018',
            'Ora' => '3',
            'Materia' => 'Matematica',
            'Descrizione' => 'Serie di funzioni',
            'CFDocente' => 'DONELI60S20L736G',
            'IdClasse' => 1
        ));
    }

}