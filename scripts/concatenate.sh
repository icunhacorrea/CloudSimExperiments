#!/bin/bash

dir=$1

if [ -f "$dir/allFiles.in" ]; then
	rm $dir/allFiles.in
fi

cat $dir/*.in >> $dir/allFiles.in

