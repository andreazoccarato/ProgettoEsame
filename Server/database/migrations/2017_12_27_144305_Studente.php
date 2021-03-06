<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class Studente extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('Studente', function($table) {
            $table->text('CodiceFiscale')->primary();
            $table->text('Nome');
            $table->text('Cognome');
            $table->date('DataNascita');
            $table->text('IdClasse');
            $table->integer('IdCredenziali');
            $table->integer('CodiceScuola');
            $table->foreign('IdClasse')->references('IdClasse')->on('Classe');
            $table->foreign('IdCredenziali')->references('ID')->on('Credenziali');
            $table->foreign('CodiceScuola')->references('CodiceScuola')->on('Scuola');
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
       Schema::drop('Studente');
    }
}
