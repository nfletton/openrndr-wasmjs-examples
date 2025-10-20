package wasmjs.openrndr

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.LineCap
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle
import kotlin.random.Random


fun DemoBasicDraw() {
    application {
        program {
            val columns = 3
            val rows = 6
            val margin = 20.0

            fun createGrid() = drawer.bounds.grid(
                columns, rows, margin, margin, margin, margin
            )

            var grid: List<List<Rectangle>> = createGrid()
            window.sized.listen {
                grid = createGrid()
            }

            extend {
                val cellWidth = grid[0][0].width
                val cellHeight = grid[0][0].height

                drawer.clear(ColorRGBa.BLACK)

                /* CIRCLES */
                val circleRadius = if (cellWidth < cellHeight) cellWidth / 2.0 else cellHeight / 2.0
                // -- draw a circle with pink fill and white stroke
                drawer.stroke = ColorRGBa.WHITE
                drawer.fill = ColorRGBa.PINK
                drawer.strokeWeight = 1.0
                drawer.circle(grid[0][0].center, circleRadius)

                // -- draw a circle without a fill, but with white stroke
                drawer.stroke = ColorRGBa.WHITE
                drawer.fill = null
                drawer.strokeWeight = 1.0
                drawer.circle(grid[0][1].center, circleRadius)

                // -- draw a circle with pink fill, but without a stroke
                drawer.stroke = null
                drawer.fill = ColorRGBa.PINK
                drawer.strokeWeight = 1.0
                drawer.circle(grid[0][2].center, circleRadius)

                /* RECTANGLES */
                // -- draw rectangle with pink fill and white stroke
                drawer.fill = ColorRGBa.PINK
                drawer.stroke = ColorRGBa.WHITE
                drawer.strokeWeight = 1.0
                drawer.rectangle(grid[1][0].corner, cellWidth, cellHeight)

                // -- draw rectangle without fill, but with white stroke
                drawer.fill = null
                drawer.stroke = ColorRGBa.WHITE
                drawer.strokeWeight = 1.0
                drawer.rectangle(grid[1][1].corner, cellWidth, cellHeight)

                // -- draw a rectangle with pink fill, but without stroke
                drawer.fill = ColorRGBa.PINK
                drawer.stroke = null
                drawer.strokeWeight = 1.0
                drawer.rectangle(grid[1][2].corner, cellWidth, cellHeight)

                /* LINES */
                // -- setup line appearance
                drawer.stroke = ColorRGBa.WHITE
                drawer.strokeWeight = 5.0
                drawer.lineCap = LineCap.ROUND

                drawer.lineSegment(
                    grid[2][0].center - Vector2(0.0, cellHeight / 3),
                    grid[2][2].center - Vector2(0.0, cellHeight / 3)
                )

                drawer.lineCap = LineCap.BUTT
                drawer.lineSegment(grid[2][0].center, grid[2][2].center)

                drawer.lineCap = LineCap.SQUARE
                drawer.lineSegment(
                    grid[2][0].center + Vector2(0.0, cellHeight / 3),
                    grid[2][2].center + Vector2(0.0, cellHeight / 3)
                )

                /* LINE STRIP */
                // -- setup line appearance
                drawer.stroke = ColorRGBa.WHITE
                drawer.strokeWeight = 5.0
                drawer.lineCap = LineCap.ROUND

                var points = listOf(
                    grid[3][0].corner,
                    grid[3][1].center + Vector2(0.0, cellHeight / 2.0),
                    grid[3][2].corner + Vector2(cellWidth, 0.0),
                )
                drawer.lineStrip(points)

                /* LINE LOOP */
                drawer.lineCap = LineCap.BUTT
                drawer.strokeWeight = 2.0

                drawer.lineLoop(points.map { it + Vector2(0.0, cellHeight + margin) })

                /* POINTS */
                val random = Random(1234)
                val nPoints = 2000
                points = buildList {
                    val topLeftX = grid[5][0].x
                    val topLeftY = grid[5][0].y
                    val topRightX = topLeftX + columns * cellWidth + (columns - 1) * margin
                    val bottomLeftY = topLeftY + cellHeight
                    repeat(nPoints) {
                        val x = random.nextDouble(topLeftX, topRightX)
                        val y = random.nextDouble(topLeftY, bottomLeftY)
                        add(Vector2(x, y))
                    }
                }
                drawer.fill = ColorRGBa.WHITE
                drawer.points(points)
            }
        }
    }
}