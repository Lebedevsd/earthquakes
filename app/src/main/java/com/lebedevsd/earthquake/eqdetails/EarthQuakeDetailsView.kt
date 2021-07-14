package com.lebedevsd.earthquake.eqdetails

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class EarthQuakeDetailsView
@JvmOverloads
constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var listener: ViewContract = ViewContract.NULL

    fun setListener(listener: ViewContract) {
        this.listener = listener
    }

    fun render(model: EarthQuakeDetailsModel) {

    }

}

interface ViewContract {
    object NULL : ViewContract
}
