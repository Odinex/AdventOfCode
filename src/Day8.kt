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
            for (j in i+1..<coordinatesList.size) {
                val secondPair = coordinatesList[j]
                val antiX = secondPair.first - firstPair.first
                val antiY = secondPair.second - firstPair.second
                try {
                    val pointAfterX = secondPair.first + antiX
                    val pointAfterY = secondPair.second + antiY
                    val getPoint = matrix[pointAfterX][pointAfterY]
                    antinodeCoordinates.add(Pair(pointAfterX, pointAfterY))
                } catch (e: IndexOutOfBoundsException) {
                    println(firstPair)
                    println(secondPair)
                    println(antiX)
                    println(antiY)
                }
                try {
                    val pointBeforeX = firstPair.first - antiX
                    val pointBeforeY = firstPair.second - antiY
                    val getPoint = matrix[pointBeforeX][pointBeforeY]
                    antinodeCoordinates.add(Pair(pointBeforeX, pointBeforeY))
                } catch (e: IndexOutOfBoundsException) {
                    println(firstPair)
                    println(secondPair)
                    println(antiX)
                    println(antiY)
                }
            }
        }
    }
    println(antinodeCoordinates.toString())
    println(antinodeCoordinates.size)
    for(antiNode in antinodeCoordinates) {
        val c = matrix[antiNode.first][antiNode.second]
        if(c == '.') {
            matrix[antiNode.first][antiNode.second] = 'X'
        }
    }
    matrix.forEach {
        it.forEach{c ->
            print(c)
        }
        println()
    }
}
