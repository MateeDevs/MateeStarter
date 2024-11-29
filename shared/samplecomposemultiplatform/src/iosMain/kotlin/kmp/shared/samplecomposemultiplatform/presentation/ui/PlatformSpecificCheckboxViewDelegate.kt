package kmp.shared.samplecomposemultiplatform.presentation.ui

public interface PlatformSpecificCheckboxViewDelegate {
    public fun updateText(text: String)

    public fun updateChecked(checked: Boolean)

    public fun updateOnCheckedChanged(onCheckedChanged: (Boolean) -> Unit)
}
