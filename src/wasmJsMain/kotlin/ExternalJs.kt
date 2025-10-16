@file:JsModule("./js/main.js")
@file:OptIn(ExperimentalWasmJsInterop::class)

package wasmJs

external fun initUI(sketchJson: String): Unit