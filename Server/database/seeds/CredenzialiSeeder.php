<?php

use Illuminate\Database\Seeder;

class CredenzialiSeeder extends Seeder {

    public function run() {
        DB::table('Credenziali')->delete();
        DB::table('Credenziali')->insert(array(
            'Username' => 'andrea',
            'Password' => 'andreaz001',
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'csal',
            'Password' => 'csal001',
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'borsato',
            'Password' => 'borsato001',
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'luppi',
            'Password' => 'luppi001',
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'doni',
            'Password' => 'doni001',
        ));
    }

}
