# Use Case Creation Guidelines

This document provides a step-by-step guide for creating new use cases in the Lemio iOS project. Following these guidelines ensures consistency with the existing architecture.

---

## 1. **Create the Use Case File**

-   **Location:** Navigate to `DomainLayer/SharedDomain/Sources/SharedDomain/`. Inside this directory, find the folder corresponding to the feature you are working on (e.g., `Purchases`, `Feedback`). If a folder doesn't exist, create one. Inside the feature folder, navigate to the `UseCases` subfolder.
-   **File Name:** Create a new Swift file named `YourUseCaseName.swift` (e.g., `SetupPurchasesUseCase.swift`).
-   **Header:** Use the `./scripts/generate_header.sh "Your Name"` script to generate the correct file header.

---

## 2. **Define the Protocol and Implementation**

Both the protocol and its implementation should reside in the same file to keep related code together.

### a. Protocol Definition
-   Define a `public protocol` named `YourUseCaseName`.
-   It **must** conform to `Sendable`.
-   Annotate it with `@Spyable` to automatically generate a test mock.
-   Add Apple-style documentation explaining what the use case does, including its parameters, return values, and any errors it might throw.

### b. Implementation
-   Create a `public struct` named `YourUseCaseNameImpl` that conforms to your protocol.
-   Inject any required repositories as `private let` constants through its `init`.
-   Implement the `execute` method, which should contain the business logic, typically by calling methods on the injected repositories.

### c. Dependency Hierarchy
-   Use cases defined in `SharedDomain` **must not** import `DependencyInjection`. This module should remain independent of the dependency injection mechanism.

---

## 3. **Register for Dependency Injection**

You need to register both the real implementation for the app and its mock for testing and previews.

### a. Register the Real Implementation
-   **File:** Open `Application/DependencyInjection/Sources/DependencyInjection/UseCases.swift`.
-   **Action:** Add a new factory to the `Container` extension. This tells the app how to create your use case.
-   **Grouping:** Place it under the appropriate `// MARK: {FeatureName}` section.

### b. Register the Mock (Spy)
-   **File:** Open `Application/DependencyInjection/Sources/DependencyInjectionMocks/UseCaseMocks.swift`.
-   **Action:** Inside the `registerUseCaseMocks()` function, register the generated spy for your use case.
-   **Important:** Provide default return values for any methods in your use case to ensure previews and tests don't crash.

---

By following these steps, you create a new use case that is well-integrated into the project's architecture, testable, and easy to maintain.