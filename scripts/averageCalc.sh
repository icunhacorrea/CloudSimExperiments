#!/bin/bash

file=$1
sum=0
count=0
c=0
d=0
arr=()

function abs() {
	local aux=$1
	if (( $(echo "$aux < -1" | bc -l) )); then
		aux=$(echo "$aux * -1" | bc -l)
	fi
	echo $aux
}

function calculateMedian() {
	while read LINE; do
		sum=$(( $sum + $LINE ))
		(( count++ ))
		#echo $LINE
	done < $file
	average=$( echo "scale=4;$sum / $count" | bc -l )
}

function standartDeviation() {
	local sumSquares=0
	local square=0
	local standartDeviation
	while read LINE; do
		c=$( bc <<< "$LINE - $average" )
		c=$( abs $c )
		arr=("${arr[@]}" "$c")
	done < $file

	#Sum dos quadrados das distâncias
	for i in $(seq 0 $(( ${#arr[@]} - 1 ))); do
		#echo "scale=4;${arr[i]}^2" | bc -l

		square=$( echo "scale=4;${arr[i]}^2" | bc -l )
		
		sumSquares=$( echo "scale=4;$sumSquares + $square" | bc -l )
	done
	
	standardDeviation=$( echo "scale=4; sqrt($sumSquares/$count)" | bc -l )
	echo $standardDeviation
}

function main() {
	local result=0;
	calculateMedian
	result=$( standartDeviation )

	echo "Media: $average" >> results.txt
	echo "Desvio Padrao: $result" >> results.txt
}

main
