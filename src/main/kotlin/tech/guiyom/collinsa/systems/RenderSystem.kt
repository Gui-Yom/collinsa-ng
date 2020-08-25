package tech.guiyom.collinsa.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.utils.ImmutableArray
import tech.guiyom.collinsa.components.PositionComponent
import tech.guiyom.collinsa.rendering.Renderer

class RenderSystem(private val renderer: Renderer) : EntitySystem() {

    var entities: ImmutableArray<Entity>? = null

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent::class.java).get())
        renderer.init()
    }

    override fun update(deltaTime: Float) {
        renderer.render(entities!!)
    }

    override fun removedFromEngine(engine: Engine) {
        renderer.close()
        entities = null
    }
}
