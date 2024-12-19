import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.math.min
import kotlin.math.max

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main() = coroutineScope {
    val stripes = getStartInfo().toList().sortedByDescending { it.length }
    val stripeCombos = mutableMapOf<String, Set<String>>()

    val flags = getStartFlags().toList()
    val minStripeSize = stripes.minOf { it.length }
    val maxStripeSize = stripes.maxOf { it.length }
    val stripesByLength = stripes.groupBy { it.length }

    val stripeQueue = stripes.toMutableList()
    while (stripeQueue.size > 2) {
        val currentStripe = stripeQueue.removeFirst()
        val checkResult = checkIsPossible(currentStripe, stripeQueue.groupBy { it.length }, stripeQueue.minOf { it.length }, stripeQueue.maxOf { it.length })
        if (checkResult.first) {
            stripeCombos[currentStripe] = checkResult.second
        }
    }

    var count = 0
    flags.asFlow()
        .flatMapMerge(concurrency = 2) { flag ->
            flow {
                val isPossible = checkIsPossible(flag, stripesByLength, minStripeSize, maxStripeSize)
                if (isPossible.first) emit(isPossible)
            }
        }
        .collect { possibleFlag ->
            val allCombos = mutableSetOf<Set<String>>()
            allCombos.add(possibleFlag.second.toMutableSet())
            val batchSize = 1000 // 65083306
            val batch = mutableSetOf<Set<String>>()
            val processed = mutableSetOf<Pair<String, Set<String>>>()
            possibleFlag.second.forEach { stripe ->
                val stack = ArrayDeque<Pair<String, Set<String>>>()
                stripeCombos[stripe]?.let { stack.add(stripe to it) }

                while (stack.isNotEmpty()) {
                    val (current, parts) = stack.removeFirst()
                    if (!processed.add(current to parts)) continue
                    allCombos.toList().asSequence().forEach { combo ->
                        val newCombo = (combo - current) + parts
                        batch.add(newCombo)
                        if (batch.size >= batchSize) {
                            allCombos.addAll(batch)
                            batch.clear()
                        }

                    }


                    parts.forEach { part ->
                        stripeCombos[part]?.let { stack.add(part to it) }
                    }
                }
                if (batch.size >= 0) {
                    allCombos.addAll(batch)
                    batch.clear()
                }
            }

            val largestCombo = allCombos.maxByOrNull { it.size }?.toList().orEmpty()
            val stack = ArrayDeque<List<String>>()
            stack.add(largestCombo)

            while (stack.isNotEmpty()) {
                val currentCombo = stack.removeFirst()
                for (i in currentCombo.indices) {
                    for (j in (i + 1) until currentCombo.size) {
                        val combinedIndex = stripes.indexOf(currentCombo[i] + currentCombo[j])
                        if (combinedIndex != -1) {
                            val newCombo = currentCombo.filterIndexed { index, _ -> index != i && index != j } + stripes[combinedIndex]
                            if (allCombos.add(newCombo.toMutableSet())) stack.add(newCombo)
                        }
                    }
                }
            }
            // 758691
            count += allCombos.size
            println(count)
            println(possibleFlag)
        }
}

private suspend fun checkIsPossible(
    flag: String,
    stripes: Map<Int, List<String>>,
    minStripeSize: Int,
    maxStripeSize: Int
): Pair<Boolean, Set<String>> {
    val visited = mutableSetOf<String>()

    fun recurse(remainingFlag: String, currentStripes: Set<String> = emptySet()): Pair<Boolean, Set<String>> {
        if (remainingFlag.isEmpty()) return true to currentStripes
        if (!visited.add(remainingFlag)) return false to currentStripes

        val endRange = min(maxStripeSize, remainingFlag.length)
        for (end in endRange downTo minStripeSize) {
            val prefix = remainingFlag.substring(0, end)
            if (stripes[prefix.length]?.contains(prefix) == true) {
                val result = recurse(remainingFlag.substring(end), currentStripes + prefix)
                if (result.first) return result
            }
        }
        return false to currentStripes
    }

    return recurse(flag)
}

private fun getStartInfo() = flow {
    readFile("CurrentTest.txt")?.useLines { lines ->
        lines.forEach { line -> line.split(", ").forEach { emit(it) } }
    }
}

private fun getStartFlags() = flow {
    readFile("CurrentTest2.txt")?.useLines { lines ->
        lines.forEach { emit(it) }
    }
}

private fun readFile(fileName: String) =
    object {}.javaClass.getResourceAsStream(fileName)?.bufferedReader()
