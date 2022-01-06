package org.devio.hi.library.restful

import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

open class HiRestFul constructor(val baseUrl: String, private var callFactory: HiCall.Factory){
    private var interceptors: MutableList<HiInterceptor> = mutableListOf()
    private var methodService: ConcurrentHashMap<Method, MethodParser> = ConcurrentHashMap()
    private var scheduler:Scheduler
    fun addInterceptor(interceptor: HiInterceptor) {
        interceptors.add(interceptor)
    }

    init {
        scheduler = Scheduler(callFactory, interceptors)

    }

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
            var methodParser = methodService.get(method)
            if(methodParser == null) {
                methodParser = MethodParser.parse(baseUrl, method, args)
                methodService.put(method, methodParser)
            }
            val request = methodParser.newRequest()
            scheduler.newCall(request)
        } as T
    }
}