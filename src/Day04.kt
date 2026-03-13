import kotlin.io.path.Path
import kotlin.io.path.readText


class Passport(private val map: Map<String, String>) {
    private val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid") // initialise once to check for all passports
    private val eyeColour = "amb blu brn gry grn hzl oth".split(" ")
    private val hexa = """#[0-9a-f]{6}""".toRegex() // Regex for # followed by 6 hexadecimals
    companion object {
        fun fromString(s: String): Passport {
            val fieldsWithValues: List<String> = s.split(" ","\n","\r\n")
            val map:Map<String,String> = fieldsWithValues.associate {
                val (key, value) = it.split(":")
                key to value
            }
            return Passport(map)

        }
    }
    fun hasAllRequiredFields(): Boolean {
        return map.keys.containsAll(requiredFields)
    }

    fun hasAllFieldsValid(): Boolean =
        map.all {(key,value) ->
            when(key) {
                "byr" -> value.length == 4 && value.toIntOrNull() in 1920..2002
                "iyr" -> value.length == 4 && value.toIntOrNull() in 2010..2020
                "eyr" -> value.length == 4 && value.toIntOrNull() in 2020..2030
                "pid" -> value.length == 9 && value.all(Char::isDigit)
                "ecl" -> value in eyeColour
                "hgt" -> when (value.takeLast(2)) {
                    "cm" -> value.removeSuffix("cm").toIntOrNull() in 150..193
                    "in" -> value.removeSuffix("in").toIntOrNull() in 59..76
                    else -> false
                }

                "hcl" -> value matches hexa
                else -> true
            }
        }
}


fun main() {
    val passports = Path("src/Day04.txt").readText().trim().split("\n\n","\r\n\r\n").map{Passport.fromString(it)}
    // passports are separated by an empty line, so we split by two end of lines
    println(passports.count(Passport::hasAllRequiredFields))
    println(passports.count{it.hasAllFieldsValid() && it.hasAllRequiredFields()})


}