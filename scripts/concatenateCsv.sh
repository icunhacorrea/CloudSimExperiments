#!/bin/bash

dir="/home/icorrea/workspace/CloudSimExperiments/outputs/1_cenario/RankBased"
dir2="/home/icorrea/workspace/CloudSimExperiments/outputs/1_cenario/CloudSim"
dir3="/home/icorrea/workspace/CloudSimExperiments/outputs/1_cenario/CapacityBased"

for i in 128 256 512 1024 2048; do
	if [ -f "$dir/$i/allCsv.csv" ]; then
		rm $dir/$i/allCsv.csv
	fi
	if [ -f "$dir2/$i/allFiles.csv" ]; then
		rm $dir2/$i/allCsv.csv
	fi
	if [ -f "$dir3/$i/allFiles.csv" ]; then
		rm $dir3/$i/allCsv.csv
	fi
done

for i in 128 256 512 1024 2048; do
	cat $dir/$i/*.csv >> $dir/$i/allCsv.csv
	cat $dir2/$i/*.csv >> $dir2/$i/allCsv.csv
	cat $dir3/$i/*.csv >> $dir3/$i/allCsv.csv
done

