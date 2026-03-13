import kotlin.io.path.Path
import kotlin.io.path.readText

data class State(val index: Int, val acc: Int )

sealed class Instruction(val action: (State)-> State)

fun Instruction(str: String): Instruction { // factory function to make Instructions
    val (ins: String, by: String) = str.split(" ")
    val value = by.toInt()
    return when (ins) {
        "nop" -> Nop(value)
        "jmp" -> Jmp(value)
        "acc" -> Acc(value)
        else -> throw Exception("Unknown instruction: $ins ($value)")
    }
}

class Nop(val value: Int) : Instruction({ State(it.index + 1, it.acc) })

class Jmp(val value: Int) : Instruction({ State(it.index + value, it.acc) })

class Acc(val value: Int) : Instruction({ State(it.index + 1, it.acc + value) })

fun runInstructions(instructions: List<Instruction>) : State {
    var state = State(0,0)
    val ranInstructions : MutableSet<Int> = mutableSetOf()
    while (state.index in instructions.indices) {
        val nextInstruction = instructions[state.index]
        state = nextInstruction.action(state)
        if (state.index in ranInstructions) return state
        ranInstructions.add(state.index)
    }
    return state

}

fun generateModifiedInstructions(instructions: List<Instruction>) : Sequence<List<Instruction>> =
    sequence {
        for ((index, ins) in instructions.withIndex()) {
            val modifiedInstructions = instructions.toMutableList()
            modifiedInstructions[index] = when(ins) {
                is Jmp -> Nop(ins.value)
                is Acc -> continue
                is Nop -> Jmp(ins.value)
            // no need for else since using sealed class means Instructions is "sealed" in this file
            }
            yield(modifiedInstructions) // one new set of instructions generated per changed row
        }

    }


fun main() {
    val instructions: List<Instruction> = Path("src/Day08.txt").readText().trim()
        .split("\n")
        .map { Instruction(it.trim()) } // trim removes \n, white space for line 10
    println(runInstructions(instructions)) // part 1 answer
    println(generateModifiedInstructions(instructions)
        .map { modifiedInstructions -> runInstructions(modifiedInstructions) }
        .first { state -> state.index !in instructions.indices  }
    ) // part 2 answer
}