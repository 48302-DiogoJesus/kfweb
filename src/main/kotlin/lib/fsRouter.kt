package lib

import io.ktor.server.routing.*
import java.io.File
import java.lang.reflect.Method

typealias Handlers = Method

const val routesFolderName = "routes"
const val routesFolderPath = "src/main/kotlin/${routesFolderName}"
const val endpointFileName = "endpoint.kt"
const val endpointClassName = "EndpointKt"
const val endpointHandlersFunName = "handlers"
const val LOG_PREFIX = "[FS-ROUTER]"

fun Routing.setup() {
    log("Starting Scan ...")

    val startTime = System.currentTimeMillis()

    val routesFolder = File(routesFolderPath)
    val ePaths = getEndpointPaths(routesFolder)
    val endpointsHandlers = ePaths.getHandlersFromEndpointFiles()

    log("${endpointsHandlers.entries.size} endpoints found")

    for ((endpoint, handlers) in endpointsHandlers) {
        val endpointName = endpoint.transformEndpointName()
        log("Setting up handlers for: $endpointName")

        route(endpointName) {
            handlers.invoke(null, this)
        }
    }

    val endTime = System.currentTimeMillis() - startTime

    log("Handlers created in ${endTime}ms ...")
}

// Internal Functions

fun log(msg: String) {
    println("$LOG_PREFIX $msg")
}

fun getEndpointPaths(root: File): List<String> {
    val handlersFiles = mutableListOf<String>()

    if (root.isFile && root.name == endpointFileName) {
        handlersFiles.add(root.path.replace("\\", "/").replace("/", ".").replace("src.main.kotlin.", ""))
    } else {
        root.listFiles()?.forEach { file ->
            handlersFiles.addAll(getEndpointPaths(file))
        }
    }

    return handlersFiles
}

fun List<String>.getHandlersFromEndpointFiles(): Map<String, Handlers> {
    val endpointsFiles = this
    val endpointHandlers = mutableMapOf<String, Handlers>()

    endpointFiles@ for (endpointPath in endpointsFiles) {
        val endpointClass = ClassLoader.getSystemClassLoader()
            .loadClass(endpointPath.removeSuffix(endpointFileName) + endpointClassName)

        val handler = endpointClass.methods.find { it.name == endpointHandlersFunName }
            ?: throw IllegalStateException("At $endpointPath: $endpointFileName files should contain a function called Route.$endpointHandlersFunName")

        endpointHandlers[
            endpointPath
                .removePrefix(routesFolderName)
                .removeSuffix(".${endpointFileName}")
                .replace(".", "/")
        ] = handler
    }
    return endpointHandlers
}

fun String.transformEndpointName(): String {
    return this.replace(Regex("/_([^/]+)"), "/{$1}")
}