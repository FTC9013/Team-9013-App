# How we use github

This repo, [Team-9013-App](https://github.com/FTC9013/Team-9013-App), is forked from the upstream [Roadrunner](https://github.com/acmerobotics/road-runner-quickstart) repository, which itself is forked from the FTC code base, [FtcRobotController](https://github.com/FIRST-Tech-Challenge/FtcRobotController).

As we update code locally, we push to the `master` branch.  We never submit any changes back upstream.

## Staying in sync with FTC

Each season, and sometimes periodically during the season, the upstream code changes and we must stay up to date.  The easiest way to do this is from the command line.

First, make sure the upstream repos are set up correctly.

```
git remote -v
```

You should see the upstream repos.  If not, run:

```
git remote add ftc https://github.com/FIRST-Tech-Challenge/FtcRobotController
git remote add roadrunner https://github.com/acmerobotics/road-runner-quickstart
```

Assuming the roadrunner repo is already up to date with the latest FTC, run:

```
git fetch roadrunner
git merge roadrunner/master
git commit
```

If not, you may want to pull from FTC while waiting for Roadrunner to catch up:
```
git fetch ftc
git merge ftc/master
git commit
```

In rare cases, `git merge` may result in merge conflicts.  Use Android Studio's merge tool to decide how to fix them. 

## Tags

At the end of each season, we'll create a Tag that represents the state of the code.  The easiest way to do that is through the github web UI.  Add a tag with the season year and name and a corresponding Release.  Try to follow the existing naming pattern.

## Old Stuff

There are a plethora of old repos under our [github account](https://github.com/FTC9013) from prior years.  Just ignore them.

