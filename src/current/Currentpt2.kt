import utils.Utils



fun main() {
    val moves = getMoves()
    var time = 0
    warehouse = getWareHouse()
    for ((index, move) in moves.withIndex()) {
        if(index == 137) {
            printWarehouse()
        }
        val direction = MoveDirectionMap[move]
        val newPosition = direction?.let { getNewPosition(it, robot.robotPosition) }

        if (newPosition != null) {
            val newPositionElement = warehouse[newPosition.first][newPosition.second]
            if (newPositionElement != '#') {
                if (newPositionElement == '.') {
                    warehouse[robot.robotPosition.first][robot.robotPosition.second] = '.'
                    robot.setRobotPositionAndValidate(newPosition)
                    warehouse[newPosition.first][newPosition.second] = '@'
                } else {

                    val set = mutableSetOf<Int>()
                    val startPositions = mutableListOf(Pair(robot.robotPosition, false))
                    var adjacentToRobotDot: Pair<Int,Int>? = null
                    if(direction == Utils.Direction.UP || direction == Utils.Direction.DOWN) {
                        set.add(robot.robotPosition.second)
                        var oneStepDirection = Utils.Direction.LEFT
                        if(warehouse[newPosition.first][newPosition.second] == '[') {
                            oneStepDirection = Utils.Direction.RIGHT
                        }
                        adjacentToRobotDot = getNewPosition(oneStepDirection, robot.robotPosition)
                        startPositions.add(Pair(adjacentToRobotDot, true))
                        set.add(adjacentToRobotDot.second)
                    }
                    val visited = mutableSetOf<Pair<Utils.Direction,Pair<Int,Int>>>()
                    val visitedColumns = mutableSetOf<Int> ()
                    val mapStartToFinish = startPositions.associateWith {
                        getNextDotsFromPosition(direction, it.first, visited, visitedColumns)
                    }
                    if(mapStartToFinish.isEmpty() || mapStartToFinish.values.any { it.isEmpty() || it.any { dot -> dot.first == -1}}) {
                        continue
                    }
                    for(startPosition in startPositions) {

                        push(direction, startPosition,set)
                    }
                }
            } else {
                continue
            }
            if(warehouse.any { it.toString().contains("[, .") || it.toString().contains("., ]")|| it.toString().contains("[, [")
                        || it.toString().contains("], ]")}) {
                printWarehouse()
                throw Exception()
            }
        }
    }
    var sum =0
    for(x in 0..<warehouse.size) {
        for(y in 0..<warehouse[x].size) {

            if(warehouse[x][y] == '[') {
                sum += 100 *x + y
            }
        }
    }
    println(sum)


}

private fun push(
    direction: Utils.Direction,
    startPositionPair: Pair<Pair<Int, Int>, Boolean>,
    visitedColumns: MutableSet<Int>
): Boolean {
    val startPosition = startPositionPair.first
    val nextDot = getNextDotFromPosition(direction, startPosition)
    if(nextDot.first == -1) {
        return false
    }
    var nextStep = getNewPosition(direction, startPosition)
    var currentElement = warehouse[startPosition.first][startPosition.second]
    var nextElement = warehouse[nextStep.first][nextStep.second]
    if(currentElement == '@' || "[]".contains(warehouse[startPosition.first][startPosition.second]) && !startPositionPair.second) {
        warehouse[startPosition.first][startPosition.second] = '.'
        if(currentElement == '@') {
            robot.setRobotPositionAndValidate(nextStep)
        }
    }
    if(startPositionPair.second) {
        warehouse[nextStep.first][nextStep.second] = '.'
    } else {
        warehouse[nextStep.first][nextStep.second] = currentElement
    }
    while (nextDot != nextStep) {
        var prevStep = nextStep
        nextStep = getNewPosition(direction, nextStep)
        currentElement = nextElement
        nextElement = warehouse[nextStep.first][nextStep.second]

        var shouldPushDiagonally = false
        if (direction == Utils.Direction.UP || direction == Utils.Direction.DOWN) {
            getAdjacentStepIfPushingDiagonally(direction, nextElement, nextStep, visitedColumns)?.let {
                shouldPushDiagonally = true
                for(adjacent in it) {
                    push(direction, Pair(adjacent, false),visitedColumns)
                }
            }
        }
        warehouse[nextStep.first][nextStep.second] = currentElement

    }
    return true
}

