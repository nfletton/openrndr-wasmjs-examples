package wasmJs

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import web.console.console

@Serializable
private data class SketchDto(
    val funcId: String,
    val navTitle: String,
    val title: String,
    val docLink: String,
    val knownIssues: List<String> = emptyList(),
    val codeLink: String,
)


private val visibleSketches: List<SketchData> by lazy {
    sketches.asSequence()
        .filter { it.status != SketchStatus.HIDDEN }
        .sortedWith(compareBy<SketchData> { it.group.ordinal })
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