package org.andrekot.monstercafe

/*Created by Andrekot on 07/10/18*/

class Game(conf: Map<Card, Int> = DEFAULT_DECK_CONFIG) {
    var playersCount: Int = 0
    private var total: Int = 0
    private lateinit var playerConf: Map<Player, Int>
    private var cardDeck: CardDeck
    lateinit var playerCards: MutableMap<Player, MutableList<Card>>

    init {
        conf.forEach { total += it.value }
        cardDeck = CardDeck(conf, total)
    }

    object gameField {
        //карты игроков на поле
        var fieldCards: MutableMap<Player, MutableList<Card>> = mutableMapOf()
        var fieldKrolleg: MutableMap<Player, MutableList<Card>> = mutableMapOf()
        var fieldLatte: MutableMap<Player, MutableMap<Card, Card?>> = mutableMapOf()
    }

    fun  getPlayerField(player: Player): Array<Card> {
        return gameField.fieldCards[player]?.toTypedArray() ?: arrayOf()
    }

    fun initGame(players: Array<String?>) {
        val cardsCount = 12 - players.size
        val playerConfIn: MutableMap<Player, Int> = players.map { Player(it!!) to cardsCount }.toMap().toMutableMap()
        //перемешиваем порядок игроков
        playerConf = playerConfIn.map { it.key to it.value }.shuffled().toMap()
    }

    fun firstCardFold() {
        playerCards = cardDeck.deal(playerConf)
    }

    private fun isLatteFree(player: Player): Card? {
        var freeLatte: Card? = null
        gameField.fieldLatte[player]?.forEach {
            if (it.value == null) {
                freeLatte = it.key
            }
        }
        return freeLatte
    }

    fun userTurn(player: Player, card: Card) {
        val cards = playerCards[player]
        var freeLatte: Card? = null
        val turnCards: MutableList<Card?> = mutableListOf()//карты которыми ходит игрок
        if (gameField.fieldKrolleg[player] == null) gameField.fieldKrolleg[player] = mutableListOf()
        if (gameField.fieldLatte[player] == null) gameField.fieldLatte[player] = mutableMapOf()
        when (card) {
            is Latte -> {
                turnCards.add(card)
                gameField.fieldLatte[player]?.put(card, null)
                freeLatte = isLatteFree(player)
            }
            is Krolleg -> {
                turnCards.add(card)
                gameField.fieldKrolleg[player]?.add(card)
            }
            else -> turnCards.add(card)
        }
        if (freeLatte != null) {
            gameField.fieldLatte[player]?.set(freeLatte, card)
        }
        cards?.removeAll(turnCards) //кладем карты игрока на поле в колоду
        if (gameField.fieldCards[player] == null) gameField.fieldCards[player] = mutableListOf()
        turnCards.forEach {
            if (it != null) gameField.fieldCards[player]?.add(it)
        }
    }

    fun turnOnCards() {//игроки передают карты друг другу
        val pC: MutableMap<Player, MutableList<Card>> = playerCards
        val players = playerCards.keys.toList()
        var nextPlayerCards = mutableListOf<Card>()
        val lastPlayerCards = playerCards[players.last()] ?: mutableListOf()
        for ((player, cards) in pC) {
            if (nextPlayerCards.size == 0) {
                playerCards[player] = lastPlayerCards
            } else {
                playerCards[player] = nextPlayerCards
            }
            nextPlayerCards = cards
        }
    }

}