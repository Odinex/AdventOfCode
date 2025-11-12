enum class Pipes(val value: Char) {
    UPDOWN('|'), LEFTRIGHT('-'), UPRIGHT('L'), UPLEFT('J'), DOWNLEFT('7'), DOWNRIGHT('F'), GROUND('.'), START('S');

    companion object {
        fun valueOf(value: Char): Pipes {
            return entries.find { it.value == value } ?: GROUND

        }

    }
}

enum class Direction(val pair: Pair<Int, Int>){
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
val pair = getStartInfo()
val start: Pair<Int, Int>? = pair.first
val matrix = pair.second
fun main() {

    var numberOfSafeReports = 0
    var longestPath: Long? = null
    if(start != null) {
        for(d in Direction.entries) {
            val nextStep = Pair(start.first + d.pair.first, start.second + d.pair.second)
            if (nextStep.first in matrix.indices && nextStep.second in matrix.indices) {
                longestPath = dfs(nextStep, 2, d)
                if(longestPath != null) {
                    break;
                }
            }
        }
        if(longestPath != null) {
            println(longestPath/2)
        }
    }
}

private fun getStartInfo(): Pair<Pair<Int, Int>?, Array<CharArray>> {
    var start: Pair<Int, Int>? = null
    var currentLineIndex = 0
    val matrix = mutableListOf<List<Char>>()
    readFile("Current.txt")?.forEachLine {
        matrix.add(it.toList())
        val indexOf = it.indexOf(Pipes.START.value)
        if (indexOf != -1) {
            start = Pair(currentLineIndex, indexOf)
        }
        currentLineIndex++;

    }
    return Pair(start, matrix.map{ it.toCharArray() }.toTypedArray())
}


fun readFile(fileName: String) = object {}.javaClass.getResourceAsStream(fileName)?.reader()

private fun dfs(
    current: Pair<Int, Int>,
    count: Long,
    currentDirection: Direction
): Long? {
    val currentPipe = Pipes.valueOf(
        matrix[current.first][current.second])
    if (currentPipe == Pipes.START) {
        return count
    }
    if(currentPipe == Pipes.GROUND) {
        return null;
    }
    val oppositeDir = Direction.getOpposite(currentDirection);
    val directions = CONNECT_PIPES_MAP[currentPipe]?.filter { it != oppositeDir}
    if(directions == null) {
        return null
    }

    for (direction in directions) {
        val nextStep = Pair(current.first + direction.pair.first, current.second + direction.pair.second)
        try {
            val pipe = Pipes.valueOf(matrix[nextStep.first][nextStep.second])
            if (pipe == Pipes.START) {
                return count
            }
            if (directionToPipesMap[direction]?.contains(pipe) == true) {
                val dfs = dfs(nextStep, count + 1, direction)
                return dfs
            } else {
                return null
            }
        } catch (_: Exception) {return null}
    }

    return count
}

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