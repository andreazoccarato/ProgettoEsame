<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class Classe extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('Classe', function($table) {
            $table->increments('IdClasse');
            $table->text('Sezione');
            $table->text('Indirizzo');
            $table->integer('CodiceScuola')->unique();
            $table->foreign('CodiceScuola')->references('CodiceScuola')->on('Scuola');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('Classe');
    }
}
