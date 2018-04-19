<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Migrations\Migration;

class Assenza extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('Assenza', function($table) {
            $table->increments('ID');
            $table->text('CFStudente');
            $table->text('Data');
            $table->boolean('Giustificato')->default(false);
            $table->foreign('CFStudente')->references('CodiceFiscale')->on('Studente');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('Assenza');
    }

}
