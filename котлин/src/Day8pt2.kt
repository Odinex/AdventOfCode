import java.util.*
import kotlin.math.abs


fun main() {
    val matrix = mutableListOf<MutableList<Char>>()
    val charMap = mutableMapOf<Char, MutableSet<Pair<Int,Int>>>()
    var currentRow = 0
    readFile("Day8.txt")?.forEachLine {
        val element = it.toMutableList()
        matrix.add(element)
        for(i in element.indices) {
            val ch = element[i]
            if(ch != '.') {
                val currentSet = charMap[ch]?: mutableSetOf()
                currentSet.add(Pair(currentRow, i))
                charMap[ch] = currentSet
            }
        }
        currentRow++
    }
    val antinodeCoordinates = mutableSetOf<Pair<Int,Int>>()
    for(ch in charMap.keys) {
        val coordinatesList = charMap[ch]?.toList()!!
        for (i in 0..<coordinatesList.size - 1) {
            val firstPair = coordinatesList[i];
            antinodeCoordinates.add(firstPair)
            for (j in i+1..<coordinatesList.size) {
                val secondPair = coordinatesList[j]
                antinodeCoordinates.add(secondPair)
                val antiX = secondPair.first - firstPair.first
                val antiY = secondPair.second - firstPair.second
                var pointAfterX = secondPair.first + antiX
                var pointAfterY = secondPair.second + antiY
                while(pointAfterY >= 0 && pointAfterY < matrix.size && pointAfterX >= 0 && pointAfterX < matrix.size ) {
                    try {
                        val getPoint = matrix[pointAfterX][pointAfterY]
                        antinodeCoordinates.add(Pair(pointAfterX, pointAfterY))
                        pointAfterX += antiX
                        pointAfterY += antiY
                    } catch (e: IndexOutOfBoundsException) {
                        println(firstPair)
                        println(secondPair)
                        println(antiX)
                        println(antiY)
                    }
                }

                var pointBeforeX = firstPair.first - antiX
                var pointBeforeY = firstPair.second - antiY
                while(pointBeforeY >= 0 && pointBeforeY < matrix.size && pointBeforeX >= 0 && pointBeforeX < matrix.size ) {
                    try {
                        val getPoint = matrix[pointBeforeX][pointBeforeY]
                        antinodeCoordinates.add(Pair(pointBeforeX, pointBeforeY))
                        pointBeforeX -= antiX
                        pointBeforeY -= antiY
                    } catch (e: IndexOutOfBoundsException) {
                        println(firstPair)
                        println(secondPair)
                        println(antiX)
                        println(antiY)
                    }
                }
            }
        }
    }
    println(antinodeCoordinates.toString())
    println(antinodeCoordinates.size)
    var count = 0
    for(antiNode in antinodeCoordinates) {
        val c = matrix[antiNode.first][antiNode.second]
        if(c == '.') {
            matrix[antiNode.first][antiNode.second] = 'X'
            count++
        }
    }
    matrix.forEach {
        it.forEach{c ->
            print(c)
        }
        println()
    }
}
