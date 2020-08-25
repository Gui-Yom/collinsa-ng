package tech.guiyom.collinsa.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool

data class SpeedComponent(var speedX: Float = 0f, var speedY: Float = 0f) : Component, Pool.Poolable {
    override fun reset() {
        speedX = 0f
        speedY = 0f
    }
}
