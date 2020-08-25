package tech.guiyom.collinsa.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import java.awt.*
import java.awt.event.FocusEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import kotlin.system.exitProcess

/**
 * Renders the world in a canvas in an AWT Frame.
 */
open class CanvasRenderer : AbstractRenderer() {

    protected val frame = Frame("Collinsa-ng")
    protected val canvas = Canvas()

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
        canvas.createBufferStrategy(
            2,
            BufferCapabilities(
                ImageCapabilities(true),
                ImageCapabilities(true),
                null
            )
        )
    }

    override fun render(entities: ImmutableArray<Entity>, deltaTime: Float) {
        val g = canvas.bufferStrategy.drawGraphics as Graphics2D

        super.render(entities, deltaTime, g)

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
