import utils.Utils


private var general = 0L
fun main() {


    while (startArray.any { charList -> charList.any { pair -> !pair.second } }) {
        for ((index, charArray) in startArray.withIndex()) {
            for ((charIndex, j) in charArray.withIndex()) {
                if (!j.second) {
                    val currentCoordinates = Pair(index, charIndex);
                    val relevantGroups: List<Pair<Char, MutableList<Pair<Int, Int>>>> =
                        getRelevantAdjacentGroups(j, currentCoordinates).toList()
                    if (relevantGroups.isEmpty()) {
                        val newGroup = Pair(j.first, mutableListOf<Pair<Int, Int>>())
                        newGroup.second.add(currentCoordinates)
                        groups.add(newGroup)
                    } else {
                        val currentGroup = relevantGroups[0]
                        for(i in 1..<relevantGroups.size) {
                            val next=relevantGroups[i]
                            currentGroup.second.addAll(next.second)
                        }
                        currentGroup.second.add(currentCoordinates)
                        groups.removeAll(relevantGroups.subList(1,relevantGroups.size))
                    }
                    startArray[index][charIndex] = Pair(j.first, true)
                }
            }

        }
        var total = 0L
        for((index, group) in groups.withIndex()) {
            var sides = mutableSetOf<Pair<Utils.Direction, Set<Pair<Int,Int>>>>();

            for(current in group.second) {

                for (direction in directions) {
                    val nextStep = Pair(current.first + direction.pair.first, current.second + direction.pair.second)
                    if (nextStep.first in startArray.indices && nextStep.second in startArray[nextStep.first].indices) {
                        val nextChar = startArray[nextStep.first][nextStep.second]
                        if(nextChar.first != group.first) {
                            val listOfSide = mutableSetOf<Pair<Int,Int>>()
                            listOfSide.add(nextStep)
                            for(perDir in Utils.Direction.getPerpendicular(direction)) {
                                var nexPStep = Pair(current.first + perDir.pair.first, current.second + perDir.pair.second)
                                while(group.second.contains(nexPStep)) {
                                    if (nexPStep.first in startArray.indices && nexPStep.second in startArray[nexPStep.first].indices) {
                                        val nextPChar = startArray[nexPStep.first][nexPStep.second]
                                        if (nextPChar.first == group.first) {
                                            val nextSideStep = Pair(
                                                nexPStep.first + direction.pair.first,
                                                nexPStep.second + direction.pair.second
                                            )
                                            if (nextSideStep.first in startArray.indices && nextSideStep.second in startArray[nextSideStep.first].indices) {
                                                val nextSideChar = startArray[nextSideStep.first][nextSideStep.second]
                                                if (nextSideChar.first != group.first) {
                                                    listOfSide.add(nextSideStep)
                                                } else {
                                                    break;
                                                }
                                            }

                                        }

                                    }
                                    nexPStep = Pair(nexPStep.first + perDir.pair.first, nexPStep.second + perDir.pair.second)
                                }
                            }
                            sides.add(Pair(direction, listOfSide))
                        }
                    } else {
                        val listOfSide = mutableSetOf<Pair<Int,Int>>()
                        listOfSide.add(nextStep)
                        for(perDir in Utils.Direction.getPerpendicular(direction)) {
                            var nexPStep = Pair(current.first + perDir.pair.first, current.second + perDir.pair.second)
                            while(group.second.contains(nexPStep)) {
                                if (nexPStep.first in startArray.indices && nexPStep.second in startArray[nexPStep.first].indices) {
                                    val nextPChar = startArray[nexPStep.first][nexPStep.second]
                                    if (nextPChar.first == group.first) {
                                        val nextSideStep = Pair(
                                            nexPStep.first + direction.pair.first,
                                            nexPStep.second + direction.pair.second
                                        )
                                        if (nextSideStep.first in startArray.indices && nextSideStep.second in startArray[nextSideStep.first].indices) {
                                            break;

                                        } else {
                                            listOfSide.add(nextSideStep)
                                        }

                                    }

                                }
                                nexPStep = Pair(nexPStep.first + perDir.pair.first, nexPStep.second + perDir.pair.second)
                            }
                        }
                        sides.add(Pair(direction, listOfSide))
                    }
                }
            }

            perimeterMap[index] = sides.size.toLong()
            total += sides.size * group.second.size
        }
        println(total)
    }
}

