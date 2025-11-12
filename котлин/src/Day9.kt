fun main() {

    readFile("Day9.txt")?.forEachLine { line ->
        var numbers = mutableListOf<String>()
        val fileMap = mutableMapOf<Int,Int>()
        var fileIdSequence = 0
        val split = line.toList().map { it.toString().toInt()}
        val dot = "."
        for((index, number) in split.withIndex()) {
            if(index % 2 == 0) {
                for(i in 0..<number) {
                    numbers.add(fileIdSequence.toString())
                }
                fileMap[fileIdSequence] = number
                fileIdSequence++
            } else {
                for(i in 0..<number) {
                    numbers.add(dot)
                }
            }
        }

        val keys = fileMap.keys.toList().sortedDescending()
        var lastFormattedValue = 0
        for(keyId in keys.indices) {
            var numberOfRepetitions = fileMap[keys[keyId]]!!
            val numberToString = keys[keyId].toString()
            val length = numberToString.length
            if(!numbers.subList(lastFormattedValue, numbers.size).contains(numberToString)) {
                break;
            }
            numbers = numbers.filterIndexed { index, it -> it != numberToString || it == dot || index <= lastFormattedValue}.toMutableList()
            for(i in lastFormattedValue..<numbers.size - length) {
                if(numberOfRepetitions == 0) {
                    break
                }
                if(numbers[i] == dot) {
                    var hasEnoughDots = true;
                    for(j in i+1..<i+length) {
                        if(numbers[j] != dot) {
                            hasEnoughDots = false;
                            break;
                        }
                    }
                    if(hasEnoughDots) {

                        for(j in i..<i+length) {
                            if(j < numbers.size) {
                                numbers.removeAt(j)
                            }
                        }
                        numbers.add(i, numberToString)
                        lastFormattedValue = i;
                        numberOfRepetitions--
                    }
                }
            }
            if(numberOfRepetitions > 0) {
                for(r in 0..numberOfRepetitions) {
                    numbers.add(numberToString)
                    numberOfRepetitions--
                }
            }

        }
        var mutableChars = mutableListOf<Char>()
        numbers.forEach {
            mutableChars.addAll(it.toList())
        }
        var checksum = 0L
        for((index, i) in mutableChars.withIndex()) {
            try {
                val number = i.toString().toLong()
                checksum += index.toLong() * number;
            } catch (_: Exception) {

            }
        }
        println(numbers.toString())
        println(checksum)

    }


}
