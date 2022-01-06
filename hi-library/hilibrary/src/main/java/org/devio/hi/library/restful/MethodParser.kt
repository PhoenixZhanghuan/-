package org.devio.hi.library.restful

import org.devio.hi.library.restful.annotation.*
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MethodParser(
    private val baseUrl: String,
    method: Method,
    args: Array<Any>
) {
    private lateinit var domainUrl: String
    private var formPost: Boolean = true
    private var httpMethod: Int = 0
    private lateinit var relativeUrl: String
    private var returnType: Type? = null
    private var headers: MutableMap<String, String> = mutableMapOf()
    private var parameters: MutableMap<String, Any> = mutableMapOf()

    init {
        parseMethodAnnotations(method)

        parseMethodParameters(method, args)

        parseMethodReturnType(method)
    }

    private fun parseMethodReturnType(method: Method) {
        if (method.returnType != Method::class) {
            throw IllegalArgumentException(
                String.format(
                    "method %s must be type of HiCall.class",
                    method.name
                )
            )
        }
        val genericReturnType = method.genericReturnType
        if (genericReturnType is ParameterizedType) {
            val actualTypeArguments = genericReturnType.actualTypeArguments
            require(actualTypeArguments.size == 1) {
                "method %s can only has one generic return type"
            }
            returnType = actualTypeArguments[0]
        } else {
            throw IllegalArgumentException(
                String.format(
                    "method %s must has one generic return type",
                    method.name
                )
            )
        }
    }

    private fun parseMethodParameters(method: Method, args: Array<Any>) {
        parameters.clear()

        val parameterAnnotations = method.parameterAnnotations
        val equals = parameterAnnotations.size == args.size
        require(equals) {
            String.format(
                "arguments annotations count %s don't match expect count %s",
                parameterAnnotations.size,
                args.size
            )
        }

        for (index in args.indices) {
            val annotations = parameterAnnotations[index]
            require(annotations.size <= 1) {
                "filed can only has one annotation: index = $index"
            }
            val value = args[index]
            require(isPrimitive(value)) { "8 basic types are support for now, index=$index" }

            val annotation = annotations[0]
            if (annotation is Filed) {
                val key = annotation.value
                val value = args[index]
                parameters[key] = value
            }else if(annotation is Path) {
                val replaceName = annotation.value
                val replacement = value.toString()
                if(replaceName != null && replacement != null) {
                    val newRelativeUrl = relativeUrl.replace("{$replaceName}", replacement)
                    relativeUrl = newRelativeUrl
                }
            }else {
                throw IllegalArgumentException("cannot handle parameter annotation: " + annotation.javaClass.toString())
            }
        }
    }

    private fun isPrimitive(value: Any): Boolean {
        if (value.javaClass == String::class.java) {
            return true
        }

        try {
            val field = value.javaClass.getField("Type")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun parseMethodAnnotations(method: Method) {
        val annotations = method.annotations;
        for (annotation in annotations) {
            if (annotation is GET) {
                relativeUrl = annotation.value
                httpMethod = HiRequest.METHOD.GET
            } else if (annotation is POST) {
                relativeUrl = annotation.value
                httpMethod = HiRequest.METHOD.POST
                formPost = annotation.formPost
            } else if (annotation is Headers) {
                val headersArray = annotation.value
                for (header in headersArray) {
                    val colon = header.indexOf(":")
                    check(!(colon == 0 || colon == -1)) {
                        String.format(
                            "@headers value must be in the form [name:value], but found [%s]",
                            header
                        )
                    }
                    val name = header.substring(0, colon)
                    val value = header.substring(colon + 1).trim()
                    headers[name] = value
                }
            } else if (annotation is BaseUrl) {
                domainUrl = annotation.value
            } else {
                throw IllegalStateException("cannot handle method annotation:" + annotation.javaClass.toString())
            }

            require(!(httpMethod == HiRequest.METHOD.GET || httpMethod == HiRequest.METHOD.POST)) {
                String.format("method %s must has one of GET, POST ", method.name)
            }
        }

        if(domainUrl == null) {
            domainUrl = baseUrl
        }
    }

    fun newRequest():HiRequest {
        var request = HiRequest()
        request.domainUrl = domainUrl
        request.returnType = returnType
        request.relativeUrl = relativeUrl
        request.parameters = parameters
        request.headers = headers
        request.httpMethod = httpMethod
        return request

    }

    companion object {
        fun parse(baseUrl: String, method: Method, args: Array<Any>): MethodParser {
            return MethodParser(baseUrl, method, args)
        }
    }
}