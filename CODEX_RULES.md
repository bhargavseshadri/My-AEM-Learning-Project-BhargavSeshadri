# Codex Response Rules

Codex should follow these rules before every response in this project.

1. Do not run Maven commands unnecessarily.
   - Do not run `mvn` commands unless they are required for the specific task.
   - Do not run packaging/build commands unnecessarily.

2. Make only code-level changes unless asked otherwise.
   - Implement the requested code change or bug fix and provide it to the user.
   - Do not build or package the project by default.
   - The user will handle project builds and packaging.

3. Ask before mandatory commands or downloads.
   - If a task requires running a command, downloading dependencies, or fetching external resources, ask the user first.
   - Briefly explain why the command or download is needed.

4. Explain every change after completion.
   - After each change, bug fix, or implementation, explain what was changed.
   - Keep the explanation clear and focused on the actual work done.
