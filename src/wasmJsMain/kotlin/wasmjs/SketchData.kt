package wasmJs

import kotlinx.serialization.Serializable
import rbf.RbfInterpolation01
import rbf.RbfInterpolation02
import wasmjs.openrndr.DemoBasicDraw
import wasmjs.openrndr.DemoColor
import wasmjs.openrndr.DemoColorModels
import wasmjs.orxcompositor.DemoAside01
import wasmjs.orxeasing.DemoEasings01
import wasmjs.orxmath.DemoLeastSquares01
import wasmjs.orxmath.DemoLeastSquares02
import wasmjs.orxmath.DemoLinearRange02
import wasmjs.orxmath.DemoLinearRange03

internal enum class SketchStatus {
    HIDDEN,
    COMPLETE,
    PARTIAL,
    BROKEN,
}

@Serializable
internal enum class Package(val displayName: String) {
    OPENRNDR("OPENRNDR"),
    ORXCOMPOSITOR("ORX Compositor"),
    ORXEASING("ORX Easing"),
    ORXMATH("ORX Math"),
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
    /* BASICS */
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
        docLink = "${GUIDE_ROOT}drawing/color.html#color-operations",
        status = SketchStatus.PARTIAL,
        comment = "BUG: Colors are not rendering correctly",
    ),
    SketchData(
        navTitle = "Color Models",
        title = "Alternative color models",
        function = ::DemoColorModels,
        pkg = Package.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/color.html#alternative-color-models",
        status = SketchStatus.PARTIAL,
        comment = "BUG: Colors are not rendering correctly",
    ),
    /* COMPOSITOR */
    SketchData(
        navTitle = "Compositor aside",
        title = "Demonstrates how to reuse a layer in the Compositor by using aside { }",
        function = ::DemoAside01,
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#demoaside01",
        status = SketchStatus.COMPLETE,
        comment = "ISSUES: Comparable to JVM version on ipad. Grainier and darker on 1080p screen.",
    ),
    /* EASING */
    SketchData(
        navTitle = "Easings",
        title = "ORX Easing",
        function = ::DemoEasings01,
        pkg = Package.ORXEASING,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-easing",
        status = SketchStatus.PARTIAL,
        comment = "Text labelling is not implemented",
    ),
    /* MATH */
    SketchData(
        navTitle = "Linear Range 2",
        title = "ORX Math Linear Range",
        function = ::DemoLinearRange02,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#linearrangedemolinearrange02",
        status = SketchStatus.COMPLETE,
        comment = "",
    ),
    SketchData(
        navTitle = "Linear Range 3",
        title = "ORX Math Linear Range",
        function = ::DemoLinearRange03,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#linearrangedemolinearrange03",
        status = SketchStatus.COMPLETE,
        comment = "",
    ),
    SketchData(
        navTitle = "Least Squares 1",
        title = "Least squares method to fit a regression line to noisy points",
        function = ::DemoLeastSquares01,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#matrixdemoleastsquares01",
        status = SketchStatus.COMPLETE,
        comment = "",
    ),
    SketchData(
        navTitle = "Least Squares 2",
        title = "Least squares method to fit a cubic bezier to noisy points",
        function = ::DemoLeastSquares02,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#matrixdemoleastsquares02",
        status = SketchStatus.COMPLETE,
        comment = "",
    ),
    SketchData(
        navTitle = "RBF 1",
        title = "",
        function = ::RbfInterpolation01,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#rbfrbfinterpolation01",
        status = SketchStatus.COMPLETE,
        comment = "",
    ),
    SketchData(
        navTitle = "RBF 2",
        title = "",
        function = ::RbfInterpolation02,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#rbfrbfinterpolation02",
        status = SketchStatus.COMPLETE,
        comment = "",
    ),
)