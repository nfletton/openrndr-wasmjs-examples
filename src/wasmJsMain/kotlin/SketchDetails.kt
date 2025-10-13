package wasmJs

import wasmjs.openrndr.basicDrawerDemo
import wasmjs.openrndr.colorDemo

enum class SketchStatus {
    HIDDEN,
    COMPLETE,
    PARTIAL,
}

enum class NavGroup {
    OPENRNDR,
    ORX,
    OTHER,
}

const val EXAMPLES_ROOT =
    "https://github.com/nfletton/openrndr-wasmjs-examples/blob/master/src/wasmJsMain/kotlin"
const val GUIDE_ROOT = "https://guide.openrndr.org/"

data class SketchDetails(
    val navTitle: String,
    val title: String,
    val function: () -> Unit,
    val group: NavGroup,
    val docLink: String,
    val status: SketchStatus,
    val knownIssues: List<String> = listOf()
) {
    val codeLink: String
        get() = "$EXAMPLES_ROOT/${group.toString().lowercase()}/${
            function.toString().substringBefore("$")
        }.kt"
}

val sketches = listOf(
    SketchDetails(
        "Drawing Basics",
        "Drawing circles, rectangles and lines",
        ::basicDrawerDemo,
        NavGroup.OPENRNDR,
        "${GUIDE_ROOT}drawing/circlesRectanglesLines.html",
        SketchStatus.COMPLETE,
    ),
    SketchDetails(
        "Color Basics",
        "Core color functionality",
        ::colorDemo,
        NavGroup.OPENRNDR,
        "${GUIDE_ROOT}drawing/color.html",
        SketchStatus.PARTIAL,
        listOf("Colors are not working correctly")
    )
)