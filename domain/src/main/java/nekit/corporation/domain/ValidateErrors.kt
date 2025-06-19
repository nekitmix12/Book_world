package nekit.corporation.domain

import nekit.corporation.common.Error

data object EmptyName : Error
data object EmptyPassword : Error
data object EmptyEmail : Error
data object EmptySurname : Error
data object EmptyPhone : Error
data object InvalidEmail : Error
data object ShortName : Error
data object DifferentPasswords : Error
data object InvalidPassword : Error
data object RepeatNameOrEmail : Error