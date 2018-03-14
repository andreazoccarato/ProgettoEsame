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
            $table->boolean('00:00')->default(true);
            $table->boolean('01:00')->default(true);
            $table->boolean('02:00')->default(true);
            $table->boolean('03:00')->default(true);
            $table->boolean('04:00')->default(true);
            $table->boolean('05:00')->default(true);
            $table->boolean('06:00')->default(true);
            $table->boolean('07:00')->default(true);
            $table->boolean('08:00')->default(true);
            $table->boolean('09:00')->default(true);
            $table->boolean('10:00')->default(true);
            $table->boolean('11:00')->default(true);
            $table->boolean('12:00')->default(true);
            $table->boolean('13:00')->default(true);
            $table->boolean('14:00')->default(true);
            $table->boolean('15:00')->default(true);
            $table->boolean('16:00')->default(true);
            $table->boolean('17:00')->default(true);
            $table->boolean('18:00')->default(true);
            $table->boolean('19:00')->default(true);
            $table->boolean('20:00')->default(true);
            $table->boolean('21:00')->default(true);
            $table->boolean('22:00')->default(true);
            $table->boolean('23:00')->default(true);
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
