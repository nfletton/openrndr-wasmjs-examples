package wasmJs

import wasmjs.openrndr.basicDrawerDemo
import wasmjs.openrndr.colorDemo

/*
* Data for each demo sketch
* Each sketch will have data associated with it as follows:
* - name: the name of the sketch
* - description: a short description of the sketch
* - source: the function that creates the sketch
* - tags: a list of tags associated with the sketch
* - code link: a link to the source code for the sketch
* - documentation link: a link to the documentation for the sketch
* - status: the status of the sketch when compared to the JVM version
* - known issues: a list of known issues the sketch has when compared to the JVM version
*/

enum class SketchStatus {
    HIDDEN,
    COMPLETE,
    PARTIAL,
}

enum class DemoPackage {
    OPENRNDR,
    ORX,
    OTHER,
}

const val EXAMPLES_ROOT =
    "https://github.com/nfletton/openrndr-wasmjs-examples/blob/master/src/wasmJsMain/kotlin"
const val GUIDE_ROOT = "https://guide.openrndr.org/"

data class SketchDetails(
    val navEntry: String,
    val title: String,
    val source: () -> Unit,
    val pkg: DemoPackage,
    val docLink: String,
    val status: SketchStatus,
    val knownIssues: List<String> = listOf()
) {
    val codeLink: String
        get() = "$EXAMPLES_ROOT/${pkg.toString().lowercase()}/${
            source.toString().substringAfter("::")
        }.kt"
}

val sketches = listOf(
    SketchDetails(
        "Drawing Basics",
        "Drawing circles, rectangles and lines",
        ::basicDrawerDemo,
        DemoPackage.OPENRNDR,
        "drawing/circlesRectanglesLines.html",
        SketchStatus.COMPLETE,
    ),
    SketchDetails(
        "Color Basics",
        "Core color functionality",
        ::colorDemo,
        DemoPackage.OPENRNDR,
        "drawing/color.html",
        SketchStatus.PARTIAL,
        listOf("Colors are not working correctly")
    )
)