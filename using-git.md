# How we use github

This repo, [Team-9013-App](https://github.com/FTC9013/Team-9013-App), is forked from the upstream FTC code base, [FtcRobotController](https://github.com/FIRST-Tech-Challenge/FtcRobotController).

As we update code locally, we push to the `master` branch.


## Staying in sync with FTC

Each season, and sometimes periodically during the season, the upstream code changes and we must stay up to date.  The easiest way to do this is from the command line.

```
git fetch upstream
git merge upstream/master
git commit
```

In rare cases, `git merge` may result in merge conflicts.  To resolve, open the offending files and decide how to fix them.  Then run `git add <file>` on each one before committing.

## Tags

At the end of each season, we'll create a Tag that represents the state of the code.  The easiest way to do that is through the github web UI.  Add a tag with the season year and name and a corresponding Release.  Try to follow the existing naming pattern.

## Old Stuff

There are a plethora of old repos under our [github account](https://github.com/FTC9013) from prior years.  Just ignore them.

