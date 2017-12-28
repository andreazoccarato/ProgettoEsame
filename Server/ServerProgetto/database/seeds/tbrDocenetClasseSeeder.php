<?php

use Illuminate\Database\Seeder;

class tbrDocenteClasseSeeder extends Seeder {

    public function run() {
        DB::table('tbrDocenteClasse')->delete();
        DB::table('tbrDocenteClasse')->insert(array(
            'Sezione' => '5ID',
            'CodiceFiscale' => 'CRLSVG60S20L736G',
        ));
    }

}