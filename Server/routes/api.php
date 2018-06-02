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
Route::post('addVoto', 'Api\Auth\gestioneVoti@addVoto');
Route::post('getEventi', 'Api\Auth\gestioneEventi@getEventiByCred');
Route::post('getEventiByClass', 'Api\Auth\gestioneEventi@getEventiByClass');
Route::post('aggiungiEvento', 'Api\Auth\gestioneEventi@aggiungiEvento');
Route::post('getAssenze', 'Api\Auth\AssenzeController@getAssenze');
Route::post('getAssenzeNonGiustificate', 'Api\Auth\AssenzeController@getAssenzeNonGiustificate');
Route::post('giustificaAssenza', 'Api\Auth\AssenzeController@giustificaAssenza');
Route::post('setPresenzaAssenza', 'Api\Auth\AssenzeController@setPresenzaAssenza');
Route::post('getProfilo', 'Api\Auth\ProfiloController@getProfilo');
Route::post('setProfilo', 'Api\Auth\ProfiloController@updateProfilo');
Route::post('getClassi', 'Api\Auth\ClassiController@getClassi');
Route::post('getStudentiByClasse', 'Api\Auth\ClassiController@getStudentiByClasse');
Route::post('firma', 'Api\Auth\FirmaController@firma');
Route::post('cancellaFirma', 'Api\Auth\FirmaController@cancellaFirma');

Route::middleware('auth:api')->group(function () {
    Route::get('posts', 'Api\PostController@index');
});


