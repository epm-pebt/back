# EcoBites CI workflow

This workflow uses [`semantic-release`](https://github.com/cycjimmy/semantic-release-action) for automating the versioning and release process. To ensure that the release process works correctly, it's important that all team members follow the [Conventional Commits specification](https://www.conventionalcommits.org/en/v1.0.0/) when writing commit messages.

## Commit Message Format

Each commit message should consist of a header, a body, and a footer. The header has a special format that includes a type, a scope, and a subject:

```
<type>(<scope>): <subject>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

### Types

The following commit types will trigger a new release:

- `feat`: A new feature (corresponds to MINOR in semantic versioning).
- `fix`: A bug fix (corresponds to PATCH in semantic versioning).

### Scopes

The scope is optional and represents the area of the codebase affected by the changes:

```
feat(authentication): implement OAuth 2.0
fix(database): resolve connection issue
```

### Subject

The subject contains a succinct description of the changes:

- Use the imperative, present tense: "change" not "changed" nor "changes".
- Don't capitalize the first letter.
- No dot (.) at the end.

### Body

The body is optional and provides a more detailed description of the changes.

### Footer

The footer is optional and is used to reference issue tracker IDs or include `BREAKING CHANGE:` with a description of the breaking changes.

## Examples

### Commit message with description and breaking change footer

```
feat: allow provided config object to extend other configs

BREAKING CHANGE: `extends` key in config file is now used for extending other config files
```

### Commit message with scope

```
feat(lang): add Polish language
```

### Commit message for a fix using an issue number

```
fix: correct minor typos in code

see the issue for details on the typos fixed

closes issue #12
```

## Git Commit Examples

The following examples show how to write commit messages that will trigger releases based on the default `semantic-release` rules:

### Patch Release Triggered by `fix` Type

```sh
git commit -m "fix: correct typo in README"
```

### Minor Release Triggered by `feat` Type

```sh
git commit -m "feat: add user profile page"
```

### Major Release Triggered by `BREAKING CHANGE` in Footer

```sh
git commit -m "refactor: simplify user model" -m "BREAKING CHANGE: remove support for legacy user profiles"
```

### Major Release Triggered by `!` After Type/Scope

```sh
git commit -m "feat!: drop support for Node 6"
```

### Other Types Not Triggering a Release by Default

```sh
git commit -m "chore: update build scripts"
```

## Handling Existing Version Tags

If your project already has existing version tags, `semantic-release` will use the latest tag to determine the next version to release. It's important to ensure that the existing tags follow the semantic versioning format (e.g., `v1.2.3`). If the tags do not follow this format, you may need to manually adjust them or configure `semantic-release` to recognize your existing versioning scheme.

For more details on configuring `semantic-release` for existing version tags, please refer to the [official documentation](https://github.com/semantic-release/semantic-release/blob/master/docs/usage/configuration.md#existing-version-tags).

## Additional Resources

- [Conventional Commits](https://www.conventionalcommits.org/)
- [Semantic Release](https://semantic-release.gitbook.io/)
- [Semantic Release Configuration](https://github.com/semantic-release/semantic-release/blob/master/docs/usage/configuration.md#existing-version-tags)

Please ensure that your commit messages follow this format to facilitate automatic versioning and changelog generation.