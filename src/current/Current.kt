var A: Int = 0
var B: Int = 0
var C: Int = 0

class ThreeBit {
    private var value: Int = 0
        get() = field
        set(value) {
            if(value in 0..7) {
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
        return combo*combo
    }
    fun getLong(): Long {
        return value.toLong()
    }
    fun get() = value
    fun getCombo(): Int {
        if(value % 8 in 0..3) {
            return 1 shl value
        } else if(value % 8 == 4) {
            return A
        } else if(value % 8 == 5) {
            return B
        } else if(value % 8 == 6) {
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

private fun divAByComboPowed(operand: ThreeBit) = A.div( operand.getPowed())
fun xor(int: Int, other: Int): Int {
    return int.toLong().xor(other.toLong()).toInt()
}
fun bxl(operand: ThreeBit) {
    B = xor(B,operand.get())
}
fun bst(operand: ThreeBit) {
    B = operand.getCombo() % 8
}
var skipJump = false
fun jnz(operand: ThreeBit) {
    if(A != 0) {
        currentIndex = operand.get()
        shouldSkipJump = true
    }
}
fun bxc() {
    B = xor(B, C)
}
fun out(operand: ThreeBit) {
    if(output.isNotBlank()) {
        output.append(',')
    }
    val i = operand.getCombo() % 8
    output.append(i)
}
var output = StringBuilder()
var shouldSkipJump = false
fun readInstruction() {
    while(currentIndex < instructions.size) {
        val opCode = instructions[currentIndex]
        val operand = instructions[currentIndex + 1]
        execute(opCode, operand)
        if(opCode.get() != 3) {
            shouldSkipJump = false
        }
        if (!shouldSkipJump) {
            jumb()
        }
    }
    println(output.toString())
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

fun main() {
//    A = 60589763
//    instructions.addAll(listOf(2,4,1,5,7,5,1,6,4,1,5,5,0,3,3,0).map { ThreeBit(it)})
    B = 2024
    C = 43690
    instructions.addAll(listOf(4,0).map { ThreeBit(it)})
    readInstruction()

}