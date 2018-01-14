<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class TbrDocenteClasse extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('tbrDocenteClasse', function($table) {
            $table->increments('ID');
            $table->text('Sezione');
            $table->integer('CodiceFiscale');
            $table->foreign('Sezione')->references('Sezione')->on('Classe');
            $table->foreign('CodiceFiscale')->references('CodiceFiscale')->on('Docente');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('tbrDocenteClasse');
    }
}
