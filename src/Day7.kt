import java.util.*


enum class Operators {
    ADD, MULTIPLY, CONCAT
}
fun main() {
    var sum = 0L;
    readFile("Day7.txt")?.forEachLine {
        val (resultString: String, numbersString: String) = it.split(": ")
        val result = resultString.toLong()
        val numbers = numbersString.split(" ").map { number -> number.toLong()}
        val operatorsLists = createOperatorsLists((numbers.size - 1).toLong())
        for(operatorList in operatorsLists) {
            if (evaluateExpression(numbers, operatorList) == result) {
                sum += result
                break
            }
        }
    }
    println(sum)
}


fun evaluateExpression(numbers: List<Long>, operators: List<Operators>): Long {
    var currentValue = numbers[0]

    for (i in operators.indices) {
        when (operators[i]) {
            Operators.ADD -> currentValue += numbers[i + 1]
            Operators.MULTIPLY -> currentValue *= numbers[i + 1]
            Operators.CONCAT -> currentValue = currentValue.toString().plus(numbers[i+1].toString()).toLong()
        }
    }

    return currentValue

}

fun createOperatorsLists(size: Long): List<MutableList<Operators>> {
    if (size <= 0) return mutableListOf()

    val results = mutableListOf<MutableList<Operators>>()
    results.add(mutableListOf())

    for (i in 1.rangeTo(size)) {
        val newResults = mutableListOf<MutableList<Operators>>()
        results.forEach { operators ->
            newResults.add((operators + Operators.ADD).toMutableList())
            newResults.add((operators + Operators.MULTIPLY).toMutableList())
            newResults.add((operators + Operators.CONCAT).toMutableList())
        }
        results.clear()
        results.addAll(newResults)
    }

    return results

}

