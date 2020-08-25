package tech.guiyom.collinsa.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import tech.guiyom.collinsa.components.PositionComponent
import tech.guiyom.collinsa.components.SpeedComponent

class MovementSystem : IteratingSystem(Family.all(PositionComponent::class.java, SpeedComponent::class.java).get()) {

    private val positionM = ComponentMapper.getFor(PositionComponent::class.java)
    private val speedM = ComponentMapper.getFor(SpeedComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = positionM[entity]
        val speed = speedM[entity]
        position.x += speed.speedX * deltaTime * 0.001f
        position.y += speed.speedY * deltaTime * 0.001f
    }
}
