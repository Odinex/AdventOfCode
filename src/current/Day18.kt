import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.math.max

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main() = coroutineScope {
    val stripes = getStartInfo().toList().sortedByDescending { it.length }
    val stripesCombos = mutableMapOf<String, Set<String>>()

    val flags = getStartFlags().toList()
    val minStripeSize = stripes.minOf { it.length }
    val maxStripeSize = stripes.maxOf { it.length }
    val mapOfStripes = stripes.groupBy { it.length}
    val mutant = stripes.toMutableList()
    while(mutant.size > 2) {
        val s1 = mutant.removeFirst()
        val minS = mutant.minOf { it.length }
        val maxS = mutant.maxOf { it.length }
        val checkIsPossible = checkIsPossible(s1, mutant.groupBy { it.length }, minS, maxS)
        if(checkIsPossible.first) {
                stripesCombos[s1] = checkIsPossible.second
        }
//
//        val filter2 =
//            mutant.filter { s2 -> s1.substring(0, s2.length).contains(s2) && mutant.co(s1.substring(s2.length)) }
//        if(filter2.isNotEmpty()) {
//            for(s2 in filter2) {
//                val s3 = s1.substring(s2.length)
//                val pairs = stripesCombos[s1]
//                if(pairs != null) {
//                    stripesCombos[s1] = pairs + Pair(s2,s3)
//                } else {
//                    stripesCombos[s1] = listOf(Pair(s2,s3))
//                }
//            }
//
//        }
    }
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
            //count += possibleFlag.second
            val allPossibleCombo = mutableSetOf<Set<String>>()
            allPossibleCombo.add(possibleFlag.second)

            for (i in possibleFlag.second) {
                val currentSet = possibleFlag.second.toMutableSet()
                var current = i
                val set = stripesCombos[current]
                val stack = Stack<Pair<String, Set<String>>>()
                if(set != null) {
                    stack.add(Pair(current, set))

                    while(stack.isNotEmpty()) {
                        val (c, setOfCParts) = stack.removeFirst()
                        val toList = allPossibleCombo.toList()
                        toList.forEach { resultSet ->
                            val mutableResultSet = resultSet.toMutableSet()
                            val removedSuccess = mutableResultSet.remove(c)
                            if(removedSuccess) {
                                mutableResultSet.addAll(setOfCParts)
                                allPossibleCombo.add(mutableResultSet.toSet())
                            }
                        }
                        for(part in setOfCParts) {
                            val otherCombos = stripesCombos[part]
                            if (otherCombos != null) {
                                stack.add(Pair(c, otherCombos))
                            }
                        }

                    }
                }

            }
            val maxElements: List<String> = allPossibleCombo.maxBy { it.size }.toList()
            val stack = Stack<List<String>>()

            stack.add(maxElements)
            while(stack.isNotEmpty()) {
                val current = stack.removeFirst()
                for (i in current.indices - 1) {
                    for (j in 1..<current.size) {
                        val indexOf = stripes.indexOf(current[i].plus(current[j]))
                        if (indexOf != -1) {
                            val mutant2 =
                                current.filterIndexed { index, _ -> index != i && index != j }.toMutableList()
                            mutant2.add(stripes[indexOf])

                            val flag = allPossibleCombo.add(mutant2.toSet())
                            if(flag) {
                                stack.add(mutant2)
                            }
                        }
                    }
                }
            }


            count += allPossibleCombo.size
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
    fun checkRecursive(remainingFlag: String, possibleStripes: Set<String> = mutableSetOf()) : Pair<Boolean, Set<String>>{

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
    readFile("Current.txt")?.useLines { lines ->
        lines.forEach { line ->
            line.split(", ").forEach { stripe ->
                emit(stripe)
            }
        }
    }
}

private fun getStartFlags() = flow {
    readFile("Current2.txt")?.useLines { lines ->
        lines.forEach { line ->
            emit(line)
        }
    }
}

private fun readFile(fileName: String) =
    object {}.javaClass.getResourceAsStream(fileName)?.bufferedReader()