package hits.tsu.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import nekit.corporation.common_ui.theme.BookWorldTheme
import nekit.corporation.root.RootUi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent =
            DaggerAppDaggerComponent.create().rootComponentFactory(defaultComponentContext())
        enableEdgeToEdge()
        setContent {
            BookWorldTheme {
                RootUi(rootComponent)
            }
        }
    }
}




