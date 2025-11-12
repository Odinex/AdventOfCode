fun main() {
    var count = 0
    var count2 = 0
    val matrix: MutableList<MutableList<Char>> = mutableListOf()
    val (xmas, samx) = Pair("XMAS", "SAMX")

    // Read the file and create matrix
    readFile("Day4.txt")?.forEachLine {
        if (matrix.isEmpty()) {
            for (i in it.indices) {
                matrix.add(mutableListOf())
            }
        }

        for (i in it.indices) {
            matrix[i].add(it[i])
        }

        if(it.contains(xmas)) {
            count++
        }
        if(it.contains(samx)) {
            count++

        }
    }

    matrix.forEach {
        val string = String(it.toCharArray())
        if(string.contains(xmas)) {
            count++
        }
        if(string.contains(samx)) {
            count++
        }
    }

    val directions = arrayOf(
        Pair(0, 1),   // right
        Pair(0, -1),  // left
        Pair(1, 0),   // down
        Pair(-1, 0),  // up
        Pair(1, 1),   // down-right
        Pair(1, -1),  // down-left
        Pair(-1, 1),  // up-right
        Pair(-1, -1)  // up-left
    )

    // Check diagonal paths
    for (startRow in matrix.indices) {
        for (startCol in matrix[startRow].indices) {
            for (direction in directions) {
                val (dx, dy) = direction

                // Check for XMAS
                val xmasPath = findWordInDirection(matrix, startRow, startCol, dx, dy, "XMAS")
                if (xmasPath) {
                    count2++
                }
            }
        }
    }

    println(count2)
    println(count)
}

fun findWordInDirection(
    matrix: List<List<Char>>,
    startRow: Int,
    startCol: Int,
    dx: Int,
    dy: Int,
    word: String
): Boolean {
    if (startRow < 0 || startRow >= matrix.size ||
        startCol < 0 || startCol >= matrix[0].size) {
        return false
    }

    val path = StringBuilder()
    var currentRow = startRow
    var currentCol = startCol

    // Try to find the full word in the given direction
    for (i in word.indices) {
        // Check if we're still within matrix bounds
        if (currentRow < 0 || currentRow >= matrix.size ||
            currentCol < 0 || currentCol >= matrix[0].size) {
            return false
        }

        // Append current character
        path.append(matrix[currentRow][currentCol])

        // Move to next position
        currentRow += dx
        currentCol += dy
    }

    // Check if the path matches the word
    return path.toString().contains(word)
}

// Assume readFile is defined elsewhere in the project