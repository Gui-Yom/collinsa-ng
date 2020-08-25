package tech.guiyom.collinsa.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool

data class PositionComponent(var x: Float = 0f, var y: Float = 0f) : Component, Pool.Poolable {
    override fun reset() {
        x = 0f
        y = 0f
    }
}
