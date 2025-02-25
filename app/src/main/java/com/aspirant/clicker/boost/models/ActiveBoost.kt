package com.aspirant.clicker.boost.models
import android.content.SharedPreferences
import com.aspirant.clicker.boost.AbsBoost
import kotlin.math.ceil

class ActiveBoost: AbsBoost() {
    var inc: Long = 0

    companion object{
        const val PRICE_COEF = 2.0
        const val INC_COEF = 1.5

        private var shared: SharedPreferences? = null
        private var sharedEditor: SharedPreferences.Editor? = null

        fun init(shared: SharedPreferences){
            this.shared = shared
            sharedEditor = shared.edit()
        }
    }


    override fun buy() {
        apply()
        level++
        price = ceil(price * PRICE_COEF).toLong()
        inc = ceil(inc * INC_COEF).toLong()
        save()
    }

    override fun load(id: Int) {
        this.id = id

        val getted_title = shared?.getString("boost_${id}_title", null)
        if (getted_title != null) {
            title = getted_title
            inc = shared?.getLong("boost_${id}_inc", 1)!!
            price = shared?.getLong("boost_${id}_price", 1)!!
            level = shared?.getInt("boost_${id}_level", 0)!!
        } else {
            //TODO
        }
    }

    override fun save() {
        sharedEditor?.putString("boost_${id}_title", title)
        sharedEditor?.putLong("boost_${id}_inc", inc)
        sharedEditor?.putLong("boost_${id}_price", price)
        sharedEditor?.putInt("boost_${id}_level", level)
        sharedEditor?.commit()

    }

    override fun apply() {
        var oldInc = shared?.getLong("inc", 1)
        if (oldInc != null) {
            sharedEditor?.putLong("inc", oldInc + inc)
            sharedEditor?.commit()
        }
    }
}