#!/bin/bash

for i in $(seq 0 9)
do
	for j in $(seq 1 $1)
	do
		rNumber=$(( RANDOM % 3000 + 1000 ))
		echo $rNumber >> $1_$i.in
		echo $j $rNumber
	done
done

mkdir $1
mv *.in $1/
