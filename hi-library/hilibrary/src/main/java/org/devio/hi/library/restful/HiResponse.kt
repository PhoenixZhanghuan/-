package org.devio.hi.library.restful

open class HiResponse<T> {

    companion object {
        val SUCCESS: Int = 0
    }

    var rawData: String? = null
    var code = 0
    var data: T? = null
    var errorData: Map<String, String>? = null
    var msg: String? = null
}