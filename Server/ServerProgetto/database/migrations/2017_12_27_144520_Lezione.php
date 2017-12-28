<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class Lezione extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('Lezione', function($table) {
            $table->increments('ID');
            $table->date('Data');
            $table->integer('Ora');
            $table->text('Materia');
            $table->text('Descrizione');
            $table->text('CFDocente');
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
        Schema::drop('Lezione');
    }
}
