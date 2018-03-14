<?php

use Illuminate\Database\Seeder;

class AgendaSeeder extends Seeder {

    public function run() {
            
        DB::table('Agenda')->delete();
        DB::table('Agenda')->insert(array(
            'Data' => '14/03/2018',
            'Materia' => 'Informatica',
            'Descrizione' => 'Simulazione terza prova',
            'CFDocente' => 'CRLSVG60S20L736G',
            'IdClasse' => 1
        ));
        DB::table('Agenda')->insert(array(
            'Data' => '24/03/2018',
            'Materia' => 'Scienze motorie e sportive',
            'Descrizione' => 'Verifica alimentazione',
            'CFDocente' => 'DONBOR60S20L736G',
            'IdClasse' => 1
        ));
        DB::table('Agenda')->insert(array(
            'Data' => '25/02/2018',
            'Materia' => 'Lingua e letteratura italiana',
            'Descrizione' => 'Tema italiano tip. D',
            'CFDocente' => 'BENLUP60S20L736G',
            'IdClasse' => 1
        ));
        DB::table('Agenda')->insert(array(
            'Data' => '1/03/2018',
            'Materia' => 'Matematica',
            'Descrizione' => 'Verifica Serie numeriche',
            'CFDocente' => 'DONELI60S20L736G',
            'IdClasse' => 1
        ));

    }

}