<?php

use Illuminate\Database\Seeder;

class AssenzaSeeder extends Seeder {

    public function run() {
        DB::table('Assenza')->delete();
        DB::table('Assenza')->insert(array(
            'CFStudente' => 'ZCCNDR99S22F241A',
            'Data' => '20/04/2018',
            'Orario' => '000000000111111111111111',
            'Giustificato' => 'true'
        ));
        DB::table('Assenza')->insert(array(
            'CFStudente' => 'ZCCNDR99S22F241A',
            'Data' => '03/04/2018',
            'Orario' => '000000000111111111111111',
            'Giustificato' => 'true'
        ));
        DB::table('Assenza')->insert(array(
            'CFStudente' => 'ZCCNDR99S22F241A',
            'Data' => '01/01/2018',
            'Orario' => '000000000111111111111111',
            'Giustificato' => 'true'
        ));
        DB::table('Assenza')->insert(array(
            'CFStudente' => 'ZCCNDR99S22F241A',
            'Data' => '27/03/2018',
            'Orario' => '111111111100000000000000',
            'Giustificato' => 'true'
        ));
        DB::table('Assenza')->insert(array(
            'CFStudente' => 'ZCCNDR99S22F241A',
            'Data' => '20/04/2018',
            'Orario' => '111111111111111111111111',
            'Giustificato' => 'false'
        ));
        DB::table('Assenza')->insert(array(
            'CFStudente' => 'ZCCNDR99S22F241A',
            'Data' => '15/04/2018',
            'Orario' => '000000000111111111111111',
            'Giustificato' => 'false'
        ));
        DB::table('Assenza')->insert(array(
            'CFStudente' => 'ZCCNDR99S22F241A',
            'Data' => '27/04/2018',
            'Orario' => '111111111111111111111111',
            'Giustificato' => 'false'
        ));
        DB::table('Assenza')->insert(array(
            'CFStudente' => 'ZCCNDR99S22F241A',
            'Data' => '18/05/2018',
            'Orario' => '000000000111111111111111',
            'Giustificato' => 'false'
        ));
    }

}
