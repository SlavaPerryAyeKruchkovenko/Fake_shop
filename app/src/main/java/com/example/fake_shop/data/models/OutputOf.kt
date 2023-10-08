package com.example.fake_shop.data.models

sealed class OutputOf<out T> {
    data class Success<out R>(val value: R) : OutputOf<R>()
    data class Failure<out R>(
        val message: String?
    ) : OutputOf<R>()

    sealed class Error<out R>(val message: String) : OutputOf<R>(){
        class NotFoundError<out R> : Error<R>("Not found")
        class InternetError<out R> : Error<R>("Internet connection error")
    }
    class Loader<out R> : OutputOf<R>()
}
