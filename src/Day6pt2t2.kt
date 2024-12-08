import java.util.*


fun simulateGuardPath(matrix: List<CharArray>, startPos: Pair<Int, Int>, startDir: Direction, obstaclePos: Pair<Int, Int>): Boolean {
    val visited = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()
    var currentPos = startPos
    var currentDir = startDir
    val obstacleChar = 'O'

    // Temporarily place the obstacle
    val originalChar = matrix[obstaclePos.first][obstaclePos.second]
    (matrix as MutableList<CharArray>)[obstaclePos.first][obstaclePos.second] = obstacleChar

    try {
        while (true) {
            val state = Pair(currentPos, currentDir)
            if (state in visited) return true  // Detected a loop

            visited.add(state)

            val nextStep = getNextStep(currentPos.first, currentPos.second, currentDir, matrix)
            val nextPos = Pair(nextStep.first, nextStep.second)
            val nextChar = matrix[nextPos.first][nextPos.second]

            when {
                nextChar == '#' || nextChar == obstacleChar -> {
                    currentDir = getNextDirection2(currentDir)
                }
                else -> {
                    currentPos = nextPos
                }
            }

            if (nextStep.third) break  // Reached map boundary
        }
    } finally {
        // Restore the original character
        (matrix as MutableList<CharArray>)[obstaclePos.first][obstaclePos.second] = originalChar
    }

    return false
}

fun getNextStep(
    verticalIndex: Int,
    horizontalIndex: Int,
    currentDirection: Direction,
    matrix: List<CharArray>
): Triple<Int, Int, Boolean> {
    var nextVerticalIndex = verticalIndex
    var nextHorizontalIndex = horizontalIndex
    var hasReachedEnd = false

    when (currentDirection) {
        Direction.UP -> {
            if (verticalIndex > 0) nextVerticalIndex--
            else hasReachedEnd = true
        }
        Direction.RIGHT -> {
            if (horizontalIndex < matrix[verticalIndex].size - 1) nextHorizontalIndex++
            else hasReachedEnd = true
        }
        Direction.DOWN -> {
            if (verticalIndex < matrix.size - 1) nextVerticalIndex++
            else hasReachedEnd = true
        }
        Direction.LEFT -> {
            if (horizontalIndex > 0) nextHorizontalIndex--
            else hasReachedEnd = true
        }
    }
    return Triple(nextVerticalIndex, nextHorizontalIndex, hasReachedEnd)
}

fun getNextDirection2(currentDirection: Direction): Direction {
    return when (currentDirection) {
        Direction.UP -> Direction.RIGHT
        Direction.RIGHT -> Direction.DOWN
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
    }
}

fun main() {
    val matrix = mutableListOf<CharArray>()
    val directionMap = mapOf(
        '^' to Direction.UP,
        '>' to Direction.RIGHT,
        '<' to Direction.LEFT,
        'v' to Direction.DOWN
    )

    var startPos = Pair(-1, -1)
    var startDir = Direction.UP

    // Read the input file and initialize matrix
    readFile("Day6.txt")?.forEachLine { line ->
        matrix.add(line.toCharArray())
        for ((symbol, direction) in directionMap) {
            if (line.contains(symbol)) {
                startPos = Pair(matrix.size - 1, line.indexOf(symbol))
                startDir = direction
                break
            }
        }
    }

    val newObstacles = mutableSetOf<Pair<Int, Int>>()

    for (v in matrix.indices) {
        for (h in matrix[v].indices) {
            if (matrix[v][h] != '#' && (v != startPos.first || h != startPos.second)) {
                if (simulateGuardPath(matrix, startPos, startDir, Pair(v, h))) {
                    newObstacles.add(Pair(v, h))
                }
            }
        }
    }

    println(newObstacles.size)
}