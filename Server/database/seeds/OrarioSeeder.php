<?php

use Illuminate\Database\Seeder;

class OrarioSeeder extends Seeder {

    public function run() {
        DB::table('tblOrario')->delete();
        DB::table('tblOrario')->insert(array(
            'Orario' => '000000000111111111111111',
            'IdAssenza' => '1'
        ));
        DB::table('tblOrario')->insert(array(
            'Orario' => '000000000111111111111111',
            'IdAssenza' => '2'
        ));
        DB::table('tblOrario')->insert(array(
            'Orario' => '000000000001111111111111',
            'IdAssenza' => '3'
        ));
        DB::table('tblOrario')->insert(array(
            'Orario' => '000000000111111111111111',
            'IdAssenza' => '4'
        ));
    }

}