<?php

use Illuminate\Database\Seeder;

class ScuolaSeeder extends Seeder {

    public function run() {
        DB::table('Scuola')->delete();
        DB::table('Scuola')->insert(array(
            'CodiceScuola' => '123',
            'Nome' => 'Carlo Zuccante',
            'Indirizzo' => ' Via Raffaele Cattaneo 3',
            'Città' => 'Mestre'
        ));
    }

}