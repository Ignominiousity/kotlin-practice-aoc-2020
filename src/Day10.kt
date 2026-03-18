import java.io.File
import java.math.BigInteger


fun  List<Int>.voltDifferenceProduct() : Int {
    val oneVoltDifference = mutableListOf<Int>()
    val twoVoltDifference = mutableListOf<Int>()
    val threeVoltDifference = mutableListOf<Int>()
    var curr = 0
    for (adapter in this) {
        val difference = adapter-curr
        when (difference) {
            1 -> oneVoltDifference.add(adapter)
            2 -> twoVoltDifference.add(adapter) // honestly we can ignore this
            3 -> threeVoltDifference.add(adapter)
            else -> println(adapter)
        }
        curr= adapter
    }
    return oneVoltDifference.size*(threeVoltDifference.size+1) // add 1 for connection to device(not in adapters)
}


class Solution(private val adapters: List<Int>) {
    private val map = HashMap<Int, BigInteger>() // store our hash map, BigInteger to be safe

    fun numWays(adapter: Int) : BigInteger { //numWays from adapter to device
        if (adapter == adapters.last()) { // same as to last adapter since final volt difference is 3
            map[adapter] = 1.toBigInteger()
            return 1.toBigInteger()
        }
        else if (map.containsKey(adapter)) return map[adapter]!!
        val choices = adapters.filter{volt -> volt in adapter+1..adapter+3}
        var total = 0.toBigInteger()
        for (choice in choices) { // from current adapter the number of ways to the end
            // is the sum of ways to the end from the choices we have
            total += numWays(choice)
        }
        map[adapter] = total //hashing
        return total
    }
}



fun main() {
    var adapters = File("src/Day10.txt").readLines().map{it.toInt()}
    adapters + listOf(0)
    adapters = adapters.sorted()
    println(adapters.voltDifferenceProduct())// part 1
    println(Solution(adapters).numWays(0))
}