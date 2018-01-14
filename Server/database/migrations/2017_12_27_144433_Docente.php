<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class Docente extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('Docente', function($table) {
            $table->text('CodiceFiscale')->primary();
            $table->text('Nome');
            $table->text('Cognome');
            $table->date('DataNascita');
            $table->integer('IdCredenziali');
            $table->foreign('IdCredenziali')->references('ID')->on('Credenziali');
            $table->unique(array('Nome','Cognome'));  
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('Docente');
    }
}
