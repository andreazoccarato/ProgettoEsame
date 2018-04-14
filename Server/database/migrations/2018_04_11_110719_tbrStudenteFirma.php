<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Migrations\Migration;

class TbrStudenteFirma extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('tbrStudenteFirma', function($table) {
            $table->increments('ID')->primary();
            $table->text('CFStudente');
            $table->text('CodiceFirma');
            $table->boolean('Assenza')->default(true);
            $table->boolean('Giustificato')->default(true);
            $table->foreign('CFStudente')->references('CodiceFiscale')->on('Studente');
            $table->foreign('CodiceFirma')->references('CodiceFirma')->on('Firma');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('tbrStudenteFirma');
    }

}
