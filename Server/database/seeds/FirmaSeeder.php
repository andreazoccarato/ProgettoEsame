<?php

use Illuminate\Database\Seeder;

class FirmaSeeder extends Seeder {

    public function run() {
        DB::table('Firma')->delete();
        DB::table('Firma')->insert(array(
            'Data' => '21/12/2017',
            'CFDocente' => 'CRLSVG60S20L736G',
            'Orario' => '000000000000000000000000',
            'IdLezione' => 1
        ));
        DB::table('Firma')->insert(array(
            'Data' => '21/12/2017',
            'CFDocente' => 'DONBOR60S20L736G',
            'Orario' => '000000000000000000000000',
            'IdLezione' => 2
        ));
        DB::table('Firma')->insert(array(
            'Data' => '21/12/2017',
            'CFDocente' => 'CRLSVG60S20L736G',
            'Orario' => '000000000000000000000000',
            'IdLezione' => 3
        ));
    }

}