<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class Scuola extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('Scuola', function($table) {
            $table->increments('CodiceScuola');
            $table->text('Nome')->unique();
            $table->text('Indirizzo')->unique();
            $table->text('Città')->unique();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
         Schema::drop('Scuola');
    }

}
