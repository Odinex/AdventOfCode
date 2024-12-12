import utils.Utils

val startArray = getStartCharArrayWithVisited()
val directions = Utils.Direction.entries.toTypedArray()
val groups: MutableList<Pair<Char, MutableList<Pair<Int, Int>>>> = mutableListOf()
val perimeterMap = mutableMapOf<Int, Long>()
private var general = 0L
fun main() {


    while (startArray.any { charList -> charList.any { pair -> !pair.second } }) {
        for ((index, charArray) in startArray.withIndex()) {
            for ((charIndex, j) in charArray.withIndex()) {
                if (!j.second) {
                    val currentCoordinates = Pair(index, charIndex);
                    val relevantGroups: List<Pair<Char, MutableList<Pair<Int, Int>>>> =
                        getRelevantAdjacentGroups(j, currentCoordinates).toList()
                    if (relevantGroups.isEmpty()) {
                        val newGroup = Pair(j.first, mutableListOf<Pair<Int, Int>>())
                        newGroup.second.add(currentCoordinates)
                        groups.add(newGroup)
                    } else {
                        val currentGroup = relevantGroups[0]
                        for(i in 1..<relevantGroups.size) {
                            val next=relevantGroups[i]
                            currentGroup.second.addAll(next.second)
                        }
                        currentGroup.second.add(currentCoordinates)
                        groups.removeAll(relevantGroups.subList(1,relevantGroups.size))
                    }
                    startArray[index][charIndex] = Pair(j.first, true)
                }
            }

        }
        var total = 0L
        for((index, group) in groups.withIndex()) {
            var perimeter = 0L;

            for(current in group.second) {

                for (direction in directions) {
                    val nextStep = Pair(current.first + direction.pair.first, current.second + direction.pair.second)
                    if (nextStep.first in startArray.indices && nextStep.second in startArray[nextStep.first].indices) {
                        val nextChar = startArray[nextStep.first][nextStep.second]
                        if(nextChar.first != group.first) {
                            perimeter++
                        }
                    } else {
                        perimeter++
                    }
                }
            }
            perimeterMap[index] = perimeter
            total += perimeter * group.second.size
        }
        println(total)
    }
}

fun getRelevantAdjacentGroups(
    j: Pair<Char, Boolean>,
    current: Pair<Int, Int>
): MutableSet<Pair<Char, MutableList<Pair<Int, Int>>>> {
    val foundGroups = mutableSetOf<Pair<Char, MutableList<Pair<Int, Int>>>>()
    for (direction in directions) {
        val nextStep = Pair(current.first + direction.pair.first, current.second + direction.pair.second)
        if (nextStep.first in startArray.indices && nextStep.second in startArray[nextStep.first].indices) {
            val nextChar = startArray[nextStep.first][nextStep.second]
            if(nextChar.first == j.first) {
                if(nextChar.second) {
                    val found = groups.find { group ->
                        group.first == nextChar.first && group.second.contains(nextStep)
                    }
                    if(found == null) {
                        throw Exception("WTF")
                    }
                    foundGroups.add(found)
                }
            }
        }
    }
    return foundGroups;
}


private fun dfs(
    current: Long,
    count: Int,
    memo: MutableMap<Pair<Long, Int>, Long>
): Long {
    if (count == 75) return 1L

    val key = current to count
    if (key in memo) return memo[key]!!

    var result = 0L
    if (current == 0L) {
        result += dfs(1, count + 1, memo);
    } else {
        val toString = current.toString()
        if (toString.length % 2 == 0) {
            result += dfs(toString.substring(0, toString.length / 2).toLong(), count + 1, memo)
            result += dfs(toString.substring(toString.length / 2, toString.length).toLong(), count + 1, memo)
        } else {
            result += dfs(current * 2024L, count + 1, memo)
        }
    }

    memo[key] = result
    return result
}

private const val INPUT = "Current.txt"
private const val TEST_INPUT = "CurrentTest.txt"

private fun getStartCharArrayWithVisited(): Array<MutableList<Pair<Char, Boolean>>> {
    val start: MutableList<MutableList<Pair<Char, Boolean>>> = mutableListOf()
    readFile(TEST_INPUT)?.forEachLine {
        start.add(it.toCharArray().map { c -> Pair(c, false) }.toMutableList())
    }
    return start.toTypedArray()

}

private fun getStartCharArray(): Array<CharArray> {
    val start: MutableList<CharArray> = mutableListOf()
    readFile(TEST_INPUT)?.forEachLine {
        start.add(it.toCharArray())
    }
    return start.toTypedArray()

}

private fun getStartInfo(): List<Long> {
    var start: List<Long> = emptyList()
    readFile(TEST_INPUT)?.forEachLine {
        start = it.split(" ").map { c -> c.toLong() }
    }
    return start
}


fun readFile(fileName: String) = object {}.javaClass.getResourceAsStream(fileName)?.reader()

