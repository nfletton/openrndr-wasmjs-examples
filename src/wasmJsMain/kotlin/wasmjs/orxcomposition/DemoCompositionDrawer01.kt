package wasmjs.orxcomposition

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.composition.composition
import org.openrndr.extra.composition.drawComposition
//import org.openrndr.extra.svg.toSVG
import org.openrndr.math.Vector2
import kotlin.math.min

/**
 * Demonstrates how to
 *
 * - Create a Composition
 * - Draw it on the program window
 * - Save it to an SVG file
 * - Print the SVG content as text
 */
fun DemoCompositionDrawer01() = application {
    program {
        var composition = drawComposition {  }

        // print the svg to the console

        mouse.buttonDown.listen {
            //TODO: see if SVG export can be made multiplatform
//        println(composition.toSVG())
        }
        extend {
            val radius = min(width, height) / 4.0

            composition = drawComposition {
                fill = ColorRGBa.WHITE
                stroke = ColorRGBa.BLACK
                strokeWeight = 5.0
                circle(drawer.bounds.center, radius)
                circle(drawer.bounds.center - Vector2(radius, radius / 2), radius / 2)
            }
            drawer.clear(ColorRGBa.PINK)

            // draw the composition to the screen
            drawer.composition(composition)
        }
    }
}