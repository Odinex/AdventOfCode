import java.util.*

enum class Direction {
    UP, RIGHT,DOWN, LEFT

}

fun main() {
    val goingUpSymbol = '^'
    val goingForward = '>'
    val goingBackward = '<'
    val goingDownOnYou = 'v'
    val mark = 'X';
    var markCount = 1;
    var obstacle = '#'
    val directionMap = mapOf(Pair(goingDownOnYou, Direction.DOWN), Pair(goingBackward, Direction.LEFT),
        Pair(goingUpSymbol, Direction.UP),Pair(goingForward, Direction.RIGHT))
    var currentDirection = Direction.DOWN;
    val matrix = mutableListOf<CharArray>()
    var startCoordinates = Pair(-1,-1)
    var currentLine = 0
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
    matrix[verticalIndex][horizontalIndex] = mark;
    var hasReachedEnd = false;
    while(!hasReachedEnd) {
        var nextVerticalIndex = verticalIndex
        var nextHorizontalIndex = horizontalIndex
        when (currentDirection) {
            Direction.UP -> {
                if (verticalIndex > 0) {
                    nextVerticalIndex--
                } else {
                    hasReachedEnd = true
                }
            }
            Direction.RIGHT -> {
                if(horizontalIndex < matrix[verticalIndex].size - 1) {
                    nextHorizontalIndex++
                } else {
                    hasReachedEnd = true
                }
            }

            Direction.DOWN -> {
                if(verticalIndex < matrix.size - 1) {
                    nextVerticalIndex++
                } else {
                    hasReachedEnd = true
                }
            }
            Direction.LEFT -> {
                if(horizontalIndex > 0) {
                    nextHorizontalIndex--
                } else {
                    hasReachedEnd = true
                }
            }
        }
        val nextChar = matrix[nextVerticalIndex][nextHorizontalIndex]
        if(nextChar == obstacle) {
            currentDirection = getNextDirection(currentDirection);
        } else {
            if(nextChar != mark) {
                matrix[nextVerticalIndex][nextHorizontalIndex] = mark;
                markCount++;
            }
            verticalIndex = nextVerticalIndex
            horizontalIndex = nextHorizontalIndex

        }
    }
    println(markCount)
}

fun getNextDirection(currentDirection: Direction): Direction {
    val directionsOrder = Direction.entries.toTypedArray()
    val currentIndex = directionsOrder.indexOf(currentDirection)

    val nextIndex = if(currentIndex == directionsOrder.size-1) 0 else currentIndex+1
    return directionsOrder[nextIndex]
}
