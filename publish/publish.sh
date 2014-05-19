#!/bin/bash
echo -e "I got here"
cd $TRAVIS_BUILD_DIR/publish

git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"
git clone --quiet https://${GH_TOKEN}@github.com/timt/timt.github.com.git > /dev/null

cp -R releases/ timt.github.com/repo/releases/
cd timt.github.com
git add -f .
git commit -m "Lastest json lib on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to repo"
git push -fq origin master > /dev/null

echo -e "Published json lib to repo.\n"
