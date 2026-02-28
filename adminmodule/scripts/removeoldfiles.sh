#!/bin/bash
cd /home/ubuntu/code/admin/adminmodule/target
rm -rf *

aws s3 ls s3://fynte-dev-admin-build-logs/ --recursive | sort | head -n -3 | cut -d " " -f 5 | while read -r line ; 
do
    echo "Removing ${line}"
    aws s3 rm s3://fynte-dev-admin-build-logs/${line}
done