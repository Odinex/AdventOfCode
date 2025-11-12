import java.util.*

fun testAddingObstacle(
    obstacle: Char,
    currentDirection: Direction,
    verticalIndex: Int,
    horizontalIndex: Int,
    nextVerticalIndex: Int,
    nextHorizontalIndex: Int,
    matrix: MutableList<CharArray>
): Boolean {
    var hasReachedNewObstacleCount = 0;
    var hasReachedEnd = false
    var currentTestDirection = currentDirection;
    var testV = verticalIndex;
    var testH = horizontalIndex;
    val prev = matrix[nextVerticalIndex][nextHorizontalIndex];
    matrix[nextVerticalIndex][nextHorizontalIndex] = 'O'
    val visited = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()
    while (!hasReachedEnd ) {
        val state = Pair(Pair(testV, testH), currentTestDirection)
        if (state in visited) {

            matrix[nextVerticalIndex][nextHorizontalIndex] = prev;
            return true
        }  // Detected a loop
        visited.add(state)
        val triple = getNewStep(testV, testH, currentTestDirection, hasReachedEnd, matrix)
        val nextTestVertical = triple.first
        val nextTestHorizontal = triple.second
        hasReachedEnd = triple.third
        val nextChar = matrix[nextTestVertical][nextTestHorizontal]
        if (nextChar == obstacle) {
            currentTestDirection = getNextDirection(currentTestDirection);
        } else {
            if(nextChar == 'O') {
                currentTestDirection = getNextDirection(currentTestDirection);
                hasReachedNewObstacleCount++
            } else {
                testV = nextTestVertical
                testH = nextTestHorizontal
            }

        }
    }
    matrix[nextVerticalIndex][nextHorizontalIndex] = prev;
    return hasReachedNewObstacleCount > 1
}

fun main() {
    val goingUpSymbol = '^'
    val goingForward = '>'
    val goingBackward = '<'
    val goingDownOnYou = 'v'
    val obstacle = '#'
    val directionMap = mapOf(
        Pair(goingDownOnYou, Direction.DOWN), Pair(goingBackward, Direction.LEFT),
        Pair(goingUpSymbol, Direction.UP), Pair(goingForward, Direction.RIGHT)
    )
    var currentDirection = Direction.DOWN;
    val matrix = mutableListOf<CharArray>()
    var startCoordinates = Pair(-1, -1)
    var currentLine = 0

    val newObstacles = HashSet<Pair<Int, Int>>()


    readFile("Day6.txt")?.forEachLine {
        matrix.add(it.toCharArray())
        for (key in directionMap.keys) {
            if (it.contains(key)) {
                startCoordinates = Pair(currentLine, it.indexOf(key))
                currentDirection = directionMap[key]!!;
                break;
            }
        }
        currentLine++;
    }
    var verticalIndex = startCoordinates.first;
    var horizontalIndex = startCoordinates.second;
    val marked = HashSet<Pair<Int, Int>>()
    marked.add(startCoordinates)
    var hasReachedEnd = false;
    while (!hasReachedEnd) {
        val triple = getNewStep(verticalIndex, horizontalIndex, currentDirection, hasReachedEnd, matrix)
        val nextVerticalIndex = triple.first
        val nextHorizontalIndex = triple.second
        hasReachedEnd = triple.third
        val nextChar = matrix[nextVerticalIndex][nextHorizontalIndex]
        if (nextChar == obstacle) {
            currentDirection = getNextDirection(currentDirection);
        } else {
            val wouldBeValidNewObstacle:Boolean = testAddingObstacle(obstacle, currentDirection,verticalIndex, horizontalIndex, nextVerticalIndex, nextHorizontalIndex, matrix)
            if(wouldBeValidNewObstacle) {
                if(startCoordinates.first != nextVerticalIndex || startCoordinates.second != nextHorizontalIndex) {
                    newObstacles.add(Pair(nextVerticalIndex,nextHorizontalIndex))
                }
            }
            verticalIndex = nextVerticalIndex
            horizontalIndex = nextHorizontalIndex

        }
    }
    println(newObstacles.size)
}

private fun getNewStep(
    verticalIndex: Int,
    horizontalIndex: Int,
    currentDirection: Direction,
    hasReachedEnd: Boolean,
    matrix: MutableList<CharArray>
): Triple<Int, Int, Boolean> {
    var hasReachedEnd1 = hasReachedEnd
    var nextVerticalIndex = verticalIndex
    var nextHorizontalIndex = horizontalIndex
    when (currentDirection) {
        Direction.UP -> {
            if (verticalIndex > 0) {
                nextVerticalIndex--
            } else {
                hasReachedEnd1 = true
            }
        }

        Direction.RIGHT -> {
            if (horizontalIndex < matrix[verticalIndex].size - 1) {
                nextHorizontalIndex++
            } else {
                hasReachedEnd1 = true
            }
        }

        Direction.DOWN -> {
            if (verticalIndex < matrix.size - 1) {
                nextVerticalIndex++
            } else {
                hasReachedEnd1 = true
            }
        }

        Direction.LEFT -> {
            if (horizontalIndex > 0) {
                nextHorizontalIndex--
            } else {
                hasReachedEnd1 = true
            }
        }
    }
    return Triple(nextVerticalIndex, nextHorizontalIndex, hasReachedEnd1)
}


