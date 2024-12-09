import kotlin.math.abs

fun main() {
    val directions = mapOf(
        "R" to Pair(0, 1),   // right
        "L" to Pair(0, -1),  // left
        "D" to Pair(1, 0),   // down
        "U" to Pair(-1, 0)   // up
    )

    val headSteps = mutableListOf<Pair<String, Int>>()

    readFile("Day9.txt")?.forEachLine { line ->
        val split = line.split(" ")
        headSteps.add(Pair(split[0],split[1].toInt()))
    }
    val rope = MutableList(10) { Pair(0, 0) }
    val tailPositions = mutableSetOf(Pair(0, 0))
    for ((direction, steps) in headSteps) {
        val (dx, dy) = directions[direction]!!
        repeat(steps) {
            // Move head
            rope[0] = Pair(rope[0].first + dx, rope[0].second + dy)

            // Update subsequent knots
            for (i in 1..<rope.size) {
                val prev = rope[i-1]
                val curr = rope[i]
                val dx2 = prev.first - curr.first
                val dy2 = prev.second - curr.second

                // Check if knots are not touching
                if (abs(dx2) > 1 || abs(dy2) > 1) {
                    val newX = when {
                        dx2 > 0 -> curr.first + 1
                        dx2 < 0 -> curr.first - 1
                        else -> curr.first
                    }

                    val newY = when {
                        dy2 > 0 -> curr.second + 1
                        dy2 < 0 -> curr.second - 1
                        else -> curr.second
                    }
                    rope[i] = Pair(newX, newY)
                }
            }

            tailPositions.add(rope.last())
        }
    }
    println("Unique tail positions: ${tailPositions.size}")
//    var head = Pair(0,0)
//    var tails = mutableListOf(head, head, head, head, head, head, head, head, head);
//    var tailSteps = mutableSetOf<Pair<Int,Int>>()
//    tailSteps.add(tails[8])
//    var tailXmove = 0;
//    var tailYmove = 0;
//    for(pair in headSteps) {
//        val directionName = pair.first
//        val numberOfSteps = pair.second
//        val headStepDirection = directions[directionName]!!
//        for(step in 0.rangeUntil(numberOfSteps)) {
//            head = Pair(head.first + headStepDirection.first, head.second + headStepDirection.second)
//            var leadRope = head;
//            for(i in 0..8) {
//                if (abs(leadRope.first - tails[i].first) == 2) {
//                    tailXmove += if(leadRope.first - tails[i].first < 0) -1 else 1
//                    tailYmove += if(leadRope.second - tails[i].second < 0) -1 else 1
//                }
//                if (abs(leadRope.second - tails[i].second) == 2) {
//                    tailXmove += if(leadRope.first - tails[i].first < 0) -1 else 1
//                    tailYmove += if(leadRope.second - tails[i].second < 0) -1 else 1
//                }
//
//                if (tailXmove != 0 || tailYmove != 0) {
//                    tails[i] = Pair(tails[i].first + tailXmove, tails[i].second + tailYmove)
//                    if(i == 8) {
//                        tailSteps.add(tails[i])
//                    }
//                    tailXmove = 0
//                    tailYmove = 0
//                }
//                leadRope = tails[i]
//            }
//        }
//    }
//
//    println(tailSteps.size)
}
