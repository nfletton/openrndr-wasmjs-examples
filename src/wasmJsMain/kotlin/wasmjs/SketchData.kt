package wasmjs

import kotlinx.serialization.Serializable
import wasmjs.openrndr.DemoBasicDraw
import wasmjs.openrndr.DemoColor
import wasmjs.openrndr.DemoColorModels
import wasmjs.orxcamera.DemoCamera2D01
import wasmjs.orxcamera.DemoCamera2D02
import wasmjs.orxcomposition.DemoCompositionDrawer01
import wasmjs.orxcomposition.DemoCompositionDrawer02
import wasmjs.orxcomposition.DemoCompositionDrawer03
import wasmjs.orxcomposition.DemoCompositionDrawer04
import wasmjs.orxcompositor.DemoAside01
import wasmjs.orxcompositor.DemoCompositor01
import wasmjs.orxcompositor.DemoCompositor02
import wasmjs.orxeasing.DemoEasings01
import wasmjs.orxmath.*

internal enum class SketchStatus {
    HIDDEN,
    GOOD,
    PARTIAL,
    BROKEN,
}

@Serializable
internal enum class Package(val displayName: String) {
    OPENRNDR("OPENRNDR"),
    ORXCAMERA("ORX Camera"),
    ORXCOMPOSITION("ORX Composition"),
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
    val status: SketchStatus = SketchStatus.BROKEN,
    val comment: String = "",
) {
    val funcName: String = function.toString().substringBefore("$")

    val funcId: String = "$pkg-${funcName}".lowercase()

    val codeLink: String = "$EXAMPLES_ROOT/wasmjs/${pkg.toString().lowercase()}/${funcName}.kt"
}

internal val sketches = listOf(
    /* BASICS */
    SketchData(
        navTitle = "Drawing Basics",
        title = "Drawing circles, rectangles and lines",
        function = ::DemoBasicDraw,
        pkg = Package.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/circlesRectanglesLines.html",
        status = SketchStatus.GOOD,
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
    /* CAMERA */
    SketchData(
        navTitle = "2D 1",
        title = "Demonstrates 2D mouse panning and zooming",
        function = ::DemoCamera2D01,
        pkg = Package.ORXCAMERA,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-camera#democamera2d01",
        status = SketchStatus.BROKEN,
    ),
    SketchData(
        navTitle = "2D 2",
        title = "Demonstrates 2D mouse panning and zooming with a custom camera",
        function = ::DemoCamera2D02,
        pkg = Package.ORXCAMERA,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-camera#democamera2d02",
        status = SketchStatus.BROKEN,
    ),
    /* COMPOSITION */
    SketchData(
        navTitle = "Basic Composition",
        title = "Demonstrates basic composition features",
        function = ::DemoCompositionDrawer01,
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer01",
        status = SketchStatus.PARTIAL,
        comment = "ISSUE: No SVG file save. orx-svg has Java dependencies",
    ),
    SketchData(
        navTitle = "ClipMode 1",
        title = "Using ClipMode.REVERSE_DIFFERENCE to clip shapes",
        function = ::DemoCompositionDrawer02,
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer02",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "ClipMode 2",
        title = "Using ClipMode.REVERSE_DIFFERENCE to clip 3 shapes",
        function = ::DemoCompositionDrawer03,
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer03",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "Mouse Interaction",
        title = "Demonstrates how to add content and clear an existing Composition",
        function = ::DemoCompositionDrawer04,
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer04",
        status = SketchStatus.GOOD,
        comment = "Click and drag mouse button to add circles. Right-click to clear composition.",
    ),
    /* COMPOSITOR */
    SketchData(
        navTitle = "Layer Reuse",
        title = "Demonstrates how to reuse a layer in the Compositor by using aside { }",
        function = ::DemoAside01,
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#demoaside01",
        status = SketchStatus.PARTIAL,
        comment = "ISSUES: Comparable to JVM version on ipad. Grainier and darker on 1080p screen.",
    ),
    SketchData(
        navTitle = "Layered Blurs",
        title = "Demonstrates using 3 layers of moving items with a different amount of blur",
        function = ::DemoCompositor01,
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#democompositor01",
        status = SketchStatus.GOOD,
        comment = "",
    ),
    SketchData(
        navTitle = "BufferMultisample",
        title = "Demonstration of using BufferMultisample on a per layer basis.",
        function = ::DemoCompositor02,
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#democompositor02",
        status = SketchStatus.BROKEN,
        comment = "Bug: Nothing drawn",
    ),
    /* EASING */
    SketchData(
        navTitle = "Easing Types",
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
        status = SketchStatus.GOOD,
        comment = "",
    ),
    SketchData(
        navTitle = "Linear Range 3",
        title = "ORX Math Linear Range",
        function = ::DemoLinearRange03,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#linearrangedemolinearrange03",
        status = SketchStatus.GOOD,
        comment = "",
    ),
    SketchData(
        navTitle = "Least Squares 1",
        title = "Least squares method to fit a regression line to noisy points",
        function = ::DemoLeastSquares01,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#matrixdemoleastsquares01",
        status = SketchStatus.GOOD,
        comment = "",
    ),
    SketchData(
        navTitle = "Least Squares 2",
        title = "Least squares method to fit a cubic bezier to noisy points",
        function = ::DemoLeastSquares02,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#matrixdemoleastsquares02",
        status = SketchStatus.GOOD,
        comment = "",
    ),
    SketchData(
        navTitle = "RBF 1",
        title = "",
        function = ::RbfInterpolation01,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#rbfrbfinterpolation01",
        status = SketchStatus.BROKEN,
        comment = "BUG: Blank screen",
    ),
    SketchData(
        navTitle = "RBF 2",
        title = "",
        function = ::RbfInterpolation02,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#rbfrbfinterpolation02",
        status = SketchStatus.BROKEN,
        comment = "BUG: Mouse position off. Probably due to offset canvas. ",
    ),
)