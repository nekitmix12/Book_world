package nekit.corporation.auth

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
import nekit.corporation.domain.RepeatNameOrEmail
import nekit.corporation.domain.models.RegisterRequest
import nekit.corporation.domain.usecases.RegisterUseCase
import nekit.corporation.domain.usecases.ValidateRegisterFormUseCase
import nekit.corporation.yurtify.domain.Result

@ContributesAssistedFactory(AppScope::class, SignInComponent.Factory::class)
class SignInComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted val onComplete: () -> Unit,
    private val validateRegisterFormUseCase: ValidateRegisterFormUseCase,
    private val registerUseCase: RegisterUseCase,
) : ComponentContext by componentContext, SignInComponent {
    override val state = MutableStateFlow(
        SignInState(
            email = "",
            password = "",
            inProgress = false,
            carouselImages = persistentListOf(),
            userName = "",
        )
    )

    private val coroutineScope = componentCoroutineScope()

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
                    if (it.data.errors.isEmpty())
                        registerUseCase.execute(
                            RegisterUseCase.Request(
                                RegisterRequest(
                                    email = localState.email,
                                    password = localState.password,
                                    username = localState.userName,
                                )
                            )
                        ).collect { registerResult ->
                            when (registerResult) {
                                is Result.Success -> {
                                    onComplete()
                                }

                                is Result.Error -> {}
                            }
                        }
                    else {
                        localState = state.value.copy(
                            emailError = null,
                            passwordError = null,
                            userNameError = null,
                        )

                        state.value =
                            errorCheck(localState, it.data.errors).copy(inProgress = false)
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
                    emailError = R.string.empty_password
                )

                is EmptyEmail -> localState.copy(emailError = R.string.empty_email)
                is InvalidEmail -> localState.copy(emailError = R.string.incorrect_email)
                is RepeatNameOrEmail -> localState.copy(
                    emailError = R.string.repeat_email_or_username,
                    userNameError = R.string.repeat_email_or_username
                )

                else -> localState
            }
        }
        return localState
    }

    override fun onEmailChange(email: String) {
        state.value = state.value.copy(email = email)
    }

    override fun onPasswordChange(password: String) {
        state.value = state.value.copy(password = password)
    }
}