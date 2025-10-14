package wasmJs

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import wasmjs.openrndr.basicDrawerDemo
import wasmjs.openrndr.colorDemo
import web.console.console

private enum class SketchStatus {
    HIDDEN,
    COMPLETE,
    PARTIAL,
}

@Serializable
private enum class NavGroup {
    OPENRNDR,
    ORX,
    OTHER,
}

private const val EXAMPLES_ROOT =
    "https://github.com/nfletton/openrndr-wasmjs-examples/blob/master/src/wasmJsMain/kotlin"
private const val GUIDE_ROOT = "https://guide.openrndr.org/"

@Serializable
private data class SketchDto(
    val funcId: String,
    val navTitle: String,
    val title: String,
    val docLink: String,
    val knownIssues: List<String> = emptyList(),
    val codeLink: String,
)

private data class SketchData(
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

private val visibleSketches: List<SketchData> by lazy {
    sketches.asSequence()
        .filter { it.status != SketchStatus.HIDDEN }
        .sortedWith(compareBy<SketchData> { it.group.ordinal }.thenBy { it.title })
        .toList()
}

private val registry: Map<String, () -> Unit> by lazy {
    visibleSketches.associate { it.funcId to it.function }
}

private val groupedDtos: Map<String, List<SketchDto>> by lazy {
    visibleSketches
        .groupBy { it.group.name }
        .mapValues { (_, list) -> list.map { it.toDto() } }
}

private val sketchesJson: String by lazy {
    Json.encodeToString(
        MapSerializer(String.serializer(), ListSerializer(SketchDto.serializer())),
        groupedDtos
    )
}

private fun SketchData.toDto(): SketchDto = SketchDto(
    funcId = funcId,
    navTitle = navTitle,
    title = title,
    docLink = docLink,
    knownIssues = knownIssues,
    codeLink = codeLink,
)

private val sketches = listOf(
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

@OptIn(ExperimentalWasmJsInterop::class, ExperimentalJsExport::class)
@JsExport
fun getSketchData(): String {
    return sketchesJson
}

@OptIn(ExperimentalWasmJsInterop::class, ExperimentalJsExport::class)
@JsExport
fun runSketch(funcId: String) {
    console.log("Running sketch: ${registry[funcId]}")
    return registry[funcId]?.invoke() ?: console.log("No sketch found for id: $funcId")
}