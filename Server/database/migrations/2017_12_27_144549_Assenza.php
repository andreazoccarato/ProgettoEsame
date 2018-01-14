<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class Assenza extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('Assenza', function($table) {
            $table->increments('IdAssenza');
            $table->date('Data');
            $table->integer('Ora');
            $table->boolean('Giustificato')->default('false');
            $table->text('CFStudente');
            $table->foreign('CFStudente')->references('CodiceFiscale')->on('Studente');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('Assenza');
    }
}
