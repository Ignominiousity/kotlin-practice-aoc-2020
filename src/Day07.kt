import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val rulebook1 : MutableMap<String,MutableList<String>> = mutableMapOf<String,MutableList<String>>()
    val rulebook2 : MutableMap<String,Set<String>> = mutableMapOf<String,Set<String>>()
    val rules: List<String> = Path("src/Day07.txt").readText().trim().split("\n")
    for (rule in rules) {
        val words = rule.split(" ")
        var index = 2
        val containerBag = words[0] + " " + words[1]
        var noBag = true
        while (index < words.size) {
            val word = words[index]
            if (word matches ("\\d+").toRegex()) {
                val pc = words[index+1]+" "+words[index+2]
                noBag = false
                if (rulebook2.containsKey(containerBag)) {
                    rulebook2[containerBag]= rulebook2[containerBag]!! + setOf<String>(word+ " " +pc)
                } else {
                    rulebook2.put(containerBag, mutableSetOf<String>(word+ " " +pc))
                }

                if (rulebook1.containsKey(pc)) {
                    rulebook1[pc]!!.add(containerBag)
                } else {
                    rulebook1.put(pc, mutableListOf<String>(containerBag))
                }
                index+=2
            } else {
                index++
            }
        }
        if (noBag) {
            rulebook2.put(containerBag, mutableSetOf<String>())
        }

    }
    val containGold : MutableSet<String> = mutableSetOf<String>()
    val colors : MutableList<String> = mutableListOf<String>("shiny gold")
    while (colors.isNotEmpty()) {
        val c = colors[0]
        colors.removeAt(0)
        val listOfColors = rulebook1.getOrElse(c, { mutableListOf<String>() })
        for (color in listOfColors) {
            if (containGold.contains(color)) {
                continue
            } else {
                colors.add(color)
            }
        }
        containGold.addAll(listOfColors)
    }

    println(containGold.size)
    println(rulebook2.getChildren("shiny gold"))

}

fun MutableMap<String,Set<String>>.getChildren(s : String) : Int {
    val children = getOrDefault(s,setOf<String>())
    if (children.isEmpty()) return 0
    var total = 0
    for (child in children) {
        val bag = child.split(" ")
        val count = bag[0].toInt()
        total += count + count * getChildren(bag[1] + " " + bag[2])
    }
    return total
}