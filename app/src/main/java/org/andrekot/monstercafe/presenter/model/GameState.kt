package org.andrekot.monstercafe.presenter.model

enum class GameState {
    INIT,
    FOLD_CARDS,
    ROUND1,
    END_ROUND1,
    COUNT_POINTS_ROUND1,
    ROUND2,
    END_ROUND2,
    COUNT_POINTS_ROUND2,
    ROUND3,
    END_ROUND3,
    COUNT_POINTS_ROUND3,
    COUNT_POINTS_GAME,
    SHOW_RESULTS,
    RESTART
}