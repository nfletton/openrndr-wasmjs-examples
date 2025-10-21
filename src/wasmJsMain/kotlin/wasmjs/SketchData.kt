package wasmjs

import kotlinx.serialization.Serializable
import wasmjs.openrndr.DemoBasicDraw
import wasmjs.openrndr.DemoColor
import wasmjs.openrndr.DemoColorModels
import wasmjs.orxcomposition.DemoCompositionDrawer01
import wasmjs.orxcomposition.DemoCompositionDrawer02
import wasmjs.orxcomposition.DemoCompositionDrawer03
import wasmjs.orxcomposition.DemoCompositionDrawer04
import wasmjs.orxcomposition.DemoCompositionDrawer05
import wasmjs.orxcompositor.DemoAside01
import wasmjs.orxcompositor.DemoCompositor01
import wasmjs.orxcompositor.DemoCompositor02
import wasmjs.orxeasing.DemoEasings01
import wasmjs.orxmath.DemoLeastSquares01
import wasmjs.orxmath.DemoLeastSquares02
import wasmjs.orxmath.DemoLinearRange02
import wasmjs.orxmath.DemoLinearRange03
import wasmjs.orxmath.RbfInterpolation01
import wasmjs.orxmath.RbfInterpolation02

internal enum class SketchStatus {
    HIDDEN,
    COMPLETE,
    PARTIAL,
    BROKEN,
}

@Serializable
internal enum class Package(val displayName: String) {
    OPENRNDR("OPENRNDR"),
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
    val status: SketchStatus = SketchStatus.HIDDEN,
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
    /* COMPOSITION */
    SketchData(
        navTitle = "Basic Composition",
        title = "Demonstrates basic composition features",
        function = ::DemoCompositionDrawer01,
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer01",
        status = SketchStatus.HIDDEN,
        comment = "ISSUE: No SVG file save",
    ),
    SketchData(
        navTitle = "ClipMode 1",
        title = "Using ClipMode.REVERSE_DIFFERENCE to clip shapes",
        function = ::DemoCompositionDrawer02,
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer02",
        status = SketchStatus.HIDDEN,
        comment = "",
    ),
    SketchData(
        navTitle = "ClipMode 2",
        title = "Using ClipMode.REVERSE_DIFFERENCE to clip 3 shapes",
        function = ::DemoCompositionDrawer03,
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer03",
        status = SketchStatus.HIDDEN,
        comment = "",
    ),
    SketchData(
        navTitle = "Mouse Interaction",
        title = "Demonstrates how to add content and clear an existing Composition",
        function = ::DemoCompositionDrawer04,
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer04",
        status = SketchStatus.PARTIAL,
        comment = "BUG: Mouse position again. Background can go white after right click and drag.",
    ),
    SketchData(
        navTitle = "Group",
        title = "Create a composition with a group and add XML attributes",
        function = ::DemoCompositionDrawer05,
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer05",
        status = SketchStatus.HIDDEN,
        comment = "BUG: Mouse position agan. Background can go white after right click and drag.",
    ),
    /* COMPOSITOR */
    SketchData(
        navTitle = "Layer Reuse",
        title = "Demonstrates how to reuse a layer in the Compositor by using aside { }",
        function = ::DemoAside01,
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#demoaside01",
        status = SketchStatus.COMPLETE,
        comment = "ISSUES: Comparable to JVM version on ipad. Grainier and darker on 1080p screen.",
    ),
    SketchData(
        navTitle = "Layered Blurs",
        title = "Demonstrates using 3 layers of moving items with a different amount of blur",
        function = ::DemoCompositor01,
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#democompositor01",
        status = SketchStatus.COMPLETE,
        comment = "",
    ),
    SketchData(
        navTitle = "BufferMultisample",
        title = "Demonstration of using BufferMultisample on a per layer basis.",
        function = ::DemoCompositor02,
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#democompositor02",
        status = SketchStatus.HIDDEN,
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
        status = SketchStatus.HIDDEN,
        comment = "BUG: Blank screen",
    ),
    SketchData(
        navTitle = "RBF 2",
        title = "",
        function = ::RbfInterpolation02,
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#rbfrbfinterpolation02",
        status = SketchStatus.HIDDEN,
        comment = "BUG: Mouse position off. Probably due to offset canvas. ",
    ),
)