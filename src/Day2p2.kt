import kotlin.math.abs

fun main() {
    var numberOfSafeReports = 0
    readFile("Day2.txt")?.forEachLine {
        val numbers = it.split(" ");
        if(numbers.size > 1 && numbers[0] != numbers[1]) {
            val first = numbers[0].toInt()
            val second = numbers[1].toInt()
            var checkFunction: (Int, Int) -> Boolean = setCheckFunction(first, second)
            var isSafe = true
            var haveRemovedOne = false
            for (i in 0..<numbers.size - 1) {
                var current = numbers[i].toInt()
                var next = numbers[i + 1].toInt()
                if (areLevelsUnsafe(checkFunction, current, next)) {
                    if(haveRemovedOne) {
                        isSafe = false;
                        break
                    } else {
                        haveRemovedOne = true;
                        if(i== numbers.size - 2) {
                            break
                        } else if(i == 0) {
                            current = next
                            next = numbers[2].toInt();
                            checkFunction = setCheckFunction(first, second)
                        } else {
                            next = numbers[i+2].toInt();
                        }
                        if(areLevelsUnsafe(checkFunction, current, next))  {
                            isSafe = false;
                            break;
                        }
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

private fun setCheckFunction(first: Int, second: Int): (Int, Int) -> Boolean {
    var checkFunction: (Int, Int) -> Boolean = if (first > second) {
        isDecreasing()
    } else {
        isIncreasing()
    }
    return checkFunction
}

private fun areLevelsUnsafe(
    checkFunction: (Int, Int) -> Boolean,
    current: Int,
    next: Int
) = !checkFunction(current, next) || isAbsTooMuch(current, next)

private fun isAbsTooMuch(first: Int, second: Int): Boolean {
    val firstAbs = abs(first - second)
    val isSafe = firstAbs < 1 || firstAbs > 3;
    return isSafe
}

private fun isIncreasing() = { a: Int, b: Int ->
    a < b
}

private fun isDecreasing() = { a: Int, b: Int ->
    a > b
}
