# ViewModel Rules

This guide outlines the specific rules and best practices for creating ViewModels in the Lemio iOS project. Follow these to ensure consistency and maintainability.

---

## 1. **Class Declaration**
- ViewModels must be declared as `final class`.
- Inherit from `BaseViewModel` and conform to `ViewModel` and `ObservableObject`.
- Example:
  ```swift
  final class ExampleViewModel: BaseViewModel, ViewModel, ObservableObject {
      // ...
  }
  ```

---

## 2. **Dependency Injection**
- Use `@Injected` for all use case dependencies.
- Inject only what is needed for the ViewModel.
- Example:
  ```swift
  @Injected(\.someUseCase) private var someUseCase
  ```

---

## 3. **State Management**
- Define a nested `State` struct inside the ViewModel.
- Expose state as `@Published private(set) var state: State = State()`.
- Only mutate state within the ViewModel.
- Group all UI-related properties in `State`.
- Example:
  ```swift
  struct State {
      var isLoading = true
      var errorMessage: String?
      // ...
  }
  ```

---

## 4. **Intent Handling (MVI Pattern)**
- Define an `enum Intent` listing all possible user actions.
- Implement a public `func onIntent(_ intent: Intent)` method to handle actions.
- Use a `switch` statement to map intents to private handler methods.
- Name intent cases clearly (e.g., `.onButtonTap`, `.onAppear`).
- Example:
  ```swift
  enum Intent {
      case onAppear
      case onButtonTap
      // ...
  }
  ```

---

## 5. **Private Helpers & Logic**
- Implement private methods for each intent/action.
- Use `private extension` to group related helpers (e.g., Data Loading, Observing).
- Keep logic out of the view; only the ViewModel should handle business/UI logic.
- Use `withAnimation` for state changes affecting UI animations.

---

## 6. **Data Loading**
- Implement a `loadData()` async method to fetch initial data if needed.
- Use `withTaskGroup` for parallel async loading of independent data.
- Set `state.isLoading` appropriately.
- Handle errors gracefully and update state.

---

## 7. **Observing & Subscriptions**
- Use async sequences or Combine publishers to observe data changes.
- Implement observing logic in a `private extension` (e.g., `observeSubscriptionStatus`).
- Cancel tasks in `deinit` to avoid memory leaks.

---

## 8. **Lifecycle Methods**
- Override `onAppear()` and `willEnterForeground()` as needed.
- Use these to trigger data loading or resume tasks.

---

## 9. **Async Work & Task Management**
- For async work that should be automatically cancelled when the ViewModel disappears, always use `executeTask(Task { ... })`.
- This ensures that any ongoing async operations are properly cancelled when the ViewModel is deallocated, preventing memory leaks and unwanted side effects.
- Example:
  ```swift
  executeTask(Task {
      await someAsyncOperation()
  })
  ```

---

## 10. **FlowController Usage**
- Hold a weak reference to the feature's `FlowController` for navigation.
- Use the flow controller only for navigation or presenting flows.

---

## 11. **Concurrency & Error Handling**
- Use Swift 6 strict concurrency checks (`Sendable`, actor isolation, etc.).
- Use `Task` and `await` for async operations.
- Catch and handle errors; report with `recordErrorUseCase` if available.

---

## 12. **Documentation & Comments**
- Document public methods and complex logic.
- Use clear, concise comments to explain non-obvious code.
- Do **not** modify file headers of existing files.

---

## 13. **Code Quality**
- Adhere to DRY and SOLID principles.
- Keep ViewModels focused on a single responsibility and under 200 lines.

---

*Follow these rules to ensure your ViewModels are robust, maintainable, and consistent with the Lemio project standards.* 