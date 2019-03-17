package org.andrekot.monstercafe.model

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
