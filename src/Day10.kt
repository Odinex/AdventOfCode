val directions = arrayOf(
    Pair(0, 1),   // right
    Pair(0, -1),  // left
    Pair(1, 0),   // down
    Pair(-1, 0),  // up
)
fun main() {

    var sum = 0;
    val zeros = mutableListOf<Pair<Int,Int>>()
    val matrix = mutableListOf<List<Int>>()
    var currentLine = 0
    readFile("Day10Test.txt")?.forEachLine { line ->
        val toList = line.toList()
        val element: List<Int> = toList.map { it.digitToInt() }
        toList.forEachIndexed { index, c -> if(c == '0') {
            zeros.add(Pair(currentLine, index))
        } }
        matrix.add(element)
        currentLine++;
    }


    var matrixSize = matrix.size
    for(z in zeros) {
        var number = mutableSetOf<Pair<Int,Int>>()
        for(d in directions) {
            number.addAll(goInDirection(d, z, matrix, matrixSize))
        }
        sum += number.size
    }
    println(sum)
}

private fun goInDirection(d: Pair<Int, Int>, z: Pair<Int, Int>, matrix: MutableList<List<Int>>, matrixSize: Int): MutableSet<Pair<Int,Int>> {
    var number = mutableSetOf<Pair<Int,Int>>()
    val nextStep = Pair(z.first+d.first, z.second+d.second)

    if (nextStep.first in 0.rangeUntil(matrixSize) && nextStep.second in 0.rangeUntil(matrixSize)) {
        val i = matrix[nextStep.first][nextStep.second]

        if (i - matrix[z.first][z.second] == 1) {
            if (i == 9) {
                number.add(nextStep)
            } else {
                for (direction in directions) {
                    number += goInDirection(direction, nextStep, matrix, matrixSize)
                }
            }
        }
    }
    return number
}

