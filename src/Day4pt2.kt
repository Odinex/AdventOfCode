fun main() {
    var count = 0
    var count2 = 0
    val matrix: MutableList<MutableList<Char>> = mutableListOf()
    val (xmas, samx) = Pair("XMAS", "SAMX")

    // Read the file and create matrix
    readFile("Day4.txt")?.forEachLine {
        matrix.add(it.toMutableList())
    }


    val directions = arrayOf(
        Pair(1, 1),   // down-right
        Pair(1, -1),  // down-left
        Pair(-1, 1),  // up-right
        Pair(-1, -1)  // up-left
    )
    val oppositeDirectionsMap = mutableMapOf(
        Pair(directions[0], listOf(directions[1],directions[2])),
        Pair(directions[1], listOf(directions[0],directions[3])),
        Pair(directions[2], listOf(directions[0],directions[3])),
        Pair(directions[3], listOf(directions[1],directions[2])))

    val setOfA = mutableSetOf<Pair<Int, Int>>()
    // Check diagonal paths
    for (startRow in matrix.indices) {
        for (startCol in matrix[startRow].indices) {
            for (direction in directions) {
                val (dx, dy) = direction

                // Check for XMAS
                val xmasPath = findWordInDirection(matrix, startRow, startCol, dx, dy, "MAS")
                if (xmasPath) {
                    val xDirections = oppositeDirectionsMap[direction];
                    if(xDirections != null) {
                        for(xDir in xDirections) {
                            var newStartRow: Int = -1
                            var newStartCol: Int = -1
                           if (direction == directions[0])  {
                              if(xDir == directions[1]) {
                                  newStartRow = startRow
                                  newStartCol = startCol+ 2
                              } else {
                                  newStartRow = startRow + 2
                                  newStartCol = startCol
                              }
                           }  else if(direction == directions[1]) {
                               if(xDir == directions[0]) {
                                   newStartRow = startRow
                                   newStartCol = startCol - 2
                               } else {
                                   newStartRow = startRow + 2
                                   newStartCol = startCol
                               }
                           } else if(direction == directions[2]) {
                               if(xDir == directions[0]) {
                                   newStartRow = startRow - 2
                                   newStartCol = startCol
                               } else {
                                   newStartRow = startRow
                                   newStartCol = startCol + 2
                               }
                           } else if(direction == directions[3]) {
                               if(xDir == directions[1]) {
                                   newStartRow = startRow - 2
                                   newStartCol = startCol
                               } else {
                                   newStartRow = startRow
                                   newStartCol = startCol - 2
                               }
                           }
                            val xmasPath2 = findWordInDirection(matrix, newStartRow, newStartCol, xDir.first, xDir.second, "MAS")
                            if(xmasPath2) {
                                val middleIndexPair = getMiddleIndexPair(matrix, startRow, startCol, dx, dy, "MAS")
                                if(middleIndexPair != null && !setOfA.contains(middleIndexPair)) {
                                    setOfA.add(middleIndexPair)
                                    count2++
                                }
                                break;

                            }
                        }
                    }
                }
            }
        }
    }

    println(count2)
    println(count)
}

fun getMiddleIndexPair(
    matrix: List<List<Char>>,
    startRow: Int,
    startCol: Int,
    dx: Int,
    dy: Int,
    word: String
): Pair<Int,Int>? {
    if (startRow < 0 || startRow >= matrix.size ||
        startCol < 0 || startCol >= matrix[0].size) {
        return null
    }

    val path = StringBuilder()
    var currentRow = startRow
    var currentCol = startCol

    // Try to find the full word in the given direction
    for ((index, i) in word.indices.withIndex()) {
        if(index == 1) {
            return Pair(currentRow, currentCol)
        }
        // Check if we're still within matrix bounds
        if (currentRow < 0 || currentRow >= matrix.size ||
            currentCol < 0 || currentCol >= matrix[0].size) {
            return null
        }

        // Append current character
        path.append(matrix[currentRow][currentCol])

        // Move to next position
        currentRow += dx
        currentCol += dy

    }

    // Check if the path matches the word
    return null
}
