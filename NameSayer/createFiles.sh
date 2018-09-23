		#!/bin/bash


		if [ ! -d UserAttempts ]; then
		        dir=$(pwd)
		        mkdir UserAttempts
		        cd $dir/names
		        for x in ./*_1; do
					x="${x%_1}"
		        	mkdir "${x}_attempts" && mv "${x}_attempts" $dir/UserAttempts
		        done
		fi