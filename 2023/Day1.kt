fun main() {
    var sum = 0;
    readFile("Day1.txt")?.forEachLine { line ->
        val digits = mutableListOf<Int>()
        line.forEach {
            try {
                digits.add(it.toString().toInt())
            } catch (e: Exception) {
                println(it)
            }


        }
        val plus = digits.first().toString().plus(digits.last().toString())
            sum += plus.toInt()
    }
    println(sum)
}
