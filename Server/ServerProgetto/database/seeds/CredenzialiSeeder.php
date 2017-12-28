<?php

use Illuminate\Database\Seeder;

class CredenzialiSeeder extends Seeder {

    public function run() {
        DB::table('Credenziali')->delete();
        DB::table('Credenziali')->insert(array(
            'Username' => 'andrea',
            'Password' => Hash::make('andreaz001'),
        ));
        DB::table('Credenziali')->insert(array(
            'Username' => 'csal',
            'Password' => Hash::make('csal001'),
        ));
    }

}