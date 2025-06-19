package nekit.corporation.auth

import android.util.Log
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope
import nekit.corporation.common.Error
import nekit.corporation.common.componentCoroutineScope
import nekit.corporation.domain.EmptyEmail
import nekit.corporation.domain.EmptyPassword
import nekit.corporation.domain.InvalidEmail
import nekit.corporation.domain.InvalidPassword
import nekit.corporation.domain.RepeatNameOrEmail
import nekit.corporation.domain.models.RegisterRequest
import nekit.corporation.domain.usecases.RegisterUseCase
import nekit.corporation.domain.usecases.ValidateRegisterFormUseCase
import nekit.corporation.common.Result

@ContributesAssistedFactory(AppScope::class, SignInComponent.Factory::class)
class SignInComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted val onComplete: () -> Unit,
    private val validateRegisterFormUseCase: ValidateRegisterFormUseCase,
    private val registerUseCase: RegisterUseCase,
) : ComponentContext by componentContext, SignInComponent {
    override val state = MutableStateFlow(
        SignInState(carouselImages = persistentListOf())
    )

    private val coroutineScope = componentCoroutineScope()

    init {
        coroutineScope.launch {
            state.collect {
                if (it.password != "" && it.userName != "" && it.email != "")
                    state.value = state.value.copy(
                        isButtonActive = true
                    )
                else
                    state.value = state.value.copy(
                        isButtonActive = false
                    )
            }
        }
    }

    override fun onSignInClick() {
        state.value = state.value.copy(inProgress = true)
        coroutineScope.launch {
            var localState = state.value
            validateRegisterFormUseCase.execute(
                ValidateRegisterFormUseCase.Request(
                    RegisterRequest(
                        email = localState.email,
                        password = localState.password,
                        username = localState.userName,
                    )
                )
            ).collect {
                if (it is Result.Success) {
                    if (it.data.errors.isEmpty()) {
                        Log.i(TAG, "response go out")

                        registerUseCase.execute(
                            RegisterUseCase.Request(
                                RegisterRequest(
                                    email = localState.email,
                                    password = localState.password,
                                    username = localState.userName,
                                )
                            )
                        ).collect { registerResult ->
                            Log.i(TAG, "request come in")
                            when (registerResult) {
                                is Result.Success -> {
                                    onComplete()
                                }

                                is Result.Error -> {
                                    state.value = state.value.copy(
                                        emailError = null,
                                        passwordError = null,
                                        userNameError = null,
                                        inProgress = false
                                    )
                                    when(registerResult.exception){
                                        ""->{}
                                    }
                                    Log.e(TAG, registerResult.exception)
                                }
                            }
                        }
                    } else {
                        localState = state.value.copy(
                            emailError = null,
                            passwordError = null,
                            userNameError = null,
                        )

                        state.value =
                            errorCheck(localState, it.data.errors).copy(inProgress = false)
                        Log.e(TAG, state.value.toString() + "\n" + it.data.errors)
                    }
                }
            }
        }
    }

    private fun errorCheck(signInState: SignInState, errors: List<Error>): SignInState {
        var localState = signInState
        errors.forEach { error ->
            localState = when (error) {
                is EmptyPassword -> localState.copy(
                    passwordError = R.string.empty_password
                )

                is EmptyEmail -> localState.copy(emailError = R.string.empty_email)
                is InvalidEmail -> localState.copy(emailError = R.string.incorrect_email)
                is RepeatNameOrEmail -> localState.copy(
                    emailError = R.string.repeat_email_or_username,
                    userNameError = R.string.repeat_email_or_username
                )

                is InvalidPassword -> localState.copy(passwordError = R.string.incorrect_password)

                else -> localState
            }
        }
        return localState
    }

    override fun onEmailChange(email: String) {
        state.value = state.value.copy(
            email = email,
            emailIconRes = if (email.isBlank()) null else R.drawable.close
        )

    }

    override fun onNameChange(name: String) {
        state.value = state.value.copy(
            userName = name,
            nameIconRes = if (name.isBlank()) null else R.drawable.close
        )
    }

    override fun onPasswordChange(password: String) {
        state.value = state.value.copy(
            password = password,
            passwordIconRes = if (state.value.password != "") R.drawable.not_see else null
        )
    }

    override fun onPasswordImageClick() {
        if (state.value.passwordImageTransformation == VisualTransformation.None)
            state.value = state.value.copy(
                passwordImageTransformation = PasswordVisualTransformation(),
                passwordIconRes = R.drawable.not_see
            )
        else
            state.value = state.value.copy(
                passwordImageTransformation = VisualTransformation.None,
                passwordIconRes = R.drawable.eye
            )
    }

    override fun onEmailImageClick() {
        state.value = state.value.copy(
            email = "",
            emailIconRes = null
        )
    }

    override fun onNameImageClick() {
        state.value = state.value.copy(
            userName = "",
            nameIconRes = null
        )
    }

    companion object {
        private const val TAG = "SignInComponentImpl"
    }
}