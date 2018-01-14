<?php

use Illuminate\Database\Seeder;

class AssenzaSeeder extends Seeder {

    public function run() {
        DB::table('Assenza')->delete();
        DB::table('Assenza')->insert(array(
            'Data' => '25/12/2017',
            'Ora' => '1',
            'Giustificato' => 'true',
            'CFStudente' => ' ZCCNDR99S22F241A'
        ));
    }

}
