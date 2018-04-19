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
        DB::table('Studente')->insert(array(
            'CodiceFiscale' => 'LEOBIGG99S22F241A',
            'Nome' => 'Leonardo',
            'Cognome' => 'Bigietti',
            'DataNascita' => '22/11/1999',
            'IdClasse' => '1',
            'IdCredenziali' => '6',
            'CodiceScuola' => 123
        ));
        DB::table('Studente')->insert(array(
            'CodiceFiscale' => 'MARDEV99S22F241A',
            'Nome' => 'Devid',
            'Cognome' => 'Martorel',
            'DataNascita' => '22/11/1999',
            'IdClasse' => '1',
            'IdCredenziali' => '7',
            'CodiceScuola' => 123
        ));
        DB::table('Studente')->insert(array(
            'CodiceFiscale' => 'MODLUC99S22F241A',
            'Nome' => 'Luca',
            'Cognome' => 'Modolo',
            'DataNascita' => '22/11/1999',
            'IdClasse' => '1',
            'IdCredenziali' => '8',
            'CodiceScuola' => 123
        ));
        DB::table('Studente')->insert(array(
            'CodiceFiscale' => 'RICCAM99S22F241A',
            'Nome' => 'Riccardo',
            'Cognome' => 'Camillo',
            'DataNascita' => '22/11/1999',
            'IdClasse' => '1',
            'IdCredenziali' => '9',
            'CodiceScuola' => 123
        ));
        DB::table('Studente')->insert(array(
            'CodiceFiscale' => 'CHRCHI99S22F241A',
            'Nome' => 'Christian',
            'Cognome' => 'Micheletto',
            'DataNascita' => '22/11/1999',
            'IdClasse' => '1',
            'IdCredenziali' => '10',
            'CodiceScuola' => 123
        ));
        DB::table('Studente')->insert(array(
            'CodiceFiscale' => 'DYLVAN99S22F241A',
            'Nome' => 'Dylan',
            'Cognome' => 'Vanzan',
            'DataNascita' => '22/11/1999',
            'IdClasse' => '1',
            'IdCredenziali' => '11',
            'CodiceScuola' => 123
        ));
        DB::table('Studente')->insert(array(
            'CodiceFiscale' => 'MARCTRA99S22F241A',
            'Nome' => 'Marco',
            'Cognome' => 'Tramontini',
            'DataNascita' => '22/11/1999',
            'IdClasse' => '1',
            'IdCredenziali' => '12',
            'CodiceScuola' => 123
        ));
    }

}
