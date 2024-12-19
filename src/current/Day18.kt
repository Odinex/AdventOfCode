import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main() = coroutineScope {
    val stripes = getStartInfo().toList()
    val flags = getStartFlags().toList()
    val minStripeSize = stripes.minOf { it.length }
    val maxStripeSize = stripes.maxOf { it.length }
    val mapOfStripes = stripes.groupBy { it.length}
    var count = 0
    flags.asFlow()
        .flatMapMerge(Runtime.getRuntime().availableProcessors()) { flag ->
            flow {
                val isPossible = checkIsPossible(flag, mapOfStripes, minStripeSize, maxStripeSize)
                if (isPossible.first) {
                    emit(isPossible)
                }
            }
        }
        .collect { possibleFlag ->
            count += possibleFlag.size
            println(count)
            println(possibleFlag)
        }
}

private suspend fun checkIsPossible(
    flag: String,
    stripes: Map<Int, List<String>>,
    minStripeSize: Int,
    maxStripeSize: Int
): Pair<Boolean, List<String>> {
    val visited = mutableSetOf<String>()
    fun checkRecursive(remainingFlag: String, possibleStripes: List<String> = mutableListOf()) : Pair<Boolean, List<String>>{

        if (remainingFlag.isEmpty()) {
            return Pair(true, possibleStripes)
        }
        if (!visited.add(remainingFlag)) return Pair(false, possibleStripes)
        val start = minStripeSize + remainingFlag.indices.first
        val end = minOf(remainingFlag.length, maxStripeSize + remainingFlag.indices.first)
        for (endIndex in end downTo start) {
            val currentStripe = remainingFlag.substring(0, endIndex)
            if (stripes[currentStripe.length]?.contains(currentStripe) == true) {
                val newRemainder = remainingFlag.substring(endIndex)

                if (newRemainder.isEmpty()) {
                    return Pair(true, possibleStripes+ currentStripe)
                } else {
                    return checkRecursive(newRemainder, possibleStripes + currentStripe)
                }
            }
        }
        return Pair(false, possibleStripes)
    }
    return checkRecursive(flag)
}

private fun getStartInfo() = flow {
    readFile("CurrentTest.txt")?.useLines { lines ->
        lines.forEach { line ->
            line.split(", ").forEach { stripe ->
                emit(stripe)
            }
        }
    }
}

private fun getStartFlags() = flow {
    readFile("CurrentTest2.txt")?.useLines { lines ->
        lines.forEach { line ->
            emit(line)
        }
    }
}

private fun readFile(fileName: String) =
    object {}.javaClass.getResourceAsStream(fileName)?.bufferedReader()