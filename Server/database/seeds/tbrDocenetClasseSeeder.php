<?php

use Illuminate\Database\Seeder;

class tbrDocenteClasseSeeder extends Seeder {

    public function run() {
        DB::table('tbrDocenteClasse')->delete();
        DB::table('tbrDocenteClasse')->insert(array(
            'IdClasse' => 1,
            'CodiceFiscale' => 'CRLSVG60S20L736G',
        ));
    }

}