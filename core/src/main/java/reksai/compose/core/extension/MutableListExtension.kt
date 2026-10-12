package reksai.compose.core.extension

fun <T> MutableList<T>.addLastSingle(t: T) {
    if (t == null) return
    val last = this.lastOrNull()
    if (last == null) {
        this.add(t)
        return
    }
    if (last::class == t::class) {
        this.removeLastOrNull()
    }
    this.add(t)
}