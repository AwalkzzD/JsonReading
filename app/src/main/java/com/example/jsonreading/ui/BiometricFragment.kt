package com.example.jsonreading.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.jsonreading.databinding.FragmentBiometricBinding
import com.example.jsonreading.utils.common.BiometricUtils
import com.example.jsonreading.utils.common.Status.ERROR
import com.example.jsonreading.utils.common.Status.FAILURE
import com.example.jsonreading.utils.common.Status.NOT_SUPPORTED
import com.example.jsonreading.utils.common.Status.NO_BIOMETRICS_ENROLLED
import com.example.jsonreading.utils.common.Status.STARTED
import com.example.jsonreading.utils.common.Status.SUCCESS
import com.example.jsonreading.utils.common.Status.UNKNOWN
import java.util.concurrent.Executor

class BiometricFragment : Fragment() {

    private lateinit var binding: FragmentBiometricBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBiometricBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testBiometricButton.setOnClickListener {
            val authStatus = BiometricUtils.performBiometricAuthentication(this)
            when(authStatus){
                SUCCESS -> Toast.makeText(requireActivity(), "Authentication Success", Toast.LENGTH_SHORT).show()
                FAILURE -> Toast.makeText(requireActivity(), "Authentication Failed", Toast.LENGTH_SHORT).show()
                ERROR -> Toast.makeText(requireActivity(), "Invalid credentials", Toast.LENGTH_SHORT).show()
                UNKNOWN -> Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                NOT_SUPPORTED -> Toast.makeText(requireActivity(), "Device unsupported", Toast.LENGTH_SHORT).show()
                STARTED -> Toast.makeText(requireActivity(), "Attempting Authentication", Toast.LENGTH_SHORT).show()
                NO_BIOMETRICS_ENROLLED -> showAddBiometricPrompt()
            }
//            checkBiometricSupport()
        }
    }

    private fun checkBiometricSupport() {
        if (BiometricManager.from(requireActivity())
                .canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            showBiometricPrompt()
        } else if (BiometricManager.from(requireActivity())
                .canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                showAddBiometricPrompt()
            }
        } else if (BiometricManager.from(requireActivity())
                .canAuthenticate(DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE
        ) {

        } else if (BiometricManager.from(requireActivity())
                .canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
        ) {

        } else {
            Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showBiometricPrompt() {
        val executor: Executor = ContextCompat.getMainExecutor(requireActivity())
        val biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int, errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Invalid biometric credential: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Authentication succeeded!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Authentication failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        val promptInfo: BiometricPrompt.PromptInfo =
            BiometricPrompt.PromptInfo.Builder().setTitle("Biometric test")
                .setSubtitle("Test Biometrics").setNegativeButtonText("Use account password")
                .build()

        biometricPrompt.authenticate(promptInfo)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun showAddBiometricPrompt() {
        // Prompts the user to create credentials that your app accepts.
        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BIOMETRIC_STRONG or DEVICE_CREDENTIAL
            )
        }
        startActivity(enrollIntent)
    }
}