@file:JsModule("./js/main.js")
@file:OptIn(ExperimentalWasmJsInterop::class)

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsModule


actual external fun initUI(sketchJson: String, target: String)