import kotlin.random.Random

fun main() {
    val game = Game()
    game.start()
}

class Game {
    private val secretNumber = Random.nextInt(0, 101)
    private var currentPlayer: Player = HumanPlayer()
    private var nextPlayer: Player = ComputerPlayer()

    fun start() {
        println("Game \"Guess The Number\"")
        println("Guess number from 0 to 100")

        while (true) {
            if (currentPlayer.name() == null) {
                currentPlayer.setName()
            }

            if (currentPlayer is HumanPlayer) {
                print("Player ${currentPlayer.name()} typing: ")
                val guess = readln().toInt()

                if (checkGuess(guess)) {
                    println("Congratulations! Your answer is correct! Number of attempts: ${currentPlayer.attempts}")
                    currentPlayer = nextPlayer.also { nextPlayer = currentPlayer }
                } else {
                    println("Try again")
                }
            } else if (currentPlayer is ComputerPlayer) {
                val guess = currentPlayer.guessNumber()
                println("Player ${currentPlayer.name()} guessed: $guess")

                if (checkGuess(guess)) {
                    println("Congratulations! ${currentPlayer.name()} guessed the number! Number of attempts: ${currentPlayer.attempts}")
                    currentPlayer = nextPlayer.also { nextPlayer = currentPlayer }  // Switching
                } else {
                    println("Let me try again...")
                }
            }
        }
    }

    private fun switchPlayers() {
        currentPlayer = nextPlayer.also { nextPlayer = currentPlayer }
    }

    private fun checkGuess(guess: Int): Boolean {
        currentPlayer.attempts++
        return when {
            guess < secretNumber -> {
                println("Secret number is bigger")
                false
            }
            guess > secretNumber -> {
                println("Secret number is smaller")
                false
            }
            else -> true
        }
    }
}

interface Player {
    fun guessNumber(): Int
    fun name(): String?
    fun setName()
    var attempts: Int
}

class HumanPlayer : Player {
    private var playerName: String? = null
    override var attempts: Int = 0

    override fun guessNumber(): Int {
        return readLine()?.toIntOrNull() ?: -1
    }

    override fun name(): String? {
        return playerName
    }

    override fun setName() {
        print("Your name: ")
        playerName = readLine()
    }
}

class ComputerPlayer : Player {
    override var attempts: Int = 0

    override fun guessNumber(): Int {
        return Random.nextInt(0, 101)
    }

    override fun name(): String? {
        return "Bot"
    }

    override fun setName() {
    }
}