package tech.guiyom.collinsa

import com.badlogic.ashley.core.PooledEngine

class World {

    val engine = PooledEngine(16, 128, 32, 256)

    fun loop() {
        engine.update(1000f)
    }
}
