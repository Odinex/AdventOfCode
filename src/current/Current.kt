
val startLongs = getStartInfo()

private var general = 0L
fun main() {

    val memo = mutableMapOf<Pair<Long, Int>, Long>()
    for (i in startLongs) {
        var newCount = 0
        general += dfs(i, newCount, memo)

    }
    println(general)
}


private fun dfs(
    current: Long,
    count: Int,
    memo: MutableMap<Pair<Long, Int>, Long>
): Long {
    if (count == 75) return 1L

    val key = current to count
    if (key in memo) return memo[key]!!

    var result = 0L
    if (current == 0L) {
        result += dfs(1, count+1, memo);
    } else {
        val toString = current.toString()
        if (toString.length % 2 == 0) {
            result +=dfs(toString.substring(0, toString.length / 2).toLong(), count + 1, memo)
            result += dfs(toString.substring(toString.length / 2, toString.length).toLong(), count + 1, memo)
        } else {
            result +=dfs(current * 2024L, count + 1, memo)
        }
    }

    memo[key] = result
    return result
}

private fun getStartInfo(): List<Long> {
    var start: List<Long> = emptyList()
    readFile("Current.txt")?.forEachLine {
        start = it.split(" ").map { c -> c.toLong() }
    }
    return start
}


fun readFile(fileName: String) = object {}.javaClass.getResourceAsStream(fileName)?.reader()

