# Commit Guidelines

When committing changes, follow this procedure:

1.  **Check Uncommitted Changes**: Before proposing a commit message, always inspect the uncommitted changes using `git status`.
2.  **Propose Commit Message**: Based on the uncommitted changes, propose a concise and descriptive commit message to the user.
    -   The commit message should start with an emoji that best fits the changes (e.g., ✨ for new features, 🐛 for bug fixes, 🧹 for refactoring/cleanup).
    -   The message should be a single line, ideally a maximum of 50 characters long.
    -   **Do not include "feat:", "fix:", "chore:", etc., in the commit message.**
3.  **Await Explicit Approval**: Wait for the user's explicit approval of the proposed commit message.
4.  **Commit Changes**: Once approval is received, commit the changes using the `git` command line, specifying the approved message.