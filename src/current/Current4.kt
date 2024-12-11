//import java.util.*
//
//fun main() {
//
//    var count = 0;
//    while(startLongs.isNotEmpty()) {
//        var current = startLongs.removeFirst()
//        val second = current.second
//        for(j in second..<25) {
//
//            if(j == 24){
//            count++
//                }
//            if(current.first == 0L) {
//                current = Pair(1, j)
//            } else {
//                val toString = current.first.toString()
//                if (toString.length % 2 == 0) {
//                    current = Pair(toString.substring(0, toString.length / 2).toLong(), j)
//                    startLongs.add(Pair(toString.substring(toString.length / 2, toString.length).toLong(), j))
//                } else {
//                    current = Pair(current.first * 2024L, j)
//                }
//            }
//        }
//    }
//    println(count)
//}
//
//
//private fun dfs(
//    current: Long,
//    count: Long
//): Long {
//    var currentSplitCount = 0L
//    if (count == 75L) {
//        return 1
//    }
//
//    if (current == 0L) {
//        currentSplitCount += dfs(1, count+1);
//    } else {
//        val toString = current.toString()
//        if (toString.length % 2 == 0) {
//           val split1 =  dfs(toString.substring(0, toString.length / 2).toLong(), count + 1)
//            val split2 = dfs(toString.substring(toString.length / 2, toString.length).toLong(), count + 1)
//            currentSplitCount = split1 + split2
//        } else {
//            currentSplitCount =  dfs(current * 2024L, count + 1)
//        }
//    }
//
//    return currentSplitCount
//}