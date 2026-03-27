import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import web.console.console
import web.dom.DocumentReadyState
import web.dom.document
import web.dom.loading
import web.events.EventType
import web.events.addEventListener
import web.url.URLSearchParams
import web.window.window
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.toJsString

@Serializable
private data class SketchDto(
    val funcId: String,
    val navTitle: String,
    val title: String,
    val docLink: String,
    val comment: String,
    val codeLink: String,
)


private val visibleSketches: List<SketchData> by lazy {
    sketches.asSequence()
        .filter { it.status != SketchStatus.BROKEN && it.status != SketchStatus.HIDDEN}
        .sortedWith(compareBy { it.pkg.ordinal })
        .toList()
}

private val registry: Map<String, () -> Unit> by lazy {
    visibleSketches.associate { it.funcId to it.function }
}

private val groupedDtos: Map<String, List<SketchDto>> by lazy {
    visibleSketches
        .groupBy { it.pkg.displayName }
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
    comment = comment,
    codeLink = codeLink,
)

fun runSketch(funcId: String) {
    console.log("Running sketch: ${registry[funcId]}")
    registry[funcId]?.invoke() ?: console.log("No sketch found for id: $funcId")
}

@OptIn(ExperimentalWasmJsInterop::class)
fun main() {
    initUI(sketchesJson, webTarget())
    URLSearchParams(window.location.search)
    val activeSketch = URLSearchParams(window.location.search).get("sketch".toJsString())
    val runActive = { activeSketch?.let { runSketch(it.toString()) } }

    if (document.readyState == DocumentReadyState.loading) {
        document.addEventListener(EventType("DOMContentLoaded"), { runActive() })
    } else {
        runActive()
    }
}