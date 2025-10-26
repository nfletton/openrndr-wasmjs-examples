package orxcomposition

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.composition.ClipMode
import org.openrndr.extra.composition.composition
import org.openrndr.extra.composition.drawComposition
import org.openrndr.math.Vector2
import kotlin.math.min

/**
 * Demonstrates how to draw a Composition and how to use
 * `ClipMode.REVERSE_DIFFERENCE` to clip shapes.
 *
 * The first shape clips part of the second one away,
 * producing a shape that seems to be behind the first one.
 *
 * Without clipping, the second circle would cover part of the first one.
 */
fun DemoCompositionDrawer02() = application {
    program {
        var composition = drawComposition {  }

        extend {
            val radius = min(width, height) / 4.0
            val offset = Vector2(radius / 4.0, 0.0)

            composition = drawComposition {
                fill = null
                circle(drawer.bounds.center - offset, radius)

                fill = ColorRGBa.BLACK
                clipMode = ClipMode.REVERSE_DIFFERENCE
                circle(drawer.bounds.center + offset, radius)
            }
            drawer.clear(ColorRGBa.PINK)
            drawer.composition(composition)
        }
    }
}