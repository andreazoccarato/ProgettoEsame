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

Route::middleware('auth:api')->group(function () {
    Route::get('posts', 'Api\PostController@index');
    
});


