# Pull Request Guidelines

This document outlines the rules and best practices for creating pull requests in this project.

## Title

1.  **Start with an Emoji:** Every PR title must begin with a relevant emoji that categorizes the change.
    *   ✨ New feature (`✨ Add user authentication`)
    *   🐛 Bug fix (`🐛 Fix crash on startup`)
    *   🧹 Code cleanup or refactoring (`🧹 Refactor networking layer`)
    *   📚 Documentation changes (`📚 Update README.md`)
    *   ⚙️ Configuration or build changes (`⚙️ Update Xcode project settings`)
    *   🧪 Tests (`🧪 Add tests for user authentication`)

2.  **Be Concise and Descriptive:** The title should be a short, high-level summary of the changes included in the pull request.

3.  **Summarize All Commits:** If your branch has multiple commits, the PR title should summarize all the work, not just the most recent commit.

## Branching

-   **Source Branch:** Your work should be on a feature or fix branch (e.g., `feature/new-screen`, `fix/login-bug`).
-   **Base Branch:** Ensure you are targeting the correct base branch (e.g., `epic/2-0-14`, `develop`, `main`).

## Description

-   The description can be left empty for minor changes.
-   For more complex changes, provide a brief description of what was changed and why.

## Process

1.  Determine the current branch and the target base branch.
2.  Review the commit history of your branch relative to the base branch.
3.  Formulate a title that summarizes the commits and starts with an appropriate emoji.
4.  Create the pull request.

## Tooling

### GitHub CLI (`gh`)

We use the GitHub CLI to create pull requests from the command line.

-   **Installation:** If you don't have it, you can install it via Homebrew: `brew install gh`
-   **Command Path:** The command may be located at `/opt/homebrew/bin/gh`.

### Getting the Current Branch

To get the name of your current branch, use the following git command:
`git rev-parse --abbrev-ref HEAD`