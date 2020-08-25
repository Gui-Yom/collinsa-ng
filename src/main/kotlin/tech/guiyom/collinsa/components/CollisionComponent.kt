package tech.guiyom.collinsa.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool

data class CollisionComponent(var radius: Float = 0f) : Component, Pool.Poolable {
    override fun reset() {
        radius = 0f
    }
}
