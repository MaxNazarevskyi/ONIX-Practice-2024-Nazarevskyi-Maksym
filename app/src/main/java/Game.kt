import kotlin.random.Random

fun main() {
    val game = Game()
    game.start()
}

class Game {
    private val secretNumber = Random.nextInt(0, 101)

    fun start() {
        println("Game \"Guess The Number\"")
        println("Guess number from 0 to 100")

        while (true) {
            print("Player typing: ")
            val guess = readln().toInt()

            if (checkGuess(guess)) {
                println("Congratulations! Your answer is correct!")
                return
            } else {
                println("Try again")
            }
        }
    }

    private fun checkGuess(guess: Int): Boolean {
        return when {
            guess < secretNumber -> {
                println("Secret number bigger")
                false
            }
            guess > secretNumber -> {
                println("Secret number smaller")
                false
            }
            else -> true
        }
    }
}