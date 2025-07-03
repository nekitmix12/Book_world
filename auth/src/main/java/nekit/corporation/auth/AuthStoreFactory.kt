package nekit.corporation.auth

import android.util.Log
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.auth.AuthStore.Action
import nekit.corporation.auth.AuthStore.Intent
import nekit.corporation.auth.AuthStore.Label
import nekit.corporation.auth.AuthStore.Msg
import nekit.corporation.auth.AuthStore.State
import nekit.corporation.common.AppScope
import nekit.corporation.common.Error
import nekit.corporation.common.Result
import nekit.corporation.domain.EmptyEmail
import nekit.corporation.domain.EmptyPassword
import nekit.corporation.domain.InvalidEmail
import nekit.corporation.domain.InvalidPassword
import nekit.corporation.domain.RepeatNameOrEmail
import nekit.corporation.domain.models.auth.RefreshRequest
import nekit.corporation.domain.models.auth.RegisterRequest
import nekit.corporation.domain.usecases.ValidateRegisterFormUseCase
import nekit.corporation.domain.usecases.auth.GetTokenUseCase
import nekit.corporation.domain.usecases.auth.RegisterUseCase
import nekit.corporation.domain.usecases.auth.SaveUserUseCase

@ContributesAssistedFactory(AppScope::class, AuthStore.Factory::class)
class AuthStoreFactory @AssistedInject constructor(
    @Assisted private val storeFactory: StoreFactory,
    private val validateRegisterFormUseCase: ValidateRegisterFormUseCase,
    private val registerUseCase: RegisterUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getTokenUseCase: GetTokenUseCase,
) {
    fun create(): AuthStore =
        object : AuthStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AuthStore",
            initialState = State(carouselImages = persistentListOf()),
            bootstrapper = SimpleBootstrapper(Action.LoadRefreshToken),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeAction(action: Action) {
            when (action) {
                Action.LoadRefreshToken -> {
                    dispatch(Msg.Loading)
                    scope.launch {
                        getTokenUseCase.execute(GetTokenUseCase.Request).collect {
                            if (it is Result.Success && it.data.token != null)
                                publish(Label.SubmittedSuccessfully)
                        }
                    }
                }
            }
        }

        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.EmailChange -> dispatch(Msg.EmailChanged(intent.email))
                Intent.EmailImageClick -> dispatch(Msg.EmailChanged(""))
                is Intent.NameChange -> dispatch(Msg.UsernameChanged(intent.name))
                Intent.NameImageClick -> dispatch(Msg.UsernameChanged(""))
                is Intent.PasswordChange -> dispatch(Msg.PaswordChanged(intent.password))
                Intent.PasswordImageClick -> dispatch(Msg.PasswordTranspform)
                Intent.SignInClick -> {
                    dispatch(Msg.Confirm)

                    scope.launch {
                        var localState = state()
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
                                                saveUserUseCase.execute(
                                                    SaveUserUseCase.Request(
                                                        RefreshRequest(
                                                            localState.userName,
                                                            localState.password,
                                                        ), registerResult.data.tokenResponse.jwt
                                                    )
                                                ).collect { saveResult ->
                                                    when (saveResult) {
                                                        is Result.Success -> publish(Label.SubmittedSuccessfully)
                                                        is Result.Error -> {
                                                            Log.e(
                                                                TAG, saveResult.exception
                                                            )
                                                            publish(Label.SubmissionFailed)
                                                        }
                                                    }
                                                }


                                            }

                                            is Result.Error -> {
                                                localState = localState.copy(
                                                    emailError = null,
                                                    passwordError = null,
                                                    userNameError = null,
                                                    inProgress = false
                                                )
                                                when (registerResult.exception) {
                                                    "" -> {}
                                                }
                                                Log.e(TAG, registerResult.exception)
                                            }
                                        }
                                    }
                                } else {
                                    localState = localState.copy(
                                        emailError = null,
                                        passwordError = null,
                                        userNameError = null,
                                    )

                                    localState = errorCheck(
                                        localState, it.data.errors
                                    ).copy(inProgress = false)
                                    Log.e(TAG, localState.toString() + "\n" + it.data.errors)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.Loading -> copy(
                inProgress = true, emailError = null, passwordError = null, userNameError = null
            )

            is Msg.EmailError -> copy(emailError = msg.messageId)
            is Msg.PasswordError -> copy(passwordError = msg.messageId)
            is Msg.UsernameError -> copy(userNameError = msg.messageId)
            Msg.Confirm -> copy(inProgress = true)
            is Msg.EmailChanged -> copy(
                email = msg.email,
                emailError = null,
                emailIconRes = if (msg.email.isBlank()) null else R.drawable.close
            )

            Msg.PasswordTranspform -> copy(
                passwordImageTransformation = if (this.passwordImageTransformation == PasswordVisualTransformation()) VisualTransformation.None
                else PasswordVisualTransformation()
            )

            is Msg.PaswordChanged -> copy(
                password = msg.pasword,
                passwordIconRes = if (msg.pasword.isBlank()) R.drawable.not_see else null,
                passwordImageTransformation = PasswordVisualTransformation()
            )

            is Msg.UsernameChanged -> copy(
                userName = msg.username,
                nameIconRes = if (msg.username.isBlank()) null else R.drawable.close
            )
        }
    }


    private fun errorCheck(signInState: State, errors: List<Error>): State {
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

    companion object {
        private const val TAG = "AuthStoreFactory"
    }
}