package org.andrekot.monstercafe

/*Created by Andrekot on 07/10/18*/

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.text.Editable

class InitGame : AppCompatActivity() {

    lateinit var adapter: ContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_game)
        initRecyclerView()
    }

    fun onInitClick(view: View) {
        presenter.setCurrentState(view, State.FOLD_CARDS, null, adapter.names)
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_users)
        adapter = ContentAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }
}

class InitViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.init_game_item, parent, false)) {
    var name = itemView.findViewById<View>(R.id.user_name) as TextView
}

class ContentAdapter() : RecyclerView.Adapter<InitViewHolder>() {
    private var mNames: Array<String?> = arrayOfNulls(presenter.engine.playersCount)
    var names: Array<String?> = arrayOfNulls(presenter.engine.playersCount)

    init {
        repeat(presenter.engine.playersCount) {
            mNames[it] = "Игрок ${it+1}"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InitViewHolder {
        return InitViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: InitViewHolder, position: Int) {
        val pos = holder.adapterPosition
        holder.name.text = mNames[pos % mNames.size]
        names[pos] = holder.name.text.toString()
        holder.name.addTextChangedListener(
                object: TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        names[pos] = holder.name.text.toString()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }

        )
    }

    override fun getItemCount(): Int {
        return presenter.engine.playersCount
    }
}
