package tech.guiyom.collinsa.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import ktx.ashley.allOf
import tech.guiyom.collinsa.components.CollisionComponent
import tech.guiyom.collinsa.components.PositionComponent
import tech.guiyom.collinsa.components.SpeedComponent
import tech.guiyom.collinsa.components.VisualComponent
import tech.guiyom.collinsa.rendering.Renderer

class RenderSystem(private val renderer: Renderer) : EntitySystem() {

    var entities: ImmutableArray<Entity>? = null

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(
            allOf(
                PositionComponent::class,
                SpeedComponent::class,
                VisualComponent::class,
                CollisionComponent::class
            ).get()
        )
        renderer.init()
    }

    override fun update(deltaTime: Float) {
        renderer.render(entities!!, deltaTime)
    }

    override fun removedFromEngine(engine: Engine) {
        renderer.close()
        entities = null
    }
}
