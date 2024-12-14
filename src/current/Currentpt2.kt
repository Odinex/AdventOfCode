//import kotlin.math.abs
//
//fun main() {
//    var mutableList = getStartObjects()
//    var cost = 0L
//    val memo = mutableMapOf<PrizeSearch, Long>()
//    for(prizeSearch in mutableList) {
//        val solveCMProblem = solveCMProblem(
//            prizeSearch.aMoveValues.first,
//            prizeSearch.aMoveValues.second,
//            prizeSearch.bMoveValues.first,
//            prizeSearch.bMoveValues.second,
//            prizeSearch.prizeValue.first,
//            prizeSearch.prizeValue.second
//        )
//        if(solveCMProblem != -1L) {
//            cost += solveCMProblem
//        }
//    }
//    println(cost)
//}
//
//fun calculateTokenCost(aCount: Long, bCount: Long): Long {
//    return aCount * 3 + bCount * 1
//}
//
//fun solve(a: Long, b: Long, c: Long): Pair<Long, Long>? {
//
//    val (gcd, x, y) = canBeDivided(abs(a), abs(b))
//
//
//    if (c % gcd != 0L) return null
//
//
//    val scaledX = x * (c / gcd)
//    val scaledY = y * (c / gcd)
//
//
//    val signA = if (a < 0) -1 else 1
//    val signB = if (b < 0) -1 else 1
//
//    return Pair(scaledX * signA, scaledY * signB)
//}
//
//fun canBeDivided(a: Long, b: Long): Triple<Long, Long, Long> {
//    if (b == 0L) return Triple(
//        if (a > 0) a else -a,
//        if (a > 0) 1L else -1L,
//        0L
//    )
//
//    val (gcd, x, y) = canBeDivided(b, a % b)
//    return Triple(gcd, y, x - (a / b) * y)
//}
//
//fun solveCMProblem(ax: Long, ay: Long, bx: Long, by: Long, targetX: Long, targetY: Long): Long {
//    val xSolution = solve(ax, bx, targetX)
//    val ySolution = solve(ay, by, targetY)
//
//    if (xSolution == null || ySolution == null) return -1L
//
//    var minTokens = Long.MAX_VALUE
//
//    // We can adjust solutions by adding/subtracting multiples of the other axis
//    for (k in -1000..1000) {
//        val testX = xSolution.first + k * (bx / canBeDivided(abs(ax), abs(bx)).first)
//        for (j in -1000..1000) {
//            val testY = ySolution.first + j * (by / canBeDivided(abs(ay), abs(by)).first)
//
//            if (testX * ax + testY * bx == targetX &&
//                testX * ay + testY * by == targetY) {
//                val tokens = calculateTokenCost(testX, testY)
//                minTokens = minOf(minTokens, tokens)
//            }
//        }
//    }
//
//    return if (minTokens == Long.MAX_VALUE) -1L else minTokens
//}