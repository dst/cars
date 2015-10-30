#!/bin/bash

# It generates useful reports about code and opens it.
#
# Author: Dariusz Stefanski

# Generate reports
pushd ..
./gradlew clean check jacocoTestReport
popd

# and open them
./open-reports.sh
