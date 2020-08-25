package tech.guiyom.collinsa

class FpsCounter(val targetFps: Float, private val size: Int = 64) {

    private val values = FloatArray(size)
    private var index = -1
    private var sum = 0f
    private var filled = false

    init {
        push(targetFps)
    }

    fun push(value: Float) {

        if (++index == size) {
            index = 0
            filled = true
        }

        sum -= values[index]
        sum += value
        values[index] = value
    }

    fun lastValue() = values[index]

    fun average() = sum / (if (filled) size else (index + 1))

    fun diff() = targetFps - values[index]
}
