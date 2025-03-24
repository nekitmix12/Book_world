package hits.tsu.presentation.recycler_view

import hits.tsu.databinding.CarouselLayoutBinding
import hits.tsu.databinding.RecyclerViewBinding
import hits.tsu.presentation.models.NewCarouselModel

class CarouselHolder(
    binding: CarouselLayoutBinding,
) :
    BaseViewHolder<CarouselLayoutBinding, NewCarouselModel>(binding) {
    override fun onBinding(item: NewCarouselModel) = with(binding) {
           // imageView.setImageDrawable(item.image)
    }
}