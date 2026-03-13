import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() { // alternative would be to build a set of characters from string s,  pt1 we count number in set,
    // pt 2 we build sets per line and calculate intersection
    val answers: List<String> = Path("src/Day06_test.txt").readText().trim().split("\n\n","\r\n\r\n")
    println(answers.map{Answer.fromString(it)}.map {it.countOfYes() }.reduce{ a, b -> a+b})
    println(answers.map{Answer.fromString(it)}.map {it.countOfAllYes() }.reduce{a,b -> a+b})
}

class Answer(private val groupSize : Int, private val answerYesList: MutableList<Int>) {
    companion object {
        fun fromString(s: String): Answer {
            val groupSize = s.split("\n").size
            val letterToNumberMap: Map<Char,Int> = ('a'..'z').associateWith{it-'a'}
            val answerYesList = MutableList(26) { 0 }
            for (c in s.lines().joinToString("")) {
                answerYesList[letterToNumberMap.getOrElse(c) { 27 }] += 1
            }
            return Answer(groupSize,answerYesList)
        }
    }
    fun countOfYes() : Int{
        return answerYesList.map{if (it>0) {
            1
        } else {
            0 }
        }.reduce{ a,b -> a+b}
    }
    fun countOfAllYes(): Int {
        return answerYesList.map{if (it == groupSize) {
            1
        } else {
            0 }
        }.reduce{ a,b -> a+b}
    }


}