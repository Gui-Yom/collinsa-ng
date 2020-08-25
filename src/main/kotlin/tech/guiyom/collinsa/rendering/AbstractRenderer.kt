package tech.guiyom.collinsa.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import ktx.ashley.get
import ktx.ashley.mapperFor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tech.guiyom.collinsa.components.CollisionComponent
import tech.guiyom.collinsa.components.PositionComponent
import tech.guiyom.collinsa.components.SpeedComponent
import tech.guiyom.collinsa.components.VisualComponent
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import kotlin.math.roundToInt

abstract class AbstractRenderer : Renderer {

    protected val log: Logger = LoggerFactory.getLogger("Renderer")
    protected val font: Font = Font.decode("Consolas")

    protected val positionM = mapperFor<PositionComponent>()
    protected val speedM = mapperFor<SpeedComponent>()
    protected val visualM = mapperFor<VisualComponent>()
    protected val collisionM = mapperFor<CollisionComponent>()

    protected var count = 0

    fun render(entities: ImmutableArray<Entity>, deltaTime: Float, g: Graphics2D) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        g.color = Color.WHITE
        g.fillRect(0, 0, 800, 600)

        for (entity in entities) {
            val position = entity[positionM]!!
            val speed = entity[speedM]!!
            val visual = entity[visualM]!!
            val collision = entity[collisionM]!!
            // Visual
            val size = 12
            g.drawImage(visual.image, position.x.toInt() - size, position.y.toInt() - size, size * 2, size * 2, null)
            // Collision
            g.color = Color.BLACK
            g.draw(
                Ellipse2D.Float(
                    position.x - collision.radius,
                    position.y - collision.radius,
                    collision.radius * 2,
                    collision.radius * 2
                )
            )
            // Speed
            g.color = Color.RED
            g.draw(Line2D.Float(position.x, position.y, position.x + speed.speedX, position.y + speed.speedY))
        }

        g.color = Color.BLACK
        g.font = font
        g.drawString(count.toString(), 740, 20)
        g.drawString((1000 / deltaTime).roundToInt().toString(), 740, 40)
        count += 1
    }
}
