package wasmjs.openrndr

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.color.rgb
import org.openrndr.draw.LineCap
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle
import kotlin.random.Random


fun basicDrawerDemo() {
    application {
        configure {
            title = "OPENRNDR - Basic Drawing"
            windowResizable = true
        }

        program {
            extend {
                val columns = 3
                val rows = 6
                val margin = 20.0
                val cellWidth = (width - margin * (columns + 1)) / columns
                val cellHeight = (height - margin * (rows + 1)) / rows

                val cells = buildList {
                    repeat(columns) { columnIndex ->
                        add(buildList {
                            repeat(rows) { rowIndex ->
                                add(
                                    Rectangle(
                                        Vector2(
                                            columnIndex * cellWidth + (columnIndex + 1) * margin,
                                            rowIndex * cellHeight + (rowIndex + 1) * margin
                                        ), cellWidth, cellHeight
                                    )
                                )
                            }
                        })
                    }
                }


                val circleRadius = if (cellWidth < cellHeight) cellWidth / 2.0 else cellHeight / 2.0
                val rectWidth = cells[0][0].width
                val rectHeight = cells[0][0].height

                drawer.clear(ColorRGBa.PINK)

                /* CIRCLES */
                // -- draw a circle with white fill and black stroke
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 1.0
                drawer.circle(cells[0][0].center, circleRadius)

                // -- draw a circle without a fill, but with black stroke
                drawer.fill = null
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 1.0
                drawer.circle(cells[1][0].center, circleRadius)

                // -- draw a circle with white fill, but without a stroke
                drawer.fill = ColorRGBa.WHITE
                drawer.stroke = null
                drawer.strokeWeight = 1.0
                drawer.circle(cells[2][0].center, circleRadius)

                /* RECTANGLES */
                // -- draw rectangle with white fill and black stroke
                drawer.fill = ColorRGBa.WHITE
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 1.0
                drawer.rectangle(cells[0][1].corner, rectWidth, rectHeight)

                // -- draw rectangle without fill, but with black stroke
                drawer.fill = null
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 1.0
                drawer.rectangle(cells[1][1].corner, rectWidth, rectHeight)

                // -- draw a rectangle with white fill, but without stroke
                drawer.fill = ColorRGBa.WHITE
                drawer.stroke = null
                drawer.strokeWeight = 1.0
                drawer.rectangle(cells[2][1].corner, rectWidth, rectHeight)

                /* LINES */
                // -- setup line appearance
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 5.0
                drawer.lineCap = LineCap.ROUND

                drawer.lineSegment(
                    cells[0][2].center - Vector2(0.0, cellHeight / 3),
                    cells[2][2].center - Vector2(0.0, cellHeight / 3)
                )

                drawer.lineCap = LineCap.BUTT
                drawer.lineSegment(cells[0][2].center, cells[2][2].center)

                drawer.lineCap = LineCap.SQUARE
                drawer.lineSegment(
                    cells[0][2].center + Vector2(0.0, cellHeight / 3),
                    cells[2][2].center + Vector2(0.0, cellHeight / 3)
                )

                /* LINE STRIP */
                // -- setup line appearance
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 5.0
                drawer.lineCap = LineCap.ROUND

                var points = listOf(
                    cells[0][3].corner,
                    cells[1][3].center + Vector2(0.0, cellHeight / 2.0),
                    cells[2][3].corner + Vector2(cellWidth, 0.0),
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
                    val topLeftX = cells[0][5].x
                    val topLeftY = cells[0][5].y
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