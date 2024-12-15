val warehouse2 =getWareHouse2()
fun main() {
    val moves = getMoves()
    var time = 0
    for (move in moves) {
        val direction = MoveDirectionMap[move]
        val newPosition = direction?.let { getNewPosition(it, robotPosition) }
        if (newPosition != null && newPosition.first >= 0 && newPosition.first < warehouse2.size
            && newPosition.second >= 0 && newPosition.second < warehouse2[newPosition.first].size) {
            val newPositionElement = warehouse2[newPosition.first][newPosition.second]
            if (newPositionElement != "##") {
                if (newPositionElement == "..") {
                    warehouse2[robotPosition.first][robotPosition.second] = ".."
                    robotPosition = newPosition
                    warehouse2[newPosition.first][newPosition.second] = "@."
                } else {
                    val nextDot = getNextDot(direction)
                    if (nextDot.first != -1) {
                        var nextStep = newPosition
                        warehouse2[robotPosition.first][robotPosition.second] = ".."
                        robotPosition = nextStep
                        warehouse2[nextStep.first][nextStep.second] = "@."
                        warehouse2[nextDot.first][nextDot.second] = "[]"
                    }
                }
            } else {
                continue
            }
        }
    }
    var sum =0
    for(x in 0..<warehouse2.size) {
        for(y in 0..<warehouse2[x].size) {
            if(warehouse2[x][y] == "[]") {
                sum += 100 *x + y
            }
        }
    }
    println(sum)

}

fun getWareHouse2(): MutableList<MutableList<String>> {
    var start: MutableList<MutableList<String>> = mutableListOf()
    var currentLine = 0
    readFile(TEST_INPUT)?.forEachLine {
        var startIndex = 0
        var endIndex = 2
        val line = mutableListOf<String>()
        var column = 0
        while(startIndex< it.length) {

            val element = it.substring(startIndex, endIndex)
            line.add(element)
            if(element.equals("@.")) {
                robotPosition = Pair(currentLine, column)
            }
            startIndex +=2
            endIndex +=2
            column++
        }
        currentLine++
        start.add(line)
    }
    height = currentLine;
    return start
}