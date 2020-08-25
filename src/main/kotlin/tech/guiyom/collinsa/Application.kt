package tech.guiyom.collinsa

import org.slf4j.LoggerFactory
import tech.guiyom.collinsa.components.CollisionComponent
import tech.guiyom.collinsa.components.PositionComponent
import tech.guiyom.collinsa.components.SpeedComponent
import tech.guiyom.collinsa.components.VisualComponent
import tech.guiyom.collinsa.rendering.CanvasRenderer
import tech.guiyom.collinsa.systems.MovementSystem
import tech.guiyom.collinsa.systems.RenderSystem
import java.awt.GraphicsEnvironment
import javax.imageio.ImageIO
import kotlin.math.max
import kotlin.random.Random
import kotlin.system.exitProcess
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {

    val log = LoggerFactory.getLogger("Main")

    if (GraphicsEnvironment.isHeadless()) {
        log.error("No graphics environment detected, aborting.")
        exitProcess(-2)
    }

    val world = World()

    val rand = Random.Default
    val image = ImageIO.read(CanvasRenderer::class.java.getResourceAsStream("/louis.png"))
    for (i in 0..32) {
        val speed = world.engine.createComponent(SpeedComponent::class.java)
        speed.speedX = rand.nextFloat() * 15f
        speed.speedY = rand.nextFloat() * 15f

        val position = world.engine.createComponent(PositionComponent::class.java)
        position.x = rand.nextFloat() * 800
        position.y = rand.nextFloat() * 600

        val visual = world.engine.createComponent(VisualComponent::class.java)
        visual.image = image

        val collision = world.engine.createComponent(CollisionComponent::class.java)
        collision.radius = 16f

        world.engine.addEntity(
            world.engine.createEntity()
                .add(position)
                .add(speed)
                .add(visual)
                .add(collision)
        )
    }

    world.engine.addSystem(MovementSystem())
    world.engine.addSystem(RenderSystem(CanvasRenderer()))
    //world.engine.addSystem(RenderSystem(ThreadedCanvasRenderer()))

    val fpsCounter = FpsCounter(1000 / 60f)

    while (true) {
        val elapsedTime = measureNanoTime { world.loop(fpsCounter.average() + fpsCounter.diff()) } * 0.000_001f
        fpsCounter.push(elapsedTime)
        // lock fps to target fps
        Thread.sleep(max(fpsCounter.diff(), 0f).toLong())
        //print("${fpsCounter.lastValue()}, ${fpsCounter.average()}, ${fpsCounter.diff()}\r")
    }
}
