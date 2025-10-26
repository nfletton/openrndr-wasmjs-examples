package openrndr

import org.openrndr.application
import org.openrndr.color.*
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.shape.Rectangle

fun DemoColorModels() {
    application {
        program {
            val columns = 1
            val rows = 4
            val margin = 30.0
            val gutter = 30.0
            fun createGrid() = drawer.bounds.grid(
                columns, rows, margin, margin, gutter, gutter
            )

            var grid: List<List<Rectangle>> = createGrid()
            window.sized.listen {
                grid = createGrid()
            }

            extend {
                val cellWidth = grid[0][0].width
                val cellHeight = grid[0][0].height

                drawer.clear(ColorRGBa.BLACK)

                /* COLORS MODELS */
                drawer.stroke = null
                val left = grid[0][0].x
                var top = grid[0][0].y
                val stepsY = 8
                val stepsX = 32
                val width = cellWidth / stepsX
                val height = cellHeight / stepsY

                // -- draw hsv swatches
                for (j in 0..<stepsY) {
                    for (i in 0..<stepsX) {
                        drawer.fill = ColorHSVa(360 * (i / 31.0), 0.7, 0.125 + j / 8.0).toRGBa()
                        drawer.rectangle(left + width * i, top + height * j, width, height)
                    }
                }

                // -- draw hsl swatches
                top = grid[1][0].y
                for (j in 0..<stepsY) {
                    for (i in 0..<stepsX) {
                        drawer.fill = ColorHSLa(360 * (i / 31.0), 0.7, 0.125 + j / 9.0).toRGBa()
                        drawer.rectangle(left + width * i, top + height * j, width, height)
                    }
                }

                // -- draw xsv (Kuler) swatches
                top = grid[2][0].y
                for (j in 0..<stepsY) {
                    for (i in 0..<stepsX) {
                        drawer.fill = ColorXSVa(360 * (i / 31.0), 0.7, 0.125 + j / 8.0).toRGBa()
                        drawer.rectangle(left + width * i, top + height * j, width, height)
                    }
                }

                // -- draw xsl (Kuler) swatches
                top = grid[3][0].y
                for (j in 0..<stepsY) {
                    for (i in 0..<stepsX) {
                        drawer.fill =
                            ColorXSLa(360 * (i / 31.0), 0.7, 0.125 + j / 9.0, 1.0).toRGBa()
                        drawer.rectangle(left + width * i, top + height * j, width, height)
                    }
                }
            }
        }
    }
}