package tech.guiyom.collinsa

import ktx.ashley.add
import ktx.ashley.entity
import ktx.ashley.with
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
    world.engine.add {
        for (i in 0..32)
            entity {
                with<PositionComponent> {
                    x = rand.nextFloat() * 800
                    y = rand.nextFloat() * 600
                }
                with<SpeedComponent> {
                    speedX = rand.nextFloat() * 15f
                    speedY = rand.nextFloat() * 15f
                }
                with<VisualComponent> {
                    this.image = image
                }
                with<CollisionComponent> {
                    radius = 16f
                }
            }
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
