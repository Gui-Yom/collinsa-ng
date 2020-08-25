package tech.guiyom.collinsa.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import java.util.concurrent.SynchronousQueue

/**
 * Same as CanvasRenderer but offload the rendering task into another thread.
 * Currently unsafe since we use a shared World object.
 */
class ThreadedCanvasRenderer : CanvasRenderer() {

    private val queue = SynchronousQueue<Pair<ImmutableArray<Entity>, Float>>(true)

    private val renderThread = Thread {
        while (true) {
            val pair = queue.take()
            super.render(pair.first, pair.second)
        }
    }

    init {
        renderThread.name = "collinsa-renderer"
        renderThread.isDaemon = true
    }

    override fun init() {
        super.init()
        renderThread.start()
    }

    override fun render(entities: ImmutableArray<Entity>, deltaTime: Float) {
        queue.put(entities to deltaTime)
    }

    override fun close() {
        super.close()
        renderThread.interrupt()
    }
}
