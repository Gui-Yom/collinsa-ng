package tech.guiyom.collinsa.rendering

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tech.guiyom.collinsa.components.PositionComponent
import java.awt.*
import java.awt.event.FocusEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import kotlin.math.roundToInt
import kotlin.system.exitProcess

/**
 * Renders the world in a canvas in an AWT Frame.
 */
open class CanvasRenderer : Renderer {

    protected val log: Logger = LoggerFactory.getLogger("Renderer")
    protected val frame = Frame("Collinsa-ng")
    protected val canvas = Canvas()
    protected val positionM: ComponentMapper<PositionComponent> = ComponentMapper.getFor(PositionComponent::class.java)
    protected val font: Font = Font.decode("Consolas")
    protected val image: BufferedImage = ImageIO.read(CanvasRenderer::class.java.getResourceAsStream("/louis.png"))

    init {
        frame.setSize(800, 600)
        frame.layout = BorderLayout()
        frame.add(canvas, BorderLayout.CENTER)
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                close()
                frame.dispose()
                exitProcess(0)
            }
        })
        frame.focusableWindowState = true
        frame.isFocusable = true
    }

    override fun init() {
        log.info("Started rendering !")
        frame.isVisible = true
        frame.requestFocus(FocusEvent.Cause.ACTIVATION)
        //if (canvas.bufferStrategy == null)
        canvas.createBufferStrategy(2)
    }

    var count = 0

    override fun render(entities: ImmutableArray<Entity>, deltaTime: Float) {
        val g = canvas.bufferStrategy.drawGraphics as Graphics2D

        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        g.color = Color.WHITE
        g.fillRect(0, 0, 800, 600)

        g.color = Color.BLACK
        for (entity in entities) {
            val position = positionM[entity]
            //val shape = Ellipse2D.Float(position.x + 5, position.y + 5, 4f, 4f)
            //g.fill(shape)
            g.drawImage(image, position.x.toInt() + 16, position.y.toInt() + 16, 16, 16, null)
        }

        g.font = font
        g.drawString(count.toString(), 740, 20)
        g.drawString((1000 / deltaTime).roundToInt().toString(), 740, 40)
        count += 1

        EventQueue.invokeLater {
            canvas.bufferStrategy.show()
        }
        g.dispose()
    }

    override fun close() {
        canvas.bufferStrategy.dispose()
        log.info("Stopped rendering !")
    }
}
