package org.andrekot.monstercafe.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cheesecake.*
import org.andrekot.monstercafe.R
import org.andrekot.monstercafe.model.Cheesecake
import org.andrekot.monstercafe.presenter

class CheesecakeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var firstCardAdapter: ArrayAdapter<String>
    lateinit var secondCardAdapter: ArrayAdapter<String>
    var lastPos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheesecake)
        //val playerCards//: MutableList<String> = mutableListOf()
        val playerCards = presenter {
            currentPlayerCards?.filter {
                /*val draw = this.resources.getDrawable(
                        presenter.getImageId(this, it).first, this.theme
                )
                playerCards.add(SpinnerCard(it.name, draw))*/
                it !is Cheesecake
            }!!.map { it.name }
        }
        firstCardAdapter = ArrayAdapter(this,
                    R.layout.spinner_card_item, R.id.textview, playerCards)
        spinner_first_card.adapter = firstCardAdapter
        spinner_first_card.setOnItemSelectedListener(this)

        secondCardAdapter = ArrayAdapter(this,
                    R.layout.spinner_card_item, R.id.textview, playerCards)
        spinner_second_card.adapter = secondCardAdapter
        frame_second_card.visibility = View.GONE
        button_cheesecake.setOnClickListener(::onCheesecakeClick)
        }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        frame_second_card.visibility = View.VISIBLE
        if (lastPos == -1) secondCardAdapter.remove(firstCardAdapter.getItem(position))
        else if (position != lastPos) {
            secondCardAdapter.remove(firstCardAdapter.getItem(position))
            secondCardAdapter.add(firstCardAdapter.getItem(lastPos))
            lastPos = position
        }
        Toast.makeText(this, "lastPos = $lastPos pos = $position", Toast.LENGTH_LONG).show()
        
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("not implemented") //remove comment
    }

    fun onCheesecakeClick(v: View) {
        val first_card = spinner_first_card?.selectedItemPosition
        val second_card = spinner_second_card?.selectedItemPosition
        if (first_card == null || second_card == null) AlertDialog.Builder(v.context)
                .setTitle(R.string.cheesecake_err)
                .setMessage(R.string.cheesecake_alert)
                .setPositiveButton(android.R.string.yes) { _, _ -> }
                .show()
        val playerCards = presenter { currentPlayerCards!! }
        presenter {
            yesTurnClicked(v, arrayOf(playerCards[first_card!!], playerCards[second_card!!]))
        }
        this.finish()
    }
}
