<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class TblQrCode extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('tblQrCode',function($table){
            $table->increments('IdQrCode');
            $table->date('Giorno');
            $table->integer('CodiceControllo');
            $table->integer('IdScuola');
            $table->integer('IdClasse');
            $table->text('CodiceFiscale');
            $table->foreign('IdScuola')->references('IdScuola')->on('Scuola');
            $table->foreign('IdClasse')->references('IdClasse')->on('Classe');
            $table->foreign('CodiceFiscale')->references('CodiceFiscale')->on('Docente');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('tblQrCode');
    }
}
