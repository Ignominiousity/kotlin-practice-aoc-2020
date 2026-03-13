import kotlin.text.Regex
fun main() {
    fun part1(input: List<String>): Int { // counting instances of letters in string
        var count = 0
        for (str in input) {
            val s1 = Regex(" ").split(str)
            val s2 = Regex("-").split(s1[0])
          //  println(s1)
          //  println(s2)
            val (a,b) = s2.map{it.toInt()}
            val c = s1[1][0]
            var rep = 0
            for (l in s1[2]) {
                if (l==(c)) {
                    rep++
                }
            }
            if (rep in a..b) {
                count++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int { // checking indexes
        var count = 0
        for (str in input) {
            val s1 = Regex(" ").split(str)
            val s2 = Regex("-").split(s1[0])
           // println(s1)
           // println(s2)
            val a =  s2[0].toInt(); val b = s2[1].toInt()
            val c = s1[1][0]
            if ((s1[2][a - 1]==c) xor (s1[2][b-1]==c)) {
                count++
            }
        }
        return count
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 1)
   // part1(testInput).println()
  //  part2(testInput).println()
    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
