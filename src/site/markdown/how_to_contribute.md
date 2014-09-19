## How to contribute

### Fetching the source code

  SensApp-Acceptance is an open source application whose source are
  available on-line, and can be retrieved from github using:

    $> git clone https://github.com/SINTEF-9012/sensapp-acceptance

### Building

  SensApp-Acceptance is a regular Maven application, which can be
  built and packaged using the 'mvn' command:

    $> mvn clean package

### Releasing

  WARNING: Please not that releasing a new version will eventually generate a new version
  of the companion site, and upload it on Github pages. This requires the user
  have public email, on its GitHub account (with the github-site-plugin in version 0.10).

  Before to create a release, one should ensure the following:

     - Read/write access to the git repository 

     - Valid credentials to connect on the "deployment" server are
       configured in .m2/settings.xml

  The configuration of the SCM maven plugin can be tested using the
  following command:

    $> mvn scm:validate

  Under Windows, one shall set up an ssh agent to avoid that the git
  command hang waiting for a passphrase. The following commands are
  necessary (run on the git-bash):

    $> eval `ssh-agent`
    $> env | grep SSH
    $> ssh-add "/c/Users/franckc/.ssh/id_rsa"
       enter passphrase : XXXXXXXX

  The Maven release plugin can be run in a "dryRun" mode using the
  option '-DdryRun=true-'. We recommend to get the 'dry run' before to
  run the 'release:prepare' command.

    $> mvn --batch-mode release:prepare -DreleaseVersion=0.1 -DdevelopmentVersion=0.2-SNAPSHOT

    $> mvn release:perform

  If anything goes wrong during the prepare or perform command, on can
  revert the info committed in the repository using the following
  commands:

    $> git reset --hard <id of the commit you want to reset to>
    $> git push origin master --force

  Tags that have been created by the release plugin will not be removed
  by the previous commands, and must remove manually, as shown
  below. The first command retrieves all the existing tags, and the
  next two remove and publish the removal respectively.

    $> git tag
    my-app-0.1
    $> git tag -d my-app-0.1
    $> git push origin :refs/tags/my-app-0.1
