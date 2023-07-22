package routes.users

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.li
import kotlinx.html.ul

fun Route.handlers() {
    get {
        call.respondHtml {
            body {
                h1 {
                    ul {
                        li { +"hi" }
                        li { +"GET" }
                    }
                }
            }
        }
    }

    post {
        call.respondHtml {
            body {
                h1 {
                    ul {
                        li { +"hi" }
                        li { +"post" }
                    }
                }
            }
        }
    }
}