package hits.tsu.presentation.recycler_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import hits.tsu.R
import hits.tsu.databinding.CarouselLayoutBinding
import hits.tsu.presentation.models.NewCarouselModel

class CarouselDelegate(
) : Delegate<CarouselLayoutBinding, NewCarouselModel> {

    override fun isRelativeItem(item: Item): Boolean = item is NewCarouselModel

    override fun getLayoutId(): Int = R.layout.recycler_view

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<CarouselLayoutBinding, NewCarouselModel> =
        CarouselHolder(CarouselLayoutBinding.inflate(layoutInflater, parent, false))

    override fun getDiffUtil(): DiffUtil.ItemCallback<NewCarouselModel> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<NewCarouselModel>() {
        override fun areItemsTheSame(oldItem: NewCarouselModel, newItem: NewCarouselModel) = true

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: NewCarouselModel, newItem: NewCarouselModel) = true
    }

}