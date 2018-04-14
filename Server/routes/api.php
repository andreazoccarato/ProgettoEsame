<?php

/*
  |--------------------------------------------------------------------------
  | API Routes
  |--------------------------------------------------------------------------
  |
  | Here is where you can register API routes for your application. These
  | routes are loaded by the RouteServiceProvider within a group which
  | is assigned the "api" middleware group. Enjoy building your API!
  |
 */

Route::post('login', 'Api\Auth\LoginController@login')->name('login');
Route::post('qrCode', 'Api\Auth\qrCodeController@getQrCode');
Route::post('setPresenza', 'Api\Auth\qrCodeController@setPresenza');
Route::post('getVoti', 'Api\Auth\gestioneVoti@getVotiByCred');
Route::post('getEventi', 'Api\Auth\gestioneEventi@getEventiByCred');
Route::post('getEventiByClass', 'Api\Auth\gestioneEventi@getEventiByClass');
Route::post('getAssenze', 'Api\Auth\AssenzeController@getAssenze');
Route::post('getProfilo', 'Api\Auth\ProfiloController@getProfilo');
Route::post('setProfilo', 'Api\Auth\ProfiloController@updateProfilo');
Route::post('getClassi', 'Api\Auth\ClassiController@getClassi');

Route::middleware('auth:api')->group(function () {
    Route::get('posts', 'Api\PostController@index');
});


