import java.io.File


fun main() {
    var totalRating = 0
    val zeros = mutableListOf<Pair<Int,Int>>()
    val matrix = mutableListOf<List<Int>>()
    var currentLine = 0

    // Read input file
    readFile("Day10.txt")?.forEachLine { line ->
        val toList = line.toList()
        val element: List<Int> = toList.map { it.digitToInt() }
        toList.forEachIndexed { index, c ->
            if(c == '0') {
                zeros.add(Pair(currentLine, index))
            }
        }
        matrix.add(element)
        currentLine++
    }

    val matrixSize = matrix.size
    for(z in zeros) {
        val distinctPaths = mutableSetOf<List<Pair<Int,Int>>>()

        for(d in directions) {
            distinctPaths.addAll(goInDirection(d, z, matrix, matrixSize))
        }

        totalRating += distinctPaths.size
    }

    println("$totalRating")
}

private fun goInDirection(
    d: Pair<Int, Int>,
    start: Pair<Int, Int>,
    matrix: List<List<Int>>,
    matrixSize: Int
): Set<List<Pair<Int,Int>>> {
    val paths = mutableSetOf<List<Pair<Int,Int>>>()

    val nextStep = Pair(start.first + d.first, start.second + d.second)
    if (nextStep.first in 0..<matrixSize && nextStep.second in 0..<matrixSize &&
        matrix[nextStep.first][nextStep.second] == 1
    ) {
        return dfsHikingTrails(nextStep, listOf(start, nextStep), matrix, matrixSize)
    }

    return paths
}

private fun dfsHikingTrails(
    current: Pair<Int, Int>,
    path: List<Pair<Int,Int>>,
    matrix: List<List<Int>>,
    matrixSize: Int
): Set<List<Pair<Int,Int>>> {
    if (matrix[current.first][current.second] == 9) {
        return setOf(path)
    }

    val paths = mutableSetOf<List<Pair<Int,Int>>>()

    for (direction in directions) {
        val nextStep = Pair(current.first + direction.first, current.second + direction.second)

        if (nextStep.first in 0..<matrixSize && nextStep.second in 0..<matrixSize &&
            matrix[nextStep.first][nextStep.second] - matrix[current.first][current.second] == 1
        ) {
            paths.addAll(dfsHikingTrails(nextStep, path + nextStep, matrix, matrixSize))
        }
    }

    return paths
}