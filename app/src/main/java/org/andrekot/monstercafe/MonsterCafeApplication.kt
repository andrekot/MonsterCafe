package org.andrekot.monstercafe

import android.app.Application
import org.andrekot.monstercafe.presenter.MonsterCafePresenter

class MonsterCafeApplication: Application() {

    companion object {

        var instance: MonsterCafeApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext as MonsterCafeApplication
    }

    val presenter = MonsterCafePresenter()
}

fun <T> presenter(builder: MonsterCafePresenter.()->T): T =
    MonsterCafeApplication.instance!!.presenter.builder()