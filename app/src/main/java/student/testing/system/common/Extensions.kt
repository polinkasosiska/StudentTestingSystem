package student.testing.system.common

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.*
import student.testing.system.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlinx.coroutines.launch
import student.testing.system.domain.DataState

fun View.showIf(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun Fragment.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    requireActivity().window?.let {
        Snackbar.make(it.decorView, message, duration).show()
    }
}

fun Fragment.showSnackbar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    requireActivity().window?.let {
        Snackbar.make(it.decorView, message, duration).show()
    }
}

fun DialogFragment.showSnackbar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    requireDialog().window?.let {
        Snackbar.make(it.decorView, message, duration).show()
    }
}

fun DialogFragment.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    requireDialog().window?.let {
        Snackbar.make(it.decorView, message, duration).show()
    }
}

fun Fragment.confirmAction(@StringRes message: Int, listener: DialogInterface.OnClickListener) {
    MaterialAlertDialogBuilder(requireContext())
        .setMessage(message)
        .setNegativeButton(R.string.cancel) { dialog, which ->
            dialog.cancel()
        }
        .setPositiveButton(R.string.ok, listener)
        .show()
}

@SuppressLint("SimpleDateFormat")
fun String.toDate(pattern: String): Date? = SimpleDateFormat(pattern).parse(this)

@SuppressLint("SimpleDateFormat")
fun Date.formatToString(pattern: String): String? = SimpleDateFormat(pattern).format(this)

/** Fragment binding delegate, may be used since onViewCreated up to onDestroyView (inclusive) */
fun <T : ViewBinding> Fragment.viewBinding(factory: (View) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
        private var binding: T? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding ?: factory(requireView()).also {
                // if binding is accessed after Lifecycle is DESTROYED, create new instance, but don't cache it
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                    viewLifecycleOwner.lifecycle.addObserver(this)
                    binding = it
                }
            }

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }
    }

fun <T> Flow<T>.launchWhenStartedCollect(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launch {
        this@launchWhenStartedCollect.collect()
    }
}

fun <T> StateFlow<DataState<T>>.subscribeInUI(
    fragment: Fragment,
    progressBar: ProgressBar,
    listener: (T) -> Unit
) {
    this@subscribeInUI.onEach {
        progressBar.showIf(it is DataState.Loading)
        if (it is DataState.Success) {
            listener.invoke(it.data)
        } else if (it is DataState.ValidationError) {
            fragment.showSnackbar(it.messageResId)
        } else if (it is DataState.Error) {
            fragment.showSnackbar(it.exception)
        }
    }.launchWhenStartedCollect(fragment.lifecycleScope)
}

fun <T> StateFlow<DataState<T>>.subscribeInUI(
    dialogFragment: DialogFragment,
    progressBar: ProgressBar,
    listener: (T) -> Unit
) {
    this@subscribeInUI.onEach {
        progressBar.showIf(it is DataState.Loading)
        if (it is DataState.Success) {
            listener.invoke(it.data)
        } else if (it is DataState.ValidationError) {
            dialogFragment.showSnackbar(it.messageResId)
        } else if (it is DataState.Error) {
            dialogFragment.showSnackbar(it.exception)
        }
    }.launchWhenStartedCollect(dialogFragment.lifecycleScope)
}

fun Any?.trimString(): String = this@trimString.toString().trim()

fun TextInputLayout.isValidEmail(): Boolean {
    val inputLayout = this@isValidEmail
    val context = inputLayout.context
    inputLayout.editText?.doOnTextChanged { _, _, _, _ ->
        inputLayout.error = null
    }
    editText?.let {
        return if (it.text.isEmpty()) {
            inputLayout.error = context.getString(R.string.error_empty_field)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(it.text).matches()) {
            inputLayout.error = context.getString(R.string.error_invalid_email)
            false
        } else {
            inputLayout.error = null
            true
        }
    }
    return false
}

fun TextInputLayout.isNotEmpty(): Boolean {
    val inputLayout = this@isNotEmpty
    val context = inputLayout.context
    inputLayout.editText?.doOnTextChanged { _, _, _, _ ->
        inputLayout.error = null
    }
    editText?.let {
        return if (it.text.isEmpty()) {
            inputLayout.error = context.getString(R.string.error_empty_field)
            false
        } else {
            inputLayout.error = null
            true
        }
    }
    return false
}

fun EditText.isNotEmpty(): Boolean {
    val editText = this@isNotEmpty
    val context = editText.context
    return if (editText.text.isEmpty()) {
        editText.error = context.getString(R.string.error_empty_field)
        false
    } else {
        editText.error = null
        true
    }
}