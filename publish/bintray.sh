#!/bin/bash
mkdir ~/.bintray
eval "echo \"$(< ./publish/bintray.template)\"" > ~/.bintray/.credentials