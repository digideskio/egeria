<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Egeria Operations

The Egeria project provides content (standards, data, code and documentation) that is intended for wide consumption
across many types of organizations - from those that rely on data in their operation - to organizations that have products
or technology designed to help manage data and its related processing.

A project of this scope requires input from a wide range of subject matter experts with different backgrounds and allegiances.
As such we need a set of principles, roles and operating practices to ensure the results of our contributions are useful,
have high quality and are widely consumable.

## General principles

The principles set the tone of the operation of Egeria:

* The activities of the project ensure open collaboration.
Through this open collaboration we aim to build a community of people who are interested in the success of the project.
* The scope of the content is determined by the individuals who are actively contributing.
* The resulting content is licensed under the Apache 2.0 license.
* An individual's privileges and position is awarded through their contribution and engagement.

These principles should be respected as the procedures used to manage the Egeria project are evolved and matured.

## What does it mean to be part of the Egeria community?

All participants in the Egeria community are bound by the ODPi's
[Code of Conduct](https://github.com/odpi/specs/wiki/ODPi-Code-of-Conduct).
There are different roles in the Egeria project.

### Egeria project members

Anyone can become a member of the Egeria community by signing up to the [mailing
list](https://lists.odpi.org/g/odpi-pmc-datagovernance).
As a member you are able to attend our meetings, just to listen, or to play an active part in the discussion.

When you attend, your name will be recorded in the meeting minutes along with any remarks or suggestions you make.
The agenda and minutes of our meetings are publicly available on the [Egeria wiki](https://github.com/odpi/egeria/wiki).

A member may make contributions to the Egeria content by submitting a
Git change (Patch or a Git Pull Request) to the Egeria maintainers.
See the [Community Guide](./Community-Guide.md).

### Egeria project maintainers

Maintainers are members of the Egeria community that have permission to change the Egeria content.
This may be content that they have created themselves, or has been provided by another member.
Maintainers also have responsibility for helping other project members with their contributions.
This includes:
* Monitoring email aliases.
* Monitoring Slack (delayed response is perfectly acceptable).
* Triage GitHub issues and perform pull request reviews for other maintainers and the community.
* Make sure that ongoing git pull requests are moving forward at the right pace or closing them.
* In general continue to be willing to spend at least 25% of one's time
working on the project (approximately 1.25 business days per week).

#### How to become a maintainer

New maintainers are voted onto the maintainers list by the existing maintainers - see
[maintainer list](./MAINTAINERS.md).

A person wishing to become a maintainer sends a note to the existing maintainers **(need dist list here)**
listing their Egeria contributions to date and requesting to be made a maintainer.
The maintainers vote and if a majority agree then the requester
is added to the maintainers list and given write access to our
[git repository](https://github.com/odpi/egeria).

#### When does a maintainer lose maintainer status

If a maintainer is no longer interested or cannot perform the maintainer duties listed above, they
should volunteer to be moved to emeritus status. In extreme cases this can also occur by a vote of
the maintainers per the voting process below.
Emeritus maintainers can rejoin the maintainer list through a vote of the
existing maintainers.

### ODPi Egeria leadership

The leadership of ODPi Egeria is grated through a vote of the Egeria maintainers.
ODPi Egeria is currently lead by Mandy Chessell.

## Egeria project meetings

Some meetings are face-to-face, but most are conference calls.  For example, there is 
a weekly call covering both the ODPi Egeria, and its sister project **ODPi Data Governance**,
every Thursday.  [Follow this link to find out more](https://github.com/odpi/data-governance/wiki).

Attendance at meetings is open to all.  Conference calls can be joined without an explicit invitation.
However, due to physical security requirements at some of the venues we use,
it is necessary to ensure you are added to the invitee list of any face-to-face meetings
that you wish to attend and complete the necessary formalities for the venue.

For example, the face-to-face meeting may be at a conference that requires you to register for the conference to attend.
Or a meeting may be at an organization's offices that are required to maintain a list of everyone on site.

Irrespective of whether a meeting is face-to-face or a web conference, all meetings are advertised in the Egeria calendar,
the agenda is published before the meeting in the [Egeria wiki](https://github.com/odpi/egeria/wiki)
and the minutes are added once the meeting is complete.

## Egeria on Slack

Egeria uses the [ODPi's Slack community](http://slack.odpi.org/) to provide an ongoing dialogue between members.
This creates a recorded discussion of design decisions and discussions that complement the project meetings.
This is the main slack channel for the Egeria project:
[https://odpi.slack.com/messages/CAZDMLTFF](https://odpi.slack.com/messages/CAZDMLTFF]).
Additional channels will be added as new workgroups and discussion topics are established.

## Egeria email

Egeria uses the [following distribution list](https://lists.odpi.org/g/odpi-project-egeria)
to advertise events and news for the community.

## Egeria content management tools

The Egeria content is managed in GitHub under [https://github.com/odpi/egeria](https://github.com/odpi/egeria).
It may be developed using patches, branches from master, or forks/git pull requests.
Each change should have a [GitHub issue](https://github.com/odpi/egeria/issues) explaining why the change is being made.
The new or updated content should follow the Egeria
[developer guidelines](https://odpi.github.io/egeria/developer-resources/Developer-Guidelines).

When new content proposed to the project, the person contributing is required to sign the contribution
to confirm it conforms to the [Developer Certificate of Origin (DCO)](https://developercertificate.org/).

## Egeria project releases

The Egeria team aim to create an official release of the open metadata and governance capability twice a year.
This release will be available to include in products and other technology through
[Maven's Central Repository](https://search.maven.org), or through a download from the ODPi site.

In between official releases, the latest build is also available to developers, through the Egeria site.

## Egeria project releases

The Egeria team aim to create an official release of the open metadata and governance capability twice a year.
This release will be available to include in products and other technology through
[Maven's Central Repository](https://search.maven.org), or through a download from the ODPi site.

In between official releases, the latest build is also available to developers, through the Egeria site.

### Release Process Overview

New releases can be created by Egeria maintainers that have the appropriate access
on [Maven Central](https://oss.sonatype.org) and are set up to sign the release with gpg2.

Creating a new release has the following stages:

* Creating a branch of master for the release code.
* Within the branch removing "-SNAPSHOT" from all of the Egeria version numbers in the pom files and committing with a pull request.
* Pushing the release to [Maven Central](https://oss.sonatype.org).
* Documenting the release with a [Git Release](https://github.com/odpi/egeria/releases).
* Incrementing the release numbers in the pom files in master and committing through a pull request.

## Conflict resolution and voting

In general, we prefer that technical issues and maintainer membership are amicably worked out
between the persons involved. If a dispute cannot be decided independently, the maintainers can be
called in to decide an issue. If the maintainers themselves cannot decide an issue, the issue will
be resolved by voting. The voting process is a simple majority in which each maintainer receives one vote.



----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.

