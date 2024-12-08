import java.util.LinkedList
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
        var numbers = LinkedList(it.split(",").map { s -> s.toInt()})
        var isOk = true
        for(i in numbers.indices) {
            var ints = rules[numbers[i]]?.filter { number -> numbers.contains(number)}
            if(ints != null) {
                if(!numbers.subList(0, i).containsAll(ints)) {
                    isOk = false;
                    ints = ints.filter { number -> !numbers.subList(0, i).contains(number)}
                    numbers.removeAll(ints)
                    ints.forEach {
                            number ->
                        numbers.add(i, number)
                    }
                    numbers = checkAgain(numbers, rules);
                    break;
                }

            }
        }
        if(!isOk) {
            println(it)
            middlePageSum += numbers[numbers.size/2]
        }
    }
    println(middlePageSum)
}

fun checkAgain(numbers: LinkedList<Int>, rules: MutableMap<Int, MutableList<Int>>): LinkedList<Int> {
    do {
        var isOk = true
        for(i in numbers.indices) {
            var ints = rules[numbers[i]]?.filter { number -> numbers.contains(number)}
            if(ints != null) {
                if(!numbers.subList(0, i).containsAll(ints)) {
                    isOk = false;
                    ints = ints.filter { number -> !numbers.subList(0, i).contains(number)}
                    numbers.removeAll(ints)
                    ints.forEach {
                            number ->
                        numbers.add(i, number)
                    }
                    break;
                }

            }
        }
    } while(!isOk)
    return numbers;

}
