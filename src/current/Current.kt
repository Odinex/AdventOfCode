import utils.Utils


enum class Move(val value: Char) {
    UP('^'), DOWN('v'), LEFT('<'), RIGHT('>');

    companion object {
        fun valueOf(value: Char): Move? {
            return entries.find { it.value == value } ?: null

        }

    }
}

val MoveDirectionMap = mapOf(
    Pair(Move.UP, Utils.Direction.UP), Pair(Move.DOWN, Utils.Direction.DOWN),
    Pair(Move.LEFT, Utils.Direction.LEFT), Pair(Move.RIGHT, Utils.Direction.RIGHT)
)

var robotPosition: Pair<Int, Int> = Pair(-1, -1)
var height = 0;
private val warehouse = getWareHouse()

fun main() {
    val moves = getMoves()
    var time = 0
    for (move in moves) {
        val direction = MoveDirectionMap[move]
        val newPosition = direction?.let { getNewPosition(it, robotPosition) }
        if (newPosition != null) {
            val newPositionElement = warehouse[newPosition.first][newPosition.second]
            if (newPositionElement != '#') {
                if (newPositionElement == '.') {
                    warehouse[robotPosition.first][robotPosition.second] = '.'
                    robotPosition = newPosition
                    warehouse[newPosition.first][newPosition.second] = '@'
                } else {
                    val nextDot = getNextDot(direction)
                    if (nextDot.first != -1) {
                        var nextStep = newPosition
                        warehouse[robotPosition.first][robotPosition.second] = '.'
                        robotPosition = nextStep
                        warehouse[nextStep.first][nextStep.second] = '@'
                        warehouse[nextDot.first][nextDot.second] = 'O'
                    }
                }
            } else {
                continue
            }
        }
    }
    var sum =0
    for(x in 0..<warehouse.size) {
        for(y in 0..<warehouse[x].size) {
            if(warehouse[x][y] == 'O') {
                sum += 100 *x + y
            }
        }
    }
    println(sum)

}
private fun printWarehouse() {
    warehouse.forEach { it.forEach { c -> print(c)}
    println()
    }
}

fun getNextDot(direction: Utils.Direction): Pair<Int,Int> {
    var x = -1
    var y = -1
    var newPosition = robotPosition
    while(true) {
        val newPositionElement = warehouse[newPosition.first][newPosition.second]

        if (newPositionElement == '#') {
            break
        } else {
            if(newPositionElement == '.') {
                x = newPosition.first
                y = newPosition.second
                break
            }
        }
        newPosition = getNewPosition(direction, newPosition)
    }
    return x to y
}

fun getNewPosition(direction: Utils.Direction, position: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(position.first + direction.pair.first, position.second + direction.pair.second)
}


fun getWareHouse(): MutableList<MutableList<Char>> {
    var start: MutableList<MutableList<Char>> = mutableListOf()
    var currentLine = 0
    readFile(INPUT)?.forEachLine {
        start.add(it.toMutableList())
        val indexOf = it.indexOf('@')
        if (indexOf != -1) {
            robotPosition = Pair(currentLine, indexOf)
        }
        currentLine++
    }
    height = currentLine;
    return start
}

fun getMoves(): List<Move> {
    var start: MutableList<Move> = mutableListOf()
    readFile(TEST_INPUT2)?.forEachLine {
        it.forEach { c -> Move.valueOf(c)?.let { move -> start.add(move) } }
    }
    return start
}


const val INPUT = "Current.txt"
const val INPUT2 = "Current2.txt"
const val TEST_INPUT = "CurrentTest.txt"
const val TEST_INPUT2 = "CurrentTest2.txt"
fun readFile(fileName: String) = object {}.javaClass.getResourceAsStream(fileName)?.reader()

