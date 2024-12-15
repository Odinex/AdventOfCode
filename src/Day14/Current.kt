
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
            position.first < midWidth && position.second < midHeight -> 1
            position.first > midWidth && position.second < midHeight -> 2
            position.first < midWidth && position.second > midHeight -> 3
            position.first > midWidth && position.second > midHeight -> 4
            else -> -1
        }
    }
}
fun findTreeFrame(robots: List<Robot>): Pair<Int, Int>? {
    val positions = robots.map { it.position }.toSet()

    // Calculate bounds
    val minX = positions.minOf { it.first }
    val maxX = positions.maxOf { it.first }
    val minY = positions.minOf { it.second }
    val maxY = positions.maxOf { it.second }

    for (y in minY..maxY) {
        for (x in minX..maxX) {
            if (isTree(positions, x, y)) {
                println("Tree detected starting at ($x, $y)")
                return Pair(x, y)
            }
        }
    }
    return null
}

fun isTree(positions: Set<Pair<Int, Int>>, startX: Int, startY: Int): Boolean {

    val treeHeight = 7
    val trunkHeight = 2
    val trunkWidth = 1


    for (dy in 0..<
            treeHeight) {
        val rowWidth = treeHeight - dy
        for (dx in -rowWidth..rowWidth) {
            if (!positions.contains(Pair(startX + dx, startY + dy))) {
                return false
            }
        }
    }


    for (dy in treeHeight until treeHeight + trunkHeight) {
        for (dx in -trunkWidth..trunkWidth) {
            if (!positions.contains(Pair(startX + dx, startY + dy))) {
                return false
            }
        }
    }

    return true
}

fun main() {
    val robots = getStartObjects()
    var time = 0

    while (true) {
        val treeFrame = findTreeFrame(robots)
        if (treeFrame != null) {
            println("Tree detected at time: $time")
            printRobotPositions(robots)
            repeat(10) {
                robots.forEach { it.move() }
                printRobotPositions(robots)

            }
            break
        }
        robots.forEach { it.move() }

        time++
    }
}


fun findLongestColumn(robots: List<Robot>): Int? {
    val positions = robots.map { it.position }
    val columnCounts = mutableMapOf<Int, MutableList<Int>>()

    for (position in positions) {
        val x = position.first
        val y = position.second

        if (!columnCounts.containsKey(x)) {
            columnCounts[x] = mutableListOf()
        }
        columnCounts[x]!!.add(y)
    }

    val sortedBy = columnCounts.entries.sortedBy { it.value.size }
    var maxColumn: Int? = null
    var maxSize = 0
    for((column, columnValues) in sortedBy) {
        var isContinuous = true
        for (i in 0 until columnValues.size - 1) {
            if (columnValues[i + 1] != columnValues[i] + 1) {
                isContinuous = false
                break
            }
        }
        if (isContinuous && columnValues.size > maxSize) {
            maxSize = columnValues.size
            maxColumn = column
        }

    }
    return maxColumn
}

fun isChristmasTreeWithTrunk(robots: List<Robot>, trunkColumn: Int): Boolean {
    val positions = robots.map { it.position }.toSet()


    val trunkYs = positions.filter { it.first == trunkColumn }.map { it.second }
    if (trunkYs.isEmpty() || trunkYs.size < 4) return false

    printRobotPositions(robots)

    return true
}

fun printRobotPositions(robots: List<Robot>) {
    val positions = robots.map { it.position }.toSet()
    val minX = robots.minOf { it.position.first }
    val maxX = robots.maxOf { it.position.first }
    val minY = robots.minOf { it.position.second }
    val maxY = robots.maxOf { it.position.second }

    for (y in minY..maxY) {
        for (x in minX..maxX) {
            if (positions.contains(Pair(x, y))) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
}

private fun pt1(mutableList: List<Robot>) {
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

