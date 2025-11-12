fun main() {
    var sum = 0;
    val spelledDigits = mapOf(
        Pair("one", 1),
        Pair("two", 2), Pair("three", 3), Pair("four", 4), Pair("five", 5),
        Pair("six", 6), Pair("seven", 7), Pair("eight", 8), Pair("nine", 9)
    )
    readFile("Day1Test.txt")?.forEachLine { line ->
        val digits = mutableMapOf<Int, Int>()
        for ((index, it) in line.withIndex()) {
            try {
                digits[index] = it.toString().toInt()
            } catch (_: Exception) {
            }
        }
        spelledDigits.forEach {
            val indexOf = line.indexOf(it.key)
            if (indexOf != -1) {
                digits[indexOf] = it.value
            }
            val lastIndexOf = line.lastIndexOf(it.key)
            if (lastIndexOf != -1) {
                digits[lastIndexOf] = it.value
            }
        }
        var min = Pair(line.length, 10)
        var max = Pair(-1, -1)
        digits.forEach {
            if(min.first > it.key) {
                min = it.toPair()
            }
            if(max.first < it.key) {
                max = it.toPair()
            }
        }
        val plus = min.second.toString().plus(max.second.toString())
        sum += plus.toInt()
    }
    println(sum)
}
