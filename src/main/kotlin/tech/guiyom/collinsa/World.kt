package tech.guiyom.collinsa

import com.badlogic.ashley.core.PooledEngine

class World {

    val engine = PooledEngine(256, 256, 512, 1024)

    fun loop(deltaTime: Float) {
        engine.update(deltaTime)
    }
}
