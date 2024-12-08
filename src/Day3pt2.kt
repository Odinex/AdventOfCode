fun main() {
    var sum = 0;
    val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
    val regexDo = Regex("do\\(\\)")
    val regexDont = Regex("don't\\(\\)")
    var mode = MULT_MODE.DO
    var line = 1;
    readFile("Day3.txt")?.forEachLine {
        // "\\$[mul(\\d,\\d)]"

        var currentStartIndex = 0;
        var currentEndIndex: Int
        while(currentStartIndex != it.length) {
            if(mode == MULT_MODE.DO) {
                val pair = setDoNot(regexDont, it, currentStartIndex, mode)
                currentEndIndex = pair.first
                mode = pair.second
                regex.findAll(it.substring(currentStartIndex,currentEndIndex)).forEach { asd ->

                    val (first, second) = asd.value.substring(4, asd.value.indexOf(")")).split(",")
                    sum += first.toInt() * second.toInt()
                }
                currentStartIndex = currentEndIndex;
            } else {
                val nextDo = regexDo.find(it, currentStartIndex)
                if(nextDo != null) {
                    mode = MULT_MODE.DO;
                    currentStartIndex = nextDo.range.last
                    val pair = setDoNot(regexDont, it, currentStartIndex, mode)
                    currentEndIndex = pair.first
                    mode = pair.second
                    regex.findAll(it.substring(currentStartIndex,currentEndIndex)).forEach { asd ->

                        val (first, second) = asd.value.substring(4, asd.value.indexOf(")")).split(",")
                        sum += first.toInt() * second.toInt()
                    }
                    currentStartIndex = currentEndIndex;
                } else {
                    currentStartIndex = it.length
                }
            }
        }
        println(sum)
    }
}

private fun setDoNot(
    regexDont: Regex,
    it: String,
    currentStartIndex: Int,
    mode: MULT_MODE
): Pair<Int, MULT_MODE> {
    val currentEndIndex: Int
    var mode1 = mode
    val nextDoNot = regexDont.find(it, currentStartIndex)
    if (nextDoNot != null) {
        currentEndIndex = nextDoNot.range.last
        mode1 = MULT_MODE.DONT
    } else {
        currentEndIndex = it.length
    }
    return Pair(currentEndIndex, mode1)
}

private enum class MULT_MODE {
    DO, DONT
}