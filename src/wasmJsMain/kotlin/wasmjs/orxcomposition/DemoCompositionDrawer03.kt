package wasmjs.orxcomposition

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.composition.ClipMode
import org.openrndr.extra.composition.composition
import org.openrndr.extra.composition.drawComposition
import org.openrndr.math.Vector2
import web.console.console
import kotlin.math.min

/**
 * Draws a composition using 3 circles and `ClipMode.REVERSE_DIFFERENCE`.
 *
 * Console output demonstrates that the result contains 3 shapes:
 * a complete circle, a moon-like shape, and a shape with two small black areas.
 *
 * One way to verify this is by saving the design as an SVG file and opening
 * it in vector editing software.
 *
 */
fun DemoCompositionDrawer03() = application {
    program {
        var composition = drawComposition {  }

        // save svg to a File
        //composition.saveToFile(File("/path/to/design.svg"))

        window.sized.listen {
            val radius = min(width, height) / 4.0
            val offset = Vector2(radius / 2.0, 0.0)

            composition = drawComposition {
                fill = null
                clipMode = ClipMode.REVERSE_DIFFERENCE

                circle(drawer.bounds.center - offset, radius)
                circle(drawer.bounds.center + offset, radius)

                fill = ColorRGBa.BLACK
                circle(width / 2.0, height / 2.0, radius)
            }

            console.log("Shapes: ${composition.findShapes().size}")
        }

        extend {
            drawer.clear(ColorRGBa.PINK)
            drawer.composition(composition)
        }
    }
}