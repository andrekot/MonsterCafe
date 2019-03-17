package org.andrekot.monstercafe.presenter.contract

import android.view.View
import org.andrekot.monstercafe.model.Card
import org.andrekot.monstercafe.presenter.model.GameState
import org.andrekot.monstercafe.utils.Starter

interface PresenterContract {

    fun setStarter(s: Starter)
    fun getCurrentState(): GameState
    fun setCurrentState(view: View, new_state: GameState, cards: Array<Card>?, args: Array<String?>? = null)
    fun init(view: View, users: Int)
    fun stateFoldCards(view: View, players: Array<String?>)
    fun stateRound1(cards: Array<Card>)
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