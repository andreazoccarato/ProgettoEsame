<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder {

    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run() {
        Eloquent::unguard();
        $this->call(ScuolaSeeder::class);
        $this->call(ClasseSeeder::class);
        $this->call(StudenteSeeder::class);
        $this->call(DocenteSeeder::class);
        $this->call(CredenzialiSeeder::class);
        $this->call(LezioneSeeder::class);
        $this->call(GiustificaSeeder::class);
        $this->call(ValutazioneSeeder::class);
        $this->call(tbrDocenteClasseSeeder::class);
        $this->call(AgendaSeeder::class);
        $this->call(FirmaSeeder::class);
        $this->call(AssenzaSeeder::class);
       // $this->call(OrarioSeeder::class);
        $this->call(GiustificaSeeder::class);
    }

}
