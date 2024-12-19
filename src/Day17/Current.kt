import kotlin.math.pow
import kotlin.math.sqrt

var A: Int = 0
var B: Int = 0
var C: Int = 0

class ThreeBit {
    private var value: Int = 0
        get() = field
        set(value) {
            if (value in 0..7) {
                field = value
            } else {
                throw Exception("Invalid value")
            }
        }

    constructor(value: Int) {
        this.value = value
    }

    fun getPowed(): Int {
        val combo = getCombo()
        if (combo == 0) {
            return 2
        }
        return 2.0.pow(combo.toDouble()).toInt()
    }

    fun getLong(): Long {
        return value.toLong()
    }

    fun get() = value
    fun getCombo(): Int {
        if (value % 8 in 0..3) {
            return value
        } else if (value % 8 == 4) {
            return A
        } else if (value % 8 == 5) {
            return B
        } else if (value % 8 == 6) {
            return C
        } else {
            throw Exception("WTF")
        }
    }
}

var currentIndex = 0;
val instructions = ArrayList<ThreeBit>(8)
fun jumb() {
    currentIndex += 2
}

fun adv(operand: ThreeBit) {
    A = divAByComboPowed(operand)
}

private fun divAByComboPowed(operand: ThreeBit) = A.div(operand.getPowed())
private fun getOperandByMultiplyingAandSqrt(resultA: Int) = sqrt((A / resultA).toDouble()).toInt()
fun xor(int: Int, other: Int): Int {
    return int.toLong().xor(other.toLong()).toInt()
}

fun bxl(operand: ThreeBit) {
    B = xor(B, operand.get())
}

fun bst(operand: ThreeBit) {
    B = operand.getCombo() % 8
}

var skipJump = false
fun jnz(operand: ThreeBit) {
    if (A != 0) {
        currentIndex = operand.get()
        shouldSkipJump = true
    }
}

fun bxc() {
    B = xor(B, C)
}

fun out(operand: ThreeBit) {
    if (output.isNotBlank()) {
        output.append(',')
    }
    val i = operand.getCombo() % 8
    output.append(i)
    mutableList.add(i)
}

var output = StringBuilder()

var shouldSkipJump = false
fun readInstruction(instructions: List<ThreeBit>): Boolean {
    while (currentIndex < instructions.size) {
        val opCode = instructions[currentIndex]
        val operand = instructions[currentIndex + 1]
        execute(opCode, operand)
        if (opCode.get() != 3) {
            shouldSkipJump = false
        }
//        for (i in mutableList.indices) {
//            if (mutableList[i] != goal[i]) {
//                return false
//            }
//        }
        if (!shouldSkipJump) {
            jumb()
        }
    }
    println(output.toString())
    return true;
}

private fun execute(opCode: ThreeBit, operand: ThreeBit) {
    when (opCode.get()) {
        0 -> adv(operand)
        1 -> bxl(operand)
        2 -> bst(operand)
        3 -> jnz(operand)
        4 -> bxc()
        5 -> out(operand)
        6 -> bdv(operand)
        7 -> cdv(operand)
    }
}

fun bdv(operand: ThreeBit) {
    B = divAByComboPowed(operand)
}

fun cdv(operand: ThreeBit) {
    C = divAByComboPowed(operand)
}

var goal = listOf<Int>()
val instr = listOf(7,5).map { ThreeBit(it) }
val mutableList = mutableListOf<Int>()
fun main() {
    instructions.addAll(instr)
    val numberPairs = mutableListOf<MutableList<Int>>()
    numberPairs.add(mutableListOf())
    A = 546
    B = 0
    C = 0
    val readInstruction = readInstruction(instr)


//    for (i in 1.rangeTo(2)) {
//        val newResults = mutableListOf<MutableList<Int>>()
//
//        numberPairs.forEach { numbers ->
//            newResults.add((numbers + 0).toMutableList())
//            newResults.add((numbers + 1).toMutableList())
//            newResults.add((numbers + 2).toMutableList())
//            newResults.add((numbers + 3).toMutableList())
//            newResults.add((numbers + 4).toMutableList())
//            newResults.add((numbers + 5).toMutableList())
//            newResults.add((numbers + 6).toMutableList())
//            newResults.add((numbers + 7).toMutableList())
//        }
//        numberPairs.clear()
//        numberPairs.addAll(newResults)
//    }

//    for (i in 0..<numberPairs.size - 1) {
//        for (j in 1..<numberPairs.size) {
//            for (z in 1..<numberPairs.size) {
//                val plus = numberPairs[i].plus(numberPairs[j]).plus(numberPairs[z])
//                goal = plus
//                val instructions = plus.map { ThreeBit(it) }
//                for (a in 0..21474836) {
//
//                    mutableList.clear()
//                    output.clear()
//                }
//            }
//        }
//
//    }
//    val instr = mutableListOf<MutableList<MutableList<Int>>>()
//    instr.add(mutableListOf())
//
//    for (i in 1.rangeTo(7)) {
//        val newResults = mutableListOf<MutableList<MutableList<Int>>>()
//        instr.forEach { list: MutableList<MutableList<Int>> ->
//            numberPairs.forEach { numbers: MutableList<Int> ->
//                val newList = mutableListOf(numbers)
//                newList.addAll(list)
//                newResults.add(newList)
//            }
//        }
//        instr.clear()
//        instr.addAll(newResults)
//    }
//    for(instructionsList in instr) {
//        val instructions = mutableListOf<ThreeBit>()
//        instructionsList.forEach { list34 -> instructions.addAll(list34.map { ThreeBit(it)})}

//    B = 2024
//    C = 43690
//    instructions.addAll(listOf(4,0).map { ThreeBit(it)})
//    A = 729
//    instructions.addAll(listOf(0,1,5,4,3,0).map { ThreeBit(it)})
//        A = 117440
//    instructions.addAll(listOf(0,3,5,4,3,0).map { ThreeBit(it)})

//        if(broken) {
//            break
//        }
//    }

}