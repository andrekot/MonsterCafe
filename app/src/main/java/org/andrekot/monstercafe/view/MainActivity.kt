package org.andrekot.monstercafe.view

/*
 * Simple card game 'Monster Cafe'
 * Rules here: https://printgames.ru/wp-content/uploads/2017/02/pravilachu.pdf
 * Created by Andrekot on 07/10/18
 * @author Andrew Martynets - Andrekot
 */

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.andrekot.monstercafe.R
import org.andrekot.monstercafe.presenter
import org.andrekot.monstercafe.presenter.model.GameState
import org.andrekot.monstercafe.utils.Starter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onStartClick(view: View) {
        val users = spinner_start.selectedItemPosition + 2
        Toast.makeText(applicationContext, "Старт игры! Игроков: $users", Toast.LENGTH_SHORT).show()
        presenter {
            setStarter(Starter(::startActivity))
            setCurrentState(view, GameState.INIT, null, arrayOf(users.toString()))
        }
    }
}
