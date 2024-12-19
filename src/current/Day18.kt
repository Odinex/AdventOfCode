import utils.Utils
import java.util.*
import kotlin.collections.HashMap


val pairs = getStartInfo()
val start: Pair<Int, Int> = Pair(0, 0)

val end: Pair<Int, Int> = Pair(6,6)
const val size = 7
//val end: Pair<Int, Int> = Pair(70, 70)
//const val size = 71
val matrix = mutableListOf<MutableList<Char>>()
var sho = Long.MAX_VALUE
fun main() {
    val row = mutableListOf<Char>()
    for (i in 0..<size) {
        row.add('.')
    }

    for (i in 0..<size) {
        matrix.add(row.toMutableList())
    }
    val kilobyte = 12
    //val kilobyte = 1024
    var currentByte = 0
    while (currentByte < kilobyte && pairs.isNotEmpty()) {
        val (y, x) = pairs.removeFirst()
        println("$x to $y")
        if (isValidPair(x to y, size)) {
            matrix[x][y] = '#'
        } else {
        }
        currentByte++
    }
    matrix.forEach {
        it.forEach { c ->
            print(c)
        }
        println()
    }
    println('-')
    if (start != null && end != null) {
        val queue = PriorityQueue<Triple<List<Pair<Int, Int>>, Utils.Direction, Double>>(compareBy { it.third })
        val list = LinkedList<Pair<Int, Int>>()
        list.addLast(start)
        queue.add(Triple(list, Utils.Direction.RIGHT, 0.0))

        val bestSeats = mutableSetOf<Pair<Int, Int>>()
        val seen = HashMap<Pair<Pair<Int, Int>, Utils.Direction>, Double>()
        var steps = Int.MAX_VALUE
        var stepsList: List<Pair<Int, Int>> = emptyList()
        while (queue.isNotEmpty()) {
            val (pairs, dir, score) = queue.poll()

            val last = pairs.last()
            if (last == end) {
                if (pairs.distinct().size <= steps) {
                    sho = score.toLong()
                    stepsList = pairs.distinct()
                    steps = stepsList.size - 1
                }

                bestSeats.addAll(pairs)
            }

            val lastWithDirection = last to dir
            if (seen[lastWithDirection] != null && seen[lastWithDirection]!! < score) continue
            seen[lastWithDirection] = score
            val x = last.first + dir.pair.first
            val y = last.second + dir.pair.second

            if (isValidPair(x to y, size) && matrix[y][x] != '#') {
                queue.add(Triple(pairs + Pair(x, y), dir, score + 1))
            }
            val perpendicular = Utils.Direction.getPerpendicular(dir)
            perpendicular.forEach {
                queue.add(Triple(pairs, it, score + 0.1))
            }
        }
        println(bestSeats.size)
        println(steps)
        println(sho)

        stepsList.forEach { (x, y)
            ->
            matrix[y][x] = '0'
        }
        matrix.forEach {
            it.forEach { c ->
                print(c)
            };
            println()
        }
        println('-')
        while (pairs.isNotEmpty()) {
            val nextPair = pairs.removeFirst()
            matrix[nextPair.second][nextPair.first] = '#'
            var newSteps = listOf<Pair<Int, Int>>()
            var newStepscount = Int.MAX_VALUE

            val queue2 = PriorityQueue<Triple<List<Pair<Int, Int>>, Utils.Direction, Double>>(compareBy { it.third })
            val list2 = LinkedList<Pair<Int, Int>>()
            list2.addLast(start)
            queue2.add(Triple(list2, Utils.Direction.RIGHT, 0.0))
            val seen2 = HashMap<Pair<Pair<Int, Int>, Utils.Direction>, Double>()
            while (queue2.isNotEmpty()) {
                val (pairs, dir, score) = queue2.poll()

                val last = pairs.last()
                if (last == end) {
                    if (pairs.distinct().size < newStepscount) {
                        sho = score.toLong()
                        newSteps = pairs.distinct()
                        newStepscount = newSteps.size - 1
                    }

                    bestSeats.addAll(pairs)
                }

                val lastWithDirection = last to dir
                if (seen2[lastWithDirection] != null && seen2[lastWithDirection]!! < score) continue
                seen2[lastWithDirection] = score
                val x = last.first + dir.pair.first
                val y = last.second + dir.pair.second

                if (isValidPair(x to y, size) && matrix[y][x] != '#') {
                    queue2.add(Triple(pairs + Pair(x, y), dir, score + 1))
                }
                val perpendicular = Utils.Direction.getPerpendicular(dir)
                perpendicular.forEach {
                    queue2.add(Triple(pairs, it, score + 1000))
                }
            }
            if (newSteps.isEmpty() || newSteps.size > stepsList.size) {
                println(nextPair)
                break;
            } else {
//                matrix.forEach {
//                    it.forEach { c ->
//                        print(c)
//                    }
//                    println()
//                }
//                println('-')
            }


        }
    }
}

fun isValidPair(pair: Pair<Int, Int>, size: Int): Boolean {
    return pair.first in 0..<size && pair.second in 0..<size
}

private fun solvePt1() {
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

            if (x < matrix.size && y < matrix[x].size && matrix[y][x] != '#') {
                queue.add(Triple(Pair(x, y), d, s + 1))
            }
            val perpendicular = Utils.Direction.getPerpendicular(d)
            perpendicular.forEach {
                queue.add(Triple(p, it, s + 1000))
            }
        }


        println(sho)
    }
}

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


private fun getStartInfo(): MutableList<Pair<Int, Int>> {

    var currentLineIndex = 0
    val muta = mutableListOf<Pair<Int, Int>>()

    readFile("CurrentTest.txt")?.forEachLine {
        val (x, y) = it.split(',').map { it.toInt() }
        muta.add(x to y)
        currentLineIndex++;

    }
    return muta
}

fun readFile(fileName: String) = object {}.javaClass.getResourceAsStream(fileName)?.reader()