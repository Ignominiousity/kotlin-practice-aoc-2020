import java.io.File
import java.math.BigInteger


fun List<BigInteger>.slidingWindow(target: BigInteger) : BigInteger {
    var lp = 0
    var rp = 0
    var goingRight = true
    var sum = this[0]
    while (sum !=  target) {
        if (goingRight) {
            rp++
            sum+=this[rp]
            if (sum>target) {
                goingRight = false
                sum-=this[lp]
                lp++
            }
        } else {
            sum-=this[rp]
            rp--
            if (sum<target) {
                goingRight = true
                sum-=this[lp]
                lp++
            }
        }
    }
    val finalList = this.subList(lp,rp)
    println(" $finalList,$rp,$lp")
    return finalList.max()+finalList.min()

}
fun main() {
    val input = File("src/Day09.txt").readLines().map{it.toBigInteger()}
    val preamble : MutableList<BigInteger> = input.subList(0,25).toMutableList()
    val isNotSumOf : MutableList<BigInteger> = mutableListOf()
    for (no in input.indices) {
        if (no<25) continue
        val complement : List<BigInteger> = preamble.map{input[no]-it}
        when {
            preamble.none { it in complement } -> isNotSumOf.add(input[no])
        }
        preamble.removeFirst()
        preamble.add(input[no])
    }
    val target = isNotSumOf.first()
    println(target)
    println(input.slidingWindow(target))
}
