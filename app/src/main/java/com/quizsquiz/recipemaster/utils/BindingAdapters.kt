package com.quizsquiz.recipemaster.utils

import android.graphics.LightingColorFilter
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.quizsquiz.recipemaster.R
import de.hdodenhof.circleimageview.CircleImageView


@BindingAdapter("app:src")
fun setMainImage(view: CircleImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_arrow_back_24)
        .into(view)
    view.colorFilter = LightingColorFilter(0xFFFFFF, 0x696969)
}
@BindingAdapter("app:setListOfIngredients")
fun setIngredientsList(view: TextView, list: List<String>?) {
    val string = StringBuilder()
    if (list != null) {
        for (s in list) {
            string.append("- $s")
            if (s != list.last()) {
                string.append("\n")
            }
        }
    }
    view.text = string.toString()
}
@BindingAdapter("app:setListOfPreparingSteps")
fun setPreparingList(view: TextView, list: List<String>?) {
    val string = StringBuilder()
    if (list != null) {
        var count = 0
        for (s in list) {
            count ++
            string.append("$count. $s")
            if (s != list.last()){
                string.append("\n")
                string.append("\n")
            }
        }
    }
    view.text = string.toString()
}