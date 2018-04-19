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
        DB::table('Credenziali')->insert(array(
            'Username' => 'leo',
            'Password' => 'leo001',
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'eldiablo',
            'Password' => 'eldiablo280',
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'luca',
            'Password' => 'luca001',
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'camillo',
            'Password' => 'murloc',
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'chri',
            'Password' => 'chri',
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'vanzan',
            'Password' => 'vanzan001',
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'marco',
            'Password' => 'marco001',
        ));
    }

}
