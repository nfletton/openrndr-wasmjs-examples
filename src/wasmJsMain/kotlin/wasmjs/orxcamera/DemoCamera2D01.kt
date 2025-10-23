package wasmjs.orxcamera

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.camera.Camera2D
import kotlin.math.min

/**
 * # Camera2D demo
 *
 * click and drag the mouse for panning, use the mouse wheel for zooming
 */
fun DemoCamera2D01() = application {
    program {
//        val font = loadFont("demo-data/fonts/IBMPlexMono-Regular.ttf", 72.0)

        extend(Camera2D())
        extend {
            drawer.circle(drawer.bounds.center, min(width, height) * 0.3)

//            drawer.fontMap =
//            drawer.fill = ColorRGBa.PINK
//            drawer.text("click and drag mouse", 50.0, 400.0)
//            drawer.text("use mouse wheel", 50.0, 500.0)
        }
    }
}