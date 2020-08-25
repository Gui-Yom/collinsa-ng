package tech.guiyom.collinsa

import org.slf4j.LoggerFactory
import tech.guiyom.collinsa.components.PositionComponent
import tech.guiyom.collinsa.components.SpeedComponent
import tech.guiyom.collinsa.rendering.CanvasRenderer
import tech.guiyom.collinsa.systems.MovementSystem
import tech.guiyom.collinsa.systems.RenderSystem
import java.awt.GraphicsEnvironment
import java.lang.Long.max
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val log = LoggerFactory.getLogger("Main")

    if (GraphicsEnvironment.isHeadless()) {
        log.error("No graphics environment detected, aborting.")
        exitProcess(-2)
    }

    val world = World()

    val speed = world.engine.createComponent(SpeedComponent::class.java)
    speed.speedX = 10f
    world.engine.addEntity(
        world.engine.createEntity()
            .add(world.engine.createComponent(PositionComponent::class.java))
            .add(speed)
    )

    world.engine.addSystem(MovementSystem())
    world.engine.addSystem(RenderSystem(CanvasRenderer()))
    //world.engine.addSystem(RenderSystem(ThreadedCanvasRenderer()))
    while (true) {
        val startTime = System.currentTimeMillis()
        world.loop()
        val elapsedTime = System.currentTimeMillis() - startTime
        Thread.sleep(max(1000 / 1 - elapsedTime, 0))
    }
}
