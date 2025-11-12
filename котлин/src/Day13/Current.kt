import java.util.*
import kotlin.math.cos
import kotlin.math.min

//
//val startLongs = getStartInfo();
data class PrizeSearch (
    val aMoveValues: Pair<Long, Long>,
    val bMoveValues: Pair<Long, Long>,
    val prizeValue: Pair<Long, Long>

) {
    var currentValue: Pair<Long, Long> = Pair(0L, 0L)
    var currentCost = 0L
    var numB = 0L
    var numA = 0L

    fun calc() {
        this.calculateCurrentValue(numA, numB)
    }
     fun calculateCurrentValue(numberOfAButton: Long, numberOfBButton: Long) {
        val x = numberOfAButton * aMoveValues.first + numberOfBButton * bMoveValues.first
         val y = numberOfAButton * aMoveValues.second + numberOfBButton * bMoveValues.second
        this.currentCost = numberOfAButton * 3 + numberOfBButton * 1
        this.currentValue = Pair(x,y)
    }
    fun calculateNumb(): Long {

        numB =
            aMoveValues.second * prizeValue.first - aMoveValues.first * prizeValue.second / (aMoveValues.second * bMoveValues.first - aMoveValues.first * bMoveValues.second)
        calculateNumA()
        return numB
    }

    fun calculateNumA() {
        numA = (prizeValue.first - numB* bMoveValues.first)
    }




    var hasReachedPrize: Boolean = isPrizeReached()

    fun isPrizeReached(): Boolean {
        return this.currentValue == prizeValue;
    }
    fun isLessThanPrize(): Boolean {
        return currentValue.first <= prizeValue.first && currentValue.second <= prizeValue.second
    }

}

fun calculateMinimumCost(prizeSearch: PrizeSearch): Long {
    val (aX, aY) = prizeSearch.aMoveValues
    val (bX, bY) = prizeSearch.bMoveValues
    val (pX, pY) = Pair(prizeSearch.prizeValue.first , prizeSearch.prizeValue.second )

    val denominator = aY * bX - aX * bY
    if (denominator == 0L) return Long.MAX_VALUE

    val numB = (aY * pX - aX * pY)
    if (numB % denominator != 0L) return Long.MAX_VALUE
    val bPresses = numB / denominator
    if (bPresses < 0) return Long.MAX_VALUE


    val numA = (pX - bPresses * bX)
    if (numA % aX != 0L) return Long.MAX_VALUE
    val aPresses = numA / aX
    if (aPresses < 0) return Long.MAX_VALUE

    return aPresses * 3 + bPresses * 1
}

private var general = 0L
fun main() {

    var mutableList = getStartObjects()
    var cost = 0L
    val memo = mutableMapOf<PrizeSearch, Long>()
    for(prizeSearch in mutableList) {
        val calculateMinimumCost = calculateMinimumCost(prizeSearch)
        if(calculateMinimumCost != Long.MAX_VALUE) {
            cost +=calculateMinimumCost
        }
//        var xNumber = 0L
//            val l = prizeSearch.prizeValue.second - prizeSearch.aMoveValues.second * yNumber
//            if(l % prizeSearch.bMoveValues.first != 0L) {
//                xNumber++;
//                continue;
//            }
//            xNumber =
//                l / prizeSearch.bMoveValues.second
//
//
//            if(xNumber < 0L) {
//                continue
//            }
//            prizeSearch.calculateCurrentValue(xNumber, yNumber)
//            if(prizeSearch.isPrizeReached()) {
//                if(prizeSearch.currentCost < minCost) {
//                    minCost = prizeSearch.currentCost
//                }
//            }
//            xNumber++;
//
//        if(minCost != Long.MAX_VALUE) {
//            cost += minCost
//        }
    }
    println(cost)
}

//private fun dfs(
//    current: PrizeSearch,
//    memo: MutableMap<PrizeSearch, PrizeSearch>
//): MutableList<PrizeSearch> {
//    val mutableListOf = mutableListOf<PrizeSearch>()
//    val addAMove = current.addAMove()
//
//    if(addAMove.isPrizeReached()) {
//        if(addAMove.currentValue == addAMove.prizeValue) {
//            mutableListOf.add(addAMove)
//        }
//        return mutableListOf
//    } else {
//        if (addAMove.isLessThanPrize()) {
//            mutableListOf.addAll(dfs(addAMove, memo))
//        } else {
//            return mutableListOf
//        }
//    }
//    val addBMove = current.addBMove()
//    if(addBMove.isPrizeReached()) {
//
//        if(addBMove.currentValue == addBMove.prizeValue) {
//            mutableListOf.add(addBMove)
//        }
//        return mutableListOf
//    } else {
//        if (addBMove.isLessThanPrize()) {
//            mutableListOf.addAll(dfs(addBMove, memo))
//        } else {
//            return mutableListOf
//        }
//    }
//
//    return mutableListOf
//}

private fun customLogic(current: PrizeSearch): List<Long> {
    val results = mutableListOf<Long>()

    return results
}

private fun exitCondition(current: PrizeSearch): Boolean {
    return current.isPrizeReached()
}

private fun dfs(
    current: Long,
    count: Int,
    memo: MutableMap<Pair<Long, Int>, Long>
): Long {
    if (count == 75) return 1L

    val key = current to count
    if (key in memo) return memo[key]!!

    var result = 0L
    if (current == 0L) {
        result += dfs(1, count+1, memo);
    } else {
        val toString = current.toString()
        if (toString.length % 2 == 0) {
            result +=dfs(toString.substring(0, toString.length / 2).toLong(), count + 1, memo)
            result += dfs(toString.substring(toString.length / 2, toString.length).toLong(), count + 1, memo)
        } else {
            result +=dfs(current * 2024L, count + 1, memo)
        }
    }

    memo[key] = result
    return result
}
private const val INPUT = "Current.txt"
private const val TEST_INPUT = "CurrentTest.txt"

private fun getStartCharArrayWithVisited(): Array<MutableList<Pair<Char, Boolean>>> {
    val start: MutableList<MutableList<Pair<Char, Boolean>>> = mutableListOf()
    readFile(INPUT)?.forEachLine {
        start.add(it.toCharArray().map { c -> Pair(c, false) }.toMutableList())
    }
    return start.toTypedArray()

}

private fun getStartCharArray(): Array<CharArray> {
    val start: MutableList<CharArray> = mutableListOf()
    readFile(TEST_INPUT)?.forEachLine {
        start.add(it.toCharArray())
    }
    return start.toTypedArray()

}

private fun getStartInfo(): List<Long> {
    var start: List<Long> = emptyList()
    readFile(TEST_INPUT)?.forEachLine {
        start = it.split(" ").map { c -> c.toLong() }
    }
    return start
}

fun getStartObjects(): List<PrizeSearch> {
    var start: MutableList<PrizeSearch> = mutableListOf()
    var aX: Long = 0L
    var aY: Long = 0L
    var bX: Long = 0L
    var bY: Long = 0L
    var pX: Long = 0L
    var pY: Long = 0L
    readFile(INPUT)?.forEachLine {
        if(it.startsWith("Button A: X+")) {
            val substringAfter = it.substringAfter("Button A: X+")
            val (xString, yString) = substringAfter.split(", Y+")
            aX = xString.toLong()
            aY = yString.toLong()
        } else if (it.startsWith("Button B: X+")) {

            val substringAfter = it.substringAfter("Button B: X+")
            val (xString, yString) = substringAfter.split(", Y+")
            bX = xString.toLong()
            bY = yString.toLong()
        } else if(it.startsWith("Prize: X=")){

            val substringAfter = it.substringAfter("Prize: X=")
            val (xString, yString) = substringAfter.split(", Y=")
            pX = xString.toLong() + 10000000000000L
            pY = yString.toLong() + 10000000000000L
            start.add(PrizeSearch(Pair(aX,aY), Pair(bX,bY),Pair(pX,pY)))

            aX = 0L
            aY= 0L
            bX = 0L
            bY= 0L
            pX = 0L
            pY= 0L
        }

    }
    return start
}


fun readFile(fileName: String) = object {}.javaClass.getResourceAsStream(fileName)?.reader()

