import utils.Utils


val handledPairs = mutableListOf<Pair<Int,Int>>()
val pairsToBeHandled = mutableListOf<Pair<Pair<Int,Int>, Boolean>>()
fun main() {
    val moves = getMoves()
    var time = 0
    warehouse = getWareHouse()
    val rollback = warehouse.toList()
    for ((index, move) in moves.withIndex()) {

        handledPairs.clear()
        if(index == 278) {
            printWarehouse()
        }
        val direction = MoveDirectionMap[move]
        val newPosition = direction?.let { getNewPosition(it, robot.robotPosition) }
        val startPositions = mutableListOf<Pair<Pair<Int,Int>, Boolean>>()
        var isOk = true
        if (newPosition != null) {
            val newPositionElement = warehouse[newPosition.first][newPosition.second]
            if (newPositionElement != '#') {
                if (newPositionElement == '.') {
                    warehouse[robot.robotPosition.first][robot.robotPosition.second] = '.'
                    robot.setRobotPositionAndValidate(newPosition)
                    warehouse[newPosition.first][newPosition.second] = '@'
                } else {

                    startPositions.add(Pair(robot.robotPosition, false))
                    var adjacentToRobotDot: Pair<Int,Int>? = null
                    if(direction == Utils.Direction.UP || direction == Utils.Direction.DOWN) {
                        var oneStepDirection = Utils.Direction.LEFT
                        if(warehouse[newPosition.first][newPosition.second] == '[') {
                            oneStepDirection = Utils.Direction.RIGHT
                        }
                        adjacentToRobotDot = getNewPosition(oneStepDirection, robot.robotPosition)
                        startPositions.add(Pair(adjacentToRobotDot, true))
                        handledPairs
                    }

                    for(startPosition in startPositions) {

                        isOk = push(direction, startPosition)
                        if(!isOk) {
                            break
                        }
                    }
                    if(isOk) {
                        while (pairsToBeHandled.isNotEmpty()) {
                            if(!isnotok() && pairsToBeHandled.size == 1) {
                                pairsToBeHandled.removeFirst()
                                break
                            }
                            val startPositionPair = pairsToBeHandled.removeFirst()
                            isOk = push(direction, startPositionPair)
                            if(!isOk) {
                                break
                            }
                        }
                    }
                    if(!isOk) {
                        warehouse = rollback.toMutableList()
                        continue
                    }
                }
            } else {
                continue
            }
            printWarehouse()
            if(isnotok()) {
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

private fun isnotok() =
    warehouse.any {
        it.toString().contains("[, .") || it.toString().contains("., ]") || it.toString().contains("[, [")
                || it.toString().contains("], ]")
    }

fun push(
    direction: Utils.Direction,
    startPositionPair: Pair<Pair<Int, Int>, Boolean>
): Boolean {
    val startPosition = startPositionPair.first
    val nextDot = getNextDotFromPosition(direction, startPosition)
    if(nextDot.first == -1 || handledPairs.contains(nextDot)) {
        return false
    }
    handledPairs.add(nextDot)
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
            getAdjacentStepIfPushingDiagonally(direction, nextElement, nextStep)?.let { pairs ->
                shouldPushDiagonally = true
                for(adjacent in pairs) {
                    val element = getNextDotFromPosition(direction, adjacent)
                    if(handledPairs.none {element.second == it.second && element.first < it.first} && pairsToBeHandled.none { it.first.second == element.second}) {
                        pairsToBeHandled.add(Pair(adjacent, false))
                    }
                }
            }
        }
        warehouse[nextStep.first][nextStep.second] = currentElement

    }
    pairsToBeHandled.remove(startPositionPair)
    return true
}

fun getAdjacentStepIfPushingDiagonally(
    originalDirection: Utils.Direction,
    nextElement: Char,
    position: Pair<Int, Int>
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
    visited: MutableSet<Pair<Utils.Direction, Pair<Int, Int>>>
): List<Pair<Int,Int>> {
    var x = -1
    var y = -1
    var newPosition = position
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
                getAdjacentStepIfPushingDiagonally(direction, newPositionElement, newPosition)?.let{

                    for(adjacent in it) {
                        val nextDotsFromPosition = getNextDotsFromPosition(direction, adjacent, visited).filter {  !visited.contains(Pair(direction, it)) }
                        if(nextDotsFromPosition.isEmpty() || nextDotsFromPosition.any { dot -> dot.first == -1}) {
                            return listOf(Pair(-1,-1))
                        }

                        dots.addAll(nextDotsFromPosition)
                        nextDotsFromPosition.forEach { visited.add(Pair(direction, it))}
                    }
                }

            }
        }
        previousPosition = newPosition
        visited.add(Pair(direction, getNextDotFromPosition(direction, previousPosition)))
        newPosition = getNewPosition(direction, newPosition)
    }
    dots.add(Pair(x,y))
    return dots
}
