fun main() {
    fun part1(input: List<String>): Int { // 2-Sum
        val processed = input.map{it.toInt()}
        val complement = processed.map{2020-it}
        val a = processed.filter{it in complement }
        return a.first()*a.last()
    }
//
    fun part2(input: List<String>): Int { // 3-Sum
        val processed = input.map{it.toInt()}
        for (num in processed) {
            val complement = processed.map{2020-num-it}
            val a = processed.filter{it in complement }
            if (a.size==(2)) {
                return num*a.first()*a.last()
            }
        }
        return 0


    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 514579)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
