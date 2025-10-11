package wasmJs

import web.console.console
import web.cssom.ClassName
import web.dom.document
import web.events.EventHandler
import web.html.*

@OptIn(ExperimentalWasmJsInterop::class, ExperimentalJsExport::class)
@JsExport
fun getNavigation(): HTMLDivElement {
    val nav = document.createElement("div") as HTMLDivElement
    nav.className = ClassName("groups")
    nav.role = "tree"
    sketches.groupBy { it.pkg }.forEach { pkg ->
        val details = document.createElement("details") as HTMLDetailsElement
        details.className = ClassName("group")
        details.role = "treeitem"
        details.open = false
        details.ariaExpanded = "false"
        val summary = document.createElement("summary")
        summary.textContent = pkg.key.name
        details.appendChild(summary)
        val ul = document.createElement("ul") as HTMLUListElement
        ul.role = "group"
        pkg.value.forEach { sketch ->
            val li = document.createElement("li") as HTMLLIElement
            val link = document.createElement("a") as HTMLAnchorElement
            link.href = "#"
            link.onclick = EventHandler { event ->
                console.log("clicked on ${sketch.name}")
                // Set a cookie "selectedSketch" with the sketch name for 7 days
                val maxAgeSeconds = 7 * 24 * 60 * 60
                document.cookie = "selectedSketch=${sketch.source}; path=/; max-age=$maxAgeSeconds; samesite=Strict"
                document.location.reload()
            }
            link.textContent = sketch.name
            li.appendChild(link)
            ul.appendChild(li)
        }
        details.appendChild(ul)
        nav.appendChild(details)
    }
    return nav
}