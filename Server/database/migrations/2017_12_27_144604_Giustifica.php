<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class Giustifica extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('Giustifica', function($table) {
            $table->increments('IdGiustifica');
            $table->date('Data');
            $table->text('Descrizione');
            $table->text('TipologiaGiustifica');
            $table->integer('IdAssenza');
            $table->text('CFDocente');
            $table->foreign('CFDocente')->references('CodiceFiscale')->on('Docente');
            $table->foreign('IdAssenza')->references('IdAssenza')->on('Assenza');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('Giustifica');
    }
}
