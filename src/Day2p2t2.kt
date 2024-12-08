import kotlin.math.abs

fun main() {
    var numberOfSafeReports = 0
    readFile("Day2.txt")?.forEachLine {
        val numbers = it.split(" ");
        if(numbers.size > 1) {

            val (isSafe,unsafeStartIndex) = areNumbersSafe(numbers)

            if(isSafe) {
                numberOfSafeReports++;
            } else {
                val numbers0 = numbers.subList(1, numbers.size)
                val (isSafeAfterRemovingFirst, _) = areNumbersSafe(numbers0)
                if(isSafeAfterRemovingFirst) {
                    numberOfSafeReports++;
                    return@forEachLine
                }
                val numbers1 = numbers.subList(0, unsafeStartIndex)
                    .plus(numbers.subList(unsafeStartIndex + 1, numbers.size))
                val (isSafeAfterRemovingUnsafe, _) = areNumbersSafe(numbers1)
                if(isSafeAfterRemovingUnsafe) {
                    numberOfSafeReports++;
                    return@forEachLine
                }
                val indexToBeSkipped = unsafeStartIndex + 1
                val numbers2 = numbers.subList(0, indexToBeSkipped)
                    .plus(numbers.subList(indexToBeSkipped + 1, numbers.size))
                val (isSafeAfter, _) = areNumbersSafe(numbers2)
                if(isSafeAfter){
                    numberOfSafeReports++;
                    return@forEachLine
                }
                println(it)

            }
        }
    }
    println("Safe reports are: $numberOfSafeReports")
}

private fun areNumbersSafe(
    numbers: List<String>
): Pair<Boolean, Int> {
    var isSafe = true
    var unsafeStartIndex = -1
    var checkFunction: ((Int, Int) -> Boolean)? = null
    for (i in 0..<numbers.size - 1) {
        val current = numbers[i].toInt()
        var next = numbers[i + 1].toInt()
        if (i == 0) {
            if (current == next) {
                isSafe = false;
                unsafeStartIndex = i;
                break;
            } else {
                checkFunction = if (current > next) {
                    isDecreasing()
                } else {
                    isIncreasing()
                }
            }
        }
        if (checkFunction == null || areLevelsSafe(checkFunction, current, next)) {
            unsafeStartIndex = i;
            isSafe = false;
            break;
        }
    }
    return Pair(isSafe, unsafeStartIndex)
}

private fun areLevelsSafe(
    checkFunction: (Int, Int) -> Boolean,
    current: Int,
    next: Int
) = !checkFunction(current, next) || isAbsTooMuch(current, next) || current == next

private fun isAbsTooMuch(first: Int, second: Int): Boolean {
    val firstAbs = abs(first - second)
    return firstAbs < 1 || firstAbs > 3
}

private fun isIncreasing() = { a: Int, b: Int ->
    a < b
}

private fun isDecreasing() = { a: Int, b: Int ->
    a > b
}

