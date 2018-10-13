package org.andrekot.monstercafe

/*Created by Andrekot on 07/10/18*/

import android.content.Context
import android.content.Intent
import android.view.View

enum class State {
    INIT(),
    FOLD_CARDS(),
    ROUND1,
    END_ROUND1,
    COUNT_POINTS_ROUND1,
    ROUND2,
    END_ROUND2,
    COUNT_POINTS_ROUND2,
    ROUND3,
    END_ROUND3,
    COUNT_POINTS_ROUND3,
    COUNT_POINTS_GAME,
    SHOW_RESULTS,
    RESTART
}

class Starter(val starter: (i: Intent)->Unit)

interface Presenter {
    fun setStarter(s: Starter)
    fun getCurrentState(): State
    fun setCurrentState(view: View, new_state: State, vararg args: Any)
    fun init(view: View, users: Int)
    fun stateFoldCards(view: View, players: Array<String?>)
    fun stateRound1(card: Card)
    fun stateRound2()
    fun stateRound3()
    fun stateEndRound1()
    fun stateEndRound2()
    fun stateEndRound3()
    fun stateCountPointsRound1()
    fun stateCountPointsRound2()
    fun stateCountPointsRound3()
    fun stateCountPointsGame()
    fun stateShowResults()
    fun restartGame(view: View)
}

abstract class GamePresenter: Presenter {
    abstract var state: State
    abstract var engine: Game

    override fun setCurrentState(view: View, new_state: State, vararg args: Any) {
        @Suppress("UNCHECKED_CAST")
        when (new_state) {
            State.INIT -> init(view, args[0].toString().toInt())
            State.FOLD_CARDS -> stateFoldCards(view, args[0] as Array<String?>)
            State.ROUND1 -> stateRound1(args[0] as Card)
            State.ROUND2 -> stateRound2()
            State.ROUND3 -> stateRound3()
            State.END_ROUND1 -> stateEndRound1()
            State.END_ROUND2 -> stateEndRound1()
            State.END_ROUND3 -> stateEndRound1()
            State.COUNT_POINTS_ROUND1 -> stateCountPointsRound1()
            State.COUNT_POINTS_ROUND2 -> stateCountPointsRound2()
            State.COUNT_POINTS_ROUND3 -> stateCountPointsRound3()
            State.COUNT_POINTS_GAME -> stateCountPointsGame()
            State.SHOW_RESULTS -> stateShowResults()
            State.RESTART -> restartGame(view)
        }
        this.state = new_state
    }
}

class MonsterCafePresenter: GamePresenter() {
    private var currentRound = 1
    private var currentPlayerIndex = 0
    override var state = State.INIT
    override lateinit var engine: Game
    var starter: (i: Intent) -> Unit = {}

    var currentPlayer: Player? = null
        get() = engine.playerCards.keys.elementAt(currentPlayerIndex)
    var currentPlayerCards: Array<Card>? = null
        get() = engine.playerCards[currentPlayer]?.toTypedArray()
    var cardSize: Int = 0
        get() = currentPlayerCards?.size ?: 0
    var currentPlayerField: Array<Card>? = null
        get() = engine.getPlayerField(currentPlayer!!)
    var fieldSize: Int = 0
        get() = currentPlayerField?.size ?: 0

    inline fun <reified T> startActivity(view: View) {
        val i = Intent(view.context, T::class.java)
        starter(i)
    }

    override fun setStarter(s: Starter) {
        starter = s.starter
    }

    override fun getCurrentState(): State {
        return state
    }

    override fun init(view: View, users: Int) {
        engine = Game()
        engine.playersCount = users
        startActivity<InitGame>(view)
    }

    override fun stateFoldCards(view: View, players: Array<String?>) {
        if (state != State.INIT) return
        engine.initGame(players)
        engine.firstCardFold()
        startActivity<GameActivity>(view)
    }

    override fun stateRound1(card: Card) {
        engine.userTurn(currentPlayer!!, card)
        if (currentPlayerIndex < engine.playersCount - 1)
        currentPlayerIndex++
        else currentPlayerIndex = 0
        engine.turnOnCards()
    }

    override fun stateCountPointsGame() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stateCountPointsRound1() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stateCountPointsRound2() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stateRound2() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stateCountPointsRound3() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stateRound3() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stateShowResults() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stateEndRound1() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stateEndRound2() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stateEndRound3() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getImageId(context: Context, card: Card): Pair<Int, Int> {
        return Pair(context.resources.getIdentifier(card.img, "drawable", context.packageName),
                context.resources.getIdentifier(card.smallImg, "drawable", context.packageName))
    }

    override fun restartGame(view: View) {
        currentPlayerIndex = 0
        startActivity<MainActivity>(view)
    }

    fun yesTurnClicked(card: Card?, view: View) {
        if (card == null) return
        when (currentRound) {
            1 -> setCurrentState(view, State.ROUND1, card)
            2 -> setCurrentState(view, State.ROUND2, card)
            3 -> setCurrentState(view, State.ROUND3, card)
        }
        startActivity<GameActivity>(view)
    }

    fun noClicked() {
        //
    }
}

