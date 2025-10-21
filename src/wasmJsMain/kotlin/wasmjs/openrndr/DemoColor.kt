package wasmjs.openrndr

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.color.mix
import org.openrndr.extra.color.presets.LIGHT_GRAY
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.shape.Rectangle


fun DemoColor() {
    application {
        program {
            val columns = 1
            val rows = 3
            val margin = 40.0
            val gutter = 40.0
            fun createGrid() = drawer.bounds.grid(
                columns, rows, margin, margin, gutter, gutter * 2
            )

            var grid: List<List<Rectangle>> = createGrid()
            window.sized.listen {
                grid = createGrid()
            }

            extend {
                val cellWidth = grid[0][0].width
                val cellHeight = grid[0][0].height

                drawer.clear(ColorRGBa.BLACK)

                /* COLORS */
                // shades
                val baseColor = ColorRGBa.PINK
                drawer.stroke = null
                var left = grid[0][0].x
                var top = grid[0][0].y
                val steps = 16
                var width = cellWidth / steps
                var height = cellHeight / 2
                // -- draw 16 darker shades of base color
                for (i in 0..<steps) {
                    drawer.fill = baseColor.shade(i / (steps - 1.0))
                    drawer.rectangle(left + width * i, top, width, height)
                }
                // -- draw 16 lighter shades of base color
                top += height
                for (i in 0..<steps) {
                    drawer.fill = baseColor.shade(1.0 + i / (steps - 1.0))
                    drawer.rectangle(left + width * i, top, width, height)
                }

                drawer.fill = ColorRGBa.LIGHT_GRAY
                height = cellHeight * 2 / 3
                drawer.rectangle(grid[1][0].corner, cellWidth, height)

                // -- draw 16 darker shades of pink
                top = grid[1][0].y + cellHeight * 1 / 3
                left = grid[1][0].x

                for (i in 0..<steps) {
                    drawer.fill = baseColor.opacify(i / 15.0)
                    drawer.rectangle(left + width * i, top, width, height)
                }

                // -- draw 16 color mixes
                val leftColor = ColorRGBa.PINK
                val rightColor = ColorRGBa.fromHex(0xFC3549)
                width = cellWidth / steps
                top = grid[2][0].y
                height = cellHeight
                for (i in 0..<steps) {
                    drawer.fill = mix(leftColor, rightColor, i / 15.0)
                    drawer.rectangle(left + width * i, top, width, height)
                }

            }
        }
    }
}