import java.util.PriorityQueue

fun main() {
    val rules: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    readFile("Day5rules.txt")?.forEachLine {
        val (first,second) = it.split("|").map { s -> s.toInt()}
        val ints = rules[second] ?: mutableListOf<Int>()
        ints.add(first)
        rules[second] = ints
    }
    var middlePageSum = 0
    readFile("Day5printText.txt")?.forEachLine {
        val numbers = it.split(",").map { s -> s.toInt()}
        var isOk = true
        for(i in numbers.indices) {
            val ints = rules[numbers[i]]?.filter { number -> numbers.contains(number)}
            if(ints != null) {
                if(!numbers.subList(0, i).containsAll(ints)) {
                    isOk = false;
                    break;
                }
            }
        }
        if(isOk) {
            println(it)
            middlePageSum += numbers[numbers.size/2]
        }
    }
    println(middlePageSum)
}
