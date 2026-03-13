import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val input = Path("src/Day05.txt").readText().trim().lines()

    fun ans(input: List<String>) {
        var maxID = 0
        val listID: MutableList<Int> = mutableListOf<Int>()
        for (line in input) {
            var leftRow = 0
            var rightRow = 127
            var leftCol = 0
            var rightCol = 7
            for (index in line.indices) {
                val s = line[index].toString()
                when (s) {
                    "F" -> {
                        rightRow -= (rightRow - leftRow ) / 2 +1
                    }

                    "B" -> {
                        leftRow += (rightRow - leftRow ) / 2 +1
                    }

                    "L" -> {
                        rightCol -= (rightCol - leftCol ) / 2 +1
                    }

                    else -> {
                        leftCol += (rightCol - leftCol ) / 2 +1
                    }
                }
            }
            println(listOf(leftRow,rightRow,leftCol,rightCol))
            val id = leftRow * 8 + leftCol
            listID.add(id)
            if (id > maxID) {
                maxID = id
            }
        }
        listID.sort()
        println(listID.last())
        var curr=listID[0]
        for (i in listID.indices) {
            if (i==0) {
                continue
            } else {
                if (listID[i]-curr>1) {
                    println(listID[i])
                    println(curr)
                }
                curr=listID[i]
            }
        }
    }
    ans(input)
}