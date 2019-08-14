#!/bin/bash

for i in $(seq 0 9)
do
	for j in $(seq 1 $1)
	do
		rNumber=$(( RANDOM % 2000 + 4000 ))
		echo 5000 >> $1_$i.in
		echo $j $rNumber
	done
done

mkdir $1
mv *.in $1/
