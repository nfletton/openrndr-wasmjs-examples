private val FUNCTION_NAME_JS_REGEX: Regex =
    Regex("""function\s*\(\)\s*\{\s*(\w+)\(\);\s+return""")

internal data class SketchData(
    val navTitle: String,
    val title: String,
    val function: () -> Unit,
    val pkg: Package,
    val docLink: String,
    val status: SketchStatus = SketchStatus.BROKEN,
    val comment: String = "",
) {
    private val functionString: String = function.toString()

    val funcName: String by lazy {
        if (functionString.startsWith("function ()")) {
            // JavaScript
            FUNCTION_NAME_JS_REGEX.find(functionString)?.groupValues?.getOrNull(1)
                ?: "unknown"
        } else {
            // WASM
            val raw = functionString.substringBefore('$')
            raw.ifEmpty { "unknown" }
        }
    }

    val funcId: String by lazy {
        "$pkg-$funcName".lowercase()
    }

    val codeLink: String by lazy {
        "$EXAMPLES_ROOT/${pkg.toString().lowercase()}/$funcName.kt"
    }
}