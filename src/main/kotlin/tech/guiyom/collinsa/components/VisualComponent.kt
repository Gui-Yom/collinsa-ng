package tech.guiyom.collinsa.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import java.awt.Image

data class VisualComponent(var image: Image? = null) : Component, Pool.Poolable {
    override fun reset() {
        image = null
    }
}
