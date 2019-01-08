package org.andrekot.monstercafe

/*Created by Andrekot on 07/10/18*/

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class GameActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerBottom: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initRecyclerView()
        findViewById<TextView>(R.id.player_name).text = StringBuilder().append(resources.getString(R.string.player_turn), " ${presenter.currentPlayer?.name}")
    }

    private fun initRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.rv_game)
        recyclerBottom = findViewById<RecyclerView>(R.id.rv_field)
        recyclerView.adapter = GameContentAdapter(recyclerView.context)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)

        recyclerBottom.adapter = BottomContentAdapter(recyclerBottom.context)
        recyclerBottom.layoutManager = LinearLayoutManager(recyclerBottom.context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun onNewGameClick(v: View) {
        AlertDialog.Builder(v.context)
                .setTitle(R.string.question_title)
                .setMessage(v.context.resources.getString(R.string.restart_game))
                .setPositiveButton(android.R.string.yes)
                { _, _ -> presenter.setCurrentState(v, State.RESTART, null) }
                .setNegativeButton(android.R.string.no) { _, _ -> presenter.noClicked() }
                .show()
    }
}

class GameViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.card_item, parent, false)) {
    var image = itemView.findViewById<View>(R.id.card_image) as ImageView
    var name = itemView.findViewById<View>(R.id.card_name) as TextView
    var desc = itemView.findViewById<View>(R.id.card_desc) as TextView
    var card: Card? = null
}

class GameContentAdapter(context: Context) : RecyclerView.Adapter<GameViewHolder>() {
    private var mNames: Array<String?>? = arrayOfNulls(presenter.cardSize)
    private var mDesc: Array<String?>? = arrayOfNulls(presenter.cardSize)
    private var mImages: Array<Drawable?>? = arrayOfNulls(presenter.cardSize)

    init {
        mNames = presenter.currentPlayerCards?.map { it.name }?.toTypedArray()
        mDesc = presenter.currentPlayerCards?.map { it.desc }?.toTypedArray()
        mImages = arrayOfNulls(presenter.cardSize)
        presenter.currentPlayerCards?.forEachIndexed { index, card ->
            try {
                mImages!![index] = context.resources.getDrawable(presenter.getImageId(context, card).first, context.theme)
            }
            catch (e: Exception){
                Toast.makeText(context, "${card.img}", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.name.text = mNames!![position % mNames!!.size]
        holder.desc.text = mDesc!![position % mDesc!!.size]
        holder.image.setImageDrawable(mImages!![position % mImages!!.size])
        holder.card = presenter.currentPlayerCards!![position % presenter.cardSize]
        holder.itemView.setOnClickListener {v ->
            AlertDialog.Builder(v.context)
                    .setTitle(R.string.question_title)
                    .setMessage(StringBuilder().append(v.context.resources.getString(R.string.question_message), " ${holder.card?.name}") )
                    .setPositiveButton(android.R.string.yes)
                    { _, _ -> presenter.yesTurnClicked(v, arrayOf(holder.card!!)) }
                    .setNegativeButton(android.R.string.no) { _, _ -> presenter.noClicked() }
                    .show()
            }
    }

    override fun getItemCount(): Int {
        return presenter.cardSize
    }
}

class BottomViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.small_card_item, parent, false)) {
    var image = itemView.findViewById<View>(R.id.image_small) as ImageView
}

class BottomContentAdapter(context: Context) : RecyclerView.Adapter<BottomViewHolder>() {
    private var mImages: Array<Drawable?> = arrayOfNulls(presenter.fieldSize)

    init {
        mImages = arrayOfNulls(presenter.fieldSize)
        presenter.currentPlayerField?.forEachIndexed { index, card ->
            try {
                mImages[index] = context.resources.getDrawable(presenter.getImageId(context, card).second, context.theme)
            }
            catch (e: Exception){
                Toast.makeText(context, "$card.name", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomViewHolder {
        return BottomViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: BottomViewHolder, position: Int) {
        holder.image.setImageDrawable(mImages[position % mImages.size])
    }

    override fun getItemCount(): Int {
        return presenter.fieldSize
    }
}
