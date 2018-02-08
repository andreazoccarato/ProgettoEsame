<?php

use Illuminate\Database\Seeder;

class StudenteSeeder extends Seeder {

    public function run() {
        DB::table('Studente')->delete();
        DB::table('Studente')->insert(array(
            'CodiceFiscale' => 'ZCCNDR99S22F241A',
            'Nome' => 'Andrea',
            'Cognome' => 'Zoccarato',
            'DataNascita' => '22/11/1999',
            'IdClasse' => '1',
            'IdCredenziali' => '1',
            'CodiceScuola' => 123
        ));
    }

}