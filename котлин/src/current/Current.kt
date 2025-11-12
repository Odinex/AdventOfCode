import utils.Utils
import kotlin.math.abs


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
data class Robot(var robotPosition: Pair<Int, Int> = Pair(-1, -1)) {
    fun setRobotPositionAndValidate(nextStep: Pair<Int, Int>) {
        if(abs(robotPosition.first - nextStep.first) > 1 && abs(robotPosition.second - nextStep.second) > 1) {
            throw Exception("WTF")
        }else {
            robotPosition = nextStep
        }

    }
}

var height = 0;
var warehouse = getWareHouse()

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
                    printWarehouse()
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
fun printWarehouse() {
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
var robot = Robot(Pair(-1, -1))
var robotPosition = Pair(-1,-1)
fun getWareHouse(): MutableList<MutableList<Char>> {
    var start: MutableList<MutableList<Char>> = mutableListOf()
    var currentLine = 0
    readFile(INPUT)?.forEachLine {
        start.add(it.toMutableList())
        val indexOf = it.indexOf('@')
        if (indexOf != -1) {
            robotPosition = Pair(currentLine, indexOf)
            robot = Robot(robotPosition)
        }
        currentLine++
    }
    height = currentLine;
    val expandedWarehouse = mutableListOf<MutableList<Char>>()
    for ((index, row) in start.withIndex()) {
        val expandedRow = mutableListOf<Char>()
        for (c in row) {
            when (c) {
                '#' -> expandedRow.addAll(listOf('#', '#'))
                'O' -> expandedRow.addAll(listOf('[', ']'))
                '.' -> expandedRow.addAll(listOf('.', '.'))
                '@' -> expandedRow.addAll(listOf('@', '.'))
            }
        }
        expandedWarehouse.add(expandedRow)
        val indexOf = expandedRow.indexOf('@')
        if (indexOf != -1) {
            robotPosition = Pair(index, indexOf)
            robot = Robot(robotPosition)
        }
    }

    return expandedWarehouse
}

fun getMoves(): List<Move> {
    var start: MutableList<Move> = mutableListOf()
    readFile(INPUT2)?.forEachLine {
        it.forEach { c -> Move.valueOf(c)?.let { move -> start.add(move) } }
    }
    return start
}


const val INPUT = "Current.txt"
const val INPUT2 = "Current2.txt"
const val TEST_INPUT = "CurrentTest.txt"
const val TEST_INPUT2 = "CurrentTest2.txt"
fun readFile(fileName: String) = object {}.javaClass.getResourceAsStream(fileName)?.reader()

