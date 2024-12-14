
var width = 101;
var height = 103;

data class Robot(
    var position: Pair<Int, Int>,
    val velocity: Pair<Int, Int>

) {
    fun move() {
        var first = position.first + velocity.first
        var second = position.second + velocity.second
        if (first > width - 1) {
            first -= width
        } else if (first < 0) {
            first += width
        }
        if (second > height - 1) {
            second -= height
        } else if (second < 0) {
            second += height
        }

        position = Pair(first, second)
    }

    fun getQ(): Int {
        val midWidth = width / 2
        val midHeight = height / 2

        return when {
            position.first < midWidth && position.second < midHeight -> 1 // Top-left quadrant
            position.first > midWidth && position.second < midHeight -> 2 // Top-right quadrant
            position.first < midWidth && position.second > midHeight -> 3 // Bottom-left quadrant
            position.first > midWidth && position.second > midHeight -> 4 // Bottom-right quadrant
            else -> -1 // Failsafe (shouldn't happen)
        }
    }
}

fun main() {

    var mutableList = getStartObjects()
    var cost = 0L
    for (i in mutableList) {
        repeat(100) {
            i.move()
        }
    }
    var q1 = 0;
    var q2 = 0;
    var q3 = 0;
    var q4 = 0;
    mutableList.forEach {
        val q = it.getQ()
        if (q != -1) {
            when (q) {
                1 -> {
                    q1++;
                }
                2 -> {
                    q2++;
                }
                3 -> {
                    q3++
                }
                4 -> {
                    q4++
                }
            }
        }
    }

    println(q1 * q2 * q3 * q4)
}


fun getStartObjects(): List<Robot> {
    var start: MutableList<Robot> = mutableListOf()
    readFile(INPUT)?.forEachLine {
        val (pString, vString) = it.split(" ")
        val position = pString.substring(2).split(',').map { c -> c.toInt() }
        val velocity = vString.substring(2).split(',').map { c -> c.toInt() }
        start.add(Robot(Pair(position[0], position[1]), Pair(velocity[0], velocity[1])))
    }
    return start
}



private const val INPUT = "Current.txt"
private const val TEST_INPUT = "CurrentTest.txt"
fun readFile(fileName: String) = object {}.javaClass.getResourceAsStream(fileName)?.reader()