private fun getAdjacentStepIfPushingDiagonally(
    originalDirection: Utils.Direction,
    nextElement: Char,
    position: Pair<Int, Int>,
    visitedColumns: MutableSet<Int>
): List<Pair<Int,Int>> {
    val mutableListOf = mutableListOf<Pair<Int, Int>>()
    var oneStepDirection: Utils.Direction? = null
    if (nextElement == ']') {
        oneStepDirection = Utils.Direction.LEFT
    } else if (nextElement == '[') {
        oneStepDirection = Utils.Direction.RIGHT
    }
    if (oneStepDirection != null) {
        var adjacentRandomStep: Pair<Int,Int>? = null
        adjacentRandomStep = getNewPosition(oneStepDirection, position)
        if(visitedColumns.contains(adjacentRandomStep.second)) {
            return mutableListOf
        } else {
            visitedColumns.add(adjacentRandomStep.second)
        }
        //
        mutableListOf.add(adjacentRandomStep)
//        if(oneStepDirection == Utils.Direction.RIGHT && warehouse[adjacentRandomStep.first][adjacentRandomStep.second] == ']' ||
//            oneStepDirection == Utils.Direction.LEFT && warehouse[adjacentRandomStep.first][adjacentRandomStep.second] == '[') {
//            mutableListOf.add(getNewPosition(oneStepDirection, adjacentRandomStep))
//        }
    }
    return mutableListOf
}
fun getNextDotFromPosition(direction: Utils.Direction, position: Pair<Int,Int>): Pair<Int,Int> {
    var x = -1
    var y = -1
    var newPosition = position
    while (true) {
        newPosition = getNewPosition(direction, newPosition)
        val newPositionElement = warehouse[newPosition.first][newPosition.second]

        if (newPositionElement == '#') {
            break
        } else {
            if (newPositionElement == '.') {
                x = newPosition.first
                y = newPosition.second
                break
            }
        }
    }
    return x to y
}
fun getNextDotsFromPosition(
    direction: Utils.Direction,
    position: Pair<Int, Int>,
    visited: MutableSet<Pair<Utils.Direction, Pair<Int, Int>>>,
    visitedColumns: MutableSet<Int>
): List<Pair<Int,Int>> {
    var x = -1
    var y = -1
    var newPosition = position
    visitedColumns.add(newPosition.second)
    var previousPosition = position
    var dots = mutableListOf<Pair<Int,Int>>()
    while(true) {
        val newPositionElement = warehouse[newPosition.first][newPosition.second]

        if (newPositionElement == '#') {
            break
        } else {
            if(newPositionElement == '.') {
                x = newPosition.first
                y = newPosition.second
                break
            } else if (previousPosition != newPosition) {
                val previousPositionElement = warehouse[previousPosition.first][previousPosition.second]
                getAdjacentStepIfPushingDiagonally(direction, newPositionElement, newPosition, visitedColumns)?.let{

                    for(adjacent in it) {
                        if(visited.contains(Pair(direction,adjacent))) {
                            continue
                        }
                        val nextDotsFromPosition = getNextDotsFromPosition(direction, adjacent, visited, visitedColumns)
                        if(nextDotsFromPosition.any { dot -> dot.first == -1}) {
                            return listOf(Pair(-1,-1))
                        }
                        dots.addAll(nextDotsFromPosition)
                    }
                }

            }
        }
        previousPosition = newPosition
        visited.add(Pair(direction, previousPosition))
        newPosition = getNewPosition(direction, newPosition)
    }
    dots.add(Pair(x,y))
    return dots
}
