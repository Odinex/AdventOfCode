
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

