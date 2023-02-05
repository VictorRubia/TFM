package com.victorrubia.tfg.helpers

import android.text.TextUtils
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

/**
 * Helper class to validate the user input
 */
class FiltersHelper {

    /**
     * Returns true if email is valid
     * @param email the email to validate
     * @return true if email is valid, false otherwise
     */
    fun validateEmail(string : String) : Boolean{
        return !TextUtils.isEmpty(string) && Patterns.EMAIL_ADDRESS.matcher(string).matches()
    }

    /**
     * Returns true if email is valid
     * @param email the email to validate
     * @return true if email is valid, false otherwise
     */
    fun validateEmailTextInput(string : TextInputEditText): Boolean{
        val valid = validateEmail(string.text.toString())
        if (!valid) string.error = "Correo no válido"
        return valid
    }

    /**
     * Returns true if password is valid
     *
     * @param string the password to validate
     * @return true if password is valid, false otherwise
     */
    fun validatePassword(string : String) : Boolean{
        return string.isNotEmpty()
    }

    /**
     * Returns true if password is valid
     *
     * @param string the password to validate
     * @return true if password is valid, false otherwise
     */
    fun validatePasswordTextInput(string: TextInputEditText): Boolean{
        val valid = validatePassword(string.text.toString())
        if(!valid) string.error = "Contraseña no válida"
        return valid
    }

}