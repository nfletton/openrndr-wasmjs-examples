package wasmjs

import wasmjs.openrndr.basicDrawerDemo
import wasmjs.openrndr.colorDemo
import web.url.URLSearchParams
import web.window.window
import kotlin.js.ExperimentalWasmJsInterop

class WasmJsDemo(var currentDemo:Int = 0) {

    var availableDemos: List<() -> Unit> = listOf(
        ::basicDrawerDemo,
        ::colorDemo,
    )

    fun run(i: Int = 0) {
        println("Option: $i")
        currentDemo = i
        availableDemos[i.mod(availableDemos.size)].invoke()
    }
}

@OptIn(ExperimentalWasmJsInterop::class)
fun main() {
    val id = URLSearchParams(window.location.search).get("id".toJsString()) ?: "0".toJsString()

    val demo = WasmJsDemo()
//    demo.run(0)
    demo.run(id.toString().toInt())
}