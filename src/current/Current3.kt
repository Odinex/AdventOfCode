//import kotlinx.coroutines.*
//import java.util.concurrent.atomic.AtomicLong
//
//private var general = AtomicLong()
//class AsyncDfs {
//
//    suspend fun countNumbersAtMaxDepth(startLongs: List<Long>) = coroutineScope {
//        startLongs.map { start ->
//            async {
//                dfs(start, 0)
//            }
//        }.awaitAll()
//    }
//
//    private suspend fun dfs(
//        current: Long,
//        depth: Int
//    ) = withContext(Dispatchers.Default) {
//        if (depth >= 75) {
//            general.addAndGet(1L)
//            return@withContext
//        }
//
//        if (current == 0L) {
//            dfs(1, depth+1)
//        }
//        else {
//            val toString = current.toString()
//            when {
//                toString.length % 2 == 0 -> {
//                    val mid = toString.length / 2
//                    val left = toString.substring(0, mid).toLong()
//                    val right = toString.substring(mid).toLong()
//                    dfs(left, depth + 1)
//                    dfs(right, depth + 1)
//                }
//
//                else -> dfs(current * 2024L, depth + 1)
//            }
//        }
//    }
//}
//
//suspend fun main() {
//
//    val dfs = AsyncDfs()
//    val result = dfs.countNumbersAtMaxDepth(startLongs)
//    println(result)
//}