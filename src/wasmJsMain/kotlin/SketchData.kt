package wasmJs

import kotlinx.serialization.Serializable
import wasmjs.openrndr.basicDrawerDemo
import wasmjs.openrndr.colorDemo

internal enum class SketchStatus {
    HIDDEN,
    COMPLETE,
    PARTIAL,
}

@Serializable
internal enum class NavGroup {
    OPENRNDR,
    ORX,
    OTHER,
}

private const val EXAMPLES_ROOT =
    "https://github.com/nfletton/openrndr-wasmjs-examples/blob/master/src/wasmJsMain/kotlin"
private const val GUIDE_ROOT = "https://guide.openrndr.org/"

internal data class SketchData(
    val navTitle: String,
    val title: String,
    val function: () -> Unit,
    val group: NavGroup,
    val docLink: String,
    val status: SketchStatus = SketchStatus.HIDDEN,
    val knownIssues: List<String> = emptyList(),
) {
    val funcName: String = function.toString().substringBefore("$")

    val funcId: String = "$group-${funcName}".lowercase()

    val codeLink: String = "$EXAMPLES_ROOT/${group.toString().lowercase()}/${funcName}.kt";
}

internal val sketches = listOf(
    SketchData(
        navTitle = "Drawing Basics",
        title = "Drawing circles, rectangles and lines",
        function = ::basicDrawerDemo,
        group = NavGroup.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/circlesRectanglesLines.html",
        status = SketchStatus.COMPLETE,
    ),
    SketchData(
        navTitle = "Color Basics",
        title = "Core color functionality",
        function = ::colorDemo,
        group = NavGroup.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/color.html",
        status = SketchStatus.PARTIAL,
        knownIssues = listOf("Colors are not working correctly"),
    )
)