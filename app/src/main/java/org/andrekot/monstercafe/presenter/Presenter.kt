package org.andrekot.monstercafe.presenter

/*Created by Andrekot on 07/10/18*/

import android.content.Context
import android.content.Intent
import android.view.View
import org.andrekot.monstercafe.engine.Game
import org.andrekot.monstercafe.model.Card
import org.andrekot.monstercafe.model.Cheesecake
import org.andrekot.monstercafe.model.Player
import org.andrekot.monstercafe.presenter.contract.PresenterContract
import org.andrekot.monstercafe.presenter.model.GameState
import org.andrekot.monstercafe.utils.Starter
import org.andrekot.monstercafe.view.InitGameActivity
import org.andrekot.monstercafe.view.CheesecakeActivity
import org.andrekot.monstercafe.view.GameActivity
import org.andrekot.monstercafe.view.MainActivity

abstract class GamePresenter: PresenterContract {
    abstract var state: GameState
    abstract var engine: Game

    override fun setCurrentState(view: View, new_state: GameState, cards: Array<Card>?, args: Array<String?>?) {
        when (new_state) {
            GameState.INIT -> init(view, args!![0].toString().toInt())
            GameState.FOLD_CARDS -> stateFoldCards(view, args!!)
            GameState.ROUND1 -> stateRound1(cards!!)
            GameState.ROUND2 -> stateRound2()
            GameState.ROUND3 -> stateRound3()
            GameState.END_ROUND1 -> stateEndRound1()
            GameState.END_ROUND2 -> stateEndRound1()
            GameState.END_ROUND3 -> stateEndRound1()
            GameState.COUNT_POINTS_ROUND1 -> stateCountPointsRound1()
            GameState.COUNT_POINTS_ROUND2 -> stateCountPointsRound2()
            GameState.COUNT_POINTS_ROUND3 -> stateCountPointsRound3()
            GameState.COUNT_POINTS_GAME -> stateCountPointsGame()
            GameState.SHOW_RESULTS -> stateShowResults()
            GameState.RESTART -> restartGame(view)
        }
        this.state = new_state
    }
}

class MonsterCafePresenter: GamePresenter() {
    private var currentRound = 1
    private var currentPlayerIndex = 0
    override var state = GameState.INIT
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

    override fun getCurrentState(): GameState {
        return state
    }

    override fun init(view: View, users: Int) {
        engine = Game()
        engine.playersCount = users
        startActivity<InitGameActivity>(view)
    }

    override fun stateFoldCards(view: View, players: Array<String?>) {
        if (state != GameState.INIT) return
        engine.initGame(players)
        engine.firstCardFold()
        startActivity<GameActivity>(view)
    }

    override fun stateRound1(cards: Array<Card>) {
        cards.forEach {
            engine.userTurn(currentPlayer!!, it)
        }
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

    private fun doCheesecake(view: View) {
        startActivity<CheesecakeActivity>(view)
    }

    fun yesTurnClicked( view: View, card: Array<Card>) {
        if (card.isEmpty()) return
        if (card[0] is Cheesecake) doCheesecake(view)
        else {
            when (currentRound) {
                1 -> setCurrentState(view, GameState.ROUND1, card)
                2 -> setCurrentState(view, GameState.ROUND2, card)
                3 -> setCurrentState(view, GameState.ROUND3, card)
            }
            startActivity<GameActivity>(view)
        }
    }

    fun noClicked() {
        //
    }
}

