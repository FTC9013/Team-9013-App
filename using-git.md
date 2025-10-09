# How we use github

This repo, [Team-9013-App](https://github.com/FTC9013/Team-9013-App), is forked from the upstream FTC code base, [FtcRobotController](https://github.com/FIRST-Tech-Challenge/FtcRobotController).

As we update code locally, we push to the `master` branch.  We never submit any changes back upstream.

We also pull code from two other upstream sources:

- [Roadrunner](https://github.com/acmerobotics/road-runner-quickstart) for odometry and motion planning
- [SparkFUN OTOS](https://github.com/jdhs-ftc/sparkfun-otos-quickstart) which is itself a fork of roadrunner, which adds support for the OTOS odometry sensor.


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
git remote add otos https://github.com/jdhs-ftc/sparkfun-otos-quickstart
```

Then to pull changes from each of them:

```
git fetch ftc
git merge ftc/master
git commit

git fetch roadrunner
git merge roadrunner/master
git commit

git fetch otos
git merge otos/master
git commit
```

In rare cases, `git merge` may result in merge conflicts.  Use Android Studio's merge tool to decide how to fix them. 

## Tags

At the end of each season, we'll create a Tag that represents the state of the code.  The easiest way to do that is through the github web UI.  Add a tag with the season year and name and a corresponding Release.  Try to follow the existing naming pattern.

## Old Stuff

There are a plethora of old repos under our [github account](https://github.com/FTC9013) from prior years.  Just ignore them.

