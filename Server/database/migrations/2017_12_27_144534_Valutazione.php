<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class Valutazione extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('Valutazione', function($table) {
            $table->increments('ID');
            $table->text('Voto');
            $table->date('Data');
            $table->integer('Ora');
            $table->text('Materia');
            $table->text('CFStudente');
            $table->text('CFDocente');
            $table->foreign('CFStudente')->references('CodiceFiscale')->on('Studente');
            $table->foreign('CFDocente')->references('CodiceFiscale')->on('Docente');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('Valutazione');
    }
}
