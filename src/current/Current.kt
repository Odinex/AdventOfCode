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
val startLongs = getStartInfo()
fun main() {

    var mutableList = startLongs.toMutableList()
    for(j in 0..<75) {
        var newMutableList = mutableListOf<Long>()
        for(i in mutableList.indices) {
            val l = mutableList[i]
            if (l == 0L) {
                newMutableList.add(1);
            } else {
                val toString = l.toString()
                if (toString.length % 2 == 0) {
                    newMutableList.add(toString.substring(0, toString.length / 2).toLong())
                    newMutableList.add(toString.substring(toString.length / 2, toString.length).toLong())
                } else {
                    newMutableList.add(mutableList[i] * 2024L)
                }
            }
        }
        mutableList = newMutableList
    }
    println(mutableList.size)
}

private fun getStartInfo(): List<Long> {
    var start: List<Long> = emptyList()
    readFile("Current.txt")?.forEachLine {
        start = it.split(" ").map { c -> c.toLong() }
    }
    return start
}


fun readFile(fileName: String) = object {}.javaClass.getResourceAsStream(fileName)?.reader()

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