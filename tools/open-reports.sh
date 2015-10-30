#!/bin/bash

# Opens code reports.
#
# Author: Dariusz Stefanski

REPORTS_DIR=`pwd`/"../build/reports"
BROWSER="firefox"

for report in /jacoco/test/html/index.html /tests/index.html; do
    ${BROWSER} ${REPORTS_DIR}/${report}
done

