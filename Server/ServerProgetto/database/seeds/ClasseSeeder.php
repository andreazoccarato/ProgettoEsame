<?php

use Illuminate\Database\Seeder;

class ClasseSeeder extends Seeder {

    public function run() {
        DB::table('Classe')->delete();
        DB::table('Classe')->insert(array(
            'Sezione' => '5ID',
            'Indirizzo' => 'Informatica',
            'CodiceScuola' => 123,
        ));
    }

}