fun main() {
    fun part1(input: List<String>): Int { // counting instances of letters in string
        val height = input.size
        val width = input[0].length
        val scaling = height / 3 * width + 1
        val trail = input.map{it.repeat(scaling)} // we can use modulo expression instead of repeating the string
        var x = 0
        var y = 0
        var count = 0
        while (y < height) {
            if (trail[y][x] == '#') {
                count++
            }
            x+=3
            y++
        }
        return count
    }

    fun part2(input: List<String>): Long {
        val height = input.size
        val width = input[0].length
        val scaling = height*7 / width  + 2
        val trail = input.map { it.repeat(scaling) }
        var prod:Long = 1
        val slopes : List<Pair<Int,Int>> = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
        for (slope in slopes) { // we may also extract the previous function and take slopes as input
            var count = 0
            var x = 0
            var y = 0
            val (a,b) = slope
            while (y < height) {
                if (trail[y][x] == '#') {
                    count++
                }
                x += a
                y += b
            }
            prod *= count // Integer overflow, we can use Long or BigInteger
        }
        println(prod)
        return prod
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 7)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
