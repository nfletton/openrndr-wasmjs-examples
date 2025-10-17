package wasmjs.orxeasing

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.easing.Easing
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle

/**
 * # Visualizes Easing types as a graph and as motion.
 *
 * [grid] is used to layout graphs on rows and columns.
 *
 */
fun DemoEasings01() = application {

    program {
        // grid `columns * rows` must be >= Easing.values().size
        val grid = drawer.bounds.grid(
            3, 11, 10.0, 10.0, 10.0, 10.0
        ).flatten()

        // Precompute static data (graph points and label positions) once
        data class Plot(
            val easing: Easing,
            val rect: Rectangle,
            val graphPoints: List<Vector2>,
            val labelPos: Vector2
        )
        val samples = 40
        val plots: List<Plot> = enumValues<Easing>().toList()
            .zip(grid)
            .map { (easing, rect) ->
                val pts = List(samples) { i ->
                    val curveT = i.toDouble() / (samples - 1)
                    rect.position(curveT, easing.function(curveT, 1.0, -1.0, 1.0))
                }
                val labelPos = rect.position(0.02, 0.25).toInt().vector2
                Plot(easing, rect, pts, labelPos)
            }

        // Reuse colors
        val bgFill = ColorRGBa(1.0, 1.0, 1.0, 0.3)
        val graphStroke = ColorRGBa.PINK
        val labelFill = ColorRGBa.WHITE
        val dotRadius = 5.0

        extend {

            // ~4 seconds animation loop at 60 fps
            val animT = (frameCount % 240) / 60.0

            // Pass 1: backgrounds
            drawer.stroke = null
            drawer.fill = bgFill
            plots.forEach { drawer.rectangle(it.rect) }

            // Pass 2: graphs
            drawer.fill = null
            drawer.stroke = graphStroke
            plots.forEach { drawer.lineStrip(it.graphPoints) }

            // Pass 3: labels
            drawer.stroke = null
            drawer.fill = labelFill
            plots.forEach { drawer.text(it.easing.name, it.labelPos) }

            // Pass 4: animated dots
            // 4-stage opacity:
            // >3.0 -> invisible; 2.0..3.0 fade-out; 0.0..1.0 fade-in; 1.0..2.0 fully visible
            plots.forEach { plot ->
                val alpha =
                    when {
                        animT > 3.0 -> 0.0
                        animT > 2.0 -> 3.0 - animT
                        animT < 1.0 -> animT
                        else -> 1.0
                    }
                drawer.fill = ColorRGBa(1.0, 1.0, 1.0, alpha)

                // Move only while visible (when loop time in 1.0..2.0)
                val t = (animT - 1.0).coerceIn(0.0, 1.0)
                val xy = Vector2(1.0, plot.easing.function(t, 1.0, -1.0, 1.0))
                drawer.circle(plot.rect.position(xy), dotRadius)
            }
        }
    }
}