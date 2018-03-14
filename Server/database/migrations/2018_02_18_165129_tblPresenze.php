<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class TblPresenze extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('tblPresenze', function($table) {
            $table->increments('ID');
            $table->text('Data');
            $table->integer('IdClasse')->unique();
            $table->foreign('IdClasse')->references('IdClasse')->on('Classe');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('tblPresenze');
    }
}
