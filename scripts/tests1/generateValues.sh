#!/bin/bash

for i in $(seq 0 9)
do
	for j in $(seq 0 999)
	do
		rNumber=$(( RANDOM % 9000 + 1000 ))
		echo $rNumber >> input$i.in
		echo $i $rNumber
	done
done

