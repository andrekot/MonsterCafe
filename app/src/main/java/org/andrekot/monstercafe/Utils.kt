package org.andrekot.monstercafe

/*Created by Andrekot on 07/10/18*/

import java.util.Random

fun ClosedRange<Int>.random() = Random().nextInt((endInclusive + 1) - start) +  start