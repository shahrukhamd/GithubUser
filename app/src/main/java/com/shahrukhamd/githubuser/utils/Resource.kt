package com.shahrukhamd.githubuser.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.Success, data, null)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.Loading, data, null)
        }

        fun <T> error(data: T, msg: String?): Resource<T> {
            return Resource(Status.Error, data, msg)
        }
    }
}
