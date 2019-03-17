package org.andrekot.monstercafe.utils

/*Created by Andrekot on 07/10/18*/

import java.util.Random

fun ClosedRange<Int>.random() = Random().nextInt((endInclusive + 1) - start) +  start