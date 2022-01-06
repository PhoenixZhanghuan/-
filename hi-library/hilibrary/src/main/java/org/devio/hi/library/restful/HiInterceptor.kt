package org.devio.hi.library.restful

interface HiInterceptor {

    val isRequestPeriod: Boolean get() = false

    fun intercept(chain: Chain): Boolean

    interface Chain {
        fun request(): HiRequest
        fun response(): HiResponse<*>?
        val isRequestPeriod: Boolean
    }
}