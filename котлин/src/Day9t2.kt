fun main() {

    readFile("Day9.txt")?.forEachLine { line ->
        val digits = line.map { it.digitToInt() }
        val chars = mutableListOf<Char>()
        for ((index, digit) in digits.withIndex()) {
            val isFileBlock = index % 2 == 0

             if(isFileBlock) {
                 val toList = (index / 2).toString().toList()
                 repeat(digit) {
                     chars.addAll(toList)
                 }
             } else {
                 repeat(digit) {
                     chars.add('.')
                 }

             }
        } // 5857619169 90489586600
        println(chars.toString())
        var dotIndex = 0
        for(i in chars.size-1 downTo 0) {
            if(chars[i] == '.') {
                continue
            }
            // Find next available dot to the left
            while (dotIndex < chars.size && chars[dotIndex] != '.') {
                dotIndex++
            }

            if (dotIndex < i) {
                var nextIndexOfDot = 0
                for (j in i downTo 0) {
                    if(chars[j] == '.') {
                        nextIndexOfDot = j;
                        break
                    }
                }
                val blockToMove = chars.subList(nextIndexOfDot+1, i+1).toList()
                blockToMove.forEachIndexed { index, b ->
                    if (chars[dotIndex] == '.') {
                        if(dotIndex < i-index) {
                            chars[dotIndex] = b
                            chars[i-index] = '.'
                            dotIndex++
                        }
                    } else {
                        dotIndex = chars.indexOfFirst { c -> c == '.' };
                        if(dotIndex < i-index) {
                            chars[dotIndex] = b
                            chars[i-index] = '.'
                        }
                    }
                }

            }
        }
        println(chars.toString())
        val sumOf =
            chars.filter { it != '.' }.withIndex().sumOf { (index, chars) -> index.toLong() * chars.toString().toLong() }
        println(sumOf)
    }
}
