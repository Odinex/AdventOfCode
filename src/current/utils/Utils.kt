package utils

class Utils {
    enum class Pipes(val value: Char) {
        UPDOWN('|'), LEFTRIGHT('-'), UPRIGHT('L'), UPLEFT('J'), DOWNLEFT('7'), DOWNRIGHT('F'), GROUND('.'), START('S');

        companion object {
            fun valueOf(value: Char): Pipes {
                return entries.find { it.value == value } ?: GROUND

            }

        }
    }

    val directions = arrayOf(
        Pair(0, 1),   // right
        Pair(0, -1),  // left
        Pair(1, 0),   // down
        Pair(-1, 0),  // up
        Pair(1, 1),   // down-right
        Pair(1, -1),  // down-left
        Pair(-1, 1),  // up-right
        Pair(-1, -1)  // up-left
    )
    enum class Direction(val pair: Pair<Int, Int>) {
        RIGHT(Pair(0, 1)),   // right
        LEFT(Pair(0, -1)),  // left
        DOWN(Pair(1, 0)),   // down
        UP(Pair(-1, 0));

        companion object {
            fun getOpposite(currentDirection: Direction): Direction {
                return when (currentDirection) {
                    UP -> DOWN
                    DOWN -> UP
                    LEFT -> RIGHT
                    RIGHT -> LEFT
                }
            }
        }

//    DOWNRIGHT(Pair(1, 1)),   // down-right
//    DOWNLEFT(Pair(1, -1)),  // down-left
//    UPRIGHT(Pair(-1, 1)),  // up-right
//    UPLEFT(Pair(-1, -1))  // up-left
    }

    val PIPES_AFTER_RIGHT = listOf(Pipes.LEFTRIGHT, Pipes.UPLEFT, Pipes.DOWNLEFT);
    val PIPES_AFTER_LEFT = listOf(Pipes.LEFTRIGHT, Pipes.UPRIGHT, Pipes.DOWNRIGHT);
    val PIPES_AFTER_UP = listOf(Pipes.UPDOWN, Pipes.DOWNRIGHT, Pipes.DOWNLEFT);
    val PIPES_AFTER_DOWN = listOf(Pipes.UPDOWN, Pipes.UPRIGHT, Pipes.UPLEFT);
    val directionToPipesMap = mapOf<Direction, List<Pipes>>(
        Direction.DOWN to PIPES_AFTER_DOWN,
        Direction.UP to PIPES_AFTER_UP,
        Direction.LEFT to PIPES_AFTER_LEFT,
        Direction.RIGHT to PIPES_AFTER_RIGHT,
    )
    val CONNECT_PIPES_MAP = mapOf<Pipes, List<Direction>>(
        Pipes.UPDOWN to listOf(Direction.UP, Direction.DOWN),
        Pipes.LEFTRIGHT to listOf(Direction.LEFT, Direction.RIGHT),
        Pipes.UPRIGHT to listOf(Direction.UP, Direction.RIGHT),
        Pipes.UPLEFT to listOf(Direction.LEFT, Direction.UP),
        Pipes.DOWNLEFT to listOf(Direction.LEFT, Direction.DOWN),
        Pipes.DOWNRIGHT to listOf(Direction.RIGHT, Direction.DOWN)
    )

    //private fun dfs(
//    current: Pair<Int, Int>,
//    count: Long,
//    currentDirection: Direction
//): Long? {
//    val currentPipe = Pipes.valueOf(
//        matrix[current.first][current.second])
//    if (currentPipe == Pipes.START) {
//        return count
//    }
//    if(currentPipe == Pipes.GROUND) {
//        return null;
//    }
//    val oppositeDir = Direction.getOpposite(currentDirection);
//    val directions = CONNECT_PIPES_MAP[currentPipe]?.filter { it != oppositeDir}
//    if(directions == null) {
//        return null
//    }
//
//    for (direction in directions) {
//        val nextStep = Pair(current.first + direction.pair.first, current.second + direction.pair.second)
//        try {
//            val pipe = Pipes.valueOf(matrix[nextStep.first][nextStep.second])
//            if (pipe == Pipes.START) {
//                return count
//            }
//            if (directionToPipesMap[direction]?.contains(pipe) == true) {
//                val dfs = dfs(nextStep, count + 1, direction)
//                return dfs
//            } else {
//                return null
//            }
//        } catch (_: Exception) {return null}
//    }
//
//    return count
//}

    //private fun dfs(
//    current: Pair<Int, Int>,
//    path: List<Pair<Int,Int>>,
//    matrix: List<List<Char>>
//): Set<List<Pair<Int,Int>>> {
//    if (matrix[current.first][current.second] == Pipes.START.value) {
//        return setOf(path)
//    }
//
//    val paths = mutableSetOf<List<Pair<Int,Int>>>()
//
//    for (direction in Directions.entries) {
//        val nextStep = Pair(current.first + direction.pair.first, current.second + direction.pair.second)
//
//        if (nextStep.first in matrix.indices && nextStep.second in matrix.indices &&
//            matrix[nextStep.first][nextStep.second] - matrix[current.first][current.second] == 1
//        ) {
//            paths.addAll(dfs(nextStep, path + nextStep, matrix))
//        }
//    }
//
//    return paths
//}
    fun dfs(
        current: Long, count: Int, memo: MutableMap<Pair<Long, Int>, Long>
    ): Long {
        if (count == 75) return 1L

        val key = current to count
        if (key in memo) return memo[key]!!

        var result = 0L
        if (current == 0L) {
            result += dfs(1, count + 1, memo);
        } else {
            val toString = current.toString()
            if (toString.length % 2 == 0) {
                result += dfs(toString.substring(0, toString.length / 2).toLong(), count + 1, memo)
                result += dfs(toString.substring(toString.length / 2, toString.length).toLong(), count + 1, memo)
            } else {
                result += dfs(current * 2024L, count + 1, memo)
            }
        }

        memo[key] = result
        return result
    }
    private fun <T> dfs(
        current: T,
        count: Int,
        memo: MutableMap<Pair<T, Int>, Long>,
        logic: (T, Int) -> List<T>,
        exitCondition: (T, Int) -> Boolean
    ): Long {
        // Base case: return 1 if the exit condition is met
        if (exitCondition(current, count)) return 1L

        // Check if the result is already computed
        val key = current to count
        if (key in memo) return memo[key]!!

        var result = 0L
        // Apply the custom logic to determine the next states
        for (next in logic(current, count)) {
            result += dfs(next, count + 1, memo, logic, exitCondition)
        }

        // Cache the computed result and return it
        memo[key] = result
        return result
    }

    private fun customLogic(current: Long, count: Int): List<Long> {
        val results = mutableListOf<Long>()
        if (current == 0L) {
            results.add(1L)
        } else {
            val toString = current.toString()
            if (toString.length % 2 == 0) {
                val halfLength = toString.length / 2
                results.add(toString.substring(0, halfLength).toLong())
                results.add(toString.substring(halfLength).toLong())
            } else {
                results.add(current * 2024L)
            }
        }
        return results
    }

    private fun exitCondition(current: Long, count: Int): Boolean {
        return count == 75
    }

}