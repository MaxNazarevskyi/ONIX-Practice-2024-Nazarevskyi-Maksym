import kotlin.random.Random

fun main() {
    val game = Game()
    game.start()
}

class Game {
    private val secretNumber = Random.nextInt(1, 100)
    private var currentPlayer: Player = HumanPlayer()
    private var nextPlayer: Player = ComputerPlayer(secretNumber)

    fun start() {
        currentPlayer.setName()
        println("Game \"Guess The Number\"")
        println("Guess number from 0 to 100")

        while (true) {
            val guess = currentPlayer.makeGuess()
            println("Player ${currentPlayer.name()} guessed: $guess")

            if (checkGuess(guess)) {
                println("Congratulations! ${currentPlayer.name()} guessed the number! Number of attempts: ${currentPlayer.attempts}")
                if(currentPlayer.name() == "Bot") {
                    println("Attempts for ${currentPlayer.name()}: ${currentPlayer.attempts}")
                    println("Attempts for ${nextPlayer.name()}: ${nextPlayer.attempts}")
                    println("The winner is ${getWinner()}!")
                    println("Game over!")
                }
                switchPlayers()
            } else {
                println("Try again")
            }
        }
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

    private fun switchPlayers() {
        currentPlayer = nextPlayer.also { nextPlayer = currentPlayer }
    }

    private fun getWinner(): String? {
        return if (currentPlayer.attempts < nextPlayer.attempts) {
            currentPlayer.name()
        } else {
            nextPlayer.name()
        }
    }
}

interface Player {
    fun makeGuess(): Int
    fun name(): String?
    fun setName()
    var attempts: Int
}

class HumanPlayer : Player {
    private var playerName: String? = null
    override var attempts: Int = 0

    override fun makeGuess(): Int {
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

class ComputerPlayer(private val secretNumber: Int) : Player {
    override var attempts: Int = 0
    private var lastGuess = Random.nextInt(1, 100)

    override fun makeGuess(): Int {
        val guess = when {
            lastGuess < secretNumber -> {
                lastGuess += Random.nextInt(1, 11)
                lastGuess
            }
            lastGuess > secretNumber -> {
                lastGuess -= Random.nextInt(1, 11)
                lastGuess
            }
            else -> Random.nextInt(secretNumber - 10, secretNumber + 11)
        }
        Thread.sleep(750)
        return guess
    }

    override fun name(): String {
        return "Bot"
    }

    override fun setName() {
    }
}
