import java.util.*
import kotlin.math.pow


var A = 0
var B = 0
var C = 0
fun main() {
    var currentResultPairsToPrintCurrentDigit = mutableSetOf<Pair<Int, Int>>()

    var currentDigi = 0
    when (currentDigi) {
        0, 1,2 ->  {
            getResultPairsForNumber(currentResultPairsToPrintCurrentDigit, currentDigi)
        }
        4, 5, 6, 7 -> getResultFromRegisters(currentDigi, currentResultPairsToPrintCurrentDigit)
    }
    val mapForAConversionByAdv = mutableMapOf<Int, MutableList<Pair<Int,Int>>>()
    val modulosNeededStack = Stack<Int>()
    // TODO for each found % 8 add in the stack. the for each int in the stack generate possible pair of instruction and A to generate it with
    for(a in 1..1000) {
        A = a
        for (i in 1..7) {
            val oldA = getOldAtoMakeOtherRegisterEqualsNumber(a, i)

            println("$a = $oldA adv with $i")
            val integers = mapForAConversionByAdv[a]
            if (integers != null) {
                integers.add(Pair(oldA, i))
            } else {
                mapForAConversionByAdv[a] = mutableListOf(Pair(oldA, i))
            }
        }
    }
    val xorMap = mutableMapOf<Pair<Int, Int>, Int>()

    val max = 1000
    for (i in 0..max) {
        for (j in 0..max) {
            xorMap[Pair(i, j)] = i xor j
        }
    }
}

private fun getResultPairsForNumber(resultsPairs: MutableSet<Pair<Int, Int>>, i: Int) {
    resultsPairs.add(Pair(5, i))
    getResultFromRegisters(i, resultsPairs)
}

private fun getResultFromRegisters(i: Int, resultsPairs: MutableSet<Pair<Int, Int>>) {
    if (A % 8 == i) {
        resultsPairs.add(Pair(5, 4))
    }
    if (B % 8 == i) {
        resultsPairs.add(Pair(5, 5))
    }
    if (C % 8 == i) {
        resultsPairs.add(Pair(5, 6))
    }
}
fun getOldAtoMakeOtherRegisterEqualsNumber(otherReg: Int , operand: Int): Int {
    //return sqrt(A.div(otherReg).toDouble()).toInt()
    return otherReg.times(((2.0.pow(operand.toDouble())).toInt()))
}

fun getADivByPowedByTwo(int: Int): Int {
    return A.div((2.0.pow(int.toDouble())).toInt())
}