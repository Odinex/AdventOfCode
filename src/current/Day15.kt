import utils.Utils

class Day15 {

}

val pair = getStartInfo()
val start: Pair<Int, Int>? = pair.first
val matrix = pair.second
var shortest: Long = Long.MAX_VALUE;
fun main() {

    if(start != null) {
        for(d in Utils.Direction.entries) {
            val visited = mutableMapOf<Pair<Int, Int>, Long>()
            val nextStep = Pair(start.first + d.pair.first, start.second + d.pair.second)
            visited[start] = 1
            if (nextStep.first in matrix.indices && nextStep.second in matrix.indices && matrix[nextStep.first][nextStep.second] == '.') {

                val currentShortest = dfs(nextStep, 2, 0, d, visited)
                if(currentShortest != null) {
                    val calculated =currentShortest.first + 1000*currentShortest.second
                    if (calculated < shortest) {
                        shortest = calculated
                    }
                }
            }
        }
        println(shortest)
    }
}
var sho = Long.MAX_VALUE
private fun dfs(
    current: Pair<Int, Int>,
    count: Long,
    turns: Long,
    currentDirection: Utils.Direction,
    visited: MutableMap<Pair<Int, Int>, Long>
): Pair<Long, Long>? {
    var currentTurns = turns
    val currentChar =
        matrix[current.first][current.second]

    if (currentChar == 'E') {
        return Pair(count, turns)
    }
    if(sho < count + turns*1000) {
        return null
    }
    val contains = visited[current]
    if (contains != null) {
        if(contains > count) {
            visited.remove(current)
        } else {
            return null
        }
    }

    if(currentChar == '#') {
        return null;
    } //123424
    val oppositeDir = Utils.Direction.getOpposite(currentDirection);
    val directions = Utils.Direction.entries.filter { dir -> dir != oppositeDir }
    val counts = mutableListOf<Pair<Long, Long>>()
    for (direction in directions) {
        val nextStep = Pair(current.first + direction.pair.first, current.second + direction.pair.second)
        try {
            val char = matrix[nextStep.first][nextStep.second]
            if (char == 'E') {
                return Pair(count, turns)
            }
            if (char == '.') {
                visited[current] = count
                if(currentDirection != direction) {
                    currentTurns++
                }
                val dfs = dfs(nextStep, count + 1, currentTurns, direction, visited)

                if(dfs != null) {
                    if(dfs.first + dfs.second*1000 < sho) {
                        sho =  dfs.first + dfs.second*1000
                    }
                    counts.add(dfs)
                }
            }
        } catch (_: Exception) {return null}
    }

    return if(counts.isEmpty()) null else counts.minBy { it.first + it.second*1000}
}


private fun getStartInfo(): Pair<Pair<Int, Int>?, Array<CharArray>> {
    var start: Pair<Int, Int>? = null
    var currentLineIndex = 0
    val matrix = mutableListOf<List<Char>>()
    readFile("Day15.txt")?.forEachLine {
        matrix.add(it.toList())
        val indexOf = it.indexOf('S')
        if (indexOf != -1) {
            start = Pair(currentLineIndex, indexOf)
        }
        currentLineIndex++;

    }
    return Pair(start, matrix.map{ it.toCharArray() }.toTypedArray())
}