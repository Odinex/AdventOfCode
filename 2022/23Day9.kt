import kotlin.math.abs

fun main() {
    var sum = 0
    val directions = mapOf(
        Pair("R", Pair(0, 1)),   // right
        Pair("L", Pair(0, -1)),  // left
        Pair("D", Pair(1, 0)),   // down
        Pair("U", Pair(-1, 0)),  // up
        Pair("DR", Pair(1, 1)),   // down-right
        Pair("DL", Pair(1, -1)),  // down-left
        Pair("UR", Pair(-1, 1)),  // up-right
        Pair("UL", Pair(-1, -1))  // up-left
    )

    val headSteps = mutableListOf<Pair<String, Int>>()

    readFile("Day9.txt")?.forEachLine { line ->
        val split = line.split(" ")
        headSteps.add(Pair(split[0],split[1].toInt()))
    }
    var head = Pair(0,0)
    var tail = head;
    var tailSteps = mutableSetOf<Pair<Int,Int>>()
    tailSteps.add(tail)
    var tailXmove = 0;
    var tailYmove = 0;
    for(pair in headSteps) {
        val directionName = pair.first
        val numberOfSteps = pair.second
        val headStepDirection = directions[directionName]!!
        for(step in 0.rangeUntil(numberOfSteps)) {
            head = Pair(head.first + headStepDirection.first, head.second + headStepDirection.second)
            if(abs(head.first - tail.first) == 2) {
                tailXmove += headStepDirection.first
                tailYmove = head.second - tail.second
            }
            if(abs(head.second - tail.second) == 2) {
                tailYmove += headStepDirection.second
                tailXmove = head.first - tail.first
            }

            if(tailXmove != 0 || tailYmove != 0) {
                tail = Pair(tail.first + tailXmove, tail.second + tailYmove)
                tailSteps.add(tail)
                tailXmove = 0
                tailYmove = 0
            }
        }
    }

    println(tailSteps.size)
}
