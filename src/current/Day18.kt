val stripes: MutableList<String> = mutableListOf()
val flags: MutableList<String> = mutableListOf()
fun main() {
    stripes.clear()
    stripes.addAll(getStartInfo())
    flags.clear()
    flags.addAll(getStartFlags())
    val possible = mutableListOf<String>()
    val minStripeSize = stripes.minBy { it.length }.length
    val maxStripeSize = stripes.maxBy { it.length }.length

    flags.forEach {

        val isPossible = checkIsPossible(it, minStripeSize, maxStripeSize)
        if (isPossible) {
            possible.add(it)
            println(it)
        }
        println(possible.size)
    }


}

private fun checkIsPossible(it: String, minStripeSize: Int, maxStripeSize: Int): Boolean {
    var isPossible = false
    val startIndex = 0
    for (endIndex in startIndex + minStripeSize..minOf(it.length, startIndex + maxStripeSize)) {
        if(isPossible) {
            break
        }
        var test = it
        if (stripes.contains(test.substring(startIndex, endIndex))) {
            test = test.substring(endIndex)
            if (test.isEmpty()) {
                isPossible = true
                break
            } else {
                isPossible = checkIsPossible(test, minStripeSize, maxStripeSize)
            }
        }
    }

    return isPossible
}

private fun getStartInfo(): MutableList<String> {

    var currentLineIndex = 0
    val muta = mutableListOf<String>()

    readFile("CurrentTest.txt")?.forEachLine {
        val stripes = it.split(", ")
        muta.addAll(stripes)
        currentLineIndex++;

    }
    return muta
}

private fun getStartFlags(): MutableList<String> {

    var currentLineIndex = 0
    val muta = mutableListOf<String>()

    readFile("CurrentTest2.txt")?.forEachLine {
        muta.add(it)
        currentLineIndex++;
    }
    return muta
}

fun readFile(fileName: String) = object {}.javaClass.getResourceAsStream(fileName)?.reader()