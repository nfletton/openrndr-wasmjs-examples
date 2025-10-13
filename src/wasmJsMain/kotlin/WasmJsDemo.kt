package wasmJs

import web.console.console
import web.cssom.ClassName
import web.dom.ElementId
import web.dom.document
import web.events.EventHandler
import web.html.*
import web.storage.sessionStorage

@OptIn(ExperimentalWasmJsInterop::class, ExperimentalJsExport::class)
@JsExport
fun getNavigationLinks(): HTMLDivElement {
    val nav = document.createElement("div") as HTMLDivElement
    nav.className = ClassName("groups")
    nav.role = "tree"
    sketches.filter({it.status != SketchStatus.HIDDEN}).groupBy { it.group }.forEach { pkg ->
        val details = document.createElement("details") as HTMLDetailsElement
        details.className = ClassName("group")
        details.role = "treeitem"
        details.open = false
        details.ariaExpanded = "false"
        val summary = document.createElement("summary")
        summary.textContent = pkg.key.name
        details.appendChild(summary)
        val ul = document.createElement("ul") as HTMLUListElement
        val groupName = pkg.key.name.lowercase().replace(" ", "-")
        ul.id = ElementId(groupName)
        ul.role = "group"
        pkg.value.forEach { sketch ->
            val funcName = sketch.function.toString().substringBefore("$")
            val li = document.createElement("li") as HTMLLIElement
            val link = document.createElement("a") as HTMLAnchorElement
            link.href = "#"
            val id = "$groupName-${funcName}"
            link.id = ElementId(id)
            link.onclick = EventHandler { event ->
                console.log("clicked on ${sketch.navTitle}")
                sessionStorage.setItem("sketch", funcName)
                sessionStorage.setItem("sidebar", "todo")
                sessionStorage.setItem("activeNav", id)
                sessionStorage.setItem("codeLink", sketch.codeLink)
                sessionStorage.setItem("docLink", sketch.docLink)
                document.location.reload()
            }
            link.textContent = sketch.navTitle
            li.appendChild(link)
            ul.appendChild(li)
        }
        details.appendChild(ul)
        nav.appendChild(details)
    }
    return nav
}