package hits.tsu.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import nekit.corporation.root.RootComponentImpl
import nekit.corporation.root.RootUi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = RootComponentImpl(defaultComponentContext())
        enableEdgeToEdge()
        setContent {
            nekit.corporation.common_ui.theme.BookWorldTheme {
                RootUi(rootComponent)
            }
        }
    }
}




