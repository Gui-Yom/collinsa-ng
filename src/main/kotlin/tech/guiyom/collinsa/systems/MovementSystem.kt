package tech.guiyom.collinsa.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.ashley.mapperFor
import tech.guiyom.collinsa.components.PositionComponent
import tech.guiyom.collinsa.components.SpeedComponent

class MovementSystem : IteratingSystem(allOf(PositionComponent::class, SpeedComponent::class).get()) {

    private val positionM = mapperFor<PositionComponent>()
    private val speedM = mapperFor<SpeedComponent>()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity[positionM]!!
        val speed = entity[speedM]!!
        position.x += speed.speedX * deltaTime * 0.001f
        position.y += speed.speedY * deltaTime * 0.001f
        // TODO integrate physics solver
    }
}
