package nekit.corporation.details.chapter.chapter

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.parcelize.Parcelize
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.details.chapter.bottom_sheet.BottomSheetComponent
import nekit.corporation.details.chapter.bottom_sheet.BottomSheetComponentImpl
import nekit.corporation.details.chapter.side_sheet.SideSheetComponent
import nekit.corporation.details.chapter.side_sheet.SideSheetComponentImpl
import nekit.corporation.details.chapter.toolbar.ToolbarComponent
import nekit.corporation.details.chapter.toolbar.ToolbarComponentImpl
import nekit.corporation.details.models.ShortChapterModel

@ContributesAssistedFactory(AppScope::class, ChapterComponent.Factory::class)
class ChapterComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext, chapterId: String, onClose: () -> Unit
) : ComponentContext by componentContext, ChapterComponent {

    private val bottomNavigation = SlotNavigation<ChildConfig.BottomSheet>()
    private val sideNavigation = SlotNavigation<ChildConfig.SideSheet>()


    override val bottomSheet: Value<ChildSlot<*, BottomSheetComponent>> = childSlot(
        source = bottomNavigation, key = "BottomSlot", handleBackButton = true
    ) { _, childCtx ->
        BottomSheetComponentImpl(
            componentContext = childCtx, onDismissClick = bottomNavigation::dismiss
        )
    }

    override val sideSheet: Value<ChildSlot<*, SideSheetComponent>> = childSlot(
        source = sideNavigation, key = "BottomSlot", handleBackButton = true
    ) { _, childCtx ->
        SideSheetComponentImpl(
            componentContext = childCtx, onDismissClick = sideNavigation::dismiss
        )
    }
    override val toolbarComponent: ToolbarComponent = ToolbarComponentImpl(
        componentContext = childContext(key = "toolbar"),
        onPlay = this::onChangePlaying,
        onNextChapterClick = this::changeOnNextChapter,
        onPrevChapterClick = this::changeOnPrevChapter,
        openSettings = this::openSettings,
        openChaptersList = this::openChapters,
    )
    override val state = MutableStateFlow(
        ChapterState(
            isPlay = false,
            isInteracted = true,
            text = persistentListOf(),
            chapter = ShortChapterModel(
                "", "", nekit.corporation.details.models.ChapterState.InProgress
            ),
            bookName = "",
            loading = true
        )
    )

    override fun stopPlaying() {
    }

    override fun parse() {
    }

    override fun startInterrupted() {
    }

    override fun stopInterrupted() {
    }

    override fun onBackClick() {
    }

    override fun onChangePlaying() {

    }

    override fun changeOnNextChapter() {
    }

    override fun changeOnPrevChapter() {
    }

    override fun openSettings() {

    }

    override fun openChapters() {
    }

    @Parcelize
    private sealed interface ChildConfig : Parcelable {
        @Parcelize
        data object BottomSheet : ChildConfig, Parcelable

        @Parcelize
        data class SideSheet(val chapterId: String) : ChildConfig, Parcelable
    }
}