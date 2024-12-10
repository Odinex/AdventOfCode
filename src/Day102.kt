
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
    val mutableSetOf = mutableSetOf<List<Pair<Int, Int>>>()
    for(z in zeros) {
        val number = mutableListOf<List<Pair<Int, Int>>>()
        for(d in directions) {
            number.addAll(goInDirection(d, mutableListOf(z), matrix, matrixSize))
        }
        for(n in number) {
            val last = n.last()
            if(matrix[last.first][last.second] == 9) {
                mutableSetOf.add(n)
                sum++
            }
        }

        sum += number.size
    }
    println(mutableSetOf.size)
    println(sum)
}

private fun goInDirection(d: Pair<Int, Int>, pairs: MutableList<Pair<Int, Int>>, matrix: MutableList<List<Int>>, matrixSize: Int): MutableList<List<Pair<Int, Int>>> {
    var number = mutableListOf<List<Pair<Int, Int>>>()
    val z = pairs.last()
    val nextStep = Pair(z.first+d.first, z.second+d.second)

    if (nextStep.first in 0.rangeUntil(matrixSize) && nextStep.second in 0.rangeUntil(matrixSize)) {
        val i = matrix[nextStep.first][nextStep.second]

        if (i - matrix[z.first][z.second] == 1) {
            pairs.add(nextStep)
            number.add(pairs.toList())
            if (i < 9) {
                for (direction in directions) {
                    number.addAll(goInDirection(direction, pairs, matrix, matrixSize))
                }
            }
        }
    }
    return number
}

