package org.andrekot.monstercafe.model

import org.andrekot.monstercafe.utils.random

/*Created by Andrekot on 07/10/18*/

val DEFAULT_DECK_CONFIG = mapOf(Gryz() to 6, Bambr() to 6, Ulit() to 5, Drakondt1() to 3
        , Drakondt2() to 4, Drakondt3() to 3, Chuding() to 4, Nosotyk() to 2,
        Koloban() to 2, Krolleg() to 5, Latte() to 3, Cheesecake() to 2)

class CardDeck(ini: Map<Card, Int>, private val total:Int) {

    private var cards:MutableList<Card> = mutableListOf()

    init {
        for(c in ini) {
            when (c.key){
                is Gryz -> cards.addAll(copyCard(c.value, ::Gryz))
                is Koloban -> cards.addAll(copyCard(c.value, ::Koloban))
                is Drakondt1 -> cards.addAll(copyCard(c.value, ::Drakondt1))
                is Drakondt2 -> cards.addAll(copyCard(c.value, ::Drakondt2))
                is Drakondt3 -> cards.addAll(copyCard(c.value, ::Drakondt3))
                is Bambr -> cards.addAll(copyCard(c.value, ::Bambr))
                is Ulit -> cards.addAll(copyCard(c.value, ::Ulit))
                is Chuding -> cards.addAll(copyCard(c.value, ::Chuding))
                is Nosotyk -> cards.addAll(copyCard(c.value, ::Nosotyk))
                is Krolleg -> cards.addAll(copyCard(c.value, ::Krolleg))
                is Latte -> cards.addAll(copyCard(c.value, ::Latte))
                is Cheesecake -> cards.addAll(copyCard(c.value, ::Cheesecake))
                else -> throw Exception("Неизвестный тип карт ${c.key}")
            }
        }
        if (cards.size != total) throw Exception("В колоде всего лишь ${cards.size} карт, а нужно для игры $total")
    }

    private fun <T> copyCard (num:Int, body: ()->T ): MutableList<T>{
        val listCards: MutableList<T> = mutableListOf()
        repeat(num) {listCards.add(body())}
        return listCards
    }

    fun deal(indata: Map<Player, Int>): MutableMap<Player, MutableList<Card>> {
        val out: MutableMap<Player, MutableList<Card>> = mutableMapOf()
        var rr: Int
        val rrList = mutableListOf<Int>()
        if (indata.values.sum() > total) {
            throw Exception("Карт в колоде не хватает!")
        }
        indata.forEach {
            val player = it.key
            val l: MutableList<Card> = mutableListOf()
            repeat(it.value){v ->
                do rr = (IntRange(0, total-1)).random()
                while (rr in rrList)
                l.add(v, cards[rr])
                cards[rr].owner = player
                rrList.add(rr)
            }
            out[it.key] = l
        }
        cards.removeAll(cards.filter { it.owner != null })
        return out
    }
}