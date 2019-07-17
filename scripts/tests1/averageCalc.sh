#!/bin/bash

file=$1
sum=0
count=0
arr=()

c=0
d=0

while read LINE; do
	sum=$(( $sum + $LINE ))
	(( count++ ))
	#echo $LINE
done < $file

average=$(echo 'scale=4;'"$sum / $count" | bc -l)
echo $average

# Cálculo do desvio padrão

while read LINE; do
	c=$(( $LINE - $average ))
	d=$(echo 'scale=4;'"sqrt($c)" | bc -l)
	
	echo $c $d
done


