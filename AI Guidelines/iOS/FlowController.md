# FlowController Creation Guidelines

This document provides a step-by-step guide for creating new FlowControllers in the Lemio iOS project. Follow these instructions to ensure consistency, maintainability, and adherence to project architecture.

---

## 1. **Understand the FlowController Role**
- FlowControllers manage navigation and flow logic for a feature.
- They are responsible for presenting views, handling navigation, and coordinating child flows.
- FlowControllers should not contain business logic or UI code.

---

## 2. **File and Location**
- **Location:** Place the new FlowController in the appropriate feature module directory:
  - `PresentationLayer/<FeatureName>/Sources/<FeatureName>/`
- **File Name:** Name the file `<FeatureName>FlowController.swift` (e.g., `SettingsFlowController.swift`).

---

## 3. **Declare the Flow Enum**
- Define a `public enum <FeatureName>Flow: Flow, Equatable` listing all navigation cases for the feature.
- Each case represents a navigation action or sub-flow.
- Example:
  ```swift
  public enum SettingsFlow: Flow, Equatable {
      case notifications
      case about
      case back
      // ...
  }
  ```

---

## 4. **Create the FlowController Class**
- Declare the class as `public final class <FeatureName>FlowController: FlowController`.
- Inherit from the base `FlowController` (from UIToolkit).
- Use `@Injected` for dependencies (use cases, repositories, etc.).
- Hold weak references to any delegates or view models as needed.
- Example:
  ```swift
  public final class SettingsFlowController: FlowController {
      @Injected(\.triggerAppRatingUseCase) private var triggerAppRatingUseCase
      private var settingsViewModel: SettingsViewModel?
      // ...
  }
  ```

---

## 5. **Implement the `setup()` Method**
- Override `public func setup() -> UIViewController`.
- Instantiate the feature's ViewModel and View.
- Return the root view controller (usually a `BaseHostingController`).
- Example:
  ```swift
  override public func setup() -> UIViewController {
      let viewModel = SettingsViewModel(flowController: self)
      settingsViewModel = viewModel
      let view = SettingsView(viewModel: viewModel)
      return BaseHostingController(rootView: view, navbarTitle: L10n.settings_title)
  }
  ```

---

## 6. **Implement the `handleFlow(_:)` Method**
- Override `public func handleFlow(_ flow: Flow)`.
- Cast the flow to your feature's flow enum and use a `switch` statement to handle each case.
- Call private methods to perform navigation or present child flows.
- Example:
  ```swift
  override public func handleFlow(_ flow: Flow) {
      guard let settingsFlow = flow as? SettingsFlow else { return }
      switch settingsFlow {
      case .notifications: presentNotifications()
      case .about: presentAbout()
      // ...
      }
  }
  ```

---

## 7. **Navigation and Child Flows**
- Use `startChildFlow(_:)` to start and present child FlowControllers.
- Use `navigationController.present(_:animated:)` or `navigationController.pushViewController(_:animated:)` for navigation.
- Example:
  ```swift
  private func presentNotifications() {
      let fc = NotificationsFlowController(navigationController: navigationController)
      let rootVc = startChildFlow(fc)
      navigationController.present(rootVc, animated: true)
  }
  ```

---

## 8. **Deeplink and Lifecycle Handling**
- Override `handleDeeplink(type:)` if your flow needs to respond to deeplinks.
- Override `willEnterForeground()` for any foreground-specific logic.
- Always call `super` when overriding these methods.

---

## 9. **Best Practices**
- Keep FlowControllers under 200 lines and focused on navigation logic.
- Use `// MARK: - Section` comments to organize code.
- Adhere to Clean Architecture, SOLID, and DRY principles.
- Use strict concurrency checks (Swift 6).

*Follow these steps to ensure your FlowControllers are robust, maintainable, and consistent with Lemio project standards.*