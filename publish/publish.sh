#!/bin/bash
cd $HOME
pwd
echo 'hello $HOME'
cd $HOME/publish
git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"
git clone --quiet https://${GH_TOKEN}@github.com/timt/timt.github.com.git > /dev/null
cd $HOME