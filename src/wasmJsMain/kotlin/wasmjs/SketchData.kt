package wasmJs

import kotlinx.serialization.Serializable
import wasmjs.openrndr.DemoBasicDraw
import wasmjs.openrndr.DemoColor
import wasmjs.orxeasing.DemoEasings01

internal enum class SketchStatus {
    HIDDEN,
    COMPLETE,
    PARTIAL,
}

@Serializable
internal enum class Package(val displayName: String) {
    OPENRNDR("OPENRNDR"),
    ORXEASING("ORX Easing"),
}

private const val EXAMPLES_ROOT =
    "https://github.com/nfletton/openrndr-wasmjs-examples/blob/master/src/wasmJsMain/kotlin"
private const val GUIDE_ROOT = "https://guide.openrndr.org/"

internal data class SketchData(
    val navTitle: String,
    val title: String,
    val function: () -> Unit,
    val pkg: Package,
    val docLink: String,
    val status: SketchStatus = SketchStatus.HIDDEN,
    val comment: String = "",
) {
    val funcName: String = function.toString().substringBefore("$")

    val funcId: String = "$pkg-${funcName}".lowercase()

    val codeLink: String = "$EXAMPLES_ROOT/wasmjs/${pkg.toString().lowercase()}/${funcName}.kt";
}

internal val sketches = listOf(
    SketchData(
        navTitle = "Drawing Basics",
        title = "Drawing circles, rectangles and lines",
        function = ::DemoBasicDraw,
        pkg = Package.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/circlesRectanglesLines.html",
        status = SketchStatus.COMPLETE,
    ),
    SketchData(
        navTitle = "Color Basics",
        title = "Core color functionality",
        function = ::DemoColor,
        pkg = Package.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/color.html",
        status = SketchStatus.PARTIAL,
        comment = "Bug: Colors are not displaying correctly",
    ),
    SketchData(
        navTitle = "Easings",
        title = "ORX Easing",
        function = ::DemoEasings01,
        pkg = Package.ORXEASING,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-easing",
        status = SketchStatus.PARTIAL,
        comment = "Text labelling is not implemented",
    ),
)