package tech.guiyom.collinsa.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import java.util.concurrent.SynchronousQueue

/**
 * Same as CanvasRenderer but offload the rendering task into another thread.
 * Currently unsafe since we use a shared World object.
 */
class ThreadedCanvasRenderer : CanvasRenderer() {

    private val queue = SynchronousQueue<ImmutableArray<Entity>>(true)

    private val renderThread = Thread {
        while (true) {
            val world = queue.take()
            super.render(world)
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

    override fun render(entities: ImmutableArray<Entity>) {
        queue.put(entities)
    }

    override fun close() {
        super.close()
        renderThread.interrupt()
    }
}
