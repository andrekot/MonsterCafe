package org.andrekot.monstercafe

/*Created by Andrekot on 07/10/18*/

const val GRYZ = "Грызь"
const val CHEESECAKE = "Чизкейк"
const val KOLOBAN = "Колобан"
const val DRAKONDT = "Дракондт"
const val BAMBR = "Бамбр"
const val ULIT = "Улит"
const val LATTE = "Карамельное латте"
const val NOSOTYK = "Носотык"
const val KROLLEG = "Кроллег"
const val CHUDING = "Чудинг"

val DEFAULT_DECK_CONFIG = mapOf(Gryz() to 6, Bambr() to 6, Ulit() to 5, Drakondt1() to 3
        , Drakondt2() to 4, Drakondt3() to 3, Chuding() to 4, Nosotyk() to 2,
        Koloban() to 2, Krolleg() to 5, Latte() to 3, Cheesecake() to 2)

class Player(val name: String)

//классы для карт
open class Card(open val name:String, var points:Int, var img:String? = null, var desc:String? = null) {
    var owner: Player? = null
    open val shortName: String
        get() = name.subSequence(0, 3).toString() + name.last() + "-" + points.toString()
    val smallImg: String?
        get() = img + "_small"
}

class Gryz: Card(GRYZ,0, "@drawable/gryz", "Каждые 2 Грызя приносят 5 очков")

class Koloban: Card(KOLOBAN,1, "@drawable/koloban", "Каждый Колобан приносит 1 очко, на Латте 3")

open class Drakondt(img: String?): Card(DRAKONDT,0, img, desc = "У кого больше всего голов, получает 6 очков") {
    open val heads: Short = 1
    override val name: String
        get() = "$DRAKONDT $heads"

    override val shortName: String
        get() = name.subSequence(0, 3).toString() + this.heads.toString() + "-" + points.toString()
}

class Drakondt1: Drakondt(img = "@drawable/drakondt_one") {
    override val heads: Short = 1
}

class Drakondt2: Drakondt(img = "@drawable/drakondt_two") {
    override val heads: Short = 2
}

class Drakondt3: Drakondt(img = "@drawable/drakondt_three") {
    override val heads: Short = 3
}

class Bambr: Card(BAMBR,0,"@drawable/bambr", "Каждые 3 Бамбра приносят 10 очков")

class Ulit: Card(ULIT,1,"@drawable/ulit", "Улит дает 1(1) 2(3) 3(5) 4(9) 5(14)")

class Chuding: Card(CHUDING,2,"@drawable/chuding", desc = "Чудинг дает 2 очка, на Латте 6")

class Nosotyk: Card(NOSOTYK,3,"@drawable/nosotyk", desc = "Носотык дает 3 очка, на Латте 9")

class Krolleg: Card(KROLLEG,0,"@drawable/krolleg", desc = "У кого больше Кроллегов 6 очков")

class Latte: Card(LATTE,0,"@drawable/latte", "На Латте можно класть карты")

class Cheesecake: Card(CHEESECAKE,0,"@drawable/cheesecake", "Чизкейк дает право выбросить 2 карты")

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