package com.profilizer.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.AttrRes
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.profilizer.ui.OnItemClickListener

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun AppCompatActivity.setTitle(@StringRes resId: Int, toolbar: Toolbar) {
    toolbar.apply {
        setSupportActionBar(this)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        title = getString(resId)
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Parcelable> createParcel(createFromParcel : (Parcel) -> T) : Parcelable.Creator<T> =
        object : Parcelable.Creator<T> {
            override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)

            override fun createFromParcel(source: Parcel?): T = createFromParcel(source)
        }

fun RecyclerView.addOnItemClickListener(itemClickListener: OnItemClickListener) {
    val attach = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            view.setOnClickListener { itemClickListener.onItemClick(view) }
        }
        override fun onChildViewDetachedFromWindow(view: View?) {}
    }
    this.addOnChildAttachStateChangeListener(attach)
}

fun Context.tintDrawable(@ColorRes color: Int, @DrawableRes drawableId : Int): Drawable {
    val drawable = ContextCompat.getDrawable(this, drawableId)
    if (drawable != null) {
        DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(this, color))
        return drawable
    }
    throw IllegalArgumentException("Drawable not found")
}