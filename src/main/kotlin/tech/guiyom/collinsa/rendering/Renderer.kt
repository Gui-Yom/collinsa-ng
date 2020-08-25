package tech.guiyom.collinsa.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import java.io.Closeable

interface Renderer : Closeable {

    fun init()

    fun render(entities: ImmutableArray<Entity>, deltaTime: Float)
}
