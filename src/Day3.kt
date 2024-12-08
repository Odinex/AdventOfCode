fun main() {
    var sum = 0;
    val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
    readFile("Day3.txt")?.forEachLine {
        // "\\$[mul(\\d,\\d)]"
        regex.findAll(it).forEach { asd ->
            val (first, second) = asd.value.substring(4, asd.value.indexOf(")")).split(",")
            sum += first.toInt() * second.toInt()
            println(sum)
        }

    }
}