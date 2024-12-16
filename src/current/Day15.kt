import utils.Utils
import java.util.*
import kotlin.collections.HashMap

class Day15 {

}

val pair = getStartInfo()
val start: Pair<Int, Int>? = pair.first
val end: Pair<Int, Int>? = pair.second
val matrix = pair.third
var shortest: Long = Long.MAX_VALUE;
fun main() {

    if (start != null && end != null) {
        val queue = PriorityQueue<Triple<Pair<Int, Int>, Utils.Direction, Int>>(compareBy { it.third })
        queue.add(Triple(start, Utils.Direction.RIGHT, 0))

        val seen = HashMap<Pair<Pair<Int, Int>, Utils.Direction>, Int>()
        while (queue.isNotEmpty()) {
            val (p, d, s) = queue.poll()

            if (p == end) {
                if (s.toLong() < sho) {
                    sho = s.toLong()
                }
                continue;
            }

            if (seen[p to d] != null && seen[p to d]!! < s) continue
            seen[p to d] = s
            val x = p.first + d.pair.first
            val y = p.second + d.pair.second

            if (x < matrix.size && y < matrix[x].size && matrix[x][y] != '#') {
                queue.add(Triple(Pair(x, y), d, s + 1))
            }
            val perpendicular = Utils.Direction.getPerpendicular(d)
            perpendicular.forEach {
                queue.add(Triple(p, it, s + 1000))
            }
        }

//        for (d in Utils.Direction.entries) {
//            val visited = mutableMapOf<Pair<Int, Int>, Long>()
//            val nextStep = Pair(start.first + d.pair.first, start.second + d.pair.second)
//            visited[start] = 1
//            if (nextStep.first in matrix.indices && nextStep.second in matrix.indices && matrix[nextStep.first][nextStep.second] == '.') {
//                val turn = if (d == Utils.Direction.RIGHT) 0L else 1L
//                val currentShortest = dfs(nextStep, 2, turn, d, visited)
//                if (currentShortest != null) {
//                    val calculated = currentShortest.first + 1000 * currentShortest.second
//                    if (calculated < shortest) {
//                        shortest = calculated
//                    }
//                }
//            }
//        }
        println(sho)
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
    if (sho < count + turns * 1000) {
        return null
    }
    val contains = visited[current]
    if (contains != null) {
        if (contains > count) {
            visited.remove(current)
        } else {
            return null
        }
    }
    visited[current] = count

    if (currentChar == '#') {
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
                val nextTurns = if (currentDirection != direction) {
                    currentTurns + 1
                } else currentTurns
                val dfs = dfs(nextStep, count + 1, nextTurns, direction, visited)

                if (dfs != null) {
                    if (dfs.first + dfs.second * 1000 < sho) {
                        sho = dfs.first + dfs.second * 1000
                    }
                }
            }
        } catch (_: Exception) {
            return null
        }
    }

    return null
}


private fun getStartInfo(): Triple<Pair<Int, Int>?, Pair<Int, Int>?, Array<CharArray>> {
    var start: Pair<Int, Int>? = null
    var end: Pair<Int, Int>? = null
    var currentLineIndex = 0
    val matrix = mutableListOf<List<Char>>()
    readFile("Day15.txt")?.forEachLine {
        matrix.add(it.toList())
        val indexOf = it.indexOf('S')
        if (indexOf != -1) {
            start = Pair(currentLineIndex, indexOf)
        }
        val indexOfE = it.indexOf('E')
        if (indexOfE != -1) {
            end = Pair(currentLineIndex, indexOfE)
        }
        currentLineIndex++;

    }
    return Triple(start, end, matrix.map { it.toCharArray() }.toTypedArray())
}