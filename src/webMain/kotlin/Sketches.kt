import openrndr.DemoBasicDraw
import openrndr.DemoColor
import openrndr.DemoColorModels
import orxcamera.DemoCamera2D01
import orxcamera.DemoCamera2D02
import orxcomposition.DemoCompositionDrawer01
import orxcomposition.DemoCompositionDrawer02
import orxcomposition.DemoCompositionDrawer03
import orxcomposition.DemoCompositionDrawer04
import orxcompositor.DemoAside01
import orxmath.DemoLeastSquares01
import orxmath.DemoLinearRange03
import orxmath.RbfInterpolation01
import orxcompositor.DemoCompositor01
import orxmath.RbfInterpolation02
import orxcompositor.DemoCompositor02
import orxmath.DemoLeastSquares02
import orxmath.DemoLinearRange02
import orxeasing.DemoEasings01

internal val sketches = listOf(
    /* BASICS */
    SketchData(
        navTitle = "Drawing Basics",
        title = "Drawing circles, rectangles and lines",
        function = ::DemoBasicDraw,
        funcName = "DemoBasicDraw",
        pkg = Package.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/circlesRectanglesLines.html",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "Color Basics",
        title = "Core color functionality",
        function = ::DemoColor,
        funcName = "DemoColor",
        pkg = Package.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/color.html#color-operations",
        status = SketchStatus.PARTIAL,
        comment = "BUG: Colors are not rendering correctly",
    ),
    SketchData(
        navTitle = "Color Models",
        title = "Alternative color models",
        function = ::DemoColorModels,
        funcName = "DemoColorModels",
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
        funcName = "DemoCamera2D01",
        pkg = Package.ORXCAMERA,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-camera#democamera2d01",
        status = SketchStatus.BROKEN,
    ),
    SketchData(
        navTitle = "2D 2",
        title = "Demonstrates 2D mouse panning and zooming with a custom camera",
        function = ::DemoCamera2D02,
        funcName = "DemoCamera2D02",
        pkg = Package.ORXCAMERA,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-camera#democamera2d02",
        status = SketchStatus.BROKEN,
    ),
    /* COMPOSITION */
    SketchData(
        navTitle = "Basic Composition",
        title = "Demonstrates basic composition features",
        function = ::DemoCompositionDrawer01,
        funcName = "DemoCompositionDrawer01",
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer01",
        status = SketchStatus.PARTIAL,
        comment = "ISSUE: No SVG file save. orx-svg has Java dependencies",
    ),
    SketchData(
        navTitle = "ClipMode 1",
        title = "Using ClipMode.REVERSE_DIFFERENCE to clip shapes",
        function = ::DemoCompositionDrawer02,
        funcName = "DemoCompositionDrawer02",
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer02",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "ClipMode 2",
        title = "Using ClipMode.REVERSE_DIFFERENCE to clip 3 shapes",
        function = ::DemoCompositionDrawer03,
        funcName = "DemoCompositionDrawer03",
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer03",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "Mouse Interaction",
        title = "Demonstrates how to add content and clear an existing Composition",
        function = ::DemoCompositionDrawer04,
        funcName = "DemoCompositionDrawer04",
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
        funcName = "DemoAside01",
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#demoaside01",
        status = SketchStatus.PARTIAL,
        comment = "ISSUES: Comparable to JVM version on ipad. Grainier and darker on 1080p screen.",
    ),
    SketchData(
        navTitle = "Layered Blurs",
        title = "Demonstrates using 3 layers of moving items with a different amount of blur",
        function = ::DemoCompositor01,
        funcName = "DemoCompositor01",
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#democompositor01",
        status = SketchStatus.GOOD,
        comment = "",
    ),
    SketchData(
        navTitle = "BufferMultisample",
        title = "Demonstration of using BufferMultisample on a per layer basis.",
        function = ::DemoCompositor02,
        funcName = "DemoCompositor02",
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
        funcName = "DemoEasings01",
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
        funcName = "DemoLinearRange02",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#linearrangedemolinearrange02",
        status = SketchStatus.GOOD,
        comment = "",
    ),
    SketchData(
        navTitle = "Linear Range 3",
        title = "ORX Math Linear Range",
        function = ::DemoLinearRange03,
        funcName = "DemoLinearRange03",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#linearrangedemolinearrange03",
        status = SketchStatus.GOOD,
        comment = "",
    ),
    SketchData(
        navTitle = "Least Squares 1",
        title = "Least squares method to fit a regression line to noisy points",
        function = ::DemoLeastSquares01,
        funcName = "DemoLeastSquares01",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#matrixdemoleastsquares01",
        status = SketchStatus.GOOD,
        comment = "",
    ),
    SketchData(
        navTitle = "Least Squares 2",
        title = "Least squares method to fit a cubic bezier to noisy points",
        function = ::DemoLeastSquares02,
        funcName = "DemoLeastSquares02",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#matrixdemoleastsquares02",
        status = SketchStatus.GOOD,
        comment = "",
    ),
    SketchData(
        navTitle = "RBF 1",
        title = "",
        function = ::RbfInterpolation01,
        funcName = "RbfInterpolation01",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#rbfrbfinterpolation01",
        status = SketchStatus.BROKEN,
        comment = "BUG: Blank screen",
    ),
    SketchData(
        navTitle = "RBF 2",
        title = "",
        function = ::RbfInterpolation02,
        funcName = "RbfInterpolation02",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#rbfrbfinterpolation02",
        status = SketchStatus.BROKEN,
        comment = "BUG: Mouse position off. Probably due to offset canvas. ",
    ),
)