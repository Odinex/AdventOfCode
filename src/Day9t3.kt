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
        } // 5857619169 90489586600 6331813711078 6332189866718
        println(chars.toString())
        var dotIndex = 0
        for(i in chars.size-1 downTo 0) {
            if (chars[i] == '.') continue

            // Find next available dot to the left
            while (dotIndex < i && chars[dotIndex] != '.') {
                dotIndex++
            }

            if (dotIndex < i) {
                chars[dotIndex] = chars[i]
                chars[i] = '.'
                dotIndex++
            }
        }
        println(chars.map { it.code.toLong()})
        val sumOf =
            chars.filter { it != '.' }.withIndex().sumOf { (index, char) -> index.toLong() *
                    (if(char == Char.MAX_VALUE) 46L else char.code.toLong()) }
        println(sumOf)
    }
}