package com.example.jsonreading.utils.common

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.jsonreading.ui.BiometricFragment
import java.util.concurrent.Executor

object BiometricUtils {

    private var biometricStatus: Status = Status.STARTED

    fun performBiometricAuthentication(biometricFragment: BiometricFragment): Status {
        // check biometric support
        biometricStatus = when (BiometricManager.from(biometricFragment.requireActivity())
            .canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> return showBiometricPrompt(biometricFragment)

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Status.NOT_SUPPORTED

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Status.NOT_SUPPORTED

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> Status.NO_BIOMETRICS_ENROLLED

            else -> Status.UNKNOWN
        }

        return biometricStatus
    }

    private fun showBiometricPrompt(biometricFragment: BiometricFragment): Status {

        var biometricAuthStatus = Status.STARTED

        // perform biometric authentication
        val executor: Executor = ContextCompat.getMainExecutor(biometricFragment.requireActivity())
        val biometricPrompt = BiometricPrompt(biometricFragment,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int, errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    biometricAuthStatus = Status.ERROR
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    biometricAuthStatus = Status.SUCCESS
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    biometricAuthStatus = Status.FAILURE
                }
            })

        val promptInfo: BiometricPrompt.PromptInfo =
            BiometricPrompt.PromptInfo.Builder().setTitle("Biometric test")
                .setSubtitle("Test Biometrics")
                .setAllowedAuthenticators(DEVICE_CREDENTIAL or BIOMETRIC_WEAK)
                .build()

        biometricPrompt.authenticate(promptInfo)

        return biometricAuthStatus
    }
}