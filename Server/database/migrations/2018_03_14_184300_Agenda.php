<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Migrations\Migration;

class Agenda extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('Agenda', function($table) {
            $table->increments('ID');
            $table->date('Data');
            $table->text('Materia');
            $table->text('Descrizione');
            $table->text('CFDocente');
            $table->integer('IdClasse');
            $table->foreign('CFDocente')->references('CodiceFiscale')->on('Docente');
            $table->foreign('IdClasse')->references('IdClasse')->on('Classe');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('Agenda');
    }

}
