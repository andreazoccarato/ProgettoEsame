<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Migrations\Migration;

class Firma extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('Firma', function($table) {
            $table->increments('CodiceFirma')->primary();
            $table->text('Data');
            $table->integer('IdOrario');
            $table->text('CFDocente');
            $table->integer('IdLezione');
            $table->foreign('IdOrario')->references('ID')->on('tblOrario');
            $table->foreign('CFDocente')->references('CodiceFiscale')->on('Docente');
            $table->foreign('IdLezione')->references('ID')->on('Lezione');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('Firma');
    }

}
