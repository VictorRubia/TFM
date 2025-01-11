package com.victorrubia.tfm.presentation.recover_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfm.domain.usecase.RecoverPasswordUseCase

/**
 * [ViewModel] for [RecoverPasswordActivity]
 * @property recoverPasswordUseCase [RecoverPasswordUseCase]
 */
class RecoverPasswordViewModel(
    private val recoverPasswordUseCase: RecoverPasswordUseCase
) : ViewModel() {

    /**
     * [liveData] for [RecoverPasswordUseCase]
     * @return [Boolean] if request is successful or not.
     */
    fun requestPasswordReminder(email : String) = liveData {
        val confirmation = recoverPasswordUseCase.execute(email)
        emit(confirmation)
    }
}