<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Migrations\Migration;

class TblOrario extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('tblOrario', function($table) {
            $table->increments('ID');
            $table->text('Orario');
            $table->integer('IdAssenza');
            $table->foreign('IdAssenza')->references('ID')->on('Assenza');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('tblOrario');
    }

}
