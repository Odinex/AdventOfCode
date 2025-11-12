import kotlin.math.abs

fun main() {
    var numberOfSafeReports = 0
    readFile("Day2.txt")?.forEachLine {
        val numbers = it.split(" ");
        if(numbers.size > 1 && numbers[0] != numbers[1]) {
            val first = numbers[0].toInt()
            val second = numbers[1].toInt()
            val checkFunction: (Int, Int)-> Boolean = if(first > second) {
                isDecreasing()
            } else {
                isIncreasing()
            }
            var isSafe = !isAbsTooMuch(first, second)
            if(isSafe) {
                for (i in 1..<numbers.size - 1) {
                    val current = numbers[i].toInt()
                    val next = numbers[i + 1].toInt()
                    if (!checkFunction(current, next) || isAbsTooMuch(current, next)) {
                        isSafe = false;
                        break;
                    }
                }
            }
            if(isSafe) {
                numberOfSafeReports++;
            }
        }
    }
    println("Safe reports are: $numberOfSafeReports")
}

private fun isAbsTooMuch(first: Int, second: Int): Boolean {
    val firstAbs = abs(first - second)
    var isSafe = firstAbs < 1 || firstAbs > 3;
    return isSafe
}

private fun isIncreasing() = { a: Int, b: Int ->
    a < b
}

private fun isDecreasing() = { a: Int, b: Int ->
    a > b
}


fun readFile(fileName: String)
        = object {}.javaClass.getResourceAsStream(fileName)?.reader()