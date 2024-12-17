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
        val visited = mutableSetOf<Pair<Utils.Direction,Pair<Int,Int>>>()
        val startPositions = mutableListOf<Pair<Pair<Int,Int>, Boolean>>()
        if (newPosition != null) {
            val newPositionElement = warehouse[newPosition.first][newPosition.second]
            if (newPositionElement != '#') {
                if (newPositionElement == '.') {
                    warehouse[robot.robotPosition.first][robot.robotPosition.second] = '.'
                    robot.setRobotPositionAndValidate(newPosition)
                    warehouse[newPosition.first][newPosition.second] = '@'
                } else {

                    startPositions.add(Pair(robot.robotPosition, false))
                    val mapStartToFinish = startPositions.associateWith {
                        getNextDotsFromPosition(direction, it.first, visited)
                    }
                    if(mapStartToFinish.isEmpty() || mapStartToFinish.values.any { it.isEmpty() || it.any { dot -> dot.first == -1}}) {
                        continue
                    }
                    for(startPosition in startPositions) {

                        push(direction, startPosition)
                    }
                    while(pairsToBeHandled.isNotEmpty()) {
                        push(direction, pairsToBeHandled.removeFirst())
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

fun getNextDotsFromPositionT2(
    direction: Utils.Direction,
    position: Pair<Int, Int>
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
                getAdjacentStepIfPushingDiagonally(direction, newPositionElement, newPosition)?.let{

                    for(adjacent in it) {
                        val nextDotsFromPosition = getNextDotsFromPositionT2(direction, adjacent)
                        if(nextDotsFromPosition.any { dot -> dot.first == -1}) {
                            return listOf(Pair(-1,-1))
                        }
                        dots.addAll(nextDotsFromPosition)
                    }
                }

            }
        }
        previousPosition = newPosition
        newPosition = getNewPosition(direction, newPosition)
    }
    dots.add(Pair(x,y))
    return dots
}



