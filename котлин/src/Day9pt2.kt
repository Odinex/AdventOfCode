fun main() {

    readFile("Day9.txt")?.forEachLine { line ->
        val digits = line.map { it.digitToInt() }
        val chars = mutableListOf<Char>()
        for ((index, digit) in digits.withIndex()) {
            val isFileBlock = index % 2 == 0

            if(isFileBlock) {
                val toChar = if(index/2 == 46) Char.MAX_VALUE else (index / 2).toChar()
                repeat(digit) {
                    chars.add(toChar)
                }
            } else {
                repeat(digit) {
                    chars.add('.')
                }

            }
        }
        println(chars.toString())
        var dotIndex: Int;
        var i = chars.size -1
        while(i >= 0) {
            dotIndex = 0
            if(chars[i] == '.') {
                i--
                continue
            }
            // Find next available dot to the left
            while (dotIndex < chars.size && chars[dotIndex] != '.') {
                dotIndex++
            }

            if (dotIndex < i) {
                var nextDifferentIndex = 0
                for (j in i downTo 0) {
                    if(chars[j] != chars[i]) {
                        nextDifferentIndex = j;
                        break
                    }
                }
                val blockToMove = chars.subList(nextDifferentIndex+1, i+1).toList()
                var canBlockBeMovedThere = false;
                while(dotIndex < nextDifferentIndex && !canBlockBeMovedThere) {
                    canBlockBeMovedThere = true
                    for (d in dotIndex..<dotIndex + blockToMove.size) {
                        if (d >= chars.size || chars[d] != '.') {
                            canBlockBeMovedThere = false;
                            dotIndex = d
                            while (dotIndex <= nextDifferentIndex && chars[dotIndex] != '.') {
                                dotIndex++
                            }
                            break;
                        }
                    }
                }
                if(!canBlockBeMovedThere) {
                    i = nextDifferentIndex
                    continue
                }
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
            i--
        }
        println(chars.map { it.code.toLong()})
        val sumOf =
            chars.withIndex().sumOf { (index, char) -> if(char == '.') 0 else index.toLong() *
                    (if(char == Char.MAX_VALUE) 46L else char.code.toLong()) }
        println(sumOf)
    }
}