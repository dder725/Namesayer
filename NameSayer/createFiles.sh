		#!/bin/bash


		if [ ! -d UserAttempts ]; then
		        dir=$(pwd)
		        mkdir UserAttempts
		        cd $dir/names
		        for x in ./*; do
		        	mkdir "${x}_attempts" && mv "${x}_attempts" $dir/UserAttempts
		        done
		fi